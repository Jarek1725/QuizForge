package tomaszewski.in.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tomaszewski.mapper.ExamMapper;
import tomaszewski.model.ExamModel;
import tomaszewski.openapi.model.CreateExamDTO;
import tomaszewski.openapi.model.ExamDTO;
import tomaszewski.usecase.ExamUseCase;
import tomaszewski.openapi.api.ExamApi;
import tomaszewski.security.UserSecurityDetails;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ExamController implements ExamApi {

    private final ExamUseCase examUseCase;
    private final ExamMapper examMapper;

    @Override
    public ResponseEntity<Void> createExam(CreateExamDTO createExamDTO, UserSecurityDetails userSecurityDetails) {
        ExamModel examModel = examMapper.toExamModel(createExamDTO);
        examUseCase.createExam(examModel, userSecurityDetails.getId());
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<ExamDTO>> getExams(String category, String university, Integer limit) {
        List<ExamModel> examsModels = examUseCase.getExams(category, university, limit);
        List<ExamDTO> exams = examsModels.stream()
                .map(examMapper::toExamDTO)
                .toList();
        return ResponseEntity.ok(exams);
    }
}
