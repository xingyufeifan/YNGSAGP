package com.nandi.yngsagp.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;
import com.nandi.yngsagp.Constant;
import com.nandi.yngsagp.R;
import com.nandi.yngsagp.activity.MainActivity;
import com.nandi.yngsagp.activity.ReceiveVideoActivity;
import com.nandi.yngsagp.bean.Message;
import com.nandi.yngsagp.service.MyService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by ChenPeng on 2017/11/24.
 */

public class MyReceiver extends MessageReceiver {
    // 消息接收部分的LOG_TAG
    public static final String REC_TAG = "receiver";
    private NotificationManager manager;

    @Override
    public void onNotification(Context context, String title, String summary, Map<String, String> extraMap) {
        Log.e(REC_TAG, "Receive notification, title: " + title + ", summary: " + summary + ", extraMap: " + extraMap);
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String type = extraMap.get("type");
        if ("1".equals(type)) {
            Message message = new Message();
            message.setUserId(Integer.parseInt(extraMap.get("userid")));
            message.setInviteMan(extraMap.get("invite"));
            message.setMessage(extraMap.get("message"));
            message.setRoomId(Integer.parseInt(extraMap.get("room")));
            Intent intent = new Intent(context, ReceiveVideoActivity.class);
            intent.putExtra("MESSAGE", message);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    @Override
    public void onMessage(Context context, CPushMessage cPushMessage) {
        Log.e("MyMessageReceiver", "onMessage, messageId: " + cPushMessage.getMessageId() + ", title: " + cPushMessage.getTitle() + ", content:" + cPushMessage.getContent());
        String content = cPushMessage.getContent();
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Log.e(REC_TAG, "onMessage, messageId: " + cPushMessage.getMessageId() + ", title: " + cPushMessage.getTitle() + ", content:" + cPushMessage.getContent());
        try {
            JSONObject object = new JSONObject(content);
            Message message = new Message();
            String type = object.getString("type");
            if ("3".equals(type)) {
                message.setType(type);
                message.setUserId(object.getInt("userid"));
                message.setInviteMan(object.getString("invite"));
                message.setMessage(object.getString("message"));
                message.setRoomId(object.getInt("roomid"));
                Intent intent = new Intent(context, ReceiveVideoActivity.class);
                intent.putExtra("MESSAGE", message);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } else {
                message.setType(type);
                message.setMessage(object.getString("message"));
            }
            sendNotification(context, message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void sendNotification(Context context, Message message) {
        Notification.Builder builder = new Notification.Builder(context);
        Intent intent = new Intent();
        PendingIntent pendingIntent;
        if ("3".equals(message.getType())) {
            intent.setClass(context, MyService.class);
            intent.putExtra("ROOM_ID", message.getRoomId());
            intent.putExtra("USER_ID", message.getUserId());
            pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        } else {
            intent.putExtra(Constant.TYPE, message.getType());
            intent.setClass(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            pendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        Notification notification = builder.setContentTitle("消息通知")
                .setContentText(message.getMessage())
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        manager.notify(1, notification);
    }

    @Override
    public void onNotificationOpened(Context context, String title, String summary, String extraMap) {
        Log.e(REC_TAG, "onNotificationOpened, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap);
    }

    @Override
    protected void onNotificationClickedWithNoAction(Context context, String title, String summary, String extraMap) {
        Log.e(REC_TAG, "onNotificationClickedWithNoAction, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap);
    }

    @Override
    protected void onNotificationReceivedInApp(Context context, String title, String summary, Map<String, String> extraMap, int openType, String openActivity, String openUrl) {
        Log.e(REC_TAG, "onNotificationReceivedInApp, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap + ", openType:" + openType + ", openActivity:" + openActivity + ", openUrl:" + openUrl);
    }

    @Override
    protected void onNotificationRemoved(Context context, String messageId) {
        Log.e(REC_TAG, "onNotificationRemoved");
    }
}
