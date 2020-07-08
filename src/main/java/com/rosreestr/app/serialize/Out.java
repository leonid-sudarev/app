package com.rosreestr.app.serialize;

import com.rosreestr.app.model.LandPlot;
import com.rosreestr.app.model.Oks;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

//Объект в который сериализуется запрос по адресу
@Data
@NoArgsConstructor
public class Out implements Serializable {
  private List<Oks> oksList;
  private List<LandPlot> landPlotList;
}
