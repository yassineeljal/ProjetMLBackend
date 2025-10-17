package projet.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import projet.backend.dto.AuthRequest;
import projet.backend.dto.AuthResponse;
import projet.backend.models.User;
import projet.backend.repositories.UserRepository;
import projet.backend.security.JwtUtil;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:5173"}, allowCredentials = "true")
public class AuthentificationController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthentificationController(AuthenticationManager authenticationManager,
                                      UserRepository userRepository,
                                      PasswordEncoder passwordEncoder,
                                      JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody AuthRequest req) {
        if (req.username() == null || req.username().isBlank() || req.password() == null || req.password().isBlank()) {
            return ResponseEntity.badRequest().body("Username et mot de passe obligatoires");
        }

        if (userRepository.findByUsername(req.username()).isPresent()) {
            return ResponseEntity.badRequest().body("Nom d'utilisateur déjà pris");
        }

        User u = new User();
        u.setUsername(req.username());
        u.setPassword(passwordEncoder.encode(req.password()));
        u = userRepository.save(u);

        String token = jwtUtil.generateToken(u.getUsername());
        return ResponseEntity.ok(new AuthResponse(u.getId(), u.getUsername(), token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest req) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.username(), req.password())
            );
            User u = userRepository.findByUsername(req.username()).orElseThrow();
            String token = jwtUtil.generateToken(u.getUsername());
            return ResponseEntity.ok(new AuthResponse(u.getId(), u.getUsername(), token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Identifiants invalides");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(500).body("Erreur d'authentification : " + e.getMessage());
        }
    }
}
