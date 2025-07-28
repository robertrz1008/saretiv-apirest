package my.project.controller;

import my.project.entities.abm.CategoryProduct;
import my.project.services.CategoryProService;
import my.project.services.Interface.InAbmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categoryPro")
@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'VENDEDOR')")
public class CategoryProController implements InAbmService<CategoryProduct, Integer> {

    @Autowired
    private CategoryProService categoryService;

    @PostMapping
    public ResponseEntity<CategoryProduct> create(@RequestBody CategoryProduct entity) {
        return categoryService.create(entity);
    }

    @GetMapping
    public ResponseEntity<List<CategoryProduct>> findAll() {
        return categoryService.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryProduct> update(
            @PathVariable Integer id,
            @RequestBody CategoryProduct entity
    ) {
        return categoryService.update(id, entity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return categoryService.delete(id);
    }
}
