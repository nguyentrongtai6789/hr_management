package com.hr_management.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
    private Integer sanPhamId;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate ngayBatDau;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate ngayKetThuc;
}
