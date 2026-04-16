package com.hr_management.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TienDoCongViecRequest {
    private String trangThai;
    private Long trangThaiId;
    private int soLuong;
}
