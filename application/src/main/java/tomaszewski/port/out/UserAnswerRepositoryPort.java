package tomaszewski.port.out;

import tomaszewski.model.UserAnswersModel;

import java.util.List;

public interface UserAnswerRepositoryPort {
    void saveAll(List<UserAnswersModel> userAnswersModels);
}
