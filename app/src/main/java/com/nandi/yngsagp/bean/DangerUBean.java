package com.nandi.yngsagp.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by qingsong on 2017/10/27.
 */
@Entity
public class DangerUBean {
    @Id
    private Long id;
    private String reportMan;
    private String phone;
    private String time;
    private String address;
    private String location;
    private String lon;
    private String lat;
    private String type;
    private String factor;
    private String person;
    private String house;
    private String money;
    private String farm;
    private String other;
    private String mobile;
    private String name;
    private int disasterPosition;
    @Generated(hash = 1093029500)
    public DangerUBean(Long id, String reportMan, String phone, String time,
            String address, String location, String lon, String lat, String type,
            String factor, String person, String house, String money, String farm,
            String other, String mobile, String name, int disasterPosition) {
        this.id = id;
        this.reportMan = reportMan;
        this.phone = phone;
        this.time = time;
        this.address = address;
        this.location = location;
        this.lon = lon;
        this.lat = lat;
        this.type = type;
        this.factor = factor;
        this.person = person;
        this.house = house;
        this.money = money;
        this.farm = farm;
        this.other = other;
        this.mobile = mobile;
        this.name = name;
        this.disasterPosition = disasterPosition;
    }
    @Generated(hash = 1656130571)
    public DangerUBean() {
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
    public String getPerson() {
        return this.person;
    }
    public void setPerson(String person) {
        this.person = person;
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
    public String getFarm() {
        return this.farm;
    }
    public void setFarm(String farm) {
        this.farm = farm;
    }
    public String getOther() {
        return this.other;
    }
    public void setOther(String other) {
        this.other = other;
    }
    public String getMobile() {
        return this.mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DangerUBean{" +
                "id=" + id +
                ", reportMan='" + reportMan + '\'' +
                ", phone='" + phone + '\'' +
                ", time='" + time + '\'' +
                ", address='" + address + '\'' +
                ", location='" + location + '\'' +
                ", lon='" + lon + '\'' +
                ", lat='" + lat + '\'' +
                ", type='" + type + '\'' +
                ", factor='" + factor + '\'' +
                ", person='" + person + '\'' +
                ", house='" + house + '\'' +
                ", money='" + money + '\'' +
                ", farm='" + farm + '\'' +
                ", other='" + other + '\'' +
                ", mobile='" + mobile + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
    public int getDisasterPosition() {
        return this.disasterPosition;
    }
    public void setDisasterPosition(int disasterPosition) {
        this.disasterPosition = disasterPosition;
    }
}
