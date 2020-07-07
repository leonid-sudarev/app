package com.rosreestr.app.deserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.rosreestr.app.Model.ApiRosreestr;
import com.rosreestr.app.Model.Objects;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ApiRosreestrDeserializer extends JsonDeserializer<ApiRosreestr> {

  @Override
  public ApiRosreestr deserialize(JsonParser jp, DeserializationContext ctxt) {
    ObjectCodec oc = jp.getCodec();
    JsonNode node;
    try {
      node = oc.readTree(jp);
      log.info(node.toString());
    } catch (IOException e) {
      e.printStackTrace();
      log.error("IOException oc.readTree(jp)");
      return null;
    }
    List<Objects> objectsList = new ArrayList<>();

    try {
      int found = node.get("found").asInt(0);
      if (found == 0) {
        return null;
      }
      for (int i = 0; i < found; i++) {
        JsonNode objects = node.get("objects").get(i);
        objectsList.add(
            new Objects(
                objects.get("ADDRESS").asText(),
                objects.get("CADNOMER").asText(),
                objects.get("AREA").asText()));
      }
      return new ApiRosreestr(found, objectsList);
    } catch (Exception e) {
      return null;
    }
  }
}
