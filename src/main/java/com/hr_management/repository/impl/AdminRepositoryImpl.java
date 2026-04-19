package com.hr_management.repository.impl;

import com.hr_management.dto.request.TongQuanCongViecAdmRequest;
import com.hr_management.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AdminRepositoryImpl implements AdminRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<TongQuanCongViecAdmRequest> getTongQuanCongViecAdm(LocalDateTime ngayBatDau, LocalDateTime ngayKetThuc) {
        String sql = """
                select ns.HO_TEN                                               as tenNhanSu,
                       count(case when cv.TRANG_THAI_ID = 5 then ns.UUID end)  as daHoanThanh,
                       count(case when cv.TRANG_THAI_ID <> 5 then ns.UUID end) as dangThucHien,
                       count(cv.UUID)                                          as tongSoCongViec,
                       count(*) over ()                                        as tongSoNhanSu
                from nhan_su ns
                         left join cong_viec cv on ns.UUID = cv.nhan_su_id
                where 1 = 1
                    and cv.NGAY_BAT_DAU >= :ngayBatDau
                    and cv.NGAY_KET_THUC <= :ngayKetThuc
                group by ns.HO_TEN
                """;
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("ngayBatDau", ngayBatDau);
        map.addValue("ngayKetThuc", ngayKetThuc);
        return namedParameterJdbcTemplate.query(sql, map, BeanPropertyRowMapper.newInstance(TongQuanCongViecAdmRequest.class));
    }
}
