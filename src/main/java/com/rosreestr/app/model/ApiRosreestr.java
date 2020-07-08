package com.rosreestr.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiRosreestr implements Serializable {
  int found;
  private List<Objects> objectsList;

  public ApiRosreestr(int found, List<Objects> objectsList) {
    this.found = found;
    this.objectsList = objectsList;
  }
}
