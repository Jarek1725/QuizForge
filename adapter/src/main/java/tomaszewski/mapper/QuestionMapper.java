package tomaszewski.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import tomaszewski.model.QuestionModel;
import tomaszewski.model.AnswerModel;
import tomaszewski.openapi.model.CreateQuestionDTO;
import tomaszewski.out.entities.QuestionEntity;
import tomaszewski.out.entities.AnswerOptionEntity;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {AnswerMapper.class})
public interface QuestionMapper {

    @Mappings({
            @Mapping(target = "answers", source = "answers"),
    })
    QuestionEntity toEntity(QuestionModel questionModel);

    @Mappings({
            @Mapping(target = "answers", source = "answers"),
    })
    QuestionModel toModel(QuestionEntity savedEntity);

    @Mappings({
            @Mapping(target = "answers", source = "answers"),
    })
    List<QuestionModel> toModels(List<QuestionEntity> savedEntity);

    default List<AnswerModel> answerOptionEntityListToAnswerModelList(List<AnswerOptionEntity> answerOptionEntities) {
        return answerOptionEntities.stream()
                .map(this::answerOptionEntityToAnswerModel)
                .collect(Collectors.toList());
    }

    default AnswerModel answerOptionEntityToAnswerModel(AnswerOptionEntity answerOptionEntity) {
        return new AnswerModel(
                answerOptionEntity.getId(),
                answerOptionEntity.getText(),
                null,
                answerOptionEntity.isCorrect()
        );
    }
}
