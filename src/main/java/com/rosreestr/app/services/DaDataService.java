package com.rosreestr.app.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.rosreestr.app.deserialize.DaDataSecondDeserializer;
import com.rosreestr.app.model.DaData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Service
public class DaDataService {

  private final RestService restService;

  public DaDataService(RestService restService) {
    this.restService = restService;
  }

  public String getNormalizedAddress(String address) {
    ObjectMapper mapper = new ObjectMapper();
    SimpleModule module = new SimpleModule();
    module.addDeserializer(DaData.class, new DaDataSecondDeserializer());
    mapper.registerModule(module);
    DaData[] readValue = new DaData[1];
    try {
      String postWithObject = restService.createPostDaData(address);
      log.info(postWithObject);
      readValue = mapper.readValue(postWithObject, DaData[].class);
    } catch (IOException e) {
      log.error(e.getMessage());
    }
    log.info("deserialized DaData " + Arrays.toString(readValue));
    return readValue[0].toString();
  }
}
