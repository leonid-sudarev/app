package com.rosreestr.app.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.rosreestr.app.deserialize.ApiRosreestrDeserializer;
import com.rosreestr.app.model.ApiRosreestr;
import com.rosreestr.app.model.LandPlot;
import com.rosreestr.app.model.Oks;
import com.rosreestr.app.model.Server;
import com.rosreestr.app.serialize.Out;
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
  private final DaDataService daDataService;
  private final RestService restService;
  private final OksService oksService;
  private final LandPlotService landPlotService;

  public ApiRosreestr getApiRosreestr(String address) {
    ObjectMapper mapper = getObjectMapper();

    // Измеряем задержки для отладки кода
    Profiler delayProfiler = new Profiler("DELAY_PROFILER_GET_API_ROSREESTR");
    delayProfiler.start("daDataService.normalizedAddress");
    // Запрос на нормализацию адресной строки
    String normalizedAddress = daDataService.getNormalizedAddress(address);

    delayProfiler.start("restService.createPostApiRosreestr");
    // Получаем JSON С АПИ
    String postApiRosreestr = restService.createPostApiRosreestr(normalizedAddress);

    log.info("postApiRosreestr ", postApiRosreestr);

    // Парсим JSON в ApiRosreestr
    ApiRosreestr readValue;
    delayProfiler.start("mapper.readValue(postApiRosreestr, ApiRosreestr.class)");
    try {
      readValue = mapper.readValue(postApiRosreestr, ApiRosreestr.class);
    } catch (JsonProcessingException e) {
     log.error(e.getMessage());
      return null;
    }
    // Вывод результата профилирования в консоль
    delayProfiler.stop().print();
    log.info("deser ApiRosreestr " + (readValue));
    return readValue;
  }

  public Out getOksAndLandPlotByCadnumbers(ApiRosreestr apiRosreestr) {
    Out out = new Out();
    List<Oks> oksList = new ArrayList<>();
    List<LandPlot> landPlotList = new ArrayList<>();
    Oks oks;
    LandPlot landPlot;

    // Получаем для каждого кадастрового номера OKS || LandPlot и если они есть
    // то ложим в свои листы
    for (int i = 0; i < apiRosreestr.getFound(); i++) {
      String cadnomer = apiRosreestr.getObjectsList().get(i).getCADNOMER();
      log.info(cadnomer);
      // чекаем номер  как ОКС объект и если он null (не находим) то смотрим В участках
      oks =
          Server.getInstance().getPrimaryServerInUse()
              ? oksService.getOksRosreestr(cadnomer)
              : getOks(cadnomer);
      if (oks != null) {
        oksList.add(oks);
        continue;
      }
      landPlot =
          Server.getInstance().getPrimaryServerInUse()
              ? landPlotService.getLandPlotRosreestr(cadnomer)
              : getLandplot(cadnomer);
      if (landPlot != null) {
        landPlotList.add(landPlot);
      }
    }
    // Собираем объект для пользователя
    if (oksList.isEmpty() && landPlotList.isEmpty()) {
      return null;
    }
    out.setOksList(oksList);
    out.setLandPlotList(landPlotList);
    log.warn(String.valueOf(out));
    return out;
  }

  private ApiRosreestr getDataFromApiRosreestr(String address) {
    ObjectMapper mapper = getObjectMapper();
    // Измеряем задержки для отладки кода
    Profiler delayProfiler = new Profiler("DELAY_PROFILER_GET_OKS_APIROSREESTR");
    delayProfiler.start("restService.createPostApiRosreestr");
    // Формируем запрос
    String apiRosrAddress = address + "\", \"mode\": \"normal";
    // Получаем JSON С АПИ
    String postApiRosreestr = restService.createPostApiRosreestr(apiRosrAddress);
    log.info("postApiRosreestr ", postApiRosreestr);

    // Парсим JSON в ApiRosreestr
    ApiRosreestr readValue;
    try {
      readValue = mapper.readValue(postApiRosreestr, ApiRosreestr.class);
    } catch (JsonProcessingException e) {
      log.error("Парсим JSON в ApiRosreestr" + e.getMessage());
      return null;
    }
    // Вывод результата профилирования в консоль
    delayProfiler.stop().print();
    log.info("deser ApiRosreestr " + (readValue));
    return readValue;
  }

  public Oks getOks(String cadNum) {
    // Переиспользуем код
    ApiRosreestr oksFromApiRosreestr = getDataFromApiRosreestr(cadNum);
    return getOks(oksFromApiRosreestr);
  }

  public LandPlot getLandplot(String cadNum) {
    ApiRosreestr getPlot = getDataFromApiRosreestr(cadNum);
    return getLandPlot(getPlot);
  }
  // Собираем объект для пользователя
  private Oks getOks(ApiRosreestr readValue) {
    Oks info = new Oks();
    try {
      info.setAddress(readValue.getObjectsList().get(0).getADDRESS());
      info.setId(readValue.getObjectsList().get(0).getCADNOMER());
      info.setAdditionalInformation("AREA = " + readValue.getObjectsList().get(0).getAREA());
      return info;
    } catch (NullPointerException e) {
      log.error("readValue is not valid ");
      return null;
    }
  }

  // Собираем объект для пользователя
  private LandPlot getLandPlot(ApiRosreestr readValue) {
    LandPlot info = new LandPlot();
    try {
      info.setAddress(readValue.getObjectsList().get(0).getADDRESS());
      info.setId(readValue.getObjectsList().get(0).getCADNOMER());
      info.setAdditionalInformation("AREA = " + readValue.getObjectsList().get(0).getAREA());
      return info;
    } catch (NullPointerException e) {
      log.error("readValue is not valid ");
      return null;
    }
  }

  private ObjectMapper getObjectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    SimpleModule module = new SimpleModule();
    module.addDeserializer(ApiRosreestr.class, new ApiRosreestrDeserializer());
    mapper.registerModule(module);
    return mapper;
  }
}
