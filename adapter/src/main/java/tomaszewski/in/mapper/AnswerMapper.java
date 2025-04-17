package tomaszewski.in.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tomaszewski.model.AnswerModel;
import tomaszewski.out.entities.AnswerOptionEntity;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

    @Mapping(target = "correct", source = "isCorrect")
    AnswerOptionEntity toEntity(AnswerModel answerModel);
}
