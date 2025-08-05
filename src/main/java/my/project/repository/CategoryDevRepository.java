package my.project.repository;

import my.project.entities.abm.CategoryDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDevRepository extends JpaRepository<CategoryDevice, Integer> {
}
