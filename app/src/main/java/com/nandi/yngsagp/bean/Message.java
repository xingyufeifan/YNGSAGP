package com.nandi.yngsagp.bean;

import java.io.Serializable;

/**
 * Created by ChenPeng on 2017/11/24
 */

public class Message implements Serializable {
    private int userId;
    private int roomId;
    private String type;
    private String inviteMan;
    private String message;

    public Message() {
    }

    public Message(int userId, int roomId, String type, String inviteMan, String message) {
        this.userId = userId;
        this.roomId = roomId;
        this.type = type;
        this.inviteMan = inviteMan;
        this.message = message;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInviteMan() {
        return inviteMan;
    }

    public void setInviteMan(String inviteMan) {
        this.inviteMan = inviteMan;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
