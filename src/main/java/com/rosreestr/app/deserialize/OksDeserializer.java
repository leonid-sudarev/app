package com.rosreestr.app.deserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.rosreestr.app.Utils.Utils;

import java.io.IOException;

public class OksDeserializer extends JsonDeserializer<Oks> {

  @Override
  public Oks deserialize(JsonParser jp, DeserializationContext ctxt)
      throws JsonProcessingException, IOException {
    ObjectCodec oc = jp.getCodec();
    JsonNode node = oc.readTree(jp);
    JsonNode feature = node.get("feature");

    return new Oks(
        feature.get("attrs").get("address").asText(),
        feature.get("attrs").get("floors").asInt(),
        Utils.getMeasureunit(feature.get("attrs").get("cad_unit").asInt()),
        feature.get("attrs").get("cad_cost").asInt(),
        feature.get("attrs").get("id").asText());
  }
}
