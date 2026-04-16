package com.hr_management.dto.request;


public record TienDoCongViecRequest(
        String trangThai,
        Long trangThaiId,
        Long soLuong
) {}
