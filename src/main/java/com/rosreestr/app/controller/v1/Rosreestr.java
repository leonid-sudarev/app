package com.rosreestr.app.controller.v1;

import com.rosreestr.app.Model.LandPlot;
import com.rosreestr.app.Model.Oks;
import com.rosreestr.app.Model.Server;
import com.rosreestr.app.services.DaDataService;
import com.rosreestr.app.services.LandPlotService;
import com.rosreestr.app.services.OksService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Rosreestr {
  private DaDataService daDataService;
  private OksService oksService;
  private LandPlotService landPlotService;

  public Rosreestr(DaDataService daDataService, OksService oksService, LandPlotService landPlotService) {
    this.daDataService = daDataService;
    this.oksService = oksService;
    this.landPlotService = landPlotService;
  }

  @GetMapping("api/v1/getOks")
  ResponseEntity<Oks> getOks(@RequestParam("cadNum") String cadNum) {
    Oks info = oksService.getOks(cadNum);
    return info != null ? ResponseEntity.ok(info) : ResponseEntity.notFound().build();
  }

  @GetMapping("api/v1/getPlot")
  ResponseEntity<LandPlot> getPlot(@RequestParam("cadNum") String cadNum) {
    LandPlot info = landPlotService.getLandPlotRosreestr(cadNum);
    return info != null ? ResponseEntity.ok(info) : ResponseEntity.notFound().build();
  }

  @GetMapping("api/v1/getByAddress")
  ResponseEntity<String> getByAddress(@RequestParam("address") String address) {

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

    String info ;

    info = daDataService.nomalizedAddress(address);
    return info != null ? ResponseEntity.ok(info) : ResponseEntity.notFound().build();
  }

  @GetMapping("api/v1/getStatus")
  ResponseEntity<String> getStatus() {

    return ResponseEntity.ok(Server.getInstance().toString());
  }

}
