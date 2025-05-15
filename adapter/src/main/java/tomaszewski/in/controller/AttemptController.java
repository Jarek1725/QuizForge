package tomaszewski.in.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tomaszewski.mapper.AttemptMapper;
import tomaszewski.mapper.SelectedAnswerMapper;
import tomaszewski.model.AttemptModel;
import tomaszewski.model.StartAttemptModel;
import tomaszewski.model.UserSelectedAnswers;
import tomaszewski.openapi.api.AttemptApi;
import tomaszewski.openapi.model.*;
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
    public ResponseEntity<List<AttemptSummaryDTO>> getUserLastAttempts(Long userId, Integer limit) {
        List<AttemptModel> lastAttempts = attemptUseCase.getLastAttempts(userId, limit);
        return ResponseEntity.ok(attemptMapper.toAttemptSummaryDTOs(lastAttempts));
    }

    @Override
    public ResponseEntity<Void> submitAttempt(SubmitAttemptDTO submitAttemptDTO, UserSecurityDetails userSecurityDetails) {
        UserSelectedAnswers userAnswerModels = selectedAnswerMapper
                .toUserSelectedAnswers(userSecurityDetails.getId(), submitAttemptDTO.getAttemptId(), submitAttemptDTO.getAnswers());
        attemptUseCase.submitAttempt(userAnswerModels);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<StartAttemptResponseDTO> startAttempt(StartAttemptDTO startAttemptDTO, UserSecurityDetails userSecurityDetails) {
        StartAttemptModel startAttemptModel = attemptMapper.toStartAttemptModel(startAttemptDTO, userSecurityDetails.getId());
        StartAttemptModel startAttempt = attemptUseCase.startAttempt(startAttemptModel);
        return ResponseEntity.ok(attemptMapper.toStartAttemptResponseDTO(startAttempt));
    }

    @Override
    public ResponseEntity<AttemptSummaryDTO> getAttemptById(Long attemptId) {
        AttemptModel attemptModel = attemptUseCase.getAttemptById(attemptId);
        AttemptSummaryDTO attemptDTO = attemptMapper.toAttemptSummaryDTO(attemptModel);
        return ResponseEntity.ok(attemptDTO);
    }
}
