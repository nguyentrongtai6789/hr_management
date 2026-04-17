package com.hr_management.service.impl;

import com.hr_management.config.security.SecurityUtils;
import com.hr_management.dto.request.CongViecRequest;
import com.hr_management.dto.request.TienDoCongViecRequest;
import com.hr_management.dto.response.CongViecResponse;
import com.hr_management.repository.CongViecRepository;
import com.hr_management.service.CongViecService;
import com.hr_management.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CongViecServiceImpl implements CongViecService {

    private final CongViecRepository congViecRepository;

    private final SecurityUtils securityUtils;

    private final DateUtils dateUtils;

    private String getNhanSuId() {
        String id = securityUtils.getCurrentNhanSuId();
        if (id == null) {
            throw new RuntimeException("User chưa login");
        }
        return id;
    }

    @Override
    public List<TienDoCongViecRequest> getTienDoCongViec(String thoiGian) {
        DateUtils.TimeRange range = dateUtils.fromMonth(thoiGian);
        return congViecRepository.getTienDoCongViec(getNhanSuId(), range.start(), range.end());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertCongViec(CongViecRequest request) {
        congViecRepository.insertCongViec(request);
    }

    @Override
    public void updateCongViecByUuid(CongViecRequest request) {
        this.findOneByUuid(request.getUuid());
        congViecRepository.updateCongViecByUuid(request);
    }

    @Override
    public void deleteCongViecByUuid(String uuid) {
        this.findOneByUuid(uuid);
        congViecRepository.deleteCongViecByUuid(uuid);
    }

    @Override
    public CongViecResponse findOneByUuid(String uuid) {
        var congViec = congViecRepository.findOneByUuid(uuid, getNhanSuId());
        if (congViec == null) {
            throw new RuntimeException("Không tìm thấy công việc với id là: " + uuid);
        }
        return congViec;
    }

    @Override
    public List<CongViecResponse> findAll(CongViecRequest congViecRequest, Integer page, Integer size) {
        return congViecRepository.findAll(congViecRequest, getNhanSuId(), page, size);
    }
}
