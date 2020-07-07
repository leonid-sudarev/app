package com.rosreestr.app.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Oks implements Serializable {
  String id;
  String address;
  Integer floors;
  String cad_unit;
  Integer cad_cost;
  String AdditionalInformation;

  public Oks(String address, Integer floors, String cad_unit, Integer cad_cost, String id) {
    this.address = address;
    this.floors = floors;
    this.cad_unit = cad_unit;
    this.cad_cost = cad_cost;
    this.id = id;
  }
}
