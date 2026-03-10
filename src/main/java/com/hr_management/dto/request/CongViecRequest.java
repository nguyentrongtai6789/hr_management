package com.hr_management.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CongViecRequest {
    private String uuid;
    private String noiDungCongViec;
    private Integer loaiCongViecId;
    private String maCongViec;
    private String noLucThucHien;
    private Integer trangThaiId;
    private LocalDateTime  ngayBatDau;
    private LocalDateTime ngayKetThuc;
}
