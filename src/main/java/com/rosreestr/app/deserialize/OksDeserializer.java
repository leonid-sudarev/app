package com.rosreestr.app.deserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.rosreestr.app.model.Oks;
import com.rosreestr.app.utils.Utils;

import java.io.IOException;

public class OksDeserializer extends JsonDeserializer<Oks> {

  @Override
  public Oks deserialize(JsonParser jp, DeserializationContext ctxt) {
    ObjectCodec oc = jp.getCodec();
    JsonNode node;
    try {
      node = oc.readTree(jp);
    } catch (IOException e) {
      return null;
    }
    JsonNode feature = node.get("feature").get("attrs");

    try {
      return new Oks(
          feature.get("address").asText(),
          feature.get("floors").asInt(),
          Utils.getMeasureUnitFromIntValue(feature.get("cad_unit").asInt()), // преобразует коды в единицы измерения
          feature.get("cad_cost").asInt(),
          feature.get("id").asText());
    } catch (Exception e) {
      return null;
    }
  }
}
