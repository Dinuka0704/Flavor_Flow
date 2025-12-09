package com.flavorflow.recipe_api.controller;

import com.flavorflow.recipe_api.model.User;
import com.flavorflow.recipe_api.service.AuthService;
import com.flavorflow.recipe_api.service.UserDetailsServiceImp;
import com.flavorflow.recipe_api.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImp userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    // POST /api/auth/register
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User registeredUser = authService.registerUser(user);
        // Do not return the password, even if it's hashed!
        registeredUser.setPassword("");
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    // POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody User authenticationRequest) {
        try {
            // 1. Authenticate the user credentials using the AuthenticationManager
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid username or password.", HttpStatus.UNAUTHORIZED);
        }

        // 2. If authentication is successful, load the UserDetails and generate JWT
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        // 3. Return the token to the client
        Map<String, String> response = new HashMap<>();
        response.put("jwt", jwt);

        return ResponseEntity.ok(response);
    }
}