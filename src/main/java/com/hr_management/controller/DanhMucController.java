package com.hr_management.controller;

import com.hr_management.service.DanhMucService;
import com.hr_management.utils.TableName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/danh-muc")
@Slf4j
@RequiredArgsConstructor
public class DanhMucController {
    private final DanhMucService danhMucService;

    @GetMapping("/find-by-id")
    public ResponseEntity<?> findById(@RequestParam @NotBlank String nameOfTable, @RequestParam @NotNull Long id) {
        TableName tableName = TableName.valueOf(nameOfTable.toUpperCase());
        return ResponseEntity.ok(danhMucService.findById(tableName, id));
    }

    @GetMapping("/find-all")
    public ResponseEntity<?> findAll(@RequestParam @NotBlank String nameOfTable) {
        TableName tableName = TableName.valueOf(nameOfTable.toUpperCase());
        return ResponseEntity.ok(danhMucService.findAll(tableName));
    }
}
