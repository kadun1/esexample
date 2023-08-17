package com.elastic.esexample.controller;

import com.elastic.esexample.service.ElasticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/elastic")
public class ElasticController {

    private final ElasticService elasticService;

    @GetMapping("/get")
    public String elasticGet() {
        elasticService.findValue();
        elasticService.findWebInfoByQuery();
        elasticService.findWebInfoByCriteria();
        elasticService.aggregateWebInfo();
//        elasticService.okHttpRequest();
        elasticService.webClientRequest();
        return "ok";
    }
}
