package com.rosreestr.app.controller.v1;

import com.rosreestr.app.Model.ApiRosreestr;
import com.rosreestr.app.Model.LandPlot;
import com.rosreestr.app.Model.Oks;
import com.rosreestr.app.Model.Server;
import com.rosreestr.app.Serealize.Out;
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

  @GetMapping("api/v1/getOks")
  ResponseEntity<Oks> getOks(@RequestParam("cadNum") String cadNum) {
    Oks info = oksService.getOksRosreestr(cadNum);
    return info != null ? ResponseEntity.ok(info) : ResponseEntity.notFound().build();
  }

  @GetMapping("api/v1/getPlot")
  ResponseEntity<LandPlot> getPlot(@RequestParam("cadNum") String cadNum) {
    LandPlot info = landPlotService.getLandPlotRosreestr(cadNum);
    return info != null ? ResponseEntity.ok(info) : ResponseEntity.notFound().build();
  }

  @GetMapping("api/v1/getCleanAddress")
  ResponseEntity<String> getCleanAddress(@RequestParam("address") String address) {

    //    LandPlot info;
    //    if (Server.getInstance().getMainServerInUse()) {
    //      info = landPlotService.getLandPlotRosreestr(cadNum);
    //    } else if (Server.getInstance().getSecondaryServerInUse()) {
    //      // info = //ask for  2ry server API
    //      // TODO: 7/3/20
    //      info = null;
    //    } else {
    //      info = null;
    //    }

    String info;

    info = daDataService.nomalizedAddress(address);
    return info != null ? ResponseEntity.ok(info) : ResponseEntity.notFound().build();
  }

  @GetMapping("api/v1/getByAddress")
  ResponseEntity<Out> getByAddress(@RequestParam("address") String address) {

    //    LandPlot info;
    //    if (Server.getInstance().getMainServerInUse()) {
    //      info = landPlotService.getLandPlotRosreestr(cadNum);
    //    } else if (Server.getInstance().getSecondaryServerInUse()) {
    //      // info = //ask for  2ry server API
    //      // TODO: 7/3/20
    //      info = null;
    //    } else {
    //      info = null;
    //    }
    ApiRosreestr info;
    Profiler delayProfiler = new Profiler("DELAY_PROFILER_GET_BY_ADDRESS");
    delayProfiler.start("apiRosreestrService.getApiRosreestr");
    info = apiRosreestrService.getApiRosreestr(address);
    delayProfiler.start("apiRosreestrService.getOksByCadnumbers");
    Out out = apiRosreestrService.getOksByCadnumbers(info);
    delayProfiler.stop().print();

    return out != null ? ResponseEntity.ok(out) : ResponseEntity.notFound().build();
  }

  @GetMapping("api/v1/getStatus")
  ResponseEntity<String> getStatus() {
    //    Set<HttpMethod> httpMethods = restService.allowedOperations();
    //    return ResponseEntity.ok(httpMethods.toString());
    return ResponseEntity.ok(Server.getInstance().toString());
  }
}
