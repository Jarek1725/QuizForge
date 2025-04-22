package tomaszewski.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tomaszewski.model.*;
import tomaszewski.port.out.*;
import tomaszewski.usecase.ExamUseCase;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class ExamUseCaseImpl implements ExamUseCase {
    private final ExamRepositoryPort examRepositoryPort;
    private final CategoryRepositoryPort categoryRepositoryPort;
    private final UniversityRepositoryPort universityRepositoryPort;
    private final QuestionRepositoryPort questionRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;

    @Override
    public Long createExam(ExamModel examModel, Long userId) {
        if (examModel == null || userId == null) {
            throw new IllegalArgumentException("Model egzaminu i ID użytkownika nie mogą być null");
        }

        UserModel creator = findUserById(userId);
        List<CategoryModel> categories = fetchCategories(examModel);
        UniversityModel university = fetchUniversity(examModel);

        ExamModel finalExamModel = buildExamModel(examModel, creator, categories, university);
        ExamModel savedExam = examRepositoryPort.save(finalExamModel);

        questionRepositoryPort.createQuestions(examModel.questions(), savedExam.id());

        return savedExam.id();
    }

    @Override
    public List<ExamModel> getExams(String category, String university, Integer limit) {
        int effectiveLimit = (limit != null && limit > 0) ? limit : 10;

        return examRepositoryPort.findExamsWithFilters(category, university, effectiveLimit);
    }

    @Override
    public ExamModel getExamById(Long examId) {
        if (examId == null) {
            throw new IllegalArgumentException("ID egzaminu nie może być null");
        }

        return examRepositoryPort.findExamById(examId)
                .orElseThrow(() -> new EntityNotFoundException("Nie znaleziono egzaminu o ID: " + examId));
    }

    private UserModel findUserById(Long userId) {
        return userRepositoryPort.findUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Nie znaleziono użytkownika o ID: " + userId));
    }

    private List<CategoryModel> fetchCategories(ExamModel examModel) {
        return examModel.categories().stream()
                .map(cat -> categoryRepositoryPort.getOrCreateByName(cat.name()))
                .toList();
    }

    private UniversityModel fetchUniversity(ExamModel examModel) {
        return universityRepositoryPort.getOrCreateByName(examModel.university().name());
    }

    private ExamModel buildExamModel(ExamModel examModel, UserModel creator,
                                     List<CategoryModel> categories, UniversityModel university) {
        return new ExamModel(
                null,
                examModel.name(),
                null,
                university,
                categories,
                creator,
                examModel.percentageToPass(),
                examModel.questionsPerExam(),
                examModel.timeLimit()
        );
    }
}