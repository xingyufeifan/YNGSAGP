package com.nandi.yngsagp.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by apple on 2018/3/1.
 */
@Entity
public class DisasterPoint {
    @Id
    private Long id;
    private String disName;
    private String disId;
    @Generated(hash = 1170310395)
    public DisasterPoint(Long id, String disName, String disId) {
        this.id = id;
        this.disName = disName;
        this.disId = disId;
    }
    @Generated(hash = 410069605)
    public DisasterPoint() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDisName() {
        return this.disName;
    }
    public void setDisName(String disName) {
        this.disName = disName;
    }
    public String getDisId() {
        return this.disId;
    }
    public void setDisId(String disId) {
        this.disId = disId;
    }

    @Override
    public String toString() {
        return "DisasterPoint{" +
                "id=" + id +
                ", disName='" + disName + '\'' +
                ", disId='" + disId + '\'' +
                '}';
    }
}
