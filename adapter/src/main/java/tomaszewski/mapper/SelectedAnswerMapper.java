package tomaszewski.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tomaszewski.model.UserAnswersModel;
import tomaszewski.model.UserSelectedAnswers;
import tomaszewski.out.entities.UserAnswerEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SelectedAnswerMapper {

//    default List<UserAnswersModel> toUserSelectedAnswers(Long userId, List<Long> answerIds) {
//        return answerIds.stream()
//                .map(answerId -> new UserAnswersModel(userId, answerId, null, null))
//                .toList();
//    }

    UserSelectedAnswers toUserSelectedAnswers(Long userId, Long attemptId, List<Long> answerIds);

}
