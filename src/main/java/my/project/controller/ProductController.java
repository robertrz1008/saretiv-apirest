package my.project.controller;

import my.project.entities.abm.Product;
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


    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product entity) {
        return productService.create(entity);
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        return productService.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Integer id,@RequestBody Product entity) {
        return productService.update(id, entity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return productService.delete(id);
    }
}
