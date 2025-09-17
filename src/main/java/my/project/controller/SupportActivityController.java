package my.project.controller;

import my.project.entities.transaction.SupportActivity;
import my.project.services.Interface.InAbmService;
import my.project.services.SupportActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/supportActivity")
public class SupportActivityController {
    @Autowired
    private SupportActivityService activityService;

    @PostMapping
    public ResponseEntity<List<SupportActivity>> create(@RequestBody List<SupportActivity> entity) {
        return activityService.create(entity);
    }

    @GetMapping
    public ResponseEntity<List<SupportActivity>> findAll() {
        return activityService.findAll();
    }

    @GetMapping("/support/{id}")
    public ResponseEntity<List<SupportActivity>> findBySupport(@PathVariable Integer id) {
        return activityService.findBySupport(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupportActivity> update(@PathVariable Long aLong, SupportActivity entity) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return activityService.delete(id);
    }
    @DeleteMapping("/support/{id}")
    public ResponseEntity<String> deleteBySupportId(@PathVariable int id){
        return activityService.deleteBySupportId(id);
    }
}
