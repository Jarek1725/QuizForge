package tomaszewski.in.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tomaszewski.in.AttemptUserCase;
import tomaszewski.openapi.api.AttemptApi;
import tomaszewski.openapi.model.AttemptDTO;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AttemptController implements AttemptApi {

    private final AttemptUserCase attemptUserCase;

    @Override
    public ResponseEntity<List<AttemptDTO>> getUserLastAttempts(Long userId, Integer limit) {
        return AttemptApi.super.getUserLastAttempts(userId, limit);
    }
}
