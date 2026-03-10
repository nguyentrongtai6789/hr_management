package com.hr_management.controller;

import com.hr_management.dto.request.CongViecRequest;
import com.hr_management.service.CongViecService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cong-viec")
@Slf4j
@RequiredArgsConstructor
public class CongViecController {
    private final CongViecService congViecService;

    @PostMapping("/insert")
    public ResponseEntity<?> insertCongViec(@RequestBody CongViecRequest request) {
            congViecService.insertCongViec(request);
            return ResponseEntity.ok("Thêm mới công việc thành công!");
    }

    @GetMapping("/find-one-by-uuid")
    public ResponseEntity<?> findOneByUuid(@RequestParam @NotBlank String uuid) {
        return ResponseEntity.ok(congViecService.findOneByUuid(uuid));
    }

}
