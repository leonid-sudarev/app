package com.rosreestr.app.controller;

import com.rosreestr.app.Model.LandPlot;
import com.rosreestr.app.Model.Oks;
import com.rosreestr.app.services.LandPlotService;
import com.rosreestr.app.services.OksService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Rosreestr {

  private OksService oksService;
  private LandPlotService landPlotService;

  public Rosreestr(OksService oksService, LandPlotService landPlotService) {
    this.oksService = oksService;
    this.landPlotService = landPlotService;
  }



  @GetMapping("/getOks")
  ResponseEntity<Oks> getOks(@RequestParam("cadNum") String cadNum) {
    Oks info = oksService.getOks(cadNum);
    return info != null ? ResponseEntity.ok(info) : ResponseEntity.notFound().build();
  }

  @GetMapping("/getPlot")
  ResponseEntity<LandPlot> getPlot(@RequestParam("cadNum") String cadNum) {
    LandPlot info = landPlotService.getLandPlot(cadNum);
    return info != null ? ResponseEntity.ok(info) : ResponseEntity.notFound().build();
  }

  @GetMapping("/getByAddress")
  ResponseEntity<LandPlot> getByAddress(@RequestParam("address") String cadNum) {
    LandPlot info = landPlotService.getLandPlot(cadNum);
    return info != null ? ResponseEntity.ok(info) : ResponseEntity.notFound().build();
  }

  @GetMapping("/getStatus")
  ResponseEntity<String> getStatus() {

    return ResponseEntity.ok("Status");
  }

}
