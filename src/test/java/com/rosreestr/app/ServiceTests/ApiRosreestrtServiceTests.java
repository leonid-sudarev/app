package com.rosreestr.app.ServiceTests;

import com.rosreestr.app.Model.ApiRosreestr;
import com.rosreestr.app.services.ApiRosreestrService;
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
public class ApiRosreestrtServiceTests {
  private ApiRosreestrService apiRosreestrService;

  @Resource
  public void setApiRosreestrService(ApiRosreestrService apiRosreestrService) {
    this.apiRosreestrService = apiRosreestrService;
  }

  @Test
  public void ShouldReturnValidObject() {
    String address = " \"г Воронеж, пр-кт Революции, д 84\"";
    ApiRosreestr response = apiRosreestrService.getApiRosreestr(address);
    System.out.println(response);
    assertThat(response, notNullValue());
    assertThat(response.getObjectsList(), notNullValue());
    assertThat(response.getFound(), is(4));
    assertThat(response.getObjectsList().size(), is(4));
  }
}
