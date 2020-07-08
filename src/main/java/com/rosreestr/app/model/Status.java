package com.rosreestr.app.model;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Status {
  private static Status INSTANCE;
  private static SimpMessagingTemplate template;

  @Autowired
  public void setTemplate(SimpMessagingTemplate template) {
    Status.template = template;
  }

  private Status() {}

  public static synchronized Status getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new Status();
    }
    return INSTANCE;
  }
  // логика вывода серверов из работы
  public static void updateStatusServer(long delay, boolean isReacheble, boolean isPrimaryServer) {
    if (!isReacheble) {
      log.debug("isReacheble");
      if (isPrimaryServer) {
        log.debug("mainServerDown");
        mainServerDown();
      } else {
        log.debug("secondaryServerDown");
        secondaryServerDown();
      }
      return;
    }

    if (delay > 2000) {
      log.debug("delay > 2000");
      if (isPrimaryServer) {
        log.debug("mainServerDown");
        mainServerDown();
      } else {
        log.debug("secondaryServerDown");
        secondaryServerDown();
      }
      return;
    }


    if (delay < 600) {
      log.debug("delay < 600");
      if (isPrimaryServer) {
        log.debug("isPrimaryServer");
        Server.getInstance().setPrimaryServerStatus("Ok");
        Server.getInstance().setPrimaryServerIsAvailable(true);
      } else {
        log.debug("SecondaryServer");
        Server.getInstance().setSecondaryServerIsAvailable(true);
        Server.getInstance().setSecondaryServerStatus("Ok");
      }
    }

    if (delay >= 600 && delay < 2000) {
      log.debug("delay >= 600 && delay < 2000");

      if (isPrimaryServer) {
        log.debug("isPrimaryServer");
        Server.getInstance().setPrimaryServerStatus("Service is slow");
        Server.getInstance().setPrimaryServerIsAvailable(true);
      } else {
        log.debug("SecondaryServer");
        Server.getInstance().setSecondaryServerStatus("Service is slow");
        Server.getInstance().setSecondaryServerIsAvailable(true);
      }
    }
  }

  private static void secondaryServerDown() {
    Server.getInstance().setSecondaryServerIsAvailable(false);
    Server.getInstance().setSecondaryServerStatus("Server not available");
    Server.getInstance().setPrimaryServerAsActive(true);
    sendBroadcastMessage("/topic/serverStatus", Server.getInstance().toString());
  }

  private static void mainServerDown() {
    Server.getInstance().setPrimaryServerIsAvailable(false);
    Server.getInstance().setPrimaryServerStatus("Server not available");
    Server.getInstance().setPrimaryServerAsActive(false);
    sendBroadcastMessage("/topic/serverStatus", Server.getInstance().toString());
  }

  public static void sendBroadcastMessage(String destination, String message) {
    template.convertAndSend(destination, new BroadCast(message));
  }
}
