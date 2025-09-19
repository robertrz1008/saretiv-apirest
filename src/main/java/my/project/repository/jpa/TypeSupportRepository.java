package my.project.repository.jpa;
import my.project.entities.transaction.TypeSupport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeSupportRepository extends JpaRepository<TypeSupport, Integer> {

    @Query(value = """
    SELECT * 
    FROM types_support 
    WHERE description ILIKE CONCAT('%', :filter, '%')
    """, nativeQuery = true)
    List<TypeSupport> findByFilter(@Param("filter") String filter);
}
