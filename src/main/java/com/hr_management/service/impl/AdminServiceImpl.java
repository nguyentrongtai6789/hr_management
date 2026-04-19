package com.hr_management.service.impl;

import com.hr_management.dto.request.CongViecRequest;
import com.hr_management.dto.request.TongQuanCongViecAdmRequest;
import com.hr_management.dto.response.CongViecResponse;
import com.hr_management.repository.AdminRepository;
import com.hr_management.repository.CongViecRepository;
import com.hr_management.service.AdminService;
import com.hr_management.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;

    private final CongViecRepository congViecRepository;

    private final DateUtils dateUtils;

    @Override
    public List<TongQuanCongViecAdmRequest> getTongQuanCongViecAdm(String thoiGian) {
        DateUtils.TimeRange range = dateUtils.fromMonth(thoiGian);
        return adminRepository.getTongQuanCongViecAdm(range.start(), range.end());
    }

    @Override
    public List<CongViecResponse> findAll(CongViecRequest request, Integer page, Integer size) {
        return congViecRepository.findAll(request, "", page, size);
    }

}
