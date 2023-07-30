package com.namhun.hello.preword.info;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.namhun.hello.preword.info.model.Project;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class InfoController {

    @GetMapping("/info")
    public Project projectInfo() {
        Project project = new Project();
        project.projectName = "test";
        project.createadDate = new Date();
        project.author = "namhun";
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
}
