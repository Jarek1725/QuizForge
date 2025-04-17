package tomaszewski.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tomaszewski.model.CategoryModel;
import tomaszewski.model.ExamModel;
import tomaszewski.model.UniversityModel;
import tomaszewski.port.out.CategoryRepositoryPort;
import tomaszewski.port.out.ExamRepositoryPort;
import tomaszewski.port.out.UniversityRepositoryPort;
import tomaszewski.usecase.ExamUseCase;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ExamUseCaseImpl implements ExamUseCase {
    private final ExamRepositoryPort examRepositoryPort;
    private final CategoryRepositoryPort categoryRepositoryPort;
    private final UniversityRepositoryPort universityRepositoryPort;

    @Override
    public void createExam(ExamModel examModel, Long userId) {
        List<CategoryModel> categories = examModel.categories().stream()
                .map(cat -> categoryRepositoryPort.getOrCreateByName(cat.name()))
                .toList();

        UniversityModel university = universityRepositoryPort.getOrCreateByName(examModel.university().name());

        ExamModel finalExamModel = new ExamModel(
                null,
                examModel.name(),
                null,
                university,
                categories
        );

        examRepositoryPort.save(finalExamModel);
    }
}
