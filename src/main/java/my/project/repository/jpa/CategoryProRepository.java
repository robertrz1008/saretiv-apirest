package my.project.repository;

import my.project.entities.abm.CategoryProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryProRepository extends JpaRepository<CategoryProduct, Integer> {
}
