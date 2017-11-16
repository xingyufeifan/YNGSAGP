package com.nandi.yngsagp.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ChenPeng on 2017/11/16.
 */
@Entity
public class PhotoPath {
    @Id
    private Long id;
    private String path;
    @Generated(hash = 1532357288)
    public PhotoPath(Long id, String path) {
        this.id = id;
        this.path = path;
    }
    @Generated(hash = 1895854510)
    public PhotoPath() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPath() {
        return this.path;
    }
    public void setPath(String path) {
        this.path = path;
    }
}
