package tomaszewski.in.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tomaszewski.mapper.ExamMapper;
import tomaszewski.model.ExamModel;
import tomaszewski.openapi.model.CreateExamDTO;
import tomaszewski.openapi.model.ExamDTO;
import tomaszewski.openapi.model.ExamDetailsDTO;
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
    public ResponseEntity<Long> createExam(CreateExamDTO createExamDTO, UserSecurityDetails userSecurityDetails) {
        ExamModel examModel = examMapper.toExamModel(createExamDTO);
        Long examId = examUseCase.createExam(examModel, userSecurityDetails.getId());
        return ResponseEntity.ok(examId);
    }


    @Override
    public ResponseEntity<List<ExamDTO>> getExams(String name, String category, String university, Integer limit) {
        List<ExamModel> examsModels = examUseCase.getExams(name, category, university, limit);
        List<ExamDTO> exams = examsModels.stream()
                .map(examMapper::toExamDTO)
                .toList();
        return ResponseEntity.ok(exams);    }

    @Override
    public ResponseEntity<ExamDetailsDTO> getExamById(Long examId) {
        ExamModel examById = examUseCase.getExamById(examId);
        ExamDetailsDTO examDTO = examMapper.toExamDetailsDTO(examById);
        return ResponseEntity.ok(examDTO);
    }
}
