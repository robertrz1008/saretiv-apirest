package my.project.repository;

import my.project.dto.supportCustomDTO.ProductDetailResponse;
import my.project.entities.transaction.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Integer> {

    @Query(value = "SELECT pd.id, pr.description, pr.id as \"productId\", pr.entry_sale as \"price\", pd.product_amount as \"productAmount\", pd.subtotal\n" +
            "FROM products  as pr JOIN products_detail as pd ON pr.id = pd.product_id\n" +
            "WHERE pd.support_id = :supId", nativeQuery = true)
    List<ProductDetailResponse> findBySupport(@Param("supId") int id);

    @Query(value = "UPDATE products_detail SET product_amount = :amount, subtotal = :subtotal WHERE id = :id", nativeQuery = true)
    ProductDetail updateAmount(
            @Param("amount") int amount,
            @Param("subtotal") int subtotal,
            @Param("id") int id
    );
    @Query(value = "DELETE FROM products_detail WHERE support_id = :id", nativeQuery = true)
    int deleteBySupportId(@PathVariable("id") int supId);
}
