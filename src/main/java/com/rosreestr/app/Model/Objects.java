package com.rosreestr.app.Model;

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

  public Objects(String ADDRESS, String CADNOMER) {
    this.ADDRESS = ADDRESS;
    this.CADNOMER = CADNOMER;
  }
}
