package tomaszewski.in.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tomaszewski.mapper.AttemptMapper;
import tomaszewski.mapper.SelectedAnswerMapper;
import tomaszewski.model.StartAttemptModel;
import tomaszewski.model.UserSelectedAnswers;
import tomaszewski.openapi.api.ReviewApi;
import tomaszewski.openapi.model.StartAttemptDTO;
import tomaszewski.openapi.model.StartAttemptResponseDTO;
import tomaszewski.openapi.model.SubmitAttemptDTO;
import tomaszewski.security.UserSecurityDetails;
import tomaszewski.usecase.AttemptUseCase;

@RestController
@RequiredArgsConstructor
public class ReviewController implements ReviewApi {

    private final AttemptUseCase attemptUseCase;
    private final AttemptMapper attemptMapper;
    private final SelectedAnswerMapper selectedAnswerMapper;

    @Override
    public ResponseEntity<StartAttemptResponseDTO> startReview(StartAttemptDTO startAttemptDTO, UserSecurityDetails userSecurityDetails) {
        StartAttemptModel startAttemptModel = attemptMapper.toStartAttemptModel(startAttemptDTO, userSecurityDetails.getId());
        StartAttemptModel startAttempt = attemptUseCase.startReview(startAttemptModel);
        return ResponseEntity.ok(attemptMapper.toStartAttemptResponseDTO(startAttempt));
    }

    @Override
    public ResponseEntity<Void> submitAttempt(SubmitAttemptDTO submitAttemptDTO, UserSecurityDetails userSecurityDetails) {
        UserSelectedAnswers userAnswerModels = selectedAnswerMapper
                .toUserSelectedAnswers(userSecurityDetails.getId(), submitAttemptDTO.getAttemptId(), submitAttemptDTO.getAnswers());
        attemptUseCase.submitAttempt(userAnswerModels, false);
        return ResponseEntity.ok().build();
    }
}
