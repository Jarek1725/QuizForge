package tomaszewski.out.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tomaszewski.out.entities.ExamEntity;

import java.awt.print.Pageable;
import java.util.List;

@Repository

public interface JpaExamRepository extends JpaRepository<ExamEntity, Long> {
    @Query(value = """
                SELECT e.*
                FROM exam e
                INNER JOIN exam_categories ec ON e.id = ec.exam_id
                INNER JOIN categories c ON ec.category_id = c.id
                INNER JOIN university u ON e.university_id = u.id
                WHERE (:category IS NULL OR c.name = :category)
                  AND (:university IS NULL OR u.name = :university)
                GROUP BY e.id
                ORDER BY e.id DESC
                LIMIT :limit
            """, nativeQuery = true)
    List<ExamEntity> findExamsWithFilters(
            @Param("category") String category,
            @Param("university") String university,
            @Param("limit") int limit
    );
}
