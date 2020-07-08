package com.rosreestr.app.controller.v1;

import com.rosreestr.app.model.ApiRosreestr;
import com.rosreestr.app.model.LandPlot;
import com.rosreestr.app.model.Oks;
import com.rosreestr.app.model.Server;
import com.rosreestr.app.serialize.Out;
import com.rosreestr.app.services.ApiRosreestrService;
import com.rosreestr.app.services.DaDataService;
import com.rosreestr.app.services.LandPlotService;
import com.rosreestr.app.services.OksService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.profiler.Profiler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
public class Rosreestr {
  private final DaDataService daDataService;
  private final OksService oksService;
  private final LandPlotService landPlotService;
  private final ApiRosreestrService apiRosreestrService;


  /**
   * Метод получающий данные объекта по кадастровому номеру
   *
   * @param cadNum кадастровый номер объекта
   * @return "id": "address": "cad_unit": "cad_cost": "additionalInformation": "floors":
   */
  @GetMapping("api/v1/getOks")
  ResponseEntity<Oks> getOks(@RequestParam("cadNum") String cadNum) {
    Oks info;

    if (isPrimaryServerWorks()) {
      log.info("MainServerInUse");
      info = oksService.getOksRosreestr(cadNum);
    } else {
      if (isSecondaryServerWorks()) {
        log.info("SecondaryServerInUse");
        info = apiRosreestrService.getOks(cadNum);
      } else {
        info = null;
      }
    }
    return info != null ? ResponseEntity.ok(info) : ResponseEntity.notFound().build();
  }

  /**
   * Метод получающий данные земельного участока по кадастровому номеру
   *
   * @param cadNum кадастровый номер участка
   * @return "id": "address": "cad_unit": "cad_cost": "additionalInformation":
   */
  @GetMapping("api/v1/getPlot")
  ResponseEntity<LandPlot> getPlot(@RequestParam("cadNum") String cadNum) {
    LandPlot info;
    if (isPrimaryServerWorks()) {
      info = landPlotService.getLandPlotRosreestr(cadNum);
    } else if (isSecondaryServerWorks()) {
      info = apiRosreestrService.getLandplot(cadNum);
    } else {
      info = null;
    }
    return info != null ? ResponseEntity.ok(info) : ResponseEntity.notFound().build();
  }

  /**
   * Запрос к серверу нормализации данных
   *
   * @param address адрес который может содержать ошибки, очепятки пользователя, неполную информацию
   * @return строка нормализованных данных
   */
  @GetMapping("api/v1/getCleanAddress")
  ResponseEntity<String> getCleanAddress(@RequestParam("address") String address) {
    String info;
    info = daDataService.getNormalizedAddress(address);
    return info != null ? ResponseEntity.ok(info) : ResponseEntity.notFound().build();
  }

  /**
   * Метод для поиска объектов/участко по указанному адресу
   *
   * @param address адрес который может содержать ошибки, очепятки пользователя, неполную информацию
   * @return Лист участков и лист объектов которые были найдены по данному адресу
   */
  @GetMapping("api/v1/getByAddress")
  ResponseEntity<Out> getByAddress(@RequestParam("address") String address) {
    ApiRosreestr info;
    Out out;
    if (Server.getInstance().getSecondaryServerIsAvailable()){
      // delayProfiler для мониторинга задержек между сервисами
      Profiler delayProfiler = new Profiler("DELAY_PROFILER_GET_BY_ADDRESS");
      delayProfiler.start("apiRosreestrService.getApiRosreestr");
      // нормализация адреса и получение кадастровых номеров по адресу
      info = apiRosreestrService.getApiRosreestr(address);

      delayProfiler.start("apiRosreestrService.getOksByCadnumbers");
      // Получение данных из кадастровых номеров и формирование выходного файла
      out = apiRosreestrService.getOksAndLandPlotByCadnumbers(info);

      delayProfiler.stop().print();
    } else {
      return ResponseEntity.badRequest().eTag("ServerNotAvailable").build();
    }
    return out != null ? ResponseEntity.ok(out) : ResponseEntity.notFound().build();
  }

  /**
   * Статусы серверов
   *
   * @return servers status
   */
  @GetMapping("api/v1/getStatus")
  ResponseEntity<Server> getStatus() {
    return ResponseEntity.ok(Server.getInstance());
  }

  private boolean isSecondaryServerWorks() {
    return Server.getInstance().getSecondaryServerInUse()
            && Server.getInstance().getSecondaryServerIsAvailable();
  }

  private boolean isPrimaryServerWorks() {
    return Server.getInstance().getPrimaryServerInUse()
            && Server.getInstance().getPrimaryServerIsAvailable();
  }
}
