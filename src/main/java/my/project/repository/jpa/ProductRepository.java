package my.project.repository;

import my.project.entities.abm.Customer;
import my.project.entities.abm.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "SELECT * FROM products WHERE supplier_id = :id", nativeQuery = true)
    List<Product> findBySupplier(@Param("id") int id);

    @Query(value = "SELECT *FROM products WHERE category_id = :id", nativeQuery = true)
    List<Product> findByCategory(@Param("id") int id);

    @Query(value = """
    SELECT * 
    FROM products
    WHERE description ILIKE CONCAT('%', :filter, '%')
       OR barcode ILIKE CONCAT('%', :filter, '%')
    AND stock > 0
    """, nativeQuery = true)
    List<Product> findByFilter(@Param("filter") String filter);
}
