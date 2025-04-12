package tomaszewski.out.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "answer_option")
@Data
public class AnswerOptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private boolean correct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private QuestionEntity question;

}
