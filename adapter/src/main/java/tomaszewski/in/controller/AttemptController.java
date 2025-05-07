package tomaszewski.in.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tomaszewski.mapper.AttemptMapper;
import tomaszewski.mapper.SelectedAnswerMapper;
import tomaszewski.model.AttemptModel;
import tomaszewski.model.StartAttemptModel;
import tomaszewski.model.UserAnswersModel;
import tomaszewski.openapi.api.AttemptApi;
import tomaszewski.openapi.model.AttemptDTO;
import tomaszewski.openapi.model.StartAttemptDTO;
import tomaszewski.openapi.model.StartAttemptResponseDTO;
import tomaszewski.openapi.model.SubmitAttemptDTO;
import tomaszewski.security.UserSecurityDetails;
import tomaszewski.usecase.AttemptUseCase;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AttemptController implements AttemptApi {

    private final AttemptUseCase attemptUseCase;
    private final AttemptMapper attemptMapper;
    private final SelectedAnswerMapper selectedAnswerMapper;

    @Override
    public ResponseEntity<List<AttemptDTO>> getUserLastAttempts(Long userId, Integer limit) {
        List<AttemptModel> lastAttempts = attemptUseCase.getLastAttempts(userId, limit);
        return ResponseEntity.ok(attemptMapper.toAttemptDTOs(lastAttempts));
    }

    @Override
    public ResponseEntity<Void> submitAttempt(SubmitAttemptDTO submitAttemptDTO, UserSecurityDetails userSecurityDetails) {
        List<UserAnswersModel> userAnswerModels = selectedAnswerMapper.toUserSelectedAnswers(userSecurityDetails.getId(), submitAttemptDTO.getAnswers());
        attemptUseCase.submitAttempt(userAnswerModels, submitAttemptDTO.getAttemptId());
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<StartAttemptResponseDTO> startAttempt(StartAttemptDTO startAttemptDTO, UserSecurityDetails userSecurityDetails) {
        StartAttemptModel startAttemptModel = attemptMapper.toStartAttemptModel(startAttemptDTO, userSecurityDetails.getId());
        StartAttemptModel startAttempt = attemptUseCase.startAttempt(startAttemptModel);
        return ResponseEntity.ok(attemptMapper.toStartAttemptResponseDTO(startAttempt));
    }

}
