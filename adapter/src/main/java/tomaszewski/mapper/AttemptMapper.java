package tomaszewski.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tomaszewski.model.AttemptModel;
import tomaszewski.model.StartAttemptModel;
import tomaszewski.openapi.model.AttemptDTO;
import tomaszewski.openapi.model.StartAttemptDTO;
import tomaszewski.openapi.model.StartAttemptResponseDTO;
import tomaszewski.openapi.model.SubmitAttemptDTO;
import tomaszewski.out.entities.AttemptEntity;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Mapper(componentModel = "spring", uses = {SecurityUserMapper.class, UserAnswerMapper.class})
public interface AttemptMapper {
    @Mapping(target = "exam.questions", ignore = true)
    AttemptModel toAttemptModel(AttemptEntity attemptEntity);

    AttemptDTO toAttemptDTO(AttemptModel attemptModel);
    List<AttemptDTO> toAttemptDTOs(List<AttemptModel> attemptModel);

    default OffsetDateTime map(LocalDateTime value) {
        return value != null ? value.atOffset(ZoneOffset.UTC) : null;
    }

    @Mapping(target = "userId", source = "userId")
    StartAttemptModel toStartAttemptModel(StartAttemptDTO dto, Long userId);

    @Mapping(target = "userAnswers", source = "userAnswerModels")
    AttemptEntity toAttemptEntity(AttemptModel attemptModel);

    StartAttemptResponseDTO toStartAttemptResponseDTO(StartAttemptModel startAttempt);

}
