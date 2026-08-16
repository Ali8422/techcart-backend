package techcart_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import techcart_backend.dto.AuthResponse;
import techcart_backend.dto.LoginRequest;
import techcart_backend.dto.RegisterRequest;
import techcart_backend.entity.User;
import techcart_backend.repository.UserRepository;
import techcart_backend.security.JwtUtils;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: Email is already registered!");
        }

        User newUser = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // BCrypt hashing
                .role(User.Role.USER)
                .build();

        userRepository.save(newUser);

        String token = jwtUtils.generateToken(newUser.getEmail());

        AuthResponse response = AuthResponse.builder()
                .token(token)
                .email(newUser.getEmail())
                .name(newUser.getName())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        // Authenticate credentials against UserDetailsService & PasswordEncoder
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtils.generateToken(user.getEmail());

        AuthResponse response = AuthResponse.builder()
                .token(token)
                .email(user.getEmail())
                .name(user.getName())
                .build();

        return ResponseEntity.ok(response);
    }
}