package my.project.repository.jdbc;

import my.project.entities.abm.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ExampleRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Product> findwithJdbc(){
        String query = "SELECT * FROM products";

        return jdbcTemplate.query(query, (rs, rowNum) -> {
            Product product = new Product();
            product.setId(rs.getInt("id"));
            product.setDescription(rs.getString("description"));
            product.setEntryPrice(rs.getInt("entry_price"));
            product.setSalePrice(rs.getInt("entry_sale"));
            product.setBarcode(rs.getString("barcode"));
            product.setStock(rs.getInt("stock"));
            return product;
        });
    }
}
