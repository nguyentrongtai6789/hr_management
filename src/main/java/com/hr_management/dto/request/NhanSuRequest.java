package com.hr_management.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

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
