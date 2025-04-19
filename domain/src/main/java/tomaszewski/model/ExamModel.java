package tomaszewski.model;

import java.util.List;

public record ExamModel(
        Long id,
        String name,
        List<QuestionModel> questions,
        UniversityModel university,
        List<CategoryModel> categories,
        UserModel creator,
        Integer percentageToPass,
        Integer questionsPerExam,
        Integer timeLimit
) {
}
