package com.namhun.hello.preword.info;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.namhun.hello.preword.info.model.City;
import com.namhun.hello.preword.info.model.FileData;
import com.namhun.hello.preword.info.model.InfoService;
import com.namhun.hello.preword.info.model.Project;
import com.namhun.hello.preword.info.storage.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("info")
public class InfoController {

    private InfoService infoService;
    private StorageService storageService;

    @Autowired // 스프링 4.3 버전 이상에서는 생략 가능, BeanFactory에서 InfoService를 찾는다
    public InfoController(InfoService infoService, StorageService storageService) {
        this.infoService = infoService; // 생성자를 주입
        this.storageService = storageService;
    }


    @GetMapping("project")
    public Object projectInfo() {
        log.debug("/info start");
        Project project = infoService.getProjectInfo();

        return project;
    }

    @GetMapping("custom")
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

    @GetMapping("cityList")
    public Object cityList() {
        log.debug("/cityList start");
        List<City> cityList = infoService.getCityList();
        return cityList;
    }

//    @GetMapping("cityListByCode/{countryCode}/{population}")
//    public Object cityByCountryCode(@PathVariable("countryCode") String ctCode, @PathVariable("population") int population){
//        log.debug("countryCode ={}, population {}", ctCode,population);
//        List<City> cityList = infoService.findCityByCodeAndPopulation(ctCode, population);
//        return cityList;
//    }

    @GetMapping("cityListByCode")
    public Object cityByCountryCode(@RequestParam("countryCode") String ctCode,
                                    @RequestParam(value="population", required = false, defaultValue = "0") int population) {
        log.debug("countryCode = {}, population = {}", ctCode, population);
        List<City> cityList = infoService.findCityByCodeAndPopulation(ctCode, population);
        return cityList;
    }

//    @GetMapping("cityAdd/{name}/{countryCode}/{district}/{population}")
//    public Object cityAdd(@PathVariable(value="name") String name
//            , @PathVariable(value="countryCode") String ctCode
//            , @PathVariable(value="district") String district
//            , @PathVariable(value="population") int population) {
//
//        log.debug("name = {}, ctCode = {}, district = {}, population ={}", name, ctCode, district, population);
//
//        return "ok";
//    }

//    @GetMapping("cityAdd")
//    public Object cityAdd(@RequestParam(value="name", required=true) String name
//            , @RequestParam(value="countryCode", required=true) String ctCode
//            , @RequestParam(value="district", required=true) String district
//            , @RequestParam(value="population", required = false, defaultValue = "0") int population) {
//
//        log.debug("name = {}, ctCode = {}, district = {}, population ={}", name, ctCode, district, population);
//
//        return "ok";
//    }
//
//    @GetMapping(value = "cityAdd")
//    public Object cityAdd(City city){
//        log.debug("city = {}", city.toString());
//        return "ok";
//    }

//    @PostMapping("cityAdd")
//    public ResponseEntity<Void> cityAdd(@RequestBody City city) {
//        log.debug("city = {}", city.toString());
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

//    @PostMapping(value="cityAdd")
//    public ResponseEntity<String> cityAdd(String name, String countryCode, String district, Integer population) {
//        log.debug("name = {}, ctCode = {}, district = {}, population ={}", name, countryCode, district, population);
//        return new ResponseEntity<>("", HttpStatus.OK);
//    }

    @PostMapping(value="cityAdd")
    public ResponseEntity<City> cityAdd(@RequestBody City city) {
        try {
            log.debug("city = {}", city.toString());
            return new ResponseEntity<>(infoService.insert(city),HttpStatus.OK);
        }catch (Exception e){
            log.error(e.toString());
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "cityEdit")
    public ResponseEntity<String> cityEdit(@RequestBody City city){
        try {
            log.debug("city = {}", city.toString());
            Integer updatedCnt = infoService.updateById(city);
            return new ResponseEntity<>(String.format("%d updated", updatedCnt), HttpStatus.OK);
        }catch (Exception e){
            log.error(e.toString());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseBody
    @PostMapping(value = "cityDelete")
    public ResponseEntity<String> cityDelete(@RequestParam(value="id") Integer id){
        try{
            log.debug("city id ={}", id);
            Integer deleteCnt = infoService.deleteById(id);
            return new ResponseEntity<>(String.format("%d deleted", deleteCnt), HttpStatus.OK);
        }catch (Exception e){
            log.error(e.toString());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "uploadFile")
    public ResponseEntity<String> uploadFile(MultipartFile file) throws IllegalStateException, IOException{
        if(!file.isEmpty()) {
            log.debug("file org name ={}", file.getOriginalFilename());
            log.debug("file content type ={}",file.getContentType());
            file.transferTo(new File(file.getOriginalFilename()));
        }
        return new ResponseEntity<>("",HttpStatus.OK);
    }

    @PostMapping(value = "upload")
    public ResponseEntity<String> upload(MultipartFile file) throws IllegalStateException, IOException{
        storageService.store(file);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @GetMapping(value = "download")
    public ResponseEntity<Resource> serverFile(@RequestParam(value="filename") String filename){
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping(value="deleteAll")
    public ResponseEntity<String> deleteAll(){
        storageService.deleteAll();
        return new ResponseEntity<>("",HttpStatus.OK);
    }

    @GetMapping("fileList")
    public ResponseEntity<List<FileData>> getListFiles(){
        List<FileData> fileInfos = storageService.loadAll().map(path -> {FileData data = new FileData();
        String filename = path.getFileName().toString();
        data.setFilename(filename);
        String url = MvcUriComponentsBuilder.fromMethodName(InfoController.class, "serveFile",filename).build().toString();
        data.setUrl(url);
        try {
            data.setSize(Files.size(path));
        }catch (IOException e){
            log.error(e.getMessage());
        }
        return data;
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }
}
