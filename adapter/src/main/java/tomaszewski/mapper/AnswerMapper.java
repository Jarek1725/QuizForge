package tomaszewski.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tomaszewski.model.AnswerModel;
import tomaszewski.out.entities.AnswerOptionEntity;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

    @Mapping(target = "correct", source = "isCorrect")
    @Mapping(target = "question", ignore = true)
    AnswerOptionEntity toEntity(AnswerModel answerModel);

    @Mapping(target = "question", ignore = true)
    AnswerModel toModel(AnswerOptionEntity answerOptionEntity);

}
