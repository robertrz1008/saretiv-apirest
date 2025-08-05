package my.project.controller;

import my.project.dto.supportCustomDTO.SupportDTO;
import my.project.dto.supportCustomDTO.SupportRequestDTO;
import my.project.entities.transaction.Support;
import my.project.security.AuthController;
import my.project.services.Interface.InAbmService;
import my.project.services.SupportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/support")
public class SupportController{

    @Autowired
    private SupportService supportService;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping
    public ResponseEntity<Support> create(@RequestBody SupportRequestDTO entity) {
        logger.info(entity.toString());
        return supportService.create(entity);
    }

    @GetMapping
    public ResponseEntity<List<Support>> findAll() {
        return supportService.findAll();
    }

    @GetMapping("/custom")
    public ResponseEntity<List<SupportDTO>> findAllCustomized() {
        return supportService.findAllCustomized();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Support> update(@PathVariable Integer id, @RequestBody Support entity) {
        return supportService.update(id, entity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(Integer id) {
        return supportService.delete(id);
    }
}
