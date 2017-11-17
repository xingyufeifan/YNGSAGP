package com.nandi.yngsagp.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ChenPeng on 2017/11/17.
 */
@Entity
public class VideoPath {
    @Id
    private Long id;
    private int type;
    private String path;
    @Generated(hash = 1405671028)
    public VideoPath(Long id, int type, String path) {
        this.id = id;
        this.type = type;
        this.path = path;
    }
    @Generated(hash = 321257015)
    public VideoPath() {
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
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
}
