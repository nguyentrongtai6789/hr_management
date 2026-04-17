package com.hr_management.repository;

import com.hr_management.dto.request.CongViecRequest;
import com.hr_management.dto.request.TienDoCongViecRequest;
import com.hr_management.dto.response.CongViecResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface CongViecRepository {
    List<TienDoCongViecRequest> getTienDoCongViec(String nhanSuId, LocalDateTime startDate, LocalDateTime endDate);

    void insertCongViec(CongViecRequest congViecRequest);

    void updateCongViecByUuid(CongViecRequest request);

    void deleteCongViecByUuid(String uuid);

    CongViecResponse findOneByUuid(String uuid, String nhanSuId);

    List<CongViecResponse> findAll(CongViecRequest congViecRequest, String nhanSuId, Integer page, Integer size);
}
