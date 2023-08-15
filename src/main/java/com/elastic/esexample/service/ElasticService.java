package com.elastic.esexample.service;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.AggregationBuilders;
import co.elastic.clients.elasticsearch._types.aggregations.SumAggregation;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import com.elastic.esexample.model.ElasticModel;
import com.elastic.esexample.model.WebInfo;
import com.elastic.esexample.repository.ElasticRepository;
import com.elastic.esexample.repository.WebInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ElasticService {

    private final ElasticRepository elasticRepository;
    private final WebInfoRepository webInfoRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    public void findValue() {
        List<ElasticModel> model = elasticRepository.findByName("kadun");
        System.out.println("ID = " + model.get(0).getId());
        System.out.println("Name = " + model.get(0).getName());
    }

    public void findWebInfoByQuery() {
        List<WebInfo> byTs = webInfoRepository.findByTs(Instant.now().getEpochSecond() - 3600L, Instant.now().getEpochSecond());
        for (WebInfo info : byTs) {
            log.info("info.getTs() = {}", info.getTs());
            log.info("info.getMainDomain() = {}", info.getMainDomain());
        }
    }

    public void findWebInfoByCriteria() {
        Criteria criteria = new Criteria("ts")
                .greaterThanEqual(Instant.now().getEpochSecond() - 3600L)
                .and("ts")
                .lessThan(Instant.now().getEpochSecond());
        Query query = new CriteriaQuery(criteria);
        SearchHits<WebInfo> search = elasticsearchOperations.search(query, WebInfo.class);
        search.forEach(info -> {
            WebInfo webInfo = info.getContent();
            System.out.println("webInfo.getMainDomain() = " + webInfo.getMainDomain());
        });
    }

    public void aggregateWebInfo() {


    }

}
