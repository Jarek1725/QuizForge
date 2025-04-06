package tomaszewski.out.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;
}