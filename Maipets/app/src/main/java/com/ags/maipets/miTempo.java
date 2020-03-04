package com.ags.maipets;

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
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class miTempo extends Service {

    private NotificationManager nm;
    private final String CHANNEL_ID = "Channel2";
    private NotificationCompat.Builder not;
    private final int NOTIFICATION_ID = 666;

    // Para versiones de API >= 26
    private NotificationChannel ch;
    private final String CHANNEL_NAME = "Información";

    // Sonido notificación
    private Uri uri;


    @Override
    public void onCreate()
    {
        Log.i("MASCOTA", "Servicio creado para aviso de mascota") ;
    }


    public int onStartCommand(Intent tempo, int flags, int startId)
    {

        Log.i("RETARDO", "Retardo de 1 segundo en la notificación");
        new CountDownTimer(1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {}

            @Override
            public void onFinish() {
                Log.i("MASCOTA","Finalizado");
                execN();
            }
        }.start();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy()
    {
        Log.i("MASCOTA", "Servicio destruido") ;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {return null;}

    // Método que contruye y lanza la notificacion
    private void execN () {

        // Ruta archivo audio para notificacion
        // uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sound);
        uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getPackageName() + "/" + R.raw.sound);

        nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        createNotificationChannel();


        not = new NotificationCompat.Builder(miTempo.this, CHANNEL_ID);
        not.setSmallIcon(R.drawable.icon); // Obligatorio
        not.setContentTitle("Mascota añadida!");
        not.setContentText("Ha añadido con éxito una mascota!");
        // Asignamos sonido a la notificación
        // not.setDefaults(Notification.DEFAULT_SOUND) ;
        not.setSound(uri);
        // Fijamosla prioridad de la notificacion
        not.setPriority(NotificationCompat.PRIORITY_HIGH);
        // Enviamos la notificacion por el canal
        nm.notify(NOTIFICATION_ID, not.build());
    }

    private void createNotificationChannel ()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            // Definimos los atributos de audio
            AudioAttributes att = new AudioAttributes.Builder()
                    .setContentType((AudioAttributes.CONTENT_TYPE_SONIFICATION))
                    .setUsage((AudioAttributes.USAGE_NOTIFICATION))
                    .build();

            // Creamos nuestro canal de comunicacion
            ch = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH );

            // Le anexamos la descripción
            ch.setDescription(CHANNEL_NAME);

            // Asocio el audio al canal creado con sus atributos
            ch.setSound(uri,att);

            // Incorporamos vibracion en la notificación
            ch.enableVibration(true);
            ch.setVibrationPattern(new long [] {300l} );

            // Creamos el canal
            nm.createNotificationChannel(ch);

            // Para poder verlo con la pantalla bloqueada
            ch.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        }
    }
}
