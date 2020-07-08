package com.rosreestr.app.ping;

import com.rosreestr.app.model.Status;
import com.rosreestr.app.services.RestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ScheduledTasks {
  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
  private final RestService restService;
  volatile long delay;

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
    // Make Rosreestr unavailable. for test purpose
//        reachable=false;
    log.info(ip + " isReacheble - " + reachable);
    Status.updateStatusServer(
        restService.getResponseTime("https://pkk.rosreestr.ru/api/features/5/36:16:5500001:1900"),
        reachable,
        true);
  }

  @Scheduled(initialDelay = 2000, fixedRateString = "${fixed-rate.in.milliseconds}")
  //  @Scheduled(cron = "${cron.expression}") // не срабатывает при запуске
  public void scheduleTaskSecondaryServerAvailability() {
    log.info("Cron Dynamic Task: Current Time - {}", formatter.format(LocalDateTime.now()));
    String url = "apirosreestr.ru";
    boolean reachable = RestService.isReachable(url, 443, 2000);
    // Make Rosreestr unavailable. for test purpose
//                reachable=false;
    log.info(url + " isReacheble - " + reachable);
    delay = delay(url);
    Status.updateStatusServer(delay, reachable, false);
  }

  private long delay(String url) {
    // in case of Linux change the 'n'(Mac) to 'c'(Linux)
    Process p1 = null;
    try {
      p1 = Runtime.getRuntime().exec("ping -c 1 " + url);
    } catch (IOException e) {
      e.printStackTrace();
    }
    BufferedReader inputStream = new BufferedReader(new InputStreamReader(p1.getInputStream()));
    String s = "";
    List<String> list = new ArrayList<>();
    // reading output stream of the command
    while (true) {
      try {
        if ((s = inputStream.readLine()) == null) break;
      } catch (IOException e) {
        e.printStackTrace();
      }
      list.add(s);
    }
    try {
      String split = list.get(1).split("time=")[1];
      return (long) Double.parseDouble(split.substring(0, split.length() - 3));
    } catch (ArrayIndexOutOfBoundsException e) {
      return 3000;
    }
  }
}
