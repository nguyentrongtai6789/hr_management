package com.hr_management.repository.impl;

import com.hr_management.dto.request.NhanSuRequest;
import com.hr_management.dto.response.QuanLyNhanSuResponse;
import com.hr_management.repository.QuanLyNhanSuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

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
                       b.USER_NAME as userName,
                       ur.ROLE as roleName,
                       a.GHI_CHU as ghiChu
                from nhan_su a
                left join users b on a.UUID = b.nhan_su_id
                left join dm_gioi_tinh dmGioiTinh on a.GIOI_TINH_ID = dmGioiTinh.id
                left join dm_dan_toc dmDanToc on a.DAN_TOC_ID = dmDanToc.id
                left join users_role ur on b.ID = ur.USER_ID
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

            if (StringUtils.hasText(request.getRoleName())) {
                sql.append(" and ur.ROLE = :roleName");
                params.addValue("roleName", request.getRoleName());
            }
        }

        return namedParameterJdbcTemplate.query(
                sql.toString(),
                params,
                BeanPropertyRowMapper.newInstance(QuanLyNhanSuResponse.class)
        );
    }

    @Override
    public void themMoiNhanSu(NhanSuRequest request) {
        // Insert vào bảng nhan_su
        String sqlInsertNhanSu = """
                INSERT INTO nhan_su (HO_TEN,
                                     MA_DINH_DANH,
                                     GIOI_TINH_ID,
                                     DAN_TOC_ID,
                                     GHI_CHU)
                VALUES (:hoTen,
                        :maDinhDanh,
                        :gioiTinhId,
                        :danTocId,
                        :ghiChu)
                """;
        MapSqlParameterSource paramsNhanSu = new MapSqlParameterSource();
        paramsNhanSu.addValue("hoTen", request.getHoTen());
        paramsNhanSu.addValue("maDinhDanh", request.getMaDinhDanh());
        paramsNhanSu.addValue("gioiTinhId", request.getGioiTinhId());
        paramsNhanSu.addValue("danTocId", request.getDanTocId());
        paramsNhanSu.addValue("ghiChu", request.getGhiChu());
        KeyHolder keyHolderNhanSu = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sqlInsertNhanSu, paramsNhanSu, keyHolderNhanSu);
        // Lấy UUID của nhân sự vừa insert - Query lại bảng dựa trên dữ liệu đặc trưng
        String nhanSuId = namedParameterJdbcTemplate.queryForObject(
                "SELECT UUID FROM nhan_su WHERE MA_DINH_DANH = :maDinhDanh ORDER BY UUID DESC LIMIT 1",
                paramsNhanSu,
                String.class
        );
        // Insert vào bảng users
        String sqlInsertUsers = """
                INSERT INTO users (EMAIL, IS_ACTIVE, PASSWORD, USER_NAME, nhan_su_id)
                VALUES (:email, 1, '$2a$12$GAacUmhBW.n2L3npLIeDfehDlN9IQtoULMVtDrtoRu5Xa1aVjap2O',
                        :username, :nhanSuId)
                """;
        MapSqlParameterSource paramsInsertUsers = new MapSqlParameterSource();
        paramsInsertUsers.addValue("email", request.getEmail());
        paramsInsertUsers.addValue("username", request.getUserName());
        paramsInsertUsers.addValue("nhanSuId", nhanSuId);
        KeyHolder keyHolderUsers = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sqlInsertUsers, paramsInsertUsers, keyHolderUsers);
        // Lấy ID của user vừa insert
        Long userId = Objects.requireNonNull(keyHolderUsers.getKey()).longValue();
        // Insert vào bảng users_role
        String sqlInsertUr = """
                INSERT INTO users_role (USER_ID, ROLE) VALUES (:userId, :roleName)
                """;
        MapSqlParameterSource paramsInsertUr = new MapSqlParameterSource();
        paramsInsertUr.addValue("userId", userId);
        paramsInsertUr.addValue("roleName", request.getRoleName());
        namedParameterJdbcTemplate.update(sqlInsertUr, paramsInsertUr);
    }

    @Override
    public void capNhatNhanSu(NhanSuRequest request) {
        // Cập nhật bảng nhan_su
        String sqlUpdateNhanSu = """
                UPDATE nhan_su
                SET HO_TEN = :hoTen,
                    MA_DINH_DANH = :maDinhDanh,
                    GIOI_TINH_ID = :gioiTinhId,
                    DAN_TOC_ID = :danTocId,
                    GHI_CHU = :ghiChu
                WHERE UUID = (SELECT nhan_su_id FROM users WHERE USER_NAME = :userName and EMAIL = :email LIMIT 1)
                """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("hoTen", request.getHoTen());
        params.addValue("maDinhDanh", request.getMaDinhDanh());
        params.addValue("gioiTinhId", request.getGioiTinhId());
        params.addValue("danTocId", request.getDanTocId());
        params.addValue("ghiChu", request.getGhiChu());
        params.addValue("userName", request.getUserName());
        params.addValue("email", request.getEmail());
        namedParameterJdbcTemplate.update(sqlUpdateNhanSu, params);
//        // Cập nhật bảng users_role
//        String sqlUpdateUsers = """
//                update users_role set ROLE = :roleName
//                                  where USER_ID = (select id from users
//                                                             where USER_NAME = :userName
//                                                               and EMAIL = :email LIMIT 1)
//                """;
//        MapSqlParameterSource paramsUpdateUsers = new MapSqlParameterSource();
//        paramsUpdateUsers.addValue("email", request.getEmail());
//        paramsUpdateUsers.addValue("userName", request.getUserName());
//        paramsUpdateUsers.addValue("roleName", request.getRoleName());
//        namedParameterJdbcTemplate.update(sqlUpdateUsers, paramsUpdateUsers);
    }

    @Override
    public void xoaNhanSu(NhanSuRequest request) {
        String sql = "update users set IS_ACTIVE = 0 where EMAIL = :email and USER_NAME = :userName";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userName", request.getUserName());
        params.addValue("email", request.getEmail());
        namedParameterJdbcTemplate.update(sql, params);
    }
}
