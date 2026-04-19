package com.hr_management.controller;

import com.hr_management.dto.request.CongViecRequest;
import com.hr_management.service.AdminService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/quan-ly-cong-viec")
    public ResponseEntity<?> findAll(@RequestBody CongViecRequest request,
                                     @RequestParam @NotNull Integer page,
                                     @RequestParam @NotNull Integer size) {
        return ResponseEntity.ok(adminService.findAll(request, page, size));
    }
}
