package com.hr_management.repository;

import com.hr_management.dto.request.CongViecRequest;
import com.hr_management.dto.request.TienDoCongViecRequest;
import com.hr_management.dto.response.CongViecResponse;

import java.util.List;

public interface CongViecRepository {
    List<TienDoCongViecRequest> getTienDoCongViecByNhanSuId(String nhanSuId);

    void insertCongViec(CongViecRequest congViecRequest);

    void updateCongViecByUuid(CongViecRequest request);

    void deleteCongViecByUuid(String uuid);

    CongViecResponse findOneByUuid(String uuid);

    List<CongViecResponse> findAll(CongViecRequest congViecRequest, Integer page, Integer size);
}
