package tomaszewski.out.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tomaszewski.out.entities.ExamEntity;

import java.util.List;
@Repository

public interface JpaExamRepository extends JpaRepository<ExamEntity, Long> {

}
