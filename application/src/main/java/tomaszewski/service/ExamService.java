package tomaszewski.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tomaszewski.usecase.ExamUseCase;
import tomaszewski.model.ExamModel;
import tomaszewski.port.out.ExamRepositoryPort;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ExamService implements ExamUseCase {
    private final ExamRepositoryPort examRepositoryPort;
    @Override
    public List<ExamModel> getLastExams(Long userId, int limit) {
        return List.of();
    }
}
