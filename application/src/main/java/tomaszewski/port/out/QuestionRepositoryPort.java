package tomaszewski.port.out;

import tomaszewski.model.QuestionModel;

import java.util.List;

public interface QuestionRepositoryPort {
    QuestionModel createQuestion(QuestionModel questionModel, Long examId);
    List<QuestionModel> createQuestions(List<QuestionModel> questionModel, Long examId);
    List<QuestionModel> getRandomQuestions(Long limit, Long examId);
    List<QuestionModel> findAllQuestionsByAttemptId(Long attemptId);

    List<QuestionModel> getRandomQuestionsForReview(Long examId, Long questionLimit, Long userId);
}
