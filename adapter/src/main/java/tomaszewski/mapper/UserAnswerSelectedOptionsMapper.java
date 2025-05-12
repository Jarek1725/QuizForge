package tomaszewski.mapper;

import org.mapstruct.Mapper;
import tomaszewski.model.SelectedOptionModel;
import tomaszewski.out.entities.UserAnswerSelectedOptionEntity;

@Mapper(componentModel = "spring")
public interface UserAnswerSelectedOptionsMapper {
    UserAnswerSelectedOptionEntity toUserAnswerEntity(SelectedOptionModel selectedOptionModel);
}
