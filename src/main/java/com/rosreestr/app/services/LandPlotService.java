package com.rosreestr.app.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.rosreestr.app.Model.LandPlot;
import com.rosreestr.app.deserialize.LandPlotDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LandPlotService {
  private RestService restService;

  public LandPlotService(RestService restService) {
    this.restService = restService;
  }

  public LandPlot getLandPlotRosreestr(String cadNum) {
    // LandPlotMapper
    ObjectMapper landPlotMapper = new ObjectMapper();
    SimpleModule landPlotModule = new SimpleModule();
    landPlotModule.addDeserializer(LandPlot.class, new LandPlotDeserializer());
    landPlotMapper.registerModule(landPlotModule);
    LandPlot landPlot;
    try {
      landPlot =
          landPlotMapper.readValue(
              restService.getPlainJson("https://pkk.rosreestr.ru/api/features/1/" + cadNum),
              LandPlot.class);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      log.warn("JsonProcessingException");
      return null;
    }
    log.debug("deser landPlotMapper " + landPlot);
    return landPlot;
  }

  public LandPlot getLandPlotApirosreestr(String cadNum) {
    // LandPlotMapper
    // FIXME: 7/3/20
    return new LandPlot();
  }
}
