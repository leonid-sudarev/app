package com.rosreestr.app.deserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.rosreestr.app.Model.LandPlot;
import com.rosreestr.app.Utils.Utils;

import java.io.IOException;

public class LandPlotDeserializer extends JsonDeserializer<LandPlot> {

  @Override
  public LandPlot deserialize(JsonParser jp, DeserializationContext ctxt) {
    ObjectCodec oc = jp.getCodec();
    JsonNode node;
    try {
      node = oc.readTree(jp);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
    JsonNode featureAttrs = node.get("feature");
    try {
      return new LandPlot(
          featureAttrs.get("attrs").get("id").asText(),
          featureAttrs.get("attrs").get("address").asText(),
          Utils.getMeasureunit(featureAttrs.get("attrs").get("cad_unit").asInt()),
          featureAttrs.get("attrs").get("cad_cost").asInt());
    } catch (Exception e) {
      return null;
    }
  }
}
