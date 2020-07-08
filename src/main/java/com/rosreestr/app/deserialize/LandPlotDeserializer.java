package com.rosreestr.app.deserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.rosreestr.app.model.LandPlot;
import com.rosreestr.app.utils.Utils;

import java.io.IOException;
//см. ApiRosreestrDeserializer
public class LandPlotDeserializer extends JsonDeserializer<LandPlot> {

  @Override
  public LandPlot deserialize(JsonParser jp, DeserializationContext ctxt) {
    ObjectCodec oc = jp.getCodec();
    JsonNode node;
    try {
      node = oc.readTree(jp);
    } catch (IOException e) {
      return null;
    }
    JsonNode featureAttrs = node.get("feature").get("attrs");
    try {
      return new LandPlot(
          featureAttrs.get("id").asText(),
          featureAttrs.get("address").asText(),
          Utils.getMeasureUnitFromIntValue(featureAttrs.get("cad_unit").asInt()),
          featureAttrs.get("cad_cost").asInt());
    } catch (Exception e) {
      return null;
    }
  }
}
