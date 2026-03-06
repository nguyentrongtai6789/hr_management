package com.hr_management.controller;

import com.hr_management.dto.request.AuthRequest;
import com.hr_management.dto.response.AuthResponse;
import com.hr_management.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        // tạo  1 đối tượng UsernamePasswordAuthenticationToken từ username và password
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        // truyền đối tượng đó vào AuthenticationManager để authen
        // ở bước này, AuthenticationManager sẽ gọi UserDetailsService.loadUserByUsername() => cái này đã được override để trả ra user lấy từ DataBase
        // sau đó sẽ dùng PasswordEncoder.matches() để so sánh xem password trong db (ở dạng encode) có  matches với password truyền về không
        // nếu matches thì sẽ trả ra một đối tượng Authentication chứa UserDetails
        // sau đó lấu UserDetails này generateToken
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        BCryptPasswordEncoder s = new BCryptPasswordEncoder();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
