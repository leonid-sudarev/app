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

  public String getStatus() {
    return status;
  }

  public void setSecondaryServerAdressOks(String secondaryServerAdressOks) {
    this.secondaryServerAdressOks = secondaryServerAdressOks;
  }

  public void setSecondaryServerAdressLandPlot(String secondaryServerAdressLandPlot) {
    this.secondaryServerAdressLandPlot = secondaryServerAdressLandPlot;
  }

  private Status() {}

  public String getServerAdressOks() {
    return serverAdressOks;
  }

  public String getServerAdressLandPlot() {
    return serverAdressLandPlot;
  }

  public static synchronized Status getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new Status();
      INSTANCE.status = "";
      INSTANCE.serverAdressOks = INSTANCE.primaryServerAdressOks;
      INSTANCE.serverAdressLandPlot = INSTANCE.primaryServerAdressLandPlot;
    }
    return INSTANCE;
  }

  public static void updateStatusPrimaryServer(long delay, boolean isReacheble) {
    if (!isReacheble) {
      INSTANCE.status = "Server not available";
      return;
    }

    if (delay > 2000) {
      INSTANCE.status = "server to slow. We'll connect you to another one";
      INSTANCE.serverAdressOks = INSTANCE.secondaryServerAdressOks;
      INSTANCE.serverAdressLandPlot = INSTANCE.secondaryServerAdressLandPlot;
      return;
    }

    if (delay < 600) {
      INSTANCE.status = "OK";
    }

    if (delay >= 600 && delay <= 2000) {
      INSTANCE.status= "Service is slow";
    }

  }
}
