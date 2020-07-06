package com.rosreestr.app.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.rosreestr.app.Model.ApiRosreestr;
import com.rosreestr.app.Model.LandPlot;
import com.rosreestr.app.Model.Oks;
import com.rosreestr.app.Serealize.Out;
import com.rosreestr.app.deserialize.ApiRosreestrDeserializer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.profiler.Profiler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class ApiRosreestrService {
  private DaDataService daDataService;
  private RestService restService;
  private OksService oksService;
  private LandPlotService landPlotService;

  public ApiRosreestr getApiRosreestr(String address) {
    ObjectMapper mapper = new ObjectMapper();
    SimpleModule module = new SimpleModule();
    module.addDeserializer(ApiRosreestr.class, new ApiRosreestrDeserializer());
    mapper.registerModule(module);

    Profiler delayProfiler = new Profiler("DELAY_PROFILER_GET_API_ROSREESTR");
    delayProfiler.start("daDataService.normalizedAddress");
    String normalizedAddress = daDataService.nomalizedAddress(address);
    delayProfiler.start("restService.createPostApiRosreestr");
    String postApiRosreestr = restService.createPostApiRosreestr(normalizedAddress);

    System.out.println(postApiRosreestr);

    ApiRosreestr readValue;
    delayProfiler.start("mapper.readValue(postApiRosreestr, ApiRosreestr.class)");
    try {
      readValue = mapper.readValue(postApiRosreestr, ApiRosreestr.class);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return null;
    }
    delayProfiler.stop().print();
    System.out.println("deser ApiRosreestr " + (readValue));
    return readValue;
  }

  public Out getOksByCadnumbers(ApiRosreestr apiRosreestr) {
    Out out = new Out();
    List<Oks> oksList = new ArrayList<>();
    List<LandPlot> landPlotList = new ArrayList<>();
    Oks oks;
    LandPlot landPlot;
    for (int i = 0; i < apiRosreestr.getFound(); i++) {
      String cadnomer = apiRosreestr.getObjectsList().get(i).getCADNOMER();
      log.info(cadnomer);
      oks = oksService.getOksRosreestr(cadnomer);
      if (oks != null) {
        oksList.add(oks);
        continue;
      }
      landPlot = landPlotService.getLandPlotRosreestr(cadnomer);
      if (landPlot != null) {
        landPlotList.add(landPlot);
      }
    }
    out.setOksList(oksList);
    out.setLandPlotList(landPlotList);

    ObjectMapper resultMapper = new ObjectMapper();
    String resultJson = null;
    try {
      resultJson = resultMapper.writeValueAsString(out);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    System.out.println("Result " + resultJson);

    return out;
  }
}
