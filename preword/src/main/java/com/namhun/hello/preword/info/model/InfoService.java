package com.namhun.hello.preword.info.model;

import com.namhun.hello.preword.info.repository.CityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service // Spring Bean으로 등록
@Slf4j
public class InfoService {

    private final CityRepository cityRepository;

    public InfoService(CityRepository cityRepository){
        this.cityRepository = cityRepository;
    }

    public Project getProjectInfo(){

        Project project = new Project();
        project.projectName = "preword";
        project.author ="hello-namhun";
        project.createadDate = new Date();

        return project;
    }

    public List<City> getCityList() {
        return this.cityRepository.findList();
    }

    public List<City> findCityByCodeAndPopulation(String countryCode, int population) {
        log.debug("countryCode = {}, population = {}", countryCode, population);
        return this.cityRepository.findByCountryCodeAndPopulation(countryCode, population);
    }

    public City insert(City city){
        return this.cityRepository.insert(city);
    }

    public Integer updateById(City city){
        log.debug("city id = {}", city.getId());
        return this.cityRepository.updateById(city);
    }

    public Integer deleteById(Integer id){
        log.debug("city id={}", id);
        return this.cityRepository.deleteById(id);
    }

}
