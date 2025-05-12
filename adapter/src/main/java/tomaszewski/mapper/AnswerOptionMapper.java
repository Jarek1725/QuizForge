package tomaszewski.mapper;

import org.mapstruct.Mapper;
import tomaszewski.model.AnswerOptionModel;
import tomaszewski.out.entities.AnswerOptionEntity;
import tomaszewski.out.entities.AttemptEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AnswerOptionMapper {
    AnswerOptionModel toAnswerOptionModel(AnswerOptionEntity answerOptionEntity);
    List<AnswerOptionModel> toAnswerOptionModels(List<AnswerOptionEntity> answerOptionEntity);
}
