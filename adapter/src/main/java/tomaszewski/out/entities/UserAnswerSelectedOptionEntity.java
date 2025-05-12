package tomaszewski.out.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_answer_selected_options")
@Data
public class UserAnswerSelectedOptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_answer_id")
    private Long userAnswerId;

    @Column(name = "answer_option_id")
    private Long answerOptionId;
}
