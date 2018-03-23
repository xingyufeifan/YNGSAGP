package com.nandi.yngsagp.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by qingsong on 2018/3/20.
 */

public class ScienceBean implements Parcelable {


    /**
     * id : 43
     * userId : 28
     * title : 12321
     * content : 测试出局。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。
     * time : 2018-03-20 17:23:21
     * areaIds : 530428001,530428002,530428003,530428004,530428005,530428006,530428007,530428008,530428009,530428010
     * areaNames : 那诺乡,羊街乡,甘庄街道,澧江街道,龙潭乡,曼来镇,咪哩乡,洼垤乡,因远镇,红河街道
     * updateTime : 2018-03-20 17:23:21
     * state : 2
     * nickname : 云南
     * mediaId : 11
     * mediaName : 元江县群测群防人员（现场收集的数据1024）-20180319104845410.xlsx
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
    private String mediaId;
    private String mediaName;

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

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.userId);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.time);
        dest.writeString(this.areaIds);
        dest.writeString(this.areaNames);
        dest.writeString(this.updateTime);
        dest.writeString(this.state);
        dest.writeString(this.nickname);
        dest.writeString(this.mediaId);
        dest.writeString(this.mediaName);
    }

    public ScienceBean() {
    }

    protected ScienceBean(Parcel in) {
        this.id = in.readInt();
        this.userId = in.readInt();
        this.title = in.readString();
        this.content = in.readString();
        this.time = in.readString();
        this.areaIds = in.readString();
        this.areaNames = in.readString();
        this.updateTime = in.readString();
        this.state = in.readString();
        this.nickname = in.readString();
        this.mediaId = in.readString();
        this.mediaName = in.readString();
    }

    public static final Creator<ScienceBean> CREATOR = new Creator<ScienceBean>() {
        @Override
        public ScienceBean createFromParcel(Parcel source) {
            return new ScienceBean(source);
        }

        @Override
        public ScienceBean[] newArray(int size) {
            return new ScienceBean[size];
        }
    };
}