package com.namhun.hello.preword.info.repository;

import com.namhun.hello.preword.info.model.City;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class CityRowMapper implements RowMapper<City> {

    @Override
    public City mapRow(ResultSet rs, int rowNum) throws SQLException{
        City city = new City();
        city.setId(rs.getInt("ID"));
        city.setName(rs.getString("Name"));
        city.setCountryCode(rs.getString("countrycode"));
        city.setDistrict(rs.getString("district"));
        city.setPopulation(rs.getInt("population"));
        return city;
    }
}
