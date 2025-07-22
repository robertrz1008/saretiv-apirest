package my.project.services;

import my.project.entities.abm.CategoryProduct;
import my.project.repository.CategoryProRepository;
import my.project.services.Interface.InAbmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryProService implements InAbmService<CategoryProduct, Integer> {
    @Autowired
    private CategoryProRepository categoryRepository;

    @Override
    public ResponseEntity<CategoryProduct> create(CategoryProduct entity) {
        CategoryProduct categoryProduct = categoryRepository.save(entity);
        return ResponseEntity.ok(categoryProduct);
    }

    @Override
    public ResponseEntity<List<CategoryProduct>> findAll() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @Override
    public ResponseEntity<CategoryProduct> update(Integer id, CategoryProduct entity) {
        CategoryProduct categoryFound = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("category not found"));

        categoryFound.setName(entity.getName());

        return ResponseEntity.ok(categoryRepository.save(entity));
    }

    @Override
    public ResponseEntity<String> delete(Integer id) {
        CategoryProduct categoryFound = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("category not found"));

        categoryRepository.delete(categoryFound);
        return ResponseEntity.ok("deleted");
    }
}


