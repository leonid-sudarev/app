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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    info = apiRosreestrService.getApiRosreestr(address);
    //    try{
    //
    //    } catch (ConnectException e) {
    //      // закончились запросы на apirosreestr(((
    //      return ResponseEntity.badRequest().eTag("No free queries for apirosreestr(((").build();
    //    }

    info.getObjectsList().forEach(System.out::println);
    Out out = apiRosreestrService.getOksByCadnumbers(info);
    return out != null ? ResponseEntity.ok(out) : ResponseEntity.notFound().build();
  }

  @GetMapping("api/v1/getStatus")
  ResponseEntity<String> getStatus() {
    //    Set<HttpMethod> httpMethods = restService.allowedOperations();
    //    return ResponseEntity.ok(httpMethods.toString());
    return ResponseEntity.ok(Server.getInstance().toString());
  }
}
