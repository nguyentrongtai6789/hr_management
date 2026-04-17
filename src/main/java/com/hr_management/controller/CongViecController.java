package com.hr_management.controller;

import com.hr_management.dto.request.CongViecRequest;
import com.hr_management.service.CongViecService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @GetMapping("/get-tien-do")
    public ResponseEntity<?> getTienDoCongViec(@RequestParam String thoiGian) {
        var data = congViecService.getTienDoCongViec(thoiGian);
        return ResponseEntity.ok(data);
    }

    @PostMapping("/insert")
    public ResponseEntity<?> insertCongViec(@RequestBody CongViecRequest request) {
        congViecService.insertCongViec(request);
        return ResponseEntity.ok("Thêm mới công việc thành công!");
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateCongViec(@RequestBody CongViecRequest request) {
        congViecService.updateCongViecByUuid(request);
        return ResponseEntity.ok("Cập nhật công việc thành công!");
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteCongViecByUuid(@RequestParam @NotBlank String uuid) {
        congViecService.deleteCongViecByUuid(uuid);
        return ResponseEntity.ok("Xoá công việc thành công!");
    }

    @GetMapping("/find-one-by-uuid")
    public ResponseEntity<?> findOneByUuid(@RequestParam @NotBlank String uuid) {
        return ResponseEntity.ok(congViecService.findOneByUuid(uuid));
    }

    @PostMapping("/find-all")
    public ResponseEntity<?> findAll(@RequestBody CongViecRequest request, @RequestParam @NotNull Integer page, @RequestParam @NotNull Integer size) {
        return ResponseEntity.ok(congViecService.findAll(request, page, size));
    }

}
