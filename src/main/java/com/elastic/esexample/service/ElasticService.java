package com.elastic.esexample.service;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import com.elastic.esexample.model.ElasticModel;
import com.elastic.esexample.model.WebInfo;
import com.elastic.esexample.repository.ElasticRepository;
import com.elastic.esexample.repository.WebInfoRepository;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Service
public class ElasticService {

//    private final ElasticRepository elasticRepository;
//    private final WebInfoRepository webInfoRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    private OkHttpClient okHttpClient = new OkHttpClient();
    private String auth = "admin" + ":" + "admin";
    private String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
    private final String url = "http://localhost:9200/_plugins/_sql";
    final WebClient client = WebClient.builder()
            .baseUrl(url)
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Basic " + encodedAuth)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    private final String json = """
            {"query": "select sum(pt) from surffy_eum_web_info group by os"}
            """;

    public void findValue() {
//        List<ElasticModel> model = elasticRepository.findByName("kadun");
//        log.info("ID = {}", model.get(0).getId());
//        log.info("Name = {}", model.get(0).getName());
    }

    public void findWebInfoByQuery() {
//        List<WebInfo> byTs = webInfoRepository.findByTs(Instant.now().getEpochSecond() - 3600L, Instant.now().getEpochSecond());
//        for (WebInfo info : byTs) {
//            log.info("info.getTs() = {}", info.getTs());
//            log.info("info.getMainDomain() = {}", info.getMainDomain());
//        }
    }

    public void findWebInfoByCriteria() {
//        Criteria criteria = new Criteria("ts")
//                .greaterThanEqual(Instant.now().getEpochSecond() - 3600L)
//                .and("ts")
//                .lessThan(Instant.now().getEpochSecond());
//        Query query = new CriteriaQuery(criteria);
//        SearchHits<WebInfo> search = elasticsearchOperations.search(query, WebInfo.class);
//        search.forEach(info -> {
//            WebInfo webInfo = info.getContent();
//            System.out.println("webInfo.getMainDomain() = " + webInfo.getMainDomain());
//        });
    }

    public void aggregateWebInfo() {
//        Criteria criteria = new Criteria("ts")
//                .greaterThanEqual(Instant.now().getEpochSecond() - 3600L)
//                .and("ts")
//                .lessThan(Instant.now().getEpochSecond());
//        Query query = new CriteriaQuery(criteria);
//        NativeQuery nativeQuery = NativeQuery.builder()
//                .withAggregation("sum_pt", Aggregation.of(
//                        a -> a.sum(sa -> sa.field("pt"))
//                ))
//                .withAggregation("avg_count", Aggregation.of(
//                        a -> a.avg(aa -> aa.field("count"))
//                ))
//                .withQuery(query)
//                .build();
//
//        SearchHits<WebInfo> search = elasticsearchOperations.search(nativeQuery, WebInfo.class);
//        search.forEach(info -> {
//            WebInfo webInfo = info.getContent();
//            System.out.println("webInfo.getMainDomain() = " + webInfo.getMainDomain());
//        });
    }

    public void aggregateByStringQuery() {
        Query query = new StringQuery("{}");
        SearchHits<WebInfo> search = elasticsearchOperations.search(query, WebInfo.class);
    }

    public void okHttpRequest() {
        RequestBody requestBody = RequestBody.create(json, okhttp3.MediaType.get("JSON"));
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            System.out.println(response.body().string());
        } catch (IOException e) {
            log.error("IOException 발생");
        }
    }

    public String webClientRequest() {

        return client.post()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(json), String.class)
                .retrieve()
                .bodyToMono(String.class)
                .blockOptional(Duration.ofMillis(3000))
                .orElseThrow(() -> new RuntimeException("에러"));
    }
}
