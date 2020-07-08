package com.rosreestr.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Class for websocket purpose
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BroadCast {
  private String content;
}
