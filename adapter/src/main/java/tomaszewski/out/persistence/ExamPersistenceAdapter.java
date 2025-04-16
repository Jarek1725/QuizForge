package tomaszewski.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tomaszewski.model.ExamModel;
import tomaszewski.port.out.ExamRepositoryPort;
import tomaszewski.out.repositories.JpaExamRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ExamPersistenceAdapter implements ExamRepositoryPort {
    private final JpaExamRepository jpaExamRepository;
    @Override
    public List<ExamModel> findLastExamsByUser(Long userId, int limit) {
        return List.of();
    }
}
