package com.rosreestr.app.Model;

public class Status {
  private static Status INSTANCE;

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
      if (isPrimaryServer) {
        Server.getInstance().setPrimaryServerIsAvailable(false);
        Server.getInstance().setPrimaryServerStatus("Server not available");
        Server.getInstance().setMainServerInUse(false);
        Server.getInstance().setMainServer(false);
      }
      return;
    }

    if (delay > 2000) {
      if (isPrimaryServer) {
        Server.getInstance().setPrimaryServerIsAvailable(false);
        Server.getInstance().setPrimaryServerStatus("Server not available");
        Server.getInstance().setMainServer(false);
      } else {
        Server.getInstance().setSecondaryServerIsAvailable(false);
        Server.getInstance().setSecondaryServerStatus("Server not available");
        Server.getInstance().setMainServer(true);
      }
      return;
    }

    if (delay < 600) {
      if (isPrimaryServer) {
        Server.getInstance().setPrimaryServerStatus("Ok");
        Server.getInstance().setPrimaryServerIsAvailable(true);
      } else {
        Server.getInstance().setSecondaryServerIsAvailable(true);
        Server.getInstance().setSecondaryServerStatus("Ok");
      }
    }

    if (delay >= 600 && delay < 2000) {
      if (isPrimaryServer) {
        Server.getInstance().setPrimaryServerStatus("Service is slow");
        Server.getInstance().setPrimaryServerIsAvailable(true);
      } else {
        Server.getInstance().setSecondaryServerStatus("Service is slow");
        Server.getInstance().setSecondaryServerIsAvailable(true);
      }
    }
  }
}
