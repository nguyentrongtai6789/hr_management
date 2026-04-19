package com.hr_management.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TongQuanCongViecAdmRequest {
    private String tenNhanSu;
    private Integer daHoanThanh;
    private Integer dangThucHien;
    private Integer tongSoCongViec;
    private Integer tongSoNhanSu;
}
