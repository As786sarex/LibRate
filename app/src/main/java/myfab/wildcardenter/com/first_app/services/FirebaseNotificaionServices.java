package myfab.wildcardenter.com.first_app.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import myfab.wildcardenter.com.first_app.R;
import myfab.wildcardenter.com.first_app.activities.MainActivity;

public class FirebaseNotificaionServices extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        showNotification(remoteMessage.getNotification().getBody());

    }
    public void showNotification(String msg){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel("myChannelId","MyChannelId"
                    ,NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager=getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
        PendingIntent intent=PendingIntent.getActivity(this,0
                ,new Intent(this,MainActivity.class),0);
        NotificationCompat.Builder notification=new NotificationCompat.Builder(this,"myChannelId")
                .setSmallIcon(R.drawable.ic_android_black_24dp)
                .setContentIntent(intent)
                .setAutoCancel(true)
                .setContentText("hay")
                .setContentTitle("Hello there");
        NotificationManagerCompat manager=NotificationManagerCompat.from(this);
            manager.notify(0,notification.build());

    }
}
