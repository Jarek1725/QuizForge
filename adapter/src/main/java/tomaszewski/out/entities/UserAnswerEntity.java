package tomaszewski.out.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_answers")
public class UserAnswerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "user_answer_selected_options",
            joinColumns = @JoinColumn(name = "user_answer_id"),
            inverseJoinColumns = @JoinColumn(name = "answer_option_id")
    )
    private List<AnswerOptionEntity> selectedOptions = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attempt_id")
    @JsonIgnore
    private AttemptEntity attempt;
}
