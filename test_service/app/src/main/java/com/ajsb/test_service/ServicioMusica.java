package com.ajsb.test_service;

/**
 * Antonio José Sánchez Bujaldón
 * Programación Multimedia y de Dispositivos Móviles
 * curso 2019/20
 */

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class ServicioMusica extends Service
{
    private MediaPlayer media ;

    /**
     */
    @Override
    public void onCreate()
    {
        Log.i("MUSICA", "Servicio creado") ;
        media = MediaPlayer.create(this, R.raw.audioa) ;
    }

    /**
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags,
                                             int startId)
    {
        Log.i("MUSICA", "Servicio arrancado") ;
        media.start() ;

        // por defecto, cuando la app finaliza, el
        // servicio se reinicia.
        return START_STICKY ;
    }

    /**
     */
    @Override
    public void onDestroy()
    {
        Log.i("MUSICA", "Servicio destruido") ;
        media.stop() ;
    }

    /**
     * @param intent
     * @return
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
