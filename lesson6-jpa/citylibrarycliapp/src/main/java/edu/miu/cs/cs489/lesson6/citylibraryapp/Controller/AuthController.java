package edu.miu.cs.cs489.lesson6.citylibraryapp.Controller;

import edu.miu.cs.cs489.lesson6.citylibraryapp.Security.JwtTokenProvider;
import edu.miu.cs.cs489.lesson6.citylibraryapp.model.Role;
import edu.miu.cs.cs489.lesson6.citylibraryapp.model.User;
import edu.miu.cs.cs489.lesson6.citylibraryapp.model.auth.ApiResponse;
import edu.miu.cs.cs489.lesson6.citylibraryapp.model.auth.JwtAuthenticationResponse;
import edu.miu.cs.cs489.lesson6.citylibraryapp.model.auth.LoginRequest;
import edu.miu.cs.cs489.lesson6.citylibraryapp.model.auth.SignUpRequest;
import edu.miu.cs.cs489.lesson6.citylibraryapp.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        System.out.println("Roles: " + authentication.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));

        // Embed role in JWT claims


    }

    @PostMapping("/signup/Admin")
    public ResponseEntity<?> registerPatient(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setRole(Role.ADMIN);
        user.setEmail(signUpRequest.getEmail());
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setPhoneNumber(signUpRequest.getPhoneNumber());
        // Set other patient-specific fields

        userRepository.save(user);
        return ResponseEntity.ok().build();
        //return ResponseEntity.ok(new ApiResponse(true, "Patient registered successfully"));
    }


    @PostMapping("/signout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Optionally add token to a blacklist (requires Redis/db)
        }
        return ResponseEntity.ok(new ApiResponse(true, "Logged out successfully"));
    }

    // Similar methods for dentist and admin registration
    // (Admin registration might be restricted to existing admins only)
}
