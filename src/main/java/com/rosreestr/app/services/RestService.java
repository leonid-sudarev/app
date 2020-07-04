package com.rosreestr.app.services;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.Duration;
import java.util.Collections;

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

  public String createPostWithObject(String address) {
    String url = "https://cleaner.dadata.ru/api/v1/clean/address";

    String headerAuthName = "Authorization";
    String headerAuthValue = "Token c7b88c7d1cee9a3f4deeced17a4197bebc2d3031";

    String headerXSecretName = "X-Secret";
    String headerXSecretValue = "a090e73aba767131c1395563d8746a4fb527b449";

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


}
