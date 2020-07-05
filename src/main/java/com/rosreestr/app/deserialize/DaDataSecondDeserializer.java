package com.rosreestr.app.deserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.rosreestr.app.Model.DaData;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class DaDataSecondDeserializer extends JsonDeserializer<DaData> {

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
    DaData daData = new DaData();
    try {
      daData.setRegion(node.get("region").asText());
      daData.setRegion_type_full(node.get("region_type_full").asText());
      daData.setResult(node.get("result").asText());
      System.out.println(daData);
      return daData;
    } catch (Exception e) {
      e.printStackTrace();
      log.warn("noData-NPE");
      return null;
    }
  }
}
