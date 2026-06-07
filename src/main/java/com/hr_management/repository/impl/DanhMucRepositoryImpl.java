package com.hr_management.repository.impl;

import com.hr_management.dto.request.DanhMucRequest;
import com.hr_management.dto.response.DanhMucResponse;
import com.hr_management.repository.DanhMucRepository;
import com.hr_management.utils.TableName;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DanhMucRepositoryImpl implements DanhMucRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public DanhMucResponse findById(TableName tableName, Long id) {
        SqlParameterSource map = new MapSqlParameterSource("id", id);
        String sql = "SELECT * FROM " + tableName.getTableName() + " WHERE id = :id";
        var list = namedParameterJdbcTemplate.query(sql, map,  BeanPropertyRowMapper.newInstance(DanhMucResponse.class));
        return list.isEmpty() ? null : list.getFirst();
    }

    @Override
    public List<DanhMucResponse> findAll(TableName tableName) {
        String sql = "SELECT * FROM " + tableName.getTableName()  + " ORDER BY id";
        return namedParameterJdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(DanhMucResponse.class));
    }

    @Override
    public void themMoi(DanhMucRequest request, TableName tableName) {
        String sql = "INSERT INTO " + tableName.getTableName() + " (TEN, MA, MO_TA) VALUES (:ten, :ma, :moTa)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("ten", request.getTen());
        params.addValue("ma", request.getMa());
        params.addValue("moTa", request.getMoTa());
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void capNhat(DanhMucRequest request, TableName tableName) {
        String sql = "UPDATE " + tableName.getTableName() + " SET TEN = :ten, MA = :ma, MO_TA = :moTa WHERE ID = :id"
                ;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("ten", request.getTen());
        params.addValue("ma", request.getMa());
        params.addValue("moTa", request.getMoTa());
        params.addValue("id", request.getId());
        namedParameterJdbcTemplate.update(sql, params);
    }


}
