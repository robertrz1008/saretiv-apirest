package my.project.controller;

import my.project.entities.abm.Enterprise;
import my.project.services.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enterprise")
public class EnterpriseController {
    @Autowired
    private EnterpriseService enterpriseService;

    @GetMapping
    private ResponseEntity<List<Enterprise>> findAll(){
        return enterpriseService.findAll();
    }

    @PostMapping
    private ResponseEntity<Enterprise> save(@RequestBody Enterprise enterprise){
        return enterpriseService.save(enterprise);
    }
    @PostMapping("/{id}")
    private ResponseEntity<Enterprise> update(@PathVariable int id, @RequestBody Enterprise enterprise){
        return enterpriseService.update(id, enterprise);
    }
}
