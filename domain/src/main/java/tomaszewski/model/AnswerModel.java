package tomaszewski.model;

public record AnswerModel(
        Long id,
        String text,
        QuestionModel question,
        Boolean isCorrect
) {
}
