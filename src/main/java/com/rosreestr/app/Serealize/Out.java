package com.rosreestr.app.Serealize;

import com.rosreestr.app.Model.LandPlot;
import com.rosreestr.app.Model.Oks;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class Out implements Serializable {
  private List<Oks> oksList0;
  private List<LandPlot> landPlotList;
}
