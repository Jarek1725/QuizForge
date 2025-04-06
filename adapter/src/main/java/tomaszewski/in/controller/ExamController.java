package tomaszewski.in.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tomaszewski.openapi.api.ExamApi;
import tomaszewski.openapi.model.ExamDTO;
import tomaszewski.security.UserSecurityDetails;

import java.util.List;

@RestController
public class ExamController implements ExamApi {
    @Override
    public ResponseEntity<Void> createExam(ExamDTO examDTO) {
        return ExamApi.super.createExam(examDTO);
    }

    @GetMapping("/api/exams")
    public ResponseEntity<List<ExamDTO>> getExams(@AuthenticationPrincipal UserSecurityDetails userSecurityDetails) {
        System.out.println("BIORE EXAMY");
        ExamDTO examDTO = new ExamDTO();
        examDTO.setId(1L);
        ResponseEntity responseEntity = ResponseEntity.ok().build();
        responseEntity = ResponseEntity.ok().body(examDTO);
        return responseEntity;
    }
}
