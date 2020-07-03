package com.rosreestr.app.ping;

import com.rosreestr.app.services.RestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class ScheduledTasks {
  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
  private RestService restService;

  public ScheduledTasks(RestService restService) {
    this.restService = restService;
  }

  @Scheduled(fixedRateString = "${fixed-rate.in.milliseconds}")
  public void scheduleTaskWithCronExpression() {
    log.info(" delay in ms = ", restService.getResponseTime());
    System.out.println(restService.getResponseTime());
  }

  @Scheduled(cron = "${cron.expression}")
  public void scheduleDynamicTaskWithCronExpression() {
    log.info("Cron Dynamic Task: Current Time - {}", formatter.format(LocalDateTime.now()));
    String ip = "pkk.rosreestr.ru";
    System.out.println(ip + " isReacheble - " + RestService.isReachable(ip, 443, 2000));
  }
}
