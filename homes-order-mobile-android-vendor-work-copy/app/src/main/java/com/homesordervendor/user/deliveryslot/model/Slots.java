package com.homesordervendor.user.deliveryslot.model;

/**
 * Created by mac on 2/22/18.
 */

public class Slots {

    String day;
    boolean morning;
    boolean noon;
    boolean evening;

    public Slots(){
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public boolean isMorning() {
        return morning;
    }

    public void setMorning(boolean morning) {
        this.morning = morning;
    }

    public boolean isNoon() {
        return noon;
    }

    public void setNoon(boolean noon) {
        this.noon = noon;
    }

    public boolean isEvening() {
        return evening;
    }

    public void setEvening(boolean evening) {
        this.evening = evening;
    }
}
