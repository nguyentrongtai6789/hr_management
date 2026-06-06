package com.hr_management.repository.impl;

import com.hr_management.dto.response.QuanLyNhanSuResponse;
import com.hr_management.repository.QuanLyNhanSuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QuanLyNhanSuRepositoryImpl implements QuanLyNhanSuRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<QuanLyNhanSuResponse> findAllNhanSu() {
        String sql = """
                select a.HO_TEN as hoTen,
                       a.MA_DINH_DANH as maDinhDanh,
                       dmGioiTinh.ten as gioiTinh,
                       dmDanToc.ten as danToc,
                       b.EMAIL as email,
                       b.USER_NAME as userName,
                       ur.role as roleName
                from nhan_su a
                inner join users b on a.UUID = b.nhan_su_id
                inner join dm_gioi_tinh dmGioiTinh on a.GIOI_TINH_ID = dmGioiTinh.id
                inner join dm_dan_toc dmDanToc on a.DAN_TOC_ID = dmDanToc.id
                inner join users_role ur on b.id = ur.user_id
                order by roleName
                """;
        return namedParameterJdbcTemplate.query(
                sql,
                BeanPropertyRowMapper.newInstance(QuanLyNhanSuResponse.class)
        );
    }
}
