package com.hr_management.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NhanSuRequest {
    private String hoTen;
    private String maDinhDanh;
    private Integer gioiTinhId;
    private Integer danTocId;
    private String email;
    private String userName;
    private String roleName;
    private String ghiChu;
}
