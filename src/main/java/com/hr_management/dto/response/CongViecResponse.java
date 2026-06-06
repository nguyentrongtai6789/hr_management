package com.hr_management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CongViecResponse {
    private String uuid;
    private String noiDungCongViec;
    private Long loaiCongViecId;
    private String loaiCongViecTen;
    private String nhanSuHoVaTen;
    private String maCongViec;
    private String noLucThucHien;
    private String trangThaiTen;
    private Long trangThaiId;
    private Long sanPhamId;
    private String sanPhamTen;
    private LocalDateTime ngayBatDau;
    private String ngayBatDauString;
    private LocalDateTime ngayKetThuc;
    private String ngayKetThucString;
    private Integer tongSoBanGhi;
}
