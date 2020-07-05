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
  // Хотел внести материал стен но нет описания что значат возвращаемые параметры.
  // "elements_construct": [
  //        {
  //          "wall": "061001005000",
  //          "refID": 8620603
  //        }
  //      ],

  public Oks(String address, Integer floors, String cad_unit, Integer cad_cost, String id) {
    this.address = address;
    this.floors = floors;
    this.cad_unit = cad_unit;
    this.cad_cost = cad_cost;
    this.id = id;
  }
}
