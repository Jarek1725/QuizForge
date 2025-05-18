package tomaszewski.in.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tomaszewski.mapper.AttemptMapper;
import tomaszewski.mapper.SelectedAnswerMapper;
import tomaszewski.model.AttemptModel;
import tomaszewski.model.ProgressDataModel;
import tomaszewski.model.StartAttemptModel;
import tomaszewski.model.UserSelectedAnswers;
import tomaszewski.openapi.api.AttemptApi;
import tomaszewski.openapi.model.*;
import tomaszewski.security.UserSecurityDetails;
import tomaszewski.usecase.AttemptUseCase;

import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
public class AttemptController implements AttemptApi {

    private final AttemptUseCase attemptUseCase;
    private final AttemptMapper attemptMapper;
    private final SelectedAnswerMapper selectedAnswerMapper;

    @Override
    public ResponseEntity<List<AttemptSummaryDTO>> getUserLastAttempts(UserSecurityDetails userSecurityDetails) {
        List<AttemptModel> lastAttempts = attemptUseCase.getLastAttempts(userSecurityDetails.getId());
        return ResponseEntity.ok(attemptMapper.toAttemptSummaryDTOs(lastAttempts));
    }

    @Override
    public ResponseEntity<Void> submitAttempt(SubmitAttemptDTO submitAttemptDTO, UserSecurityDetails userSecurityDetails) {
        UserSelectedAnswers userAnswerModels = selectedAnswerMapper
                .toUserSelectedAnswers(userSecurityDetails.getId(), submitAttemptDTO.getAttemptId(), submitAttemptDTO.getAnswers());
        attemptUseCase.submitAttempt(userAnswerModels, true);
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

    @Override
    public ResponseEntity<ProgressDataDTO> getAttemptProgressData(UserSecurityDetails userSecurityDetails) {
        ProgressDataModel progressDataModel = attemptUseCase.getAttemptProgressData(userSecurityDetails.getId());
        ProgressDataDTO progressDataDTO = attemptMapper.toProgressDataDTO(progressDataModel);
        return ResponseEntity.ok(progressDataDTO);
    }


}
