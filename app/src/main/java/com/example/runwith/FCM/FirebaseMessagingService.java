package com.example.runwith.FCM;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    //클라우드 서버에 등록되었을때 호출, 파라미터로 전달된 token이 앱을 구분하기 위한 고유한 키가 됨
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        //token을 서버로 전송
    }

    //클라우드 서버에서 메시지를 전송하면 자동으로 호출, 해당 메서드 안에서 메세지를 처리하여 사용자에게 알림을 보내거나 할 수 있음.
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        //수신한 메시지를 처리


    }
}
