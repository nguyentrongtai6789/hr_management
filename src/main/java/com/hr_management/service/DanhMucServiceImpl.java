package com.hr_management.service;

import com.hr_management.dto.response.DanhMucResponse;
import com.hr_management.repository.DanhMucRepository;
import com.hr_management.utils.TableName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DanhMucServiceImpl implements DanhMucService {
    private final DanhMucRepository danhMucRepository;

    @Override
    public DanhMucResponse findById(TableName tableName, Long id) {
        return danhMucRepository.findById(tableName, id);
    }

    @Override
    public List<DanhMucResponse> findAll(TableName tableName) {
        return danhMucRepository.findAll(tableName);
    }
}
