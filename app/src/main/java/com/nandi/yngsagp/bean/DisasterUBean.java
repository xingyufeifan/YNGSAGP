package com.nandi.yngsagp.bean;

/**
 * Created by qingsong on 2017/10/27.
 */

public class DisasterUBean {
    private String place;
    private String type;
    private String toTime;



    public DisasterUBean(String place, String type, String toTime) {
        this.place = place;
        this.type = type;
        this.toTime = toTime;

    }


    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

}
