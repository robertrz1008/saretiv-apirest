package my.project.services;

import my.project.entities.abm.Product;
import my.project.repository.jdbc.ExampleRepository;
import my.project.repository.jpa.ProductRepository;
import my.project.services.Interface.InAbmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService implements InAbmService<Product, Integer> {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ExampleRepository exampleRepository;



    @Override
    public ResponseEntity<Product> create(Product entity) {
        return ResponseEntity.ok(productRepository.save(entity));
    }

    @Override
    public ResponseEntity<List<Product>> findAll() {
        List<Product> products = productRepository.findAll().stream()
                .sorted(Comparator.comparing(e -> e.getId()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }

    @Override
    public ResponseEntity<Product> update(Integer id, Product entity) {
        Product productFound = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("category not found"));

        productFound.setDescription(entity.getDescription());
        productFound.setCategory(entity.getCategory());
        productFound.setSupplier(entity.getSupplier());
        productFound.setStock(entity.getStock());
        productFound.setDescription(entity.getDescription());
        productFound.setDescription(entity.getDescription());
        productFound.setEntryPrice(entity.getEntryPrice());
        productFound.setSalePrice(entity.getSalePrice());
        productFound.setBarcode(entity.getBarcode());

        return ResponseEntity.ok(productRepository.save(productFound));
    }

    public ResponseEntity<Product> updateStock(Integer id, int stock) {
        Product productFound = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("product not found"));

        productFound.setStock(stock);

        return ResponseEntity.ok(productRepository.save(productFound));
    }

    @Override
    public ResponseEntity<String> delete(Integer id) {
        Product productFound = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("category not found"));

        productRepository.delete(productFound);
        return ResponseEntity.ok("deleted");
    }

    public ResponseEntity<List<Product>> findByFilter(@PathVariable String filter){
        List<Product> list = productRepository.findByFilter(filter);

        return ResponseEntity.ok(list);
    }

    public ResponseEntity<Product> findById(int id){
        Product pro = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("product not found"));
        return ResponseEntity.ok(pro);
    }

    public ResponseEntity<List<Product>> findwithJdbc(){

        List<Product> pro  = exampleRepository.findwithJdbc();

        return ResponseEntity.ok(pro);
    }

}
