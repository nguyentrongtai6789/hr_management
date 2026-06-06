package com.hr_management.repository.impl;

import com.hr_management.dto.request.NhanSuRequest;
import com.hr_management.dto.response.QuanLyNhanSuResponse;
import com.hr_management.repository.QuanLyNhanSuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QuanLyNhanSuRepositoryImpl implements QuanLyNhanSuRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<QuanLyNhanSuResponse> findAllNhanSu(NhanSuRequest request) {
        StringBuilder sql = new StringBuilder("""
                select a.HO_TEN as hoTen,
                       a.MA_DINH_DANH as maDinhDanh,
                       dmGioiTinh.ten as gioiTinh,
                       dmDanToc.ten as danToc,
                       b.EMAIL as email,
                       b.USER_NAME as userName
                from nhan_su a
                left join users b on a.UUID = b.nhan_su_id
                left join dm_gioi_tinh dmGioiTinh on a.GIOI_TINH_ID = dmGioiTinh.id
                left join dm_dan_toc dmDanToc on a.DAN_TOC_ID = dmDanToc.id
                where 1 = 1
                """);

        MapSqlParameterSource params = new MapSqlParameterSource();

        if (request != null) {
            if (StringUtils.hasText(request.getHoTen())) {
                sql.append(" and a.HO_TEN like :hoTen");
                params.addValue("hoTen", "%" + request.getHoTen() + "%");
            }

            if (StringUtils.hasText(request.getMaDinhDanh())) {
                sql.append(" and a.MA_DINH_DANH like :maDinhDanh");
                params.addValue("maDinhDanh", "%" + request.getMaDinhDanh() + "%");
            }

            if (request.getGioiTinhId() != null) {
                sql.append(" and a.GIOI_TINH_ID = :gioiTinhId");
                params.addValue("gioiTinhId", request.getGioiTinhId());
            }

            if (request.getDanTocId() != null) {
                sql.append(" and a.DAN_TOC_ID = :danTocId");
                params.addValue("danTocId", request.getDanTocId());
            }

            if (StringUtils.hasText(request.getEmail())) {
                sql.append(" and b.EMAIL like :email");
                params.addValue("email", "%" + request.getEmail() + "%");
            }

            if (StringUtils.hasText(request.getUserName())) {
                sql.append(" and b.USER_NAME like :userName");
                params.addValue("userName", "%" + request.getUserName() + "%");
            }
        }

        return namedParameterJdbcTemplate.query(
                sql.toString(),
                params,
                BeanPropertyRowMapper.newInstance(QuanLyNhanSuResponse.class)
        );
    }
}
