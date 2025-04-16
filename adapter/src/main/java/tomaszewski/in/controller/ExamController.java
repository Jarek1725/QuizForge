package tomaszewski.in.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tomaszewski.usecase.ExamUseCase;
import tomaszewski.openapi.api.ExamApi;
import tomaszewski.openapi.model.ExamDTO;
import tomaszewski.security.UserSecurityDetails;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ExamController implements ExamApi {

    private final ExamUseCase examUseCase;


    @Override
    public ResponseEntity<Void> createExam(ExamDTO examDTO) {
        return ExamApi.super.createExam(examDTO);
    }

    @GetMapping("/api/exams")
    public ResponseEntity<List<ExamDTO>> getExams(@AuthenticationPrincipal UserSecurityDetails userSecurityDetails) {
        ExamDTO examDTO = new ExamDTO();
        examDTO.setId(1L);
        ResponseEntity responseEntity = ResponseEntity.ok().build();
        responseEntity = ResponseEntity.ok().body(examDTO);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<ExamDTO>> getExams() {
        return ExamApi.super.getExams();
    }


}
