package com.hr_management.repository.impl;

import com.hr_management.dto.request.CongViecRequest;
import com.hr_management.dto.request.TienDoCongViecRequest;
import com.hr_management.dto.response.CongViecResponse;
import com.hr_management.repository.CongViecRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CongViecRepositoryImpl implements CongViecRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<TienDoCongViecRequest> getTienDoCongViec(String nhanSuId, LocalDateTime startDate, LocalDateTime endDate) {
        String sql = """
                select b.TEN    as trangThai,
                       b.id     as trangThaiId,
                       count(1) as soLuong
                from cong_viec a
                         left join trang_thai_cong_viec b on a.TRANG_THAI_ID = b.ID
                where nhan_su_id = :nhanSuId and a.NGAY_BAT_DAU >= :startDate and a.NGAY_BAT_DAU < :endDate
                group by TRANG_THAI_ID
                """;
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("nhanSuId", nhanSuId);
        sqlParameterSource.addValue("startDate", startDate);
        sqlParameterSource.addValue("endDate", endDate);
        return namedParameterJdbcTemplate.query(sql, sqlParameterSource, new BeanPropertyRowMapper<>(TienDoCongViecRequest.class));
    }

    @Override
    public void insertCongViec(CongViecRequest request) {
        String sql = """
                INSERT INTO cong_viec (
                    noi_dung_cong_viec,
                    loai_cong_viec_id,
                    ma_cong_viec,
                    no_luc_thuc_hien,
                    trang_thai_id,
                    ngay_bat_dau,
                    ngay_ket_thuc
                )
                VALUES (
                    :noiDungCongViec,
                    :loaiCongViecId,
                    :maCongViec,
                    :noLucThucHien,
                    :trangThaiId,
                    :ngayBatDau,
                    :ngayKetThuc
                )
                """;
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("noiDungCongViec", request.getNoiDungCongViec())
                .addValue("loaiCongViecId", request.getLoaiCongViecId())
                .addValue("maCongViec", request.getMaCongViec())
                .addValue("noLucThucHien", request.getNoLucThucHien())
                .addValue("trangThaiId", request.getTrangThaiId())
                .addValue("ngayBatDau", request.getNgayBatDau())
                .addValue("ngayKetThuc", request.getNgayKetThuc());
        namedParameterJdbcTemplate.update(sql, param);
    }

    @Override
    public void updateCongViecByUuid(CongViecRequest request) {
        StringBuilder sql = new StringBuilder(" UPDATE cong_viec SET ");
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("uuid", request.getUuid());
        List<String> updates = new ArrayList<>();
        if (request.getNoiDungCongViec() != null) {
            updates.add("noi_dung_cong_viec = :noiDungCongViec");
            param.addValue("noiDungCongViec", request.getNoiDungCongViec());
        }
        if (request.getLoaiCongViecId() != null) {
            updates.add("loai_cong_viec_id = :loaiCongViecId");
            param.addValue("loaiCongViecId", request.getLoaiCongViecId());
        }
        if (request.getMaCongViec() != null) {
            updates.add("ma_cong_viec = :maCongViec");
            param.addValue("maCongViec", request.getMaCongViec());
        }
        if (request.getNoLucThucHien() != null) {
            updates.add("no_luc_thuc_hien = :noLucThucHien");
            param.addValue("noLucThucHien", request.getNoLucThucHien());
        }
        if (request.getTrangThaiId() != null) {
            updates.add("trang_thai_id = :trangThaiId");
            param.addValue("trangThaiId", request.getTrangThaiId());
        }
        if (request.getNgayBatDau() != null) {
            updates.add("ngay_bat_dau = :ngayBatDau");
            param.addValue("ngayBatDau", request.getNgayBatDau());
        }
        if (request.getNgayKetThuc() != null) {
            updates.add("ngay_ket_thuc = :ngayKetThuc");
            param.addValue("ngayKetThuc", request.getNgayKetThuc());
        }
        sql.append(String.join(", ", updates));
        sql.append(" WHERE uuid = HEXTORAW(:uuid) ");
        namedParameterJdbcTemplate.update(sql.toString(), param);
    }

    @Override
    public void deleteCongViecByUuid(String uuid) {
        String sql = """
                DELETE FROM cong_viec
                WHERE uuid = HEXTORAW(:uuid)
                """;
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("uuid", uuid);
        namedParameterJdbcTemplate.update(sql, param);
    }

    @Override
    public CongViecResponse findOneByUuid(String uuid) {
        String sql = """
                    SELECT
                        RAWTOHEX(cv.uuid) AS uuid,
                        cv.noi_dung_cong_viec AS noiDungCongViec,
                        cv.loai_cong_viec_id AS loaiCongViecId,
                        lcv.ten AS loaiCongViecTen,
                        cv.ma_cong_viec AS maCongViec,
                        cv.no_luc_thuc_hien AS noLucThucHien,
                        cv.trang_thai_id AS trangThaiId,
                        ttcv.ten AS trangThaiTen,
                        cv.ngay_bat_dau AS ngayBatDau,
                        TO_CHAR(cv.ngay_bat_dau,'DD/MM/YYYY HH24:MI') AS ngayBatDauString,
                        cv.ngay_ket_thuc AS ngayKetThuc,
                        TO_CHAR(cv.ngay_ket_thuc,'DD/MM/YYYY HH24:MI') AS ngayKetThucString
                    FROM cong_viec cv
                    LEFT JOIN loai_cong_viec lcv
                        ON cv.loai_cong_viec_id = lcv.id
                    LEFT JOIN trang_thai_cong_viec ttcv
                        ON cv.trang_thai_id = ttcv.id
                    WHERE cv.uuid = HEXTORAW(:uuid)
                """;
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("uuid", uuid);
        var list = namedParameterJdbcTemplate.query(sql, param, BeanPropertyRowMapper.newInstance(CongViecResponse.class));
        return list.isEmpty() ? null : list.getFirst();
    }

    @Override
    public List<CongViecResponse> findAll(CongViecRequest request, Integer page, Integer size) {
        int offset = page * size;
        StringBuilder dataSql = new StringBuilder("""
                    SELECT
                        RAWTOHEX(cv.uuid) AS uuid,
                        cv.noi_dung_cong_viec AS noiDungCongViec,
                        cv.loai_cong_viec_id AS loaiCongViecId,
                        lcv.ten AS loaiCongViecTen,
                        cv.ma_cong_viec AS maCongViec,
                        cv.no_luc_thuc_hien AS noLucThucHien,
                        cv.trang_thai_id AS trangThaiId,
                        ttcv.ten AS trangThaiTen,
                        cv.ngay_bat_dau AS ngayBatDau,
                        TO_CHAR(cv.ngay_bat_dau,'DD/MM/YYYY HH24:MI') AS ngayBatDauString,
                        cv.ngay_ket_thuc AS ngayKetThuc,
                        TO_CHAR(cv.ngay_ket_thuc,'DD/MM/YYYY HH24:MI') AS ngayKetThucString
                    FROM cong_viec cv
                    LEFT JOIN loai_cong_viec lcv
                        ON cv.loai_cong_viec_id = lcv.id
                    LEFT JOIN trang_thai_cong_viec ttcv
                        ON cv.trang_thai_id = ttcv.id
                    WHERE 1=1
                """);
        StringBuilder countSql = new StringBuilder("""
                    SELECT COUNT(*)
                    FROM cong_viec cv
                    WHERE 1=1
                """);
        MapSqlParameterSource params = new MapSqlParameterSource();
        // search nội dung
        if (request.getNoiDungCongViec() != null && !request.getNoiDungCongViec().isBlank()) {
            dataSql.append(" AND LOWER(cv.noi_dung_cong_viec) LIKE LOWER(:noiDung) ");
            countSql.append(" AND LOWER(cv.noi_dung_cong_viec) LIKE LOWER(:noiDung) ");
            params.addValue("noiDung", "%" + request.getNoiDungCongViec() + "%");
        }
        // filter trạng thái
        if (request.getTrangThaiId() != null) {
            dataSql.append(" AND cv.trang_thai_id = :trangThaiId ");
            countSql.append(" AND cv.trang_thai_id = :trangThaiId ");
            params.addValue("trangThaiId", request.getTrangThaiId());
        }
        // sort
        dataSql.append(" ORDER BY cv.ngay_bat_dau DESC ");
        // pagination
        dataSql.append(" OFFSET :offset ROWS FETCH NEXT :size ROWS ONLY ");
        params.addValue("offset", offset);
        params.addValue("size", size);
        List<CongViecResponse> list = namedParameterJdbcTemplate.query(
                dataSql.toString(),
                params,
                BeanPropertyRowMapper.newInstance(CongViecResponse.class)
        );
        Integer total = namedParameterJdbcTemplate.queryForObject(
                countSql.toString(),
                params,
                Integer.class
        );
        // gán tổng bản ghi vào response
        for (CongViecResponse item : list) {
            item.setTongSoBanGhi(total);
        }
        return list;
    }
}
