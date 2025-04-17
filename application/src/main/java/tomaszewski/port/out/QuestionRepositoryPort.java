package tomaszewski.port.out;

import jakarta.transaction.Transactional;
import tomaszewski.model.QuestionModel;

import java.util.List;

public interface QuestionRepositoryPort {
    QuestionModel createQuestion(QuestionModel questionModel);
    List<QuestionModel> createQuestions(List<QuestionModel> questionModel);
}
