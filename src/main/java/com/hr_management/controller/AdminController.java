package com.hr_management.controller;

import com.hr_management.service.AdminService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@Slf4j
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/tong-quan-du-an")
    public ResponseEntity<?> getTongQuanDuAn(@RequestParam @NotBlank String thoiGian) {
        return ResponseEntity.ok(adminService.getTongQuanCongViecAdm(thoiGian));
    }
}
