package com.rosreestr.app.desrializerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.rosreestr.app.deserialize.OksDeserializer;
import com.rosreestr.app.model.Oks;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class OksDeserializerTest {
  private final File okValue = new File("src/main/resources/TestJsons/response-645-5.json");
  private final File nullValueFile =
      new File("src/main/resources/TestJsons/response-rosreestr-null.json");
  private ObjectMapper mapper;
  private SimpleModule module;

  @BeforeEach
  void initUseCase() {
    mapper = new ObjectMapper();
    module = new SimpleModule();
    module.addDeserializer(Oks.class, new OksDeserializer());
    mapper.registerModule(module);
  }

  @Test
  void ShouldReturnValidOksObject() throws IOException {
    Oks readValue = mapper.readValue(okValue, Oks.class);
    assertThat(readValue, notNullValue());
    assertThat(readValue.getId(), equalTo("36:16:5500001:1900"));
    assertThat(readValue.getFloors(), equalTo(2));
  }

  @Test
  void ShouldReturnNullOksObject() throws IOException {
    Oks readValue = mapper.readValue(nullValueFile, Oks.class);
    assertThat(readValue, Matchers.nullValue());
  }
}
