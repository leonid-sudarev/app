package com.rosreestr.app.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.rosreestr.app.Model.DaData;
import com.rosreestr.app.deserialize.DaDataSecondDeserializer;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
public class DaDataService {

  private RestService restService;

  public DaDataService(RestService restService) {
    this.restService = restService;
  }

  public String nomalizedAddress(String address) {
    ObjectMapper mapper = new ObjectMapper();
    SimpleModule module = new SimpleModule();
    module.addDeserializer(DaData.class, new DaDataSecondDeserializer());
    mapper.registerModule(module);
    DaData[] readValue = new DaData[1];
    try {
      String postWithObject = restService.createPostDaData(address);
      System.out.println(postWithObject);
      readValue = mapper.readValue(postWithObject, DaData[].class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("deser DaData " + Arrays.toString(readValue));
    return readValue[0].toString();
  }
}
