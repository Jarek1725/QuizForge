package tomaszewski.in.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tomaszewski.model.AttemptModel;
import tomaszewski.openapi.api.AttemptApi;
import tomaszewski.openapi.model.AttemptDTO;
import tomaszewski.out.mapper.AttemptMapper;
import tomaszewski.usecase.AttemptUseCase;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AttemptController implements AttemptApi {

    private final AttemptUseCase attemptUseCase;
    private final AttemptMapper attemptMapper;

    @Override
    public ResponseEntity<List<AttemptDTO>> getUserLastAttempts(Long userId, Integer limit) {
        List<AttemptModel> lastAttempts = attemptUseCase.getLastAttempts(userId, limit);
        return ResponseEntity.ok(attemptMapper.toAttemptDTOs(lastAttempts));
    }
}
