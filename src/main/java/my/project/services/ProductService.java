package my.project.services;

import my.project.entities.abm.CategoryProduct;
import my.project.entities.abm.Product;
import my.project.repository.ProductRepository;
import my.project.services.Interface.InAbmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements InAbmService<Product, Integer> {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ResponseEntity<Product> create(Product entity) {
        return ResponseEntity.ok(productRepository.save(entity));
    }

    @Override
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.ok(productRepository.findAll());
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

        return ResponseEntity.ok(productRepository.save(entity));
    }

    @Override
    public ResponseEntity<String> delete(Integer id) {
        Product productFound = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("category not found"));

        productRepository.delete(productFound);
        return ResponseEntity.ok("deleted");
    }
}
