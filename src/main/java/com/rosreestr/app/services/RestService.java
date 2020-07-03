package com.rosreestr.app.services;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.Duration;
import java.util.Set;

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

  public long getResponseTime() {
    // у pkk.rosreestr.ru заблокированы обращения к порту 7 для пинга
    // пришлось измерять таким образом ...
    long p1 = System.currentTimeMillis();
    this.restTemplate.getForObject(
        "https://pkk.rosreestr.ru/api/features/5/36:16:5500001:1900", String.class);
    return System.currentTimeMillis() - p1;
  }

  public HttpHeaders retrieveHeaders(String url) {

    // send HEAD request
    return this.restTemplate.headForHeaders(url);
  }

  public Set<HttpMethod> allowedOperations(String url) {
    // send HEAD request
    return this.restTemplate.optionsForAllow(url);
  }

  public String unknownRequest() {
    try {
      String url = "https://jsonplaceholder.typicode.com/404";
      return this.restTemplate.getForObject(url, String.class);
    } catch (HttpStatusCodeException ex) {
      // raw http status code e.g `404`
      System.out.println(ex.getRawStatusCode());
      // http status code e.g. `404 NOT_FOUND`
      System.out.println(ex.getStatusCode().toString());
      // get response body
      System.out.println(ex.getResponseBodyAsString());
      // get http headers
      HttpHeaders headers = ex.getResponseHeaders();
      System.out.println(headers.get("Content-Type"));
      System.out.println(headers.get("Server"));
    }
    return null;
  }
}
