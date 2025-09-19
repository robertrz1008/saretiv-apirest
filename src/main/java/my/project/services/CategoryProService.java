package my.project.services;

import my.project.entities.abm.CategoryProduct;
import my.project.entities.abm.Product;
import my.project.repository.jpa.CategoryProRepository;
import my.project.repository.jpa.ProductRepository;
import my.project.services.Interface.InAbmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryProService implements InAbmService<CategoryProduct, Integer> {
    @Autowired
    private CategoryProRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public ResponseEntity<CategoryProduct> create(CategoryProduct entity) {
        CategoryProduct categoryProduct = categoryRepository.save(entity);
        return ResponseEntity.ok(categoryProduct);
    }

    @Override
    public ResponseEntity<List<CategoryProduct>> findAll() {
        List<CategoryProduct> categories = categoryRepository.findAll().stream()
                .sorted(Comparator.comparing(e -> e.getId()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(categories);
    }

    @Override
    public ResponseEntity<CategoryProduct> update(Integer id, CategoryProduct entity) {
        CategoryProduct categoryFound = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("category not found"));

        categoryFound.setName(entity.getName());

        return ResponseEntity.ok(categoryRepository.save(categoryFound));
    }

    @Override
    public ResponseEntity<String> delete(Integer id) {
        CategoryProduct categoryFound = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("category not found"));

        List<Product> productList = productRepository.findByCategory(id);

        if(productList.size() > 0){
            return new ResponseEntity<>("it could not delete", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        categoryRepository.delete(categoryFound);
        return ResponseEntity.ok("deleted");
    }
}


