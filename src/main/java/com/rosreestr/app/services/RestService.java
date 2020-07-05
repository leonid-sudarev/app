package com.rosreestr.app.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.Duration;
import java.util.Collections;
import java.util.Set;

@Slf4j
@Service
public class RestService {

  private final RestTemplate restTemplate;

  public RestService(RestTemplateBuilder restTemplateBuilder) {
    // set connection and read timeouts
    this.restTemplate =
        restTemplateBuilder
            .setConnectTimeout(Duration.ofSeconds(5000))
            .setReadTimeout(Duration.ofSeconds(5000))
            .build();
  }

  public static boolean isReachable(String addr, int openPort, int timeOutMillis) {
    // Any Open port on other machine
    // openPort =  22 - ssh, 80 or 443 - webserver, 25 - mailserver etc.
    try (Socket soc = new Socket()) {
      soc.connect(new InetSocketAddress(addr, openPort), timeOutMillis);
      return true;
    } catch (IOException ex) {
      return false;
    }
  }

  public String getPlainJson(String url) {
    return this.restTemplate.getForObject(url, String.class);
  }

  public long getResponseTime(String url) {
    // у pkk.rosreestr.ru заблокированы обращения к порту 7 для пинга
    // пришлось измерять таким образом ...
    long p1 = System.currentTimeMillis();
    this.restTemplate.getForObject(url, String.class);
    return System.currentTimeMillis() - p1;
  }

  public String createPostDaData(String address) {
    String url = "https://cleaner.dadata.ru/api/v1/clean/address";

    String headerAuthName = "Authorization";

//    String headerAuthValue = "${daData.Auth}";
    String headerAuthValue = "Token c7b88c7d1cee9a3f4deeced17a4197bebc2d3031";

    String headerXSecretName = "X-Secret";
//    String headerXSecretValue = "${daData.XSecret}";
    String headerXSecretValue = "a090e73aba767131c1395563d8746a4fb527b449";

    System.out.println(headerXSecretValue);
    System.out.println(headerAuthValue);
    // create headers
    HttpHeaders headers = new HttpHeaders();
    headers.add(headerAuthName, headerAuthValue);
    headers.add(headerXSecretName, headerXSecretValue);

    // set `content-type` header
    headers.setContentType(MediaType.APPLICATION_JSON);
    // set `accept` header
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

    // build the request address should be like this    "Варонеж, Ревалюции, 84"
    HttpEntity<String> entity = new HttpEntity<>("[" + address + "]", headers);

    // send POST request
    return restTemplate.postForObject(url, entity, String.class);
  }

  public String createPostApiRosreestr(String address) {
    String url = "https://apirosreestr.ru/api/cadaster/search";
    String headerTokenName = "Token";
    String headerTokenValue = "${apirosreestr.Token}";
    // create headers
    HttpHeaders headers = new HttpHeaders();
    headers.add(headerTokenName, headerTokenValue);

    // set `content-type` header
    headers.setContentType(MediaType.APPLICATION_JSON);
    // set `accept` header
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

    // build the request address should be like this    "Варонеж, Ревалюции, 84"
    String body = "{\"query\": \"" + address + "\"}";
    log.info(body);
    log.info(headers.toString());
    HttpEntity<String> entity = new HttpEntity<>(body, headers);

    // send POST request
    return restTemplate.postForObject(url, entity, String.class);
  }

  public Set<HttpMethod> allowedOperations() {
    String url = "https://jsonplaceholder.typicode.com/posts";
    String url2 = "https://apirosreestr.ru/";
    String url3 = "https://cleaner.dadata.ru/";
    // send HEAD request
    return this.restTemplate.optionsForAllow(url3);
  }
}
