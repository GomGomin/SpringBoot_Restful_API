package com.namhun.hello.preword.info;

import com.namhun.hello.preword.info.model.Project;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class InfoController {

    @GetMapping("/info")
    public Project projectInfo() {
        Project project = new Project();
        project.projectName = "preword";
        project.author = "namhun";
        project.createadDate = new Date();
        return project;
    }

    @GetMapping("/info2")
    public String customJson() {
        JsonO
        return project;
    }
}
