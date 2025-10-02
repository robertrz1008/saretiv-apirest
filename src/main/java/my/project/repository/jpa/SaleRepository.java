package my.project.repository.jpa;

import my.project.dto.sales.SaleResponse;
import my.project.entities.transaction.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {

    @Query(value = "SELECT pro.description, cat.name AS \"category\", pro.entry_sale as \"price\", pd.product_amount as \"amount\", pd.subtotal as \"subtotal\", sa.create_at AS \"date\"\n" +
            "FROM sales AS sa \n" +
            "\tJOIN products_detail AS pd ON sa.id = pd.sale_id\n" +
            "\tJOIN products AS pro ON pd.product_id = pro.id\n" +
            "\tJOIN categories_product AS cat ON pro.category_id = cat.id\n" +
            "WHERE sa.create_at BETWEEN :date1 AND :date2", nativeQuery = true)
    public List<SaleResponse> findByDate(@Param("date1") Date date1, @Param("date2") Date date2);
}
