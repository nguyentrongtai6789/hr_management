package com.hr_management.utils;

import lombok.Getter;

@Getter
public enum TableName {

    LOAI_CONG_VIEC("LOAI_CONG_VIEC"),
    TRANG_THAI_CONG_VIEC("TRANG_THAI_CONG_VIEC"),
    SAN_PHAM("SAN_PHAM"),
    DM_DAN_TOC("DM_DAN_TOC"),
    DM_GIOI_TINH("DM_GIOI_TINH"),
    ;


    private final String tableName;

    TableName(String tableName) {
        this.tableName = tableName;
    }

}