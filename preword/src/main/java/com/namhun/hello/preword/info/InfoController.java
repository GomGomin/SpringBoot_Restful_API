package com.namhun.hello.preword.info;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.namhun.hello.preword.info.model.City;
import com.namhun.hello.preword.info.model.InfoService;
import com.namhun.hello.preword.info.model.Project;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@Slf4j
public class InfoController {

    private InfoService infoService;

    @Autowired // 스프링 4.3 버전 이상에서는 생략 가능, BeanFactory에서 InfoService를 찾는다
    public InfoController(InfoService infoService) {
        this.infoService = infoService; // 생성자를 주입
    }

    @GetMapping("/info")
    public Object projectInfo() {
        log.debug("/info start");
        Project project = infoService.getProjectInfo();

        return project;
    }

    @GetMapping("/info2")
    public String customJson() {
        JsonObject jsonObject = new JsonObject();

        // jsonObject 객체의 값을 하나씩 넣어준다.
        jsonObject.addProperty("Key", "Value");
        jsonObject.addProperty("projectName", "GsonTest");
        jsonObject.addProperty("author", "namhun");
        jsonObject.addProperty("createDate", new Date().toString());


        //JsonArray을 사용하여 json배열에 값들을 넣어줌
        JsonArray jsonArray = new JsonArray();
        for(int i=0; i<5; i++){
            JsonObject jsonObject1 = new JsonObject();
            jsonObject1.addProperty("prop"+i, i);
            jsonArray.add(jsonObject1);
        }

        //jsonObject에 json값들을 넣어준 json 배열을 추가
        jsonObject.add("follower", jsonArray);


        //문자열로 출력
        return jsonObject.toString();
    }

    @GetMapping("/cityList")
    public Object cityList() {
        log.debug("/cityList start");
        List<City> cityList = infoService.getCityList();
        return cityList;
    }

}
