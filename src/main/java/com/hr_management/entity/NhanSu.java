package com.hr_management.entity;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "nhan_su")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NhanSu {

    @Id
    @Column(name = "UUID", length = 36, nullable = false)
    private String uuid;

    @Column(name = "HO_TEN", length = 500)
    private String hoTen;

    @Column(name = "MA_DINH_DANH", length = 20)
    private String maDinhDanh;

    @Column(name = "GIOI_TINH_ID")
    private Integer gioiTinhId;

    @Column(name = "DAN_TOC_ID")
    private Integer danTocId;

    @Column(name = "GHI_CHU", length = 200)
    private String ghiChu;
}
