package tomaszewski.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tomaszewski.model.UserAnswersModel;
import tomaszewski.out.entities.UserAnswerEntity;

import java.util.List;

@Mapper(componentModel = "spring", uses = {QuestionMapper.class})
public interface UserAnswerMapper {

    @Mapping(target = "question", source = "question")
    UserAnswerEntity toUserAnswerEntity(UserAnswersModel answersModel);

    List<UserAnswerEntity> toUserAnswerEntities(List<UserAnswersModel> userAnswersModels);
}
