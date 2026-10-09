package com.ncbagroup.main.client;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class HttpClient {

    private final RestTemplate restTemplate;
    @Value("${dataflex.baseURL}")
    private String baseURL;

    public HttpClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Retry(name = "dataflexClientRetry", fallbackMethod = "processRequestFallback")
    public String processRequest(String request) {

        log.info("Request to Third Party ::: \n{}", request);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("text/xml; charset=utf-8"));

        HttpEntity<String> requestEntity = new HttpEntity<>(request, headers);

        return restTemplate.postForObject(baseURL, requestEntity, String.class);
    }

    public String processRequestFallback(String request, Throwable e) {
        log.error("All retry attempts exhausted for upstream call. Exception: {}", e.getMessage(), e);
        throw new RestClientException("Internal Server Error");
    }


}
