package my.project.repository.jpa;

import my.project.entities.abm.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Integer> {

    @Query(value = """
    SELECT * 
    FROM suppliers 
    WHERE name ILIKE CONCAT('%', :filter, '%')
       OR ruc ILIKE CONCAT('%', :filter, '%')
    """, nativeQuery = true)
    List<Supplier> findByFilter(@Param("filter") String filter);

}
