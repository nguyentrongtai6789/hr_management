package com.hr_management.service;

import com.hr_management.dto.request.NhanSuRequest;
import com.hr_management.dto.response.QuanLyNhanSuResponse;
import java.util.List;

public interface QuanLyNhanSuService {
    List<QuanLyNhanSuResponse> getAllNhanSu(NhanSuRequest request);
    void themMoiNhanSu(NhanSuRequest request);
    void capNhatNhanSu(NhanSuRequest request);
    void xoaNhanSu(NhanSuRequest request);
    void moKhoaNhanSu(NhanSuRequest request);

}
