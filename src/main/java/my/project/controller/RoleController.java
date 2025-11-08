package my.project.controller;

import my.project.entities.abm.Role;
import my.project.repository.jpa.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/roleList")
    public ResponseEntity<List<Role>> findAll(){
        List<Role> response = roleRepository.findAll();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/createRoleAuto")
    public ResponseEntity<?> create(){

        List<Role> roles = List.of(new Role("ADMINISTRADOR"), new Role("TECNICO"), new Role("VENDEDOR"));
        try {
            roleRepository.saveAll(roles);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            new RuntimeException(e);

            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
