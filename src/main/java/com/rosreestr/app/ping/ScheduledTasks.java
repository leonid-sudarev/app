package com.rosreestr.app.ping;

import com.rosreestr.app.Model.Status;
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
  public void scheduleTaskPrimaryServerAvailability() {
    String ip = "pkk.rosreestr.ru";
    log.info(
        String.valueOf(
            restService.getResponseTime(
                "https://pkk.rosreestr.ru/api/features/5/36:16:5500001:1900")));
    boolean reachable = RestService.isReachable(ip, 443, 2000);
    log.info(ip + " isReacheble - " + reachable);
    Status.updateStatusServer(
        restService.getResponseTime("https://pkk.rosreestr.ru/api/features/5/36:16:5500001:1900"),
        reachable,
        true);
  }

  @Scheduled(cron = "${cron.expression}")
  public void scheduleTaskSecondaryServerAvailability() {
    log.info("Cron Dynamic Task: Current Time - {}", formatter.format(LocalDateTime.now()));
    String ip = "apirosreestr.ru";
    boolean reachable = RestService.isReachable(ip, 443, 2000);
    log.info(ip + " isReacheble - " + reachable);
    Status.updateStatusServer(70, reachable, false); // FIXME: 7/3/20 delay fix
  }
}
