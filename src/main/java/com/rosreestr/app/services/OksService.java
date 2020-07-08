package com.rosreestr.app.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.rosreestr.app.deserialize.OksDeserializer;
import com.rosreestr.app.model.Oks;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OksService {
  private final RestService restService;

  public OksService(RestService restService) {
    this.restService = restService;
  }

  public Oks getOksRosreestr(String cadNum) {
    // OKS Mapper
    ObjectMapper oksMapper = new ObjectMapper();
    SimpleModule oksMapperModule = new SimpleModule();
    oksMapperModule.addDeserializer(Oks.class, new OksDeserializer());
    oksMapper.registerModule(oksMapperModule);
    Oks oks;
    try {
      oks =
          oksMapper.readValue(
              restService.getPlainJson("https://pkk.rosreestr.ru/api/features/5/" + cadNum),
              Oks.class);
    } catch (JsonProcessingException e) {
      log.error(e.getMessage());
      return null;
    }
    log.info("deser oks " + oks);
    return oks;
  }
}
