package com.rosreestr.app.model;

import java.io.Serializable;


public class Server implements Serializable {
  private static Server INSTANCE;

  private Boolean primaryServerIsAvailable;
  private Boolean primaryServerInUse;
  private String primaryServerStatus;

  private Boolean secondaryServerIsAvailable;
  private Boolean secondaryServerInUse;
  private String secondaryServerStatus;

  private transient String primaryAddressOks;

  private transient String primaryAddressLandPlot;
  private transient String secondaryAddressLandPlot;

  private transient String secondaryAddressOks;
  private transient String primaryaddressOks;

  private transient String addressLandPlot;

  public static synchronized Server getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new Server();
      INSTANCE.primaryAddressOks = "https://pkk.rosreestr.ru/api/features/5/";
      INSTANCE.primaryAddressLandPlot = "https://pkk.rosreestr.ru/api/features/1/";
      INSTANCE.secondaryAddressOks = "https://apirosreestr.ru/api/cadaster/search";
      INSTANCE.secondaryAddressLandPlot = "https://apirosreestr.ru/api/cadaster/search";
      //инициализация
      INSTANCE.setPrimaryServerAsActive(false);
      INSTANCE.setPrimaryServerAsActive(true);
    }
    return INSTANCE;
  }

  public Boolean getPrimaryServerInUse() {
    return primaryServerInUse;
  }

  public Boolean getSecondaryServerInUse() {
    return secondaryServerInUse;
  }


  public void setPrimaryServerStatus(String primaryServerStatus) {
    this.primaryServerStatus = primaryServerStatus;
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

  public void setPrimaryServerAsActive(boolean b) {
    if (b) {
      INSTANCE.primaryaddressOks = INSTANCE.primaryAddressOks;
      INSTANCE.addressLandPlot = INSTANCE.primaryAddressLandPlot;
      INSTANCE.primaryServerInUse = true;
      INSTANCE.secondaryServerInUse = false;

    } else {
      INSTANCE.primaryaddressOks = INSTANCE.secondaryAddressOks;
      INSTANCE.addressLandPlot = INSTANCE.secondaryAddressLandPlot;
      INSTANCE.primaryServerInUse = false;
      INSTANCE.secondaryServerInUse = true;
    }
  }

  //For serialize
  public void setPrimaryServerInUse(Boolean primaryServerInUse) {
    this.primaryServerInUse = primaryServerInUse;
  }

  public void setSecondaryServerInUse(Boolean secondaryServerInUse) {
    this.secondaryServerInUse = secondaryServerInUse;
  }

  public String getPrimaryServerStatus() {
    return primaryServerStatus;
  }

  public String getSecondaryServerStatus() {
    return secondaryServerStatus;
  }

  @Override
  public String toString() {
    return "Server{" + "primaryServerIsAvailable=" + primaryServerIsAvailable +
            ", secondaryServerIsAvailable=" + secondaryServerIsAvailable +
            ", primaryServerInUse=" + primaryServerInUse +
            ", secondaryServerInUse=" + secondaryServerInUse +
            ", primaryServerStatus='" + primaryServerStatus + '\'' +
            ", secondaryServerStatus='" + secondaryServerStatus + '\'' +
            ", primaryAddressOks='" + primaryAddressOks + '\'' +
            ", primaryAddressLandPlot='" + primaryAddressLandPlot + '\'' +
            ", secondaryAddressLandPlot='" + secondaryAddressLandPlot + '\'' +
            ", secondaryAddressOks='" + secondaryAddressOks + '\'' +
            ", primaryaddressOks='" + primaryaddressOks + '\'' +
            ", addressLandPlot='" + addressLandPlot + '\'' +
            '}';
  }
}
