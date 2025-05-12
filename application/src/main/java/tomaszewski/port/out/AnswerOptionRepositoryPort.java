package tomaszewski.port.out;

import tomaszewski.model.AnswerOptionModel;

import java.util.List;

public interface AnswerOptionRepositoryPort {
    List<AnswerOptionModel> findAllByQuestionId(Long questionId);

    List<AnswerOptionModel> findAllByIdIn(List<Long> userAnswersIds);
}
