package com.hr_management.repository;

import com.hr_management.dto.response.DanhMucResponse;
import com.hr_management.utils.TableName;

import java.util.List;

public interface DanhMucRepository {
    DanhMucResponse findById(TableName tableName, Long id);

    List<DanhMucResponse> findAll(TableName tableName);
}
