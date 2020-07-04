package com.rosreestr.app.Model;

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

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer();
    sb.append(region).append(' ');
    sb.append(region_type_full).append(", ");
    sb.append(city_with_type).append(", ");
    sb.append(street).append(", ");
    sb.append(house);
    return sb.toString();
  }
}
