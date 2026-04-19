package com.hr_management.repository;

import com.hr_management.dto.request.TongQuanCongViecAdmRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminRepository {
    List<TongQuanCongViecAdmRequest> getTongQuanCongViecAdm(LocalDateTime ngayBatDau, LocalDateTime ngayKetThuc);
}
