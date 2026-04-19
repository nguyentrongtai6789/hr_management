package com.hr_management.service;

import com.hr_management.dto.request.TongQuanCongViecAdmRequest;

import java.util.List;

public interface AdminService {
    List<TongQuanCongViecAdmRequest> getTongQuanCongViecAdm(String thoiGian);

}
