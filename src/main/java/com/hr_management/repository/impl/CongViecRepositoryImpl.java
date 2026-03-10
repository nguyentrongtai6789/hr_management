package com.hr_management.repository.impl;

import com.hr_management.dto.request.CongViecRequest;
import com.hr_management.dto.response.CongViecResponse;
import com.hr_management.repository.CongViecRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CongViecRepositoryImpl implements CongViecRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void insertCongViec(CongViecRequest request) {
        String sql = """
                INSERT INTO cong_viec (
                    uuid,
                    noi_dung_cong_viec,
                    loai_cong_viec_id,
                    ma_cong_viec,
                    no_luc_thuc_hien,
                    trang_thai_id,
                    ngay_bat_dau,
                    ngay_ket_thuc
                )
                VALUES (
                    SYS_GUID(),
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
        String sql = """
                UPDATE cong_viec
                SET
                    noi_dung_cong_viec = :noiDungCongViec,
                    loai_cong_viec_id = :loaiCongViecId,
                    ma_cong_viec = :maCongViec,
                    no_luc_thuc_hien = :noLucThucHien,
                    trang_thai_id = :trangThaiId,
                    ngay_bat_dau = :ngayBatDau,
                    ngay_ket_thuc = :ngayKetThuc
                WHERE uuid = HEXTORAW(:uuid)
                """;
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("uuid", request.getUuid())
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
