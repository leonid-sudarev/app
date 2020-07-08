package com.rosreestr.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DaData implements Serializable {
  String region;
  String region_type_full;
  String city_with_type;
  String street;
  String house;
  String result;

  @Override
  public String toString() {
    return result;
  }
}
