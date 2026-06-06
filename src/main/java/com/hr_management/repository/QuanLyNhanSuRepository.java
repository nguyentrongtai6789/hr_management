package com.hr_management.repository;

import com.hr_management.dto.response.QuanLyNhanSuResponse;
import java.util.List;

public interface QuanLyNhanSuRepository {
    List<QuanLyNhanSuResponse> findAllNhanSu();
}
