package com.rosreestr.app.ServiceTests;

import com.rosreestr.app.model.LandPlot;
import com.rosreestr.app.services.LandPlotService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LandPlotServiceTests {
  private LandPlotService landPlotService;

  @Resource
  public void setLandPlotService(LandPlotService landPlotService) {
    this.landPlotService = landPlotService;
  }

  @Test
  public void ShouldReturnValidObject() {
    String cadNum = "36:16:5500001:595";
    LandPlot response = landPlotService.getLandPlotRosreestr(cadNum);
    assertThat(response, notNullValue());
    assertThat(
        response.getAddress(),
        is("Воронежская область, р-н Новоусманский, с Александровка, ул Левобережная, участок 36"));
    assertThat(response.getId(), is(cadNum));
  }

  @Test
  public void ShouldReturnNull() {
    String cadNum = "36:16:5500001:5958989898";
    LandPlot response = landPlotService.getLandPlotRosreestr(cadNum);
    assertThat(response, nullValue());
  }
}
