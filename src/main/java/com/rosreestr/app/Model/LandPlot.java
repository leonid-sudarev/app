package com.rosreestr.app.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LandPlot implements Serializable, Rosr {
  String id;
  String address;
  String cad_unit;
  Integer cad_cost;

  public LandPlot(String id, String address, String cad_unit, Integer cad_cost) {
    this.id = id;
    this.address = address;
    this.cad_unit = cad_unit;
    this.cad_cost = cad_cost;
  }
}
