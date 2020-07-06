package com.rosreestr.app.desrializerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.rosreestr.app.Model.DaData;
import com.rosreestr.app.deserialize.DaDataSecondDeserializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class DaDataDeserializerTest {
  private final File okValue = new File("src/main/resources/TestJsons/response-dadata.json");
  private ObjectMapper mapper;

  @BeforeEach
  void initUseCase() {
    mapper = new ObjectMapper();
    SimpleModule module = new SimpleModule();
    module.addDeserializer(DaData.class, new DaDataSecondDeserializer());
    mapper.registerModule(module);
  }

  @Test
  void ShouldReturnValidDaDataObject() throws IOException {
    DaData[] readValue = mapper.readValue(okValue, DaData[].class);
    System.out.println(readValue[0]);
    assertThat(readValue[0], notNullValue());
    assertThat(readValue[0].getResult(), is("г Воронеж, пр-кт Революции, д 84"));
  }
}
