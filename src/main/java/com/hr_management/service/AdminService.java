package com.hr_management.service;

import com.hr_management.dto.request.CongViecRequest;
import com.hr_management.dto.request.TongQuanCongViecAdmRequest;
import com.hr_management.dto.response.CongViecResponse;

import java.util.List;

public interface AdminService {
    List<TongQuanCongViecAdmRequest> getTongQuanCongViecAdm(String thoiGian);

    List<CongViecResponse> findAll(CongViecRequest request, Integer page, Integer size);

}
