package my.project.controller;

import my.project.entities.abm.Supplier;
import my.project.services.Interface.InAbmService;
import my.project.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplier")

public class SupplierController implements InAbmService<Supplier, Integer> {
    @Autowired
    private SupplierService supplierService;

    @PostMapping
    public ResponseEntity<Supplier> create(@RequestBody Supplier entity) {
        return supplierService.create(entity);
    }

    @GetMapping
    public ResponseEntity<List<Supplier>> findAll() {
        return supplierService.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Supplier> update(@PathVariable Integer id, @RequestBody Supplier entity) {
        return supplierService.update(id, entity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return supplierService.delete(id);
    }
}
