package my.project.controller;

import my.project.entities.abm.Product;
import my.project.repository.jpa.ProductRepository;
import my.project.services.Interface.InAbmService;
import my.project.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController implements InAbmService<Product, Integer> {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product entity) {
        return productService.create(entity);
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        return productService.findAll();
    }

    @GetMapping("/jdbc")
    public ResponseEntity<?> findAllJdbc() {
        return productService.findwithJdbc();
    }
    @GetMapping("/filter/{str}")
    public ResponseEntity<List<Product>> findByFIlter(@PathVariable("str") String filter) {
        return productService.findByFilter(filter);
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<Product> findByFIlter(@PathVariable("id") int id) {
        return productService.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Integer id,@RequestBody Product entity) {
        return productService.update(id, entity);
    }
    @PutMapping("/stock/{id}/{stock}")
    public ResponseEntity<Product> updateStock(@PathVariable("id") Integer id,@PathVariable("stock") Integer stock) {
        return productService.updateStock(id, stock);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return productService.delete(id);
    }

}
