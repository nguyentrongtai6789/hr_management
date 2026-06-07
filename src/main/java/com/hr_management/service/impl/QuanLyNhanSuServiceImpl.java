package com.hr_management.service.impl;

import com.hr_management.dto.request.NhanSuRequest;
import com.hr_management.dto.response.QuanLyNhanSuResponse;
import com.hr_management.repository.QuanLyNhanSuRepository;
import com.hr_management.service.QuanLyNhanSuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuanLyNhanSuServiceImpl implements QuanLyNhanSuService {
    private final QuanLyNhanSuRepository quanLyNhanSuRepository;

    @Override
    public List<QuanLyNhanSuResponse> getAllNhanSu(NhanSuRequest request) {
        return quanLyNhanSuRepository.findAllNhanSu(request);
    }

    @Override
    public void themMoiNhanSu(NhanSuRequest request) {
        quanLyNhanSuRepository.themMoiNhanSu(request);
    }
}
