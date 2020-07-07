package com.rosreestr.app.deserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.rosreestr.app.Model.DaData;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
//см. ApiRosreestrDeserializer
@Slf4j
public class DaDataDeserializer extends JsonDeserializer<DaData> {

  @Override
  public DaData deserialize(JsonParser jp, DeserializationContext ctxt) {
    ObjectCodec oc = jp.getCodec();
    JsonNode node;
    try {
      node = oc.readTree(jp);
      log.info(node.toString());
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }

    try {
      DaData daData = new DaData();
      daData.setRegion(node.get("region").asText());
      daData.setRegion_type_full(node.get("region_type_full").asText());
      daData.setCity_with_type(node.get("city_with_type").asText());
      daData.setStreet(node.get("street").asText());
      daData.setHouse(node.get("house").asText());
      System.out.println(daData);
      return daData;
    } catch (Exception e) {
      e.printStackTrace();
      log.warn("noData-NPE");
      return null;
    }
  }
}
