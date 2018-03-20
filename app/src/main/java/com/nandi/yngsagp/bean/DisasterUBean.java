package com.nandi.yngsagp.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by qingsong on 2017/10/27.
 */
@Entity
public class DisasterUBean {
    @Id
    private Long id;
    private String reportMan;
    private String phone;
    private String time;
    private String address;
    private String location;
    private String type;
    private String factor;
    private String injured;
    private String death;
    private String miss;
    private String farm;
    private String house;
    private String money;
    private String lon;
    private String lat;
    private String other;
    private String reportName;
    private String reportMobile;
    private int disasterPosition;
    @Generated(hash = 1622756623)
    public DisasterUBean(Long id, String reportMan, String phone, String time,
            String address, String location, String type, String factor,
            String injured, String death, String miss, String farm, String house,
            String money, String lon, String lat, String other, String reportName,
            String reportMobile, int disasterPosition) {
        this.id = id;
        this.reportMan = reportMan;
        this.phone = phone;
        this.time = time;
        this.address = address;
        this.location = location;
        this.type = type;
        this.factor = factor;
        this.injured = injured;
        this.death = death;
        this.miss = miss;
        this.farm = farm;
        this.house = house;
        this.money = money;
        this.lon = lon;
        this.lat = lat;
        this.other = other;
        this.reportName = reportName;
        this.reportMobile = reportMobile;
        this.disasterPosition = disasterPosition;
    }
    @Generated(hash = 920846843)
    public DisasterUBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getReportMan() {
        return this.reportMan;
    }
    public void setReportMan(String reportMan) {
        this.reportMan = reportMan;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getLocation() {
        return this.location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getFactor() {
        return this.factor;
    }
    public void setFactor(String factor) {
        this.factor = factor;
    }
    public String getInjured() {
        return this.injured;
    }
    public void setInjured(String injured) {
        this.injured = injured;
    }
    public String getDeath() {
        return this.death;
    }
    public void setDeath(String death) {
        this.death = death;
    }
    public String getMiss() {
        return this.miss;
    }
    public void setMiss(String miss) {
        this.miss = miss;
    }
    public String getFarm() {
        return this.farm;
    }
    public void setFarm(String farm) {
        this.farm = farm;
    }
    public String getHouse() {
        return this.house;
    }
    public void setHouse(String house) {
        this.house = house;
    }
    public String getMoney() {
        return this.money;
    }
    public void setMoney(String money) {
        this.money = money;
    }
    public String getLon() {
        return this.lon;
    }
    public void setLon(String lon) {
        this.lon = lon;
    }
    public String getLat() {
        return this.lat;
    }
    public void setLat(String lat) {
        this.lat = lat;
    }
    public String getOther() {
        return this.other;
    }
    public void setOther(String other) {
        this.other = other;
    }
    public String getReportName() {
        return this.reportName;
    }
    public void setReportName(String reportName) {
        this.reportName = reportName;
    }
    public String getReportMobile() {
        return this.reportMobile;
    }
    public void setReportMobile(String reportMobile) {
        this.reportMobile = reportMobile;
    }

    @Override
    public String toString() {
        return "DisasterUBean{" +
                "id=" + id +
                ", reportMan='" + reportMan + '\'' +
                ", phone='" + phone + '\'' +
                ", time='" + time + '\'' +
                ", address='" + address + '\'' +
                ", location='" + location + '\'' +
                ", type='" + type + '\'' +
                ", factor='" + factor + '\'' +
                ", injured='" + injured + '\'' +
                ", death='" + death + '\'' +
                ", miss='" + miss + '\'' +
                ", farm='" + farm + '\'' +
                ", house='" + house + '\'' +
                ", money='" + money + '\'' +
                ", lon='" + lon + '\'' +
                ", lat='" + lat + '\'' +
                ", other='" + other + '\'' +
                ", reportName='" + reportName + '\'' +
                ", reportMobile='" + reportMobile + '\'' +
                '}';
    }
    public int getDisasterPosition() {
        return this.disasterPosition;
    }
    public void setDisasterPosition(int disasterPosition) {
        this.disasterPosition = disasterPosition;
    }
}
