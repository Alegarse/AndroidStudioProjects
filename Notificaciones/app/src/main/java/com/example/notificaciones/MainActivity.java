package com.example.notificaciones;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button boton;
    private NotificationManager nm;
    private final String CHANNEL_ID = "Channel2";
    private NotificationCompat.Builder not;
    private final int NOTIFICATION_ID = 666;
    private NotificationChannel ch;
    private final String CHANNEL_NAME = "Notificaciones";
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Definimos la ruta hacia el archivo de audio
        uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getPackageName() + "/" + R.raw.audio);

        boton = findViewById(R.id.boton);
        boton.setOnClickListener(v ->
        {
            // Obtenenmos referencia al servicio de notificaciones de Android
            // Es necesario el casting
            nm =(NotificationManager)getSystemService(NOTIFICATION_SERVICE);

            // Solo si se usa version API >= 26
            createNotificationChannel();

            // Ahora creamos la notificación
            not = new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID);
            not.setSmallIcon(R.drawable.ic_bell); // Android me obliga a definir un icono
            not.setContentTitle("Prueba de notificación");
            not.setContentText("Estamos probando el servicio de notificaciones de Android");
            not.setSound(uri);
            not.setPriority(NotificationCompat.PRIORITY_HIGH);


            //Ahora una vez configurada enviamos la notificacion a través del canal elegido
            nm.notify(NOTIFICATION_ID, not.build());

        });

    }
     // Comprueba si la aplicacioón esta corriendo en una version de API => 26,
     // en cuyo caso tendremos que crear el canal de comunicacion.
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
            //
            ch.setDescription(CHANNEL_NAME);

            // Asociamos el audio al canal
            ch.setSound(uri,att);

            //Hacemos que vibre
            ch.enableVibration(true);
            ch.setVibrationPattern(new long [] {10000l} );

            // Habilitamos el "LED"
            // Para que fncione debemos habilitar laopcion en el menu deaccesibilidad del dispositivo
            ch.enableLights(true);

            // Creamos el canal
            nm.createNotificationChannel(ch);

            // Verlo con la pantalla bloqueada
            ch.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        }
    }
}
