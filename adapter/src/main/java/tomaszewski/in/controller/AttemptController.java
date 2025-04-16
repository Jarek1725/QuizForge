package tomaszewski.in.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tomaszewski.model.AttemptModel;
import tomaszewski.out.mapper.AttemptMapper;
import tomaszewski.security.UserSecurityDetails;
import tomaszewski.usecase.AttemptUseCase;
import tomaszewski.openapi.api.AttemptApi;
import tomaszewski.openapi.model.AttemptDTO;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AttemptController implements AttemptApi {

    private final AttemptUseCase attemptUseCase;
    private final AttemptMapper attemptMapper;

//    @Override
//    @GetMapping("/users/{userId}/attempts")
//    public ResponseEntity<List<AttemptDTO>> getUserLastAttempts(Long userId, Integer limit) {
//        List<AttemptModel> lastAttempts = attemptUseCase.getLastAttempts(userId, limit);
//        return ResponseEntity.ok(lastAttempts.stream().map(attemptMapper::toAttemptDTO).toList());
//    }


    @Override
    public ResponseEntity<List<AttemptDTO>> getUserLastAttempts(Long userId, Integer limit, UserSecurityDetails userSecurityDetails) {
        return AttemptApi.super.getUserLastAttempts(userId, limit, userSecurityDetails);
    }
}
