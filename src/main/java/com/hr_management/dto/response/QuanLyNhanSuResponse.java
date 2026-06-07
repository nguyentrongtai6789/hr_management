package com.hr_management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuanLyNhanSuResponse {
    private String hoTen;
    private String maDinhDanh;
    private String gioiTinh;
    private String danToc;
    private String email;
    private String userName;
    private String roleName;
    private String ghiChu;
}
