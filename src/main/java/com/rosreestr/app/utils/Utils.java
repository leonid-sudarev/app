package com.rosreestr.app.utils;

public class Utils {
  public static String getMeasureUnitFromIntValue(int i) {
    switch (i) {
      case 3:
        return "мм";
      case 4:
        return "см";
      case 5:
        return "дм";
      case 6:
        return "м";
      case 8:
        return "км";
      case 9:
        return "Мм";
      case 47:
        return "морск. м.";
      case 50:
        return "кв. мм";
      case 51:
        return "кв. см";
      case 53:
        return "кв. дм";
      case 55:
        return "кв. м";
      case 58:
        return "тыс. кв. м";
      case 59:
        return "га";
      case 61:
        return "кв. км";
      case 109:
        return "а";
      case 359:
        return "сут.";
      case 360:
        return "нед.";
      case 361:
        return "дек.";
      case 362:
        return "мес.";
      case 364:
        return "кварт.";
      case 365:
        return "полугод.";
      case 366:
        return "г.";
      case 383:
        return "руб.";
      case 384:
        return "тыс. руб.";
      case 385:
        return "млн. руб.";
      case 386:
        return "млрд. руб.";
      case 1000:
        return "неопр.";
      case 1001:
        return "отсутств.";
      case 1002:
        return "руб. за кв. м";
      case 1003:
        return "руб. за а";
      case 1004:
        return "руб. за га";
      case 1005:
        return "иные";
      default:
        return "Wrong parametr";
    }
  }
}
