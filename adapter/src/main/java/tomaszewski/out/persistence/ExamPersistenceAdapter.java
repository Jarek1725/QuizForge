package tomaszewski.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tomaszewski.mapper.ExamMapper;
import tomaszewski.model.ExamModel;
import tomaszewski.out.entities.ExamEntity;
import tomaszewski.port.out.ExamRepositoryPort;
import tomaszewski.out.repositories.JpaExamRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ExamPersistenceAdapter implements ExamRepositoryPort {
    private final JpaExamRepository jpaExamRepository;
    private final ExamMapper examMapper;

    @Override
    public ExamModel save(ExamModel examModel) {
        ExamEntity examEntity = examMapper.toExamEntity(examModel);
        return examMapper.toExamModel(jpaExamRepository.save(examEntity));
    }

    @Override
    public List<ExamModel> findExamsWithFilters(String category, String university, int effectiveLimit) {
        return jpaExamRepository.findExamsWithFilters(category, university, effectiveLimit)
                .stream()
                .limit(effectiveLimit)
                .map(examMapper::toExamModel)
                .toList();
    }

    @Override
    public Optional<ExamModel> findExamById(Long examId) {
        return jpaExamRepository.findById(examId)
                .map(examMapper::toExamModel);
    }
}
