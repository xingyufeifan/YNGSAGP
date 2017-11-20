package com.nandi.yngsagp.bean;

import java.util.List;

/**
 * Created by qingsong on 2017/11/20.
 */

public class DisasterListBean {

    /**
     * meta : {"success":true,"message":"ok"}
     * data : [{"disasterNum":36,"phoneNum":"13987786880","personel":"刀海祥","currentLocation":"","address":null,"disasterType":"","factor":null,"personNum":null,"houseNum":null,"injurdNum":null,"farmland":null,"missingNum":null,"lossProperty":null,"deathNum":null,"area":null,"longitude":null,"latitude":null,"type":"1","happenTime":null,"otherThing":null,"isDisaster":0,"findTime":"2017-11-17 14:06:10.0","isDispose":1,"potentialLoss":null,"isDanger":0,"monitorPhone":null,"monitorName":null}]
     */

    private MetaBean meta;
    private List<DataBean> data;

    public MetaBean getMeta() {
        return meta;
    }

    public void setMeta(MetaBean meta) {
        this.meta = meta;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class MetaBean {
        /**
         * success : true
         * message : ok
         */

        private boolean success;
        private String message;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class DataBean {
        /**
         * disasterNum : 36
         * phoneNum : 13987786880
         * personel : 刀海祥
         * currentLocation :
         * address : null
         * disasterType :
         * factor : null
         * personNum : null
         * houseNum : null
         * injurdNum : null
         * farmland : null
         * missingNum : null
         * lossProperty : null
         * deathNum : null
         * area : null
         * longitude : null
         * latitude : null
         * type : 1
         * happenTime : null
         * otherThing : null
         * isDisaster : 0
         * findTime : 2017-11-17 14:06:10.0
         * isDispose : 1
         * potentialLoss : null
         * isDanger : 0
         * monitorPhone : null
         * monitorName : null
         */

        private int disasterNum;
        private String phoneNum;
        private String personel;
        private String currentLocation;
        private Object address;
        private String disasterType;
        private Object factor;
        private Object personNum;
        private Object houseNum;
        private Object injurdNum;
        private Object farmland;
        private Object missingNum;
        private Object lossProperty;
        private Object deathNum;
        private Object area;
        private Object longitude;
        private Object latitude;
        private String type;
        private Object happenTime;
        private Object otherThing;
        private int isDisaster;
        private String findTime;
        private int isDispose;
        private Object potentialLoss;
        private int isDanger;
        private Object monitorPhone;
        private Object monitorName;

        public int getDisasterNum() {
            return disasterNum;
        }

        public void setDisasterNum(int disasterNum) {
            this.disasterNum = disasterNum;
        }

        public String getPhoneNum() {
            return phoneNum;
        }

        public void setPhoneNum(String phoneNum) {
            this.phoneNum = phoneNum;
        }

        public String getPersonel() {
            return personel;
        }

        public void setPersonel(String personel) {
            this.personel = personel;
        }

        public String getCurrentLocation() {
            return currentLocation;
        }

        public void setCurrentLocation(String currentLocation) {
            this.currentLocation = currentLocation;
        }

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
        }

        public String getDisasterType() {
            return disasterType;
        }

        public void setDisasterType(String disasterType) {
            this.disasterType = disasterType;
        }

        public Object getFactor() {
            return factor;
        }

        public void setFactor(Object factor) {
            this.factor = factor;
        }

        public Object getPersonNum() {
            return personNum;
        }

        public void setPersonNum(Object personNum) {
            this.personNum = personNum;
        }

        public Object getHouseNum() {
            return houseNum;
        }

        public void setHouseNum(Object houseNum) {
            this.houseNum = houseNum;
        }

        public Object getInjurdNum() {
            return injurdNum;
        }

        public void setInjurdNum(Object injurdNum) {
            this.injurdNum = injurdNum;
        }

        public Object getFarmland() {
            return farmland;
        }

        public void setFarmland(Object farmland) {
            this.farmland = farmland;
        }

        public Object getMissingNum() {
            return missingNum;
        }

        public void setMissingNum(Object missingNum) {
            this.missingNum = missingNum;
        }

        public Object getLossProperty() {
            return lossProperty;
        }

        public void setLossProperty(Object lossProperty) {
            this.lossProperty = lossProperty;
        }

        public Object getDeathNum() {
            return deathNum;
        }

        public void setDeathNum(Object deathNum) {
            this.deathNum = deathNum;
        }

        public Object getArea() {
            return area;
        }

        public void setArea(Object area) {
            this.area = area;
        }

        public Object getLongitude() {
            return longitude;
        }

        public void setLongitude(Object longitude) {
            this.longitude = longitude;
        }

        public Object getLatitude() {
            return latitude;
        }

        public void setLatitude(Object latitude) {
            this.latitude = latitude;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getHappenTime() {
            return happenTime;
        }

        public void setHappenTime(Object happenTime) {
            this.happenTime = happenTime;
        }

        public Object getOtherThing() {
            return otherThing;
        }

        public void setOtherThing(Object otherThing) {
            this.otherThing = otherThing;
        }

        public int getIsDisaster() {
            return isDisaster;
        }

        public void setIsDisaster(int isDisaster) {
            this.isDisaster = isDisaster;
        }

        public String getFindTime() {
            return findTime;
        }

        public void setFindTime(String findTime) {
            this.findTime = findTime;
        }

        public int getIsDispose() {
            return isDispose;
        }

        public void setIsDispose(int isDispose) {
            this.isDispose = isDispose;
        }

        public Object getPotentialLoss() {
            return potentialLoss;
        }

        public void setPotentialLoss(Object potentialLoss) {
            this.potentialLoss = potentialLoss;
        }

        public int getIsDanger() {
            return isDanger;
        }

        public void setIsDanger(int isDanger) {
            this.isDanger = isDanger;
        }

        public Object getMonitorPhone() {
            return monitorPhone;
        }

        public void setMonitorPhone(Object monitorPhone) {
            this.monitorPhone = monitorPhone;
        }

        public Object getMonitorName() {
            return monitorName;
        }

        public void setMonitorName(Object monitorName) {
            this.monitorName = monitorName;
        }
    }
}
