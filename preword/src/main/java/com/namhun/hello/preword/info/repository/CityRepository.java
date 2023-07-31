package com.namhun.hello.preword.info.repository;

import com.namhun.hello.preword.info.model.City;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class CityRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CityRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<City> findList() {
        String sql = "select * from city limit 1000";

        log.debug("query : {}", sql);

        RowMapper<City> cityMapper = (rs, rowNum) -> {
            City city = new City();
            city.setId(rs.getInt("ID"));
            city.setName(rs.getString("Name"));
            city.setCountryCode(rs.getString("CountryCode"));
            city.setDistrict(rs.getString("district"));
            city.setPopulation(rs.getInt("population"));
            return city;
        };
        return namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource(), cityMapper);
    }
}