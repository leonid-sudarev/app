package com.rosreestr.app.desrializerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.rosreestr.app.deserialize.LandPlotDeserializer;
import com.rosreestr.app.model.LandPlot;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LandPlotDeserializerTest {
  private final File okValue = new File("src/main/resources/TestJsons/response-645-1.json");
  private final File nullValueFile =
      new File("src/main/resources/TestJsons/response-rosreestr-null.json");
  private ObjectMapper mapper;

  @BeforeEach
  void initUseCase() {
    mapper = new ObjectMapper();
    SimpleModule module = new SimpleModule();
    module.addDeserializer(LandPlot.class, new LandPlotDeserializer());
    mapper.registerModule(module);
  }

  @Test
  void ShouldReturnValidLandPlotObject() throws IOException {
    LandPlot readValue = mapper.readValue(okValue, LandPlot.class);
    assertThat(readValue, notNullValue());
    assertThat(readValue.getId(), equalTo("36:16:5500001:645"));
    assertThat(readValue.getCad_cost(), equalTo(195750));
  }

  @Test
  void ShouldReturnNullLandPlotObject() throws IOException {
    LandPlot readValue = mapper.readValue(nullValueFile, LandPlot.class);
    assertThat(readValue, Matchers.nullValue());
  }
}
