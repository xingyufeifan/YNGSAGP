package com.nandi.yngsagp.bean;

/**
 * Created by qingsong on 2018/3/20.
 */

public class ScienceBean {

    /**
     * id : 43
     * userId : 28
     * title : 12321
     * content : 测试出局。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。
     * time : 2018-03-20 17:23:21.0
     * areaIds : 530428001,530428002,530428003,530428004,530428005,530428006,530428007,530428008,530428009,530428010
     * areaNames : 那诺乡,羊街乡,甘庄街道,澧江街道,龙潭乡,曼来镇,咪哩乡,洼垤乡,因远镇,红河街道
     * updateTime : 2018-03-20 17:23:21.0
     * state : 2
     * nickname : 云南
     */

    private int id;
    private int userId;
    private String title;
    private String content;
    private String time;
    private String areaIds;
    private String areaNames;
    private String updateTime;
    private String state;
    private String nickname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(String areaIds) {
        this.areaIds = areaIds;
    }

    public String getAreaNames() {
        return areaNames;
    }

    public void setAreaNames(String areaNames) {
        this.areaNames = areaNames;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}