package com.rosreestr.app.Model;


public class Status {
  private static Status INSTANCE ;

  private String status;

  private String serverAdressOks;
  private String serverAdressLandPlot;
  private String primaryServerAdressOks = "https://pkk.rosreestr.ru/api/features/5/";

  private String primaryServerAdressLandPlot = "https://pkk.rosreestr.ru/api/features/1/";
  private String secondaryServerAdressOks;
  private String secondaryServerAdressLandPlot;



  private Status() {}


  public static synchronized Status getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new Status();
    }
    return INSTANCE;
  }

  public static void updateStatusServer(long delay, boolean isReacheble, boolean isPrimaryServer) {
    if (!isReacheble) {
      if(isPrimaryServer) {
        Server.getInstance().setPrimaryServerIsAvailable(false);
        Server.getInstance().setPrimaryServerStatus("Server not available");
        Server.getInstance().setMainServerInUse(false);
        // TODO:7/3/20 check if secondary is Available and if it's unavailable then 404 and...
        Server.getInstance().setMainServer(false);
      }
      return;
    }

    if (delay > 2000) {
      if(isPrimaryServer) {
        Server.getInstance().setPrimaryServerIsAvailable(false);
        Server.getInstance().setPrimaryServerStatus("Server not available");
        // TODO:7/3/20 check if secondary is Available and if it's unavailable then 404 and...
        Server.getInstance().setMainServer(false);
      } else {
        Server.getInstance().setSecondaryServerIsAvailable(false);
        Server.getInstance().setSecondaryServerStatus("Server not available");
        // TODO:7/3/20 check if primary is Available and if it's unavailable then 404 and...
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
