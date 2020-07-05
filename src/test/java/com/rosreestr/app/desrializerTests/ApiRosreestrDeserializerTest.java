package com.rosreestr.app.desrializerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.rosreestr.app.Model.ApiRosreestr;
import com.rosreestr.app.deserialize.ApiRosreestrDeserializer;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ApiRosreestrDeserializerTest {
  private final File okValue = new File("src/main/resources/TestJsons/response-apiros.json");
  private final File nullValueFile =
      new File("src/main/resources/TestJsons/response-apirosreestr-found0.json");
  private ObjectMapper mapper;

  @BeforeEach
  void initUseCase() {
    mapper = new ObjectMapper();
    SimpleModule module = new SimpleModule();
    module.addDeserializer(ApiRosreestr.class, new ApiRosreestrDeserializer());
    mapper.registerModule(module);
  }

  @Test
  void ShouldReturnValidApiRosreestrObject() throws IOException {
    ApiRosreestr readValue = mapper.readValue(okValue, ApiRosreestr.class);
    assertThat(readValue, notNullValue());
    assertThat(readValue.getFound(), is(18));
    assertThat(readValue.getObjectsList().size(), is(18));
    assertThat(readValue.getObjectsList(), not(empty()));
  }

  @Test
  void ShouldReturnNullApiRosreestrObject() throws IOException {
    ApiRosreestr readValue = mapper.readValue(nullValueFile, ApiRosreestr.class);
    assertThat(readValue, Matchers.nullValue());
  }
}
