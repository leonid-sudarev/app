package com.rosreestr.app.ServiceTests;

import com.rosreestr.app.services.DaDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DaDatatServiceTests {
  private DaDataService daDataService;

  @Resource
  public void setDaDataService(DaDataService daDataService) {
    this.daDataService = daDataService;
  }

  @Test
  public void ShouldReturnValidObject() {
    String body = " \" Варонеж, Ревалюции,84\" ";
    String response = daDataService.getNormalizedAddress(body);
    assertThat(response, notNullValue());
    assertThat(response, is("г Воронеж, пр-кт Революции, д 84"));
  }
}
