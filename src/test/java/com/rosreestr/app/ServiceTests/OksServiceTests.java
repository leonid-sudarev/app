package com.rosreestr.app.ServiceTests;

import com.rosreestr.app.model.Oks;
import com.rosreestr.app.services.OksService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OksServiceTests {
  private OksService oksService;

  @Resource
  public void setOksService(OksService oksService) {
    this.oksService = oksService;
  }

  @Test
  public void ShouldReturnValidObject() {
    String cadNum = "36:16:5500001:1900";
    Oks response = oksService.getOksRosreestr(cadNum);
    assertThat(response, notNullValue());
    assertThat(
        response.getAddress(),
        is("Воронежская область, р-н Новоусманский, с Александровка, ул Юбилейная, д 42"));
    assertThat(response.getId(), is(cadNum));
  }

  @Test
  public void ShouldReturnNull() {
    String cadNum = "36:16:5500001:1900000";
    Oks response = oksService.getOksRosreestr(cadNum);
    assertThat(response, nullValue());
  }
}
