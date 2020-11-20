package com.example.carplus3g365v2.Modelos;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.carplus3g365v2.BuildConfig;
import com.example.carplus3g365v2.Carplus3G;

import com.example.carplus3g365v2.Login;
import com.example.carplus3g365v2.MenuPrincipal;
import com.example.carplus3g365v2.R;
import com.example.carplus3g365v2.Reservar;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Methods extends AppCompatActivity {

    // Variable general de contexto actividad origen
    Context contexto;


    public Methods(Context contexto) {
        this.contexto = contexto;
    }

    // Métodos para todas las actividades
    public void cerrarSesion() {

        Carplus3G constantes = (Carplus3G) contexto.getApplicationContext();
        constantes.setIniciadoSesion(false);
        constantes.setErasePass(true);

        Intent salir = new Intent(contexto, Login.class);
        contexto.startActivity(salir);
        finish();
    }

    public void iraMenuPpal() {
        Intent menuPpal = new Intent(contexto, MenuPrincipal.class);
        contexto.startActivity(menuPpal);
        finish();
    }
}
