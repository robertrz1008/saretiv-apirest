package my.project.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import my.project.dto.auth.AuthResponse;
import my.project.dto.auth.LoginRequest;
import my.project.dto.auth.RegisterRequest;
import my.project.entities.abm.UserEntity;
import my.project.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationManager authenticationManager ;
    @Autowired
    private UserDetailServiceImpl userDetailService;
    @Autowired
    private UserRepository userRepository;


    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    @GetMapping("/user/{id}")
    public UserEntity findUserById(@PathVariable int id){
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest user){
        ResponseEntity<?> response = userDetailService.register(user);
        return response;
    }

    @PostMapping("/logIn")
    public ResponseEntity<AuthResponse> logIn(@RequestBody @Valid LoginRequest user, HttpServletResponse response){
        return userDetailService.login(user, response);
    }

    @GetMapping("/profile")
    public ResponseEntity<Optional<UserEntity>> profile(HttpServletRequest request){
        return userDetailService.verifyProfile(request);
    }

    @PutMapping("/updateUser/{doc}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable("doc") String doc, @RequestBody @Valid RegisterRequest user){
        return userDetailService.updateUser(doc, user);
    }

    @PostMapping("/logOut")
    public ResponseEntity<String> logOut(HttpServletResponse response){
        return userDetailService.logOut(response);
    }
    @GetMapping("/users/list")
    public ResponseEntity<List<UserEntity>> UsersList(){
        return userDetailService.usersList();
    }
    @DeleteMapping("/userrole/{id}")
    public ResponseEntity<?> deleteUserRoleByUser(@PathVariable int id){
        return userDetailService.deleteUserRoleByUser(id);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> delete(@PathVariable int id){
        return userDetailService.deleteUser(id);
    }
    @GetMapping("/filter/{filter}")
    public ResponseEntity<List<UserEntity>> findByFilter(@PathVariable String filter){
        List<UserEntity> list = userRepository.findByFilter(filter);

        return ResponseEntity.ok(list);
    }

    @PutMapping("/password/{id}/{pass}")
    public ResponseEntity<?> modifyPassword(@PathVariable("id") int id, @PathVariable("pass") String password){
        Optional<UserEntity> user = userRepository.modifyPassword(id, password);

        return ResponseEntity.ok(user);
    }

}
