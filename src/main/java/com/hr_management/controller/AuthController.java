package com.hr_management.controller;

import com.hr_management.config.security.JwtUtil;
import com.hr_management.config.security.UserDetailsCustom;
import com.hr_management.dto.request.AuthRequest;
import com.hr_management.dto.request.ChangePasswordRequest;
import com.hr_management.dto.response.AuthResponse;
import com.hr_management.entity.User;
import com.hr_management.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            UserDetailsCustom userDetails = (UserDetailsCustom) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails);
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody @Valid ChangePasswordRequest request) {
        try {
            // Lấy thông tin user hiện tại từ SecurityContext
            UserDetailsCustom userDetails = (UserDetailsCustom) SecurityContextHolder
                    .getContext().getAuthentication().getPrincipal();
            // Tìm user trong database
            User user = userRepository.findFirstByUserNameAndIsActive(userDetails.getUsername(), 1)
                    .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));
            // Kiểm tra mật khẩu hiện tại có match không
            if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Mật khẩu hiện tại không chính xác");
            }
            // Encode mật khẩu mới và lưu vào database
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(user);
            log.info("Người dùng {} đã đổi mật khẩu thành công", userDetails.getUsername());
            return ResponseEntity.ok("Đổi mật khẩu thành công");
        } catch (Exception e) {
            log.error("Lỗi khi đổi mật khẩu: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Đã có lỗi xảy ra khi đổi mật khẩu");
        }
    }
}
