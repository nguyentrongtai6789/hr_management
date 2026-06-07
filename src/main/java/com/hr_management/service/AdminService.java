package com.hr_management.service;

import com.hr_management.dto.request.CongViecRequest;
import com.hr_management.dto.request.DanhMucRequest;
import com.hr_management.dto.request.TongQuanCongViecAdmRequest;
import com.hr_management.dto.response.CongViecResponse;
import com.hr_management.utils.TableName;

import java.util.List;

public interface AdminService {
    List<TongQuanCongViecAdmRequest> getTongQuanCongViecAdm(String thoiGian);

    List<CongViecResponse> findAll(CongViecRequest request, Integer page, Integer size);

    CongViecResponse findOneByUuid(String uuid);

    void updateCongViecByUuid(CongViecRequest request);

    void themMoiDanhMuc(DanhMucRequest request, TableName tableName);

    void capNhatDanhMuc(DanhMucRequest request, TableName tableName);

}
