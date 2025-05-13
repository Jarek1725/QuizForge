package tomaszewski.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import tomaszewski.model.*;
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

    List<UserAnswerDetails> mapUserAnswerModels(List<UserAnswersModel> models);

    @Mapping(target = "question", source = "question")
    UserAnswerDetails mapUserAnswer(UserAnswersModel model);

    default List<Long> toSelectedOptions(List<SelectedOptionModel> models) {
        if (models == null) return null;
        return models.stream()
                .map(SelectedOptionModel::getAnswerOptionId)
                .collect(Collectors.toList());
    }

    @AfterMapping
    default void setIsSelected(UserAnswersModel source, @MappingTarget UserAnswerDetails target) {
        List<Long> selectedIds = toSelectedOptions(source.getSelectedOptions());
        if (target.getQuestion() != null && target.getQuestion().getAnswers() != null) {
            for (AnswerDetailsDTO answer : target.getQuestion().getAnswers()) {
                answer.setIsSelected(selectedIds != null && selectedIds.contains(Long.valueOf(answer.getId())));
            }
        }
    }
}
