package com.example.examenags;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

public class tempoService extends Service {

    private NotificationManager nm;
    private final String CHANNEL_ID = "Channel1";
    private NotificationCompat.Builder not;
    private final int NOTIFICATION_ID = 111;

    // Para versiones de API >= 26
    private NotificationChannel ch;
    private final String CHANNEL_NAME = "Temporizador";

    private Uri uri;

    @Override
    public void onCreate()
    {

    }

    public int onStartCommand(Intent tempo, int flags, int startId)
    {

        new CountDownTimer(5000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {}

            @Override
            public void onFinish() {
                ejecutar();
            }
        }.start();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy()
    {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void ejecutar() {

        uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getPackageName() + "/" + R.raw.sound);
        nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        createNotificationChannel();


        not = new NotificationCompat.Builder(tempoService.this, CHANNEL_ID);
        not.setSmallIcon(R.drawable.tempo);
        not.setContentTitle("Contador finalizado!");
        not.setContentText("Ha finalizado la cuenta atrás de 5 segundos!");
        not.setSound(uri);
        not.setPriority(NotificationCompat.PRIORITY_HIGH);
        nm.notify(NOTIFICATION_ID, not.build());
    }


    private void createNotificationChannel ()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            AudioAttributes att = new AudioAttributes.Builder()
                    .setContentType((AudioAttributes.CONTENT_TYPE_SONIFICATION))
                    .setUsage((AudioAttributes.USAGE_NOTIFICATION))
                    .build();

            ch = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH );

            ch.setDescription(CHANNEL_NAME);

            ch.setSound(uri,att);

            nm.createNotificationChannel(ch);

            ch.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        }
    }



}
