package tomaszewski.model;

import java.util.List;

public record QuestionModel(
       Long id,
       String content,
       String type,
       Long examId,
       Long score,
       List<AnswerModel> answers
) {
}
