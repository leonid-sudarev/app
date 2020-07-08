package com.rosreestr.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Objects implements Serializable {
  String ADDRESS;
  String CADNOMER;
  String TYPE;
  String AREA;

  public Objects(String ADDRESS, String CADNOMER, String AREA) {
    this.ADDRESS = ADDRESS;
    this.CADNOMER = CADNOMER;
    this.AREA = AREA;
  }

  public Objects(String ADDRESS, String CADNOMER) {
    this.ADDRESS = ADDRESS;
    this.CADNOMER = CADNOMER;
  }
}
