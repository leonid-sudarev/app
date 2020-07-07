package com.rosreestr.app.Model;

import java.io.Serializable;

public class Server implements Serializable {
  private static Server INSTANCE;

  private Boolean primaryServerIsAvailable;
  private Boolean secondaryServerIsAvailable;

  private transient Boolean mainServerInUse;
  private transient Boolean secondaryServerInUse;

  private transient String primaryServerStatus;

  private transient String secondaryServerStatus;
  private transient String primaryAddressOks;

  private transient String primaryAddressLandPlot;
  private transient String secondaryAddressLandPlot;

  private transient String secondaryAddressOks;
  private transient String addressOks;

  private transient String addressLandPlot;

  public static synchronized Server getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new Server();
      INSTANCE.primaryAddressOks = "https://pkk.rosreestr.ru/api/features/5/";
      INSTANCE.primaryAddressLandPlot = "https://pkk.rosreestr.ru/api/features/1/";
      INSTANCE.secondaryAddressOks = "https://apirosreestr.ru/api/cadaster/search";
      INSTANCE.secondaryAddressLandPlot = "https://apirosreestr.ru/api/cadaster/search";
      INSTANCE.setMainServer(true);
    }
    return INSTANCE;
  }

  public void setMainServerInUse(Boolean mainServerInUse) {
    this.mainServerInUse = mainServerInUse;
  }

  public Boolean getMainServerInUse() {
    return mainServerInUse;
  }

  public Boolean getSecondaryServerInUse() {
    return secondaryServerInUse;
  }

  public void setSecondaryServerInUse(Boolean secondaryServerInUse) {
    this.secondaryServerInUse = secondaryServerInUse;
  }

  public String getPrimaryServerStatus() {
    return primaryServerStatus;
  }

  public void setPrimaryServerStatus(String primaryServerStatus) {
    this.primaryServerStatus = primaryServerStatus;
  }

  public String getSecondaryServerStatus() {
    return secondaryServerStatus;
  }

  public void setSecondaryServerStatus(String secondaryServerStatus) {
    this.secondaryServerStatus = secondaryServerStatus;
  }

  public Boolean getPrimaryServerIsAvailable() {
    return primaryServerIsAvailable;
  }

  public void setPrimaryServerIsAvailable(Boolean primaryServerIsAvailable) {
    this.primaryServerIsAvailable = primaryServerIsAvailable;
  }

  public Boolean getSecondaryServerIsAvailable() {
    return secondaryServerIsAvailable;
  }

  public void setSecondaryServerIsAvailable(Boolean secondaryServerIsAvailable) {
    this.secondaryServerIsAvailable = secondaryServerIsAvailable;
  }

  public String getPrimaryAddressOks() {
    return primaryAddressOks;
  }

  public String getPrimaryAddressLandPlot() {
    return primaryAddressLandPlot;
  }

  public String getAddressOks() {
    return addressOks;
  }

  public String getAddressLandPlot() {
    return addressLandPlot;
  }

  public void setMainServer(boolean b) {
    if (b) {
      INSTANCE.addressOks = INSTANCE.primaryAddressOks;
      INSTANCE.addressLandPlot = INSTANCE.primaryAddressLandPlot;
      INSTANCE.mainServerInUse = true;
      INSTANCE.secondaryServerInUse = false;
    } else {
      INSTANCE.addressOks = INSTANCE.secondaryAddressOks;
      INSTANCE.addressLandPlot = INSTANCE.secondaryAddressLandPlot;
      INSTANCE.mainServerInUse = false;
      INSTANCE.secondaryServerInUse = true;
    }
  }

  @Override
  public String toString() {
    return "Server{"
        + "primaryServerIsAvailable="
        + primaryServerIsAvailable
        + "\n"
        + ", secondaryServerIsAvailable="
        + secondaryServerIsAvailable
        + "\n"
        + ", mainServerInUse="
        + mainServerInUse
        + "\n"
        + ", secondaryServerInUse="
        + secondaryServerInUse
        + "\n"
        + ", primaryServerStatus='"
        + primaryServerStatus
        + '\''
        + "\n"
        + ", secondaryServerStatus='"
        + secondaryServerStatus
        + '\''
        + "\n"
        + ", primaryAddressOks='"
        + primaryAddressOks
        + '\''
        + "\n"
        + ", primaryAddressLandPlot='"
        + primaryAddressLandPlot
        + '\''
        + "\n"
        + ", secondaryAddressLandPlot='"
        + secondaryAddressLandPlot
        + '\''
        + "\n"
        + ", secondaryAddressOks='"
        + secondaryAddressOks
        + '\''
        + "\n"
        + ", addressOks='"
        + addressOks
        + '\''
        + "\n"
        + ", addressLandPlot='"
        + addressLandPlot
        + '\''
        + "\n"
        + '}';
  }
}
