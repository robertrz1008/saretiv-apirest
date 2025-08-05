package my.project.controller;

import my.project.entities.abm.CategoryDevice;
import my.project.services.CategoryDeviceService;
import my.project.services.CategoryProService;
import my.project.services.Interface.InAbmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/catDevice")
public class CategoryDevController implements InAbmService<CategoryDevice, Integer> {

    @Autowired
    private CategoryDeviceService categoryDeviceService;

    @PostMapping
    public ResponseEntity<CategoryDevice> create(@RequestBody CategoryDevice entity) {
        return categoryDeviceService.create(entity);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDevice>> findAll() {
        return categoryDeviceService.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDevice> update(@PathVariable Integer id, @RequestBody CategoryDevice entity) {
        return categoryDeviceService.update(id, entity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return categoryDeviceService.delete(id);
    }
}
