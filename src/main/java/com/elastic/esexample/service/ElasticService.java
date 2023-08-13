package com.elastic.esexample.service;

import com.elastic.esexample.model.ElasticModel;
import com.elastic.esexample.model.WebInfo;
import com.elastic.esexample.repository.ElasticRepository;
import com.elastic.esexample.repository.WebInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ElasticService {

    private final ElasticRepository elasticRepository;
    private final WebInfoRepository webInfoRepository;

    public void findValue() {
        List<ElasticModel> model = elasticRepository.findByName("kadun");
        System.out.println("ID = " + model.get(0).getId());
        System.out.println("Name = " + model.get(0).getName());
    }

    public void findWebInfo() {
        List<WebInfo> byTs = webInfoRepository.findByTs(Instant.now().getEpochSecond() - 3600L, Instant.now().getEpochSecond());
        for (WebInfo info : byTs) {
            log.info("info.getTs() = {}", info.getTs());
            log.info("info.getMainDomain() = {}", info.getMainDomain());
        }
    }

}
