package com.namhun.hello.preword.info.repository;

import com.namhun.hello.preword.info.model.City;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class CityRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final CityRowMapper cityRowMapper;

    public CityRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.cityRowMapper = new CityRowMapper();
    }

    public List<City> findList() {

        log.debug("query : {}", CitySql.SELECT);

        return namedParameterJdbcTemplate.query(CitySql.SELECT, EmptySqlParameterSource.INSTANCE, this.cityRowMapper);
    }
}