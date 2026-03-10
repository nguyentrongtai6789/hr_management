package com.hr_management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DanhMucResponse {
    private Long id;
    private String ten;
    private String ma;
    private String moTa;
}
