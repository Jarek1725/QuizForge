package tomaszewski.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tomaszewski.model.*;
import tomaszewski.port.out.*;
import tomaszewski.usecase.AttemptUseCase;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttemptUseCaseImpl implements AttemptUseCase {
    private final AttemptRepositoryPort attemptRepositoryPort;
    private final ExamRepositoryPort examRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;
    private final QuestionRepositoryPort questionRepositoryPort;
    private final UserAnswerRepositoryPort userAnswerRepositoryPort;

    private static final int UNGRADED_ATTEMPT_SCORE = -1;

    @Override
    public List<AttemptModel> getLastAttempts(Long userId) {
        return attemptRepositoryPort.findLastAttemptsByUser(userId);
    }

    @Override
    public void submitAttempt(UserSelectedAnswers userSelectedAnswers, boolean isExam) {
        Optional<AttemptModel> optionalAttemptModel = attemptRepositoryPort.findAttemptById(userSelectedAnswers.attemptId());
        if (optionalAttemptModel.isEmpty()) {
            throw new IllegalArgumentException("Attempt not found");
        }
        AttemptModel attemptModel = optionalAttemptModel.get();
        if (attemptModel.getScore() != -1) {
            throw new IllegalStateException("Attempt already submitted");
        }
        List<QuestionModel> allQuestionsByAttemptId = questionRepositoryPort.findAllQuestionsByAttemptId(userSelectedAnswers.attemptId());
        int score = 0;
        int maxScore = 0;
        List<Long> userAnswersIds = new ArrayList<>();
        for (QuestionModel questionModel : allQuestionsByAttemptId) {
            List<Long> correctAnswers = new ArrayList<>();
            List<Long> userAnswers = new ArrayList<>();
            for (AnswerModel answer : questionModel.answers()) {
                if (answer.isCorrect()) {
                    correctAnswers.add(answer.id());
                }
                for (Long answerId : userSelectedAnswers.answerIds()) {
                    if (answer.id().equals(answerId)) {
                        userAnswers.add(answer.id());
                    }
                }
            }
            if (correctAnswers.containsAll(userAnswers) && userAnswers.containsAll(correctAnswers)) {
                score += questionModel.score();
            }
            userAnswersIds.addAll(userAnswers);
        }

        attemptModel.setScore(score);
        List<UserAnswersModel> userAnswersModels = userAnswerRepositoryPort.findAllByAttemptId(userSelectedAnswers.attemptId());
        for (UserAnswersModel userAnswersModel : userAnswersModels) {
            List<SelectedOptionModel> selectedOptionModels = new ArrayList<>();
            for (AnswerModel answer : userAnswersModel.getQuestion().answers()) {
                if (userAnswersIds.contains(answer.id())) {
                    selectedOptionModels.add(new SelectedOptionModel(
                            userAnswersModel.getId(),
                            answer.id()
                    ));
                }
            }
            maxScore += userAnswersModel.getQuestion().score();
            userAnswersModel.setSelectedOptions(selectedOptionModels);
        }

        attemptModel.setMaxScore(maxScore);

        attemptRepositoryPort.save(attemptModel, isExam);
        userAnswerRepositoryPort.saveAll(userAnswersModels);
    }

    @Override
    @Transactional
    public StartAttemptModel startAttempt(StartAttemptModel startAttemptModel) {
        ExamModel exam = getExamOrThrow(startAttemptModel.examId());
        UserModel user = getUserOrThrow(startAttemptModel.userId());

        List<QuestionModel> randomQuestions = getRandomQuestionsForExam(startAttemptModel.examId(), startAttemptModel.questionCount());

        AttemptModel attemptModel = createAndSaveAttempt(user, exam, true);

        createAndSaveUserAnswers(attemptModel, randomQuestions);

        return new StartAttemptModel(
                attemptModel.getUser().getId(),
                attemptModel.getExam().id(),
                attemptModel.getId(),
                (long) randomQuestions.size(),
                randomQuestions
        );
    }

    @Override
    public StartAttemptModel startReview(StartAttemptModel startAttemptModel) {
        ExamModel exam = getExamOrThrow(startAttemptModel.examId());
        UserModel user = getUserOrThrow(startAttemptModel.userId());

        List<QuestionModel> randomQuestions = questionRepositoryPort.getRandomQuestionsForReview(
                startAttemptModel.examId(),
                startAttemptModel.questionCount(),
                user.getId()
        );

        AttemptModel attemptModel = createAndSaveAttempt(user, exam, false);

        createAndSaveUserAnswers(attemptModel, randomQuestions);

        return new StartAttemptModel(
                attemptModel.getUser().getId(),
                attemptModel.getExam().id(),
                attemptModel.getId(),
                (long) randomQuestions.size(),
                randomQuestions
        );
    }

    private ExamModel getExamOrThrow(Long examId) {
        return examRepositoryPort.findExamById(examId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid exam ID"));
    }

    private UserModel getUserOrThrow(Long userId) {
        return userRepositoryPort.findUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
    }

    private List<QuestionModel> getRandomQuestionsForExam(Long examId, Long questionCount) {
        List<QuestionModel> questions = questionRepositoryPort.getRandomQuestions(questionCount, examId);
        if (questions.isEmpty()) {
            throw new IllegalStateException("No questions available");
        }
        return questions;
    }

    private AttemptModel createAndSaveAttempt(UserModel user, ExamModel exam, boolean isExam) {
        AttemptModel attemptToSave = new AttemptModel(
                null,
                user,
                exam,
                LocalDateTime.now(),
                UNGRADED_ATTEMPT_SCORE,
                false,
                new ArrayList<>()
        );
        return attemptRepositoryPort.save(attemptToSave, isExam);
    }

    private void createAndSaveUserAnswers(AttemptModel attemptModel, List<QuestionModel> questions) {
        List<UserAnswersModel> userAnswersModels = questions.stream()
                .map(question -> new UserAnswersModel(null, null, attemptModel, question))
                .collect(Collectors.toList());

        userAnswerRepositoryPort.saveAll(userAnswersModels);
    }

    @Override
    public AttemptModel getAttemptById(Long attemptId) {
        return attemptRepositoryPort.findAttemptById(attemptId)
                .orElseThrow(() -> new IllegalArgumentException("Attempt not found with ID: " + attemptId));
    }

    @Override
    public ProgressDataModel getAttemptProgressData(Long id) {
        List<AttemptModel> attempts = attemptRepositoryPort.findLastAttemptsByUserAndScoreMoreThan0(id);
        Map<Long, List<AttemptModel>> groupedByExam = attempts.stream()
                .collect(Collectors.groupingBy(
                        a -> a.getExam().id(),
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> list.stream()
                                        .sorted(Comparator.comparing(AttemptModel::getStartTime))
                                        .collect(Collectors.toList())
                        )
                ));

        Map<Integer, List<Double>> percentByAttemptIndex = new HashMap<>();

        for (List<AttemptModel> examAttempts : groupedByExam.values()) {
            for (int i = 0; i < examAttempts.size(); i++) {
                AttemptModel attempt = examAttempts.get(i);
                Long maxPoints = maxPointsForAttempt(attempt);
                if (maxPoints != null && maxPoints > 0 && attempt.getScore() != null) {
                    double percent = (attempt.getScore() * 100.0) / maxPoints;
                    percentByAttemptIndex
                            .computeIfAbsent(i, k -> new ArrayList<>())
                            .add(percent);
                }
            }
        }

        Map<Integer, Double> averagePercentByIndex = percentByAttemptIndex.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().stream()
                                .mapToDouble(Double::doubleValue)
                                .average()
                                .orElse(0.0)
                ));

        ProgressDataModel progressDataModel = new ProgressDataModel();
        for (Map.Entry<Integer, Double> entry : averagePercentByIndex.entrySet()) {
            double averagePercent = entry.getValue();
            double roundedPercent = Math.round(averagePercent);

            List<Double> progressData = progressDataModel.getProgressData();
            if (progressData == null) {
                progressData = new ArrayList<>();
                progressDataModel.setProgressData(progressData);
            }
            progressData.add(roundedPercent);
        }


        return progressDataModel;
    }

    private Long maxPointsForAttempt(AttemptModel attemptModel) {
        List<Long> list = attemptModel.getUserAnswerModels().stream().map(e -> e.getQuestion().score()).toList();
        return (Long) (long) list.stream().mapToInt(Long::intValue).sum();
    }
}