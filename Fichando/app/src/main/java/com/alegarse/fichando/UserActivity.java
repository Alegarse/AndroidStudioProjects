package com.alegarse.fichando;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UserActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    // Botones de pantalla
    private Button entrada, salida;

    // Variables del codigo
    private String FECHA_ENT, FECHA_SAL,FECHA;
    private TextView DIA,NOMBRE;
    private String situacion1 = "Fichaje de entrada";
    private String situacion2 = "Fichaje de salida";
    private String horaEnt, ubi;

    //Variables para la ubicacion
    private LocationManager locManager;
    private Location loc;

    // Para guardar los datos en BBDD (Firebase)
    private FirebaseAuth mAuth;
    private FirebaseDatabase fbdatabase ;
    private DatabaseReference dbref, dbBase, dB1, dB2, dB3;
    private String nombre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        //Instanciamos variables
        entrada = findViewById(R.id.entrada);
        salida = findViewById(R.id.salida);
        NOMBRE = findViewById(R.id.nombreT2);
        DIA = findViewById(R.id.fecha);

        // Iniciamos BBDD
        // Inicializamos Firebase
        mAuth = FirebaseAuth.getInstance();

        // Obtenemos nombre del usuario logeado para mostrarlo
        Bundle parametros = getIntent().getExtras();
        nombre = parametros.getString("nombre");
        NOMBRE.setText(nombre);

        // Obtenemos fecha actual para organizar en BBDD
        Date fecha = Calendar.getInstance().getTime();
        SimpleDateFormat fechaFormat = new SimpleDateFormat("dd-MM-yyyy");
        FECHA = fechaFormat.format(fecha);
        DIA.setText(FECHA);

        //Obtenemos la instancia de FirebaseDatabase
        fbdatabase =  FirebaseDatabase.getInstance();

        // Obtenemos referencia al documento de usuarios en FB
        dbBase = fbdatabase.getReference("CarGestion/Registros/");
        dbref = fbdatabase.getReference("CarGestion/Registros/"+nombre);

        //Para comprobaciones
        dB1 = dbref.child(FECHA);
        dB2 = dB1.child(situacion1);
        dB3 = dB1.child(situacion2);

        // Seleccionamos los permisos para ubicarnos
        ActivityCompat.requestPermissions(UserActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Obtenemos fecha antual para organizar en BBDD
        Date fecha = Calendar.getInstance().getTime();
        SimpleDateFormat fechaFormat = new SimpleDateFormat("dd-MM-yyyy");
        FECHA = fechaFormat.format(fecha);

        // Verificamos si este día ya se ha fichado
        dbBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    String user = dataSnapshot1.getKey();
                    if (user.equals(nombre)) {
                        if (dataSnapshot1.hasChild(FECHA)) {
                            if (dataSnapshot1.child(FECHA).hasChild(situacion1)){
                                if (dataSnapshot1.child(FECHA).hasChild(situacion2)) {
                                    allfalse();
                                    Toast.makeText(getApplicationContext(),R.string.nextDay,Toast.LENGTH_SHORT).show();
                                } else {
                                    entASal();
                                }
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.fe, Toast.LENGTH_SHORT).show();
                            inicial();
                        }
                    }

                }
                if (!dataSnapshot.exists()) {
                    inicial();
                    Toast.makeText(getApplicationContext(), R.string.fe, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    private void inicial() {
        entrada.setEnabled(true);
        entrada.setBackgroundColor(0xFF1B5E20);
        salida.setEnabled(false);
        salida.setBackgroundColor(0xFFCDCDCD);
    }

    private void entASal() {
        entrada.setEnabled(false);
        entrada.setBackgroundColor(0xFFCDCDCD);
        salida.setEnabled(true);
        salida.setBackgroundColor(0xFFAF3131);
    }

    private void allfalse() {
        entrada.setEnabled(false);
        entrada.setBackgroundColor(0xFFCDCDCD);
        salida.setEnabled(false);
        salida.setBackgroundColor(0xFFCDCDCD);
    }

    public void logout(View view) {

        // Eliminamos la session de la caché del dispositivo
        SessionManagement sessionManagement = new SessionManagement(UserActivity.this);
        sessionManagement.removeSession();
        Toast.makeText(getApplicationContext(),R.string.closeSes,Toast.LENGTH_LONG).show();

        // Volvemos a la actividad de logeo
        Intent volver = new Intent(UserActivity.this, MainActivity.class);
        //volver.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        mAuth.signOut();
        startActivity(volver);
    }

    // Método OnClick para guardar los datos de fichaje de entrada
    public void entrada(View view) {
        FECHA_ENT = DateFormat.getDateTimeInstance().format(new Date());
        grabardatos("entrada");
        entASal();
    }

    // Método OnClick para guardar los datos de fichaje de salida
    public void salida(View view) {
        FECHA_SAL = DateFormat.getDateTimeInstance().format(new Date());
        grabardatos("salida");
        allfalse();
    }

    // Método para obtener la ubicación del trabajador al momento de fichar
    private String obtenerUbicacion() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(getApplicationContext(), R.string.gps_not, Toast.LENGTH_LONG).show();
            return "N/A";
        } else {
            locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            String tvLatitud = String.valueOf(loc.getLatitude());
            String tvLongitud = String.valueOf(loc.getLongitude());
            String tvAltura = String.valueOf(loc.getAltitude());
            String tvPrecision = String.valueOf(loc.getAccuracy());
            String ubicacion = "Latitud: " + tvLatitud + ", Longitud:" + tvLongitud + ", Altura: " + tvAltura + ", Precisión: " + tvPrecision;

            return ubicacion;
        }
    }

    // Método de grabación de datos en la BBDD
    private void grabardatos(String dato) {

        // Datos comunes
        ubi = obtenerUbicacion();

        switch (dato) {
            case "entrada":
                //Cogemos el valor de los datos
                horaEnt = FECHA_ENT;
                // Creamos un objeto de registro
                Registro registroEnt = new Registro (horaEnt,ubi);
                dB2.setValue(registroEnt) ;
                setResult(RESULT_OK);
                break;

            case "salida":
                //Cogemos el valor de los datos
                horaEnt = FECHA_SAL;
                // Creamos un objeto de registro
                Registro registroSal = new Registro (horaEnt,ubi);
                dB3.setValue(registroSal) ;
                setResult(RESULT_OK);
                break;
        }
    }
}
