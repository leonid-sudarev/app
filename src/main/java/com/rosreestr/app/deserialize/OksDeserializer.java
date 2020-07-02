package com.rosreestr.app.deserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.rosreestr.app.Utils.Utils;

import java.io.IOException;

public class OksDeserializer extends JsonDeserializer<Oks> {

  @Override
  public Oks deserialize(JsonParser jp, DeserializationContext ctxt) {
    ObjectCodec oc = jp.getCodec();
    JsonNode node ;
    try {
      node = oc.readTree(jp);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
    JsonNode feature = node.get("feature").get("attrs");

    try {
      Oks oks =
          new Oks(
              feature.get("address").asText(),
              feature.get("floors").asInt(),
              Utils.getMeasureunit(feature.get("cad_unit").asInt()),
              feature.get("cad_cost").asInt(),
              feature.get("id").asText());
      return oks;
    } catch (Exception e) {
      return null;
    }
  }
}
