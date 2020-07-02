package com.rosreestr.app.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.rosreestr.app.deserialize.Oks;
import com.rosreestr.app.deserialize.OksDeserializer;
import org.springframework.stereotype.Service;

@Service
public class OksService {
  private RestService restService;

  public OksService(RestService restService) {
    this.restService = restService;
  }

  public Oks getOks(String cadNum) {
    // OKS Mapper
    ObjectMapper oksMapper = new ObjectMapper();
    SimpleModule oksMapperModule = new SimpleModule();
    oksMapperModule.addDeserializer(Oks.class, new OksDeserializer());
    oksMapper.registerModule(oksMapperModule);
    Oks oks = null;
    try {
      oks =
          oksMapper.readValue(
              restService.getPlainJson("https://pkk.rosreestr.ru/api/features/5/" + cadNum),
              Oks.class);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    System.out.println("deser oks " + oks);
    return oks;
  }
}
