package tomaszewski.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import tomaszewski.openapi.api.ExamApi;
import tomaszewski.openapi.model.ExamDTO;

import java.util.List;
import java.util.Optional;

@RestController
public class ExamController implements ExamApi {
    @Override
    public Optional<NativeWebRequest> getRequest() {
        return ExamApi.super.getRequest();
    }

    @Override
    public ResponseEntity<Void> createExam(ExamDTO examDTO) {
        return ExamApi.super.createExam(examDTO);
    }

    @GetMapping("/api/exams")
    @Override
    public ResponseEntity<List<ExamDTO>> getExams() {
        System.out.println("BIORE EXAMY");
        ExamDTO examDTO = new ExamDTO();
        examDTO.setId(1L);
        ResponseEntity responseEntity = ResponseEntity.ok().build();
        responseEntity = ResponseEntity.ok().body(examDTO);
        return responseEntity;
    }
}
