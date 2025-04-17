package tomaszewski.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tomaszewski.in.mapper.ExamMapper;
import tomaszewski.model.ExamModel;
import tomaszewski.out.entities.ExamEntity;
import tomaszewski.port.out.ExamRepositoryPort;
import tomaszewski.out.repositories.JpaExamRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ExamPersistenceAdapter implements ExamRepositoryPort {
    private final JpaExamRepository jpaExamRepository;
    private final ExamMapper examMapper;

    @Override
    public void save(ExamModel examModel) {
        ExamEntity examEntity = examMapper.toExamEntity(examModel);
        jpaExamRepository.save(examEntity);
    }
}
