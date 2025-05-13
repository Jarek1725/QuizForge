package tomaszewski.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tomaszewski.model.AttemptModel;
import tomaszewski.model.SelectedOptionModel;
import tomaszewski.model.StartAttemptModel;
import tomaszewski.openapi.model.*;
import tomaszewski.out.entities.AttemptEntity;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {SecurityUserMapper.class, UserAnswerMapper.class, ExamMapper.class})
public interface AttemptMapper {
    @Mapping(target = "exam.questions", ignore = true)
    @Mapping(target = "userAnswerModels", source = "userAnswers")
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

    @Mapping(target = "userAnswerDetails", source = "userAnswerModels")
    AttemptSummaryDTO toAttemptSummaryDTO(AttemptModel attemptModel);

    default List<Long> map(List<SelectedOptionModel> selectedOptions) {
        if (selectedOptions == null) {
            return null;
        }
        return selectedOptions.stream()
                .map(SelectedOptionModel::getAnswerOptionId)
                .collect(Collectors.toList());
    }
}
