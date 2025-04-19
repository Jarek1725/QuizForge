package tomaszewski.out.entities;

import jakarta.persistence.*;
import lombok.Data;
import tomaszewski.model.UserModel;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "exam")
@Data
public class ExamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id")
    private UniversityEntity university;

    @Column(name = "questions_per_exam")
    private Integer questionsPerExam;

    @Column(name = "percentage_to_pass")
    private Integer percentageToPass;

    @Column(name = "time_limit")
    private Integer timeLimit;

    @ManyToMany
    @JoinTable(
            name = "exam_categories",
            joinColumns = @JoinColumn(name = "exam_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<CategoryEntity> categories = new ArrayList<>();

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionEntity> questions = new ArrayList<>();

    @ManyToOne
    private UserEntity creator;
}
