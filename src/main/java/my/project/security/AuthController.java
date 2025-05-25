package my.project.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import my.project.dao.auth.AuthResponse;
import my.project.dao.auth.LoginRequest;
import my.project.dao.auth.RegisterRequest;
import my.project.entities.abm.UserEntity;
import my.project.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest user){
        AuthResponse response = userDetailService.register(user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/logIn")
    public ResponseEntity<AuthResponse> logIn(@RequestBody @Valid LoginRequest user, HttpServletResponse response){
        return userDetailService.login(user, response);
    }

    @GetMapping("/profile")
    public ResponseEntity<Optional<UserEntity>> profile(HttpServletRequest request){
        return userDetailService.verifyProfile(request);
    }

    @PostMapping("/logOut")
    public ResponseEntity<String> logOut(HttpServletResponse response){
        return userDetailService.logOut(response);
    }

}
