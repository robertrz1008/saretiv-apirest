package my.project.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import my.project.dto.auth.AuthResponse;
import my.project.dto.auth.LoginRequest;
import my.project.dto.auth.RegisterRequest;
import my.project.entities.abm.Role;
import my.project.entities.abm.UserEntity;
import my.project.repository.RoleRepository;
import my.project.repository.UserRepository;
import my.project.security.cookie.CookieUtil;
import my.project.security.jwt.JWTConfig;
import my.project.utils.Log;
import my.project.utils.exceptions.CustomAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private JWTConfig jwtUtils;

    private Log log = new Log<>(UserDetailsService.class);


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userFound = (UserEntity) userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<SimpleGrantedAuthority> roleList = new ArrayList<>();

        for(Role role : userFound.getRoles()){
            roleList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getName())));

        }

        return new User(
                userFound.getUsername(),
                userFound.getPassword(),
                roleList
        );
    }

    public Authentication authenticate(String name, String password) throws CustomAuthenticationException{
        UserDetails userDetails;

        try {
            userDetails = this.loadUserByUsername(name);

        }catch (UsernameNotFoundException e){
            throw new CustomAuthenticationException("Invalid username");
        }

        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new CustomAuthenticationException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
    }



    public ResponseEntity<AuthResponse> login(LoginRequest loginRequest, HttpServletResponse response){
        log.info("Usuario iniciando session");
        AuthResponse authResponse;
        try {
            Authentication authentication = this.authenticate(
                    loginRequest.username(),
                    loginRequest.password()
            );
            String accessToken;
            SecurityContextHolder.getContext().setAuthentication(authentication);
            accessToken = jwtUtils.createToken(authentication);
            CookieUtil.createCookie(response, "JWT_TOKEN", accessToken, (3600*24));

            authResponse = new AuthResponse(loginRequest.username(), "Welcome to this web service!", accessToken, true);
            log.info("el usuario: "+loginRequest.username()+" ha iniciado session");
            return new ResponseEntity<>(authResponse, HttpStatus.OK);

        }catch (CustomAuthenticationException e){

            authResponse = new AuthResponse(null, "Nombre de usuario o contraseña incorrecta", null, false);
            return new ResponseEntity<>(authResponse, HttpStatus.UNAUTHORIZED);
        }
    }

    public ResponseEntity<Optional<UserEntity>> verifyProfile(HttpServletRequest request) throws RuntimeException{
        Cookie jwtCookie = CookieUtil.getCookie(request, "JWT_TOKEN");

        String token = jwtCookie.getValue();

        DecodedJWT decodedJWT = jwtUtils.validateToken(token);
        String username = jwtUtils.extractStringUserName(decodedJWT);

        Optional<UserEntity> userMatch = userRepository.findUserByUsername(username);


        return new ResponseEntity<>(userMatch, HttpStatus.OK);

    }

    public ResponseEntity<String> logOut(HttpServletResponse response){
        try {
            CookieUtil.deleteCookie(response, "JWT_TOKEN");
            return new ResponseEntity<>("cookie deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("cookie did not delete", HttpStatus.OK);
        }

    }


//  crud

    public ResponseEntity<List<UserEntity>> usersList(){
        List<UserEntity> usersFound = userRepository.findAll().stream()
                .sorted(Comparator.comparing(e -> e.getId()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(usersFound, HttpStatus.OK);
    }

    public ResponseEntity<UserEntity> updateUser(String doc, RegisterRequest user){
        UserEntity userFound = userRepository.findByDocument(doc)
                .orElseThrow( () -> new RuntimeException("User not found"));

        String name = user.name();
        String lasname = user.lastname();
        String telephone = user.telephone();
        String document = user.document();
        String username = user.username();
        String password = user.password();
        Date entryDate = user.entryDate();
        boolean status = user.status();
        List<String> roleListName = user.roleRequest().roleListName();

//        Set<Role> roleSet = roleRepository.findRoleEntityByNameIn(roleListName).stream()
//                .collect(Collectors.toSet());
//
//        if(roleSet.isEmpty()){
//            throw new IllegalArgumentException("The role especified does not exit");
//        }

        userFound.setName(name);
        userFound.setLastname(lasname);
        userFound.setTelephone(telephone);
        userFound.setUsername(username);
        userFound.setDocument(document);
        userFound.setEntryDate(entryDate);


        return new ResponseEntity<>(userRepository.save(userFound), HttpStatus.OK);
    }

    public ResponseEntity<?>  register(RegisterRequest userRequest){
        String name = userRequest.name();
        String lasname = userRequest.lastname();
        String telephone = userRequest.telephone();
        String document = userRequest.document();
        String username = userRequest.username();
        String password = userRequest.password();
        Date entryDate = userRequest.entryDate();
        boolean status = userRequest.status();
        List<String> roleListName = userRequest.roleRequest().roleListName();

        if(userRepository.findUserByUsername(username).isPresent()){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "Nombre de usurio ya registrado"));
        }

        if(userRepository.findByDocument(document).isPresent()){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "El documento ya esta registrado"));
        }

        Set<Role> roleSet = roleRepository.findRoleEntityByNameIn(roleListName).stream()
                .collect(Collectors.toSet());

        if(roleSet.isEmpty()){
            throw new IllegalArgumentException("The role especified does not exit");
        }
        UserEntity userSave = new UserEntity.Builder()
                .name(name)
                .lastname(lasname)
                .telephone(telephone)
                .document(document)
                .username(username)
                .password(passwordEncoder.encode(password))
                .entryDate(entryDate)
                .status(status)
                .roles(roleSet)
                .build();
        UserEntity userEntity;
        try {
            userEntity = userRepository.save(userSave);

            List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
            userEntity.getRoles().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getName()))));

            Authentication auth = new UsernamePasswordAuthenticationToken(userEntity.getUsername(), userEntity.getPassword(), authorityList);
            String accessToken = jwtUtils.createToken(auth);
            AuthResponse authResponse = new AuthResponse(userEntity.getUsername(), "user registered", accessToken, true);
            return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            AuthResponse authResponse = new AuthResponse(null, "user did not register", null, true);
            return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> deleteUserRoleByUser(int id){
        try {
            userRepository.deleteUserRolByUser(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<String> deleteUser(int id){
        UserEntity userFound = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User Not Found"));
        try {
            userRepository.delete(userFound);

            return new ResponseEntity<>("User deleted", HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
