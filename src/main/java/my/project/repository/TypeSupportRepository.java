package my.project.repository;
import my.project.entities.transaction.TypeSupport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeSupportRepository extends JpaRepository<TypeSupport, Integer> {
}
