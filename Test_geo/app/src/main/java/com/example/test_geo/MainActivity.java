package com.example.test_geo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity {


    private final int PERMISSION_CODE= 666;

    private LocationManager manager;
    private Geocoder coder;

    private TextView salida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        salida = findViewById(R.id.salida);

        solicitarPermisos();


    }
    // Comprobamos si hace falta pedir permiso (no lo tengo aún concedido), en cuyo
    // caso debemos solicitarlo al usuario.
    private void solicitarPermisos()
    {
        if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION) != PERMISSION_GRANTED)
            geolocalizar();
        {
            ActivityCompat.requestPermissions(this,
                            new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                            PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        switch (requestCode)
        {
            case PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED);
                geolocalizar();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    private void geolocalizar()
    {
        Log.i("EMULADOR", "Geolocalizando...") ;

        // Solicitamos al sistema el gestor de localización
        manager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Utilizamos la clase Geocoderpar obtener la localizacion
        coder = new Geocoder(this);

        // Ahora siempre es de la misma manera
        // Obtenemos losdiferentes proveedores que nos proporciona el gestor
        List<Address> addressList;
        List<String> providers = manager.getAllProviders();

        for (String item: providers)
        {
            try {
                Location location = manager.getLastKnownLocation(item);
                if (location != null)
                {
                    // Utilizamos la clase Geocoder para obtener la localización
                    // aproximada, a partir de la longitud y latitud.
                    addressList = coder.getFromLocation(location.getLatitude(),
                            location.getLongitude(), 1) ;

                    if (addressList != null && addressList.size() > 0)
                        salida.setText("Localización: " + addressList.get(0).getLocality()) ;
                }
            } catch (SecurityException e){
                Log.i("EMULADOR",e.getMessage());
                e.printStackTrace();
            } catch (IOException e) {
                Log.i("EMULADOR", e.getMessage()) ;
                e.printStackTrace() ;
            }


        }

    }
}
