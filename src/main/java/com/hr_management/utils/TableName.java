package com.hr_management.utils;

import lombok.Getter;

@Getter
public enum TableName {

    LOAI_CONG_VIEC("LOAI_CONG_VIEC"),
    TRANG_THAI_CONG_VIEC("TRANG_THAI_CONG_VIEC"),
    SAN_PHAM("SAN_PHAM");

    private final String tableName;

    TableName(String tableName) {
        this.tableName = tableName;
    }

}