package my.project.controller;

import my.project.dto.params.TypeSupportParamsDTO;
import my.project.entities.transaction.Device;
import my.project.entities.transaction.TypeSupport;
import my.project.services.Interface.InAbmService;
import my.project.services.TypeSupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/typeSupport")
@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'TECNICO')")
public class TypeSupportController implements InAbmService<TypeSupport, Integer> {

    @Autowired
    private TypeSupportService typeSupportService;

    @PostMapping
    public ResponseEntity<TypeSupport> create(@RequestBody TypeSupport entity) {
        return typeSupportService.create(entity);
    }

    @GetMapping
    public ResponseEntity<List<TypeSupport>> findAll() {
        return typeSupportService.findAll();
    }

    @GetMapping("/filter/{fil}")
    public ResponseEntity<List<TypeSupport>> findByFilter(@PathVariable("fil") String filter){
        return typeSupportService.findByFIlter(filter);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TypeSupport> update(@PathVariable Integer id, @RequestBody TypeSupport entity) {
        return typeSupportService.update(id, entity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return typeSupportService.delete(id);
    }

    @PostMapping("/params")
    public ResponseEntity<List<TypeSupport>> findByParams(@RequestBody TypeSupportParamsDTO paramsDTO){
        return typeSupportService.findByParams(paramsDTO);
    }
}
