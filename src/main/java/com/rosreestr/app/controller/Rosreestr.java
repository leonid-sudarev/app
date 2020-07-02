package com.rosreestr.app.controller;

import com.rosreestr.app.deserialize.Oks;
import com.rosreestr.app.services.OksService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Rosreestr {

  private OksService oksService;

  public Rosreestr(OksService oksService) {
    this.oksService = oksService;
  }

  @GetMapping("/getOks")
  ResponseEntity<Oks> get(@RequestParam("cadNum") String cadNum) {
    Oks info = oksService.getOks(cadNum);
    // info.setStatus("Ok");
    return info != null ? ResponseEntity.ok(info) : ResponseEntity.notFound().build();
  }
}
