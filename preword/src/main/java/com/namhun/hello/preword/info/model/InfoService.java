package com.namhun.hello.preword.info.model;

import com.namhun.hello.preword.info.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service // Spring Bean으로 등록
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
}
