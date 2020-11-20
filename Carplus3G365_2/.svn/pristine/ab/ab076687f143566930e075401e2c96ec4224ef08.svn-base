package com.example.carplus3g365v2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.carplus3g365v2.Modelos.Methods;
import com.example.carplus3g365v2.RestClient.RequestMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Estado extends AppCompatActivity {

    private TextView txtEstadoEstado, txtEstadoDescEstado, txtMarcaEstado, txtModeloEstado, txtTelefono;
    private TextView txtGrupoEstado, txtColorEstado, txtDocumentoEstado, txtDepositarioEstado, txtEmail;
    private TextView txtF1Estado, txtF2Estado, txtDireccionEstado, txtOfiEnt, txtOfiRec;
    private Button btnLibreEstado, btnAlquiladoEstado, btnTallerEstado, btnSustraidoEstado, btnBloqueadoEstado;
    private Button btnLibreSucio, btnLibreSucioDes, btnPreparacion, btnPreparacionDes, btnLibreLimpio, btnLibreLimpioDes;

    private Methods metodo;

    private RestClient rest;
    private String respuesta = "";
    private ProgressDialog miDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado);

        // Instanciamos variables de la actividad
        // Varibles de la actividad
        TextView txtMatriculaEstado = findViewById(R.id.txtMatriculaEstado);
        txtEstadoEstado = findViewById(R.id.txtEstadoEstado);
        txtEstadoDescEstado = findViewById(R.id.txtEstadoDescEstado);
        txtMarcaEstado = findViewById(R.id.txtMarcaEstado);
        txtModeloEstado = findViewById(R.id.txtModeloEstado);
        txtGrupoEstado = findViewById(R.id.txtGrupoEstado);
        txtColorEstado = findViewById(R.id.txtColorEstado);
        txtDocumentoEstado = findViewById(R.id.txtDocumentoEstado);
        txtDepositarioEstado = findViewById(R.id.txtDepositarioEstado);
        txtF1Estado = findViewById(R.id.txtF1Estado);
        txtF2Estado = findViewById(R.id.txtF2Estado);
        txtDireccionEstado = findViewById(R.id.txtDireccionEstado);
        txtOfiEnt = findViewById(R.id.txtOfiEnt);
        txtOfiRec = findViewById(R.id.txtOfiRec);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtEmail = findViewById(R.id.txtEmail);

        metodo = new Methods(Estado.this);

        // Botones cambiantes
        btnLibreEstado = findViewById(R.id.btnLibreEstado);
        btnAlquiladoEstado = findViewById(R.id.btnAlquiladoEstado);
        btnTallerEstado = findViewById(R.id.btnTallerEstado);
        btnSustraidoEstado = findViewById(R.id.btnSustraidoEstado);
        btnBloqueadoEstado = findViewById(R.id.btnBloqueadoEstado);

        // Botones
        ImageButton menu_ppal = findViewById(R.id.menuPpalE);
        ImageButton salir = findViewById(R.id.salirE);
        btnLibreSucio = findViewById(R.id.btnLibreSucio);
        btnLibreSucioDes = findViewById(R.id.btnLibreSucioDes);
        btnPreparacion = findViewById(R.id.btnPreparacion);
        btnPreparacionDes = findViewById(R.id.btnPreparacionDes);
        btnLibreLimpio = findViewById(R.id.btnLibreLimpio);
        btnLibreLimpioDes = findViewById(R.id.btnLibreLimpioDes);

        // Ocultamos los botones al inicio
        btnLibreLimpio.setVisibility(View.GONE);
        btnLibreLimpioDes.setVisibility(View.GONE);
        btnPreparacion.setVisibility(View.GONE);
        btnPreparacionDes.setVisibility(View.GONE);
        btnLibreSucio.setVisibility(View.GONE);
        btnLibreSucioDes.setVisibility(View.GONE);

        // Asignamos la matricula introducida
        Carplus3G constantes = ((Carplus3G) getApplicationContext());
        txtMatriculaEstado.setText(constantes.getMatricula());

        // Ocultamos elementos hasta que sea determinado su estado actual
        txtEstadoEstado.setVisibility(View.GONE);

        // Escuchadores de los botones
        menu_ppal.setOnClickListener(v -> metodo.iraMenuPpal());
        salir.setOnClickListener(v -> metodo.cerrarSesion());
    }

    public void onStart() {
        super.onStart();

        Carplus3G constantes = ((Carplus3G) getApplicationContext());
        if (!constantes.cmpLastDate()) {
            constantes.setLastDate();
            constantes.setcargadoVersion(0);
            Intent i = new Intent();
            i.setClass(Estado.this, Login.class);
            startActivity(i);
            Estado.this.finish();
            return;
        }

        if (!Carplus3G.cmpConexion(this)) {
            Carplus3G.dialogInternet(this);
        } else {

            miDialog = new ProgressDialog(this);
            miDialog.setMessage(getString(R.string.getting_info_state_veh));
            miDialog.setTitle(getString(R.string.msginit));
            miDialog.setCancelable(false);
            miDialog.show();

            // Verificamos los permisos para las opciones de menú
            // Realizamos la conexión al servidor a través de un hilo

            Thread hilo = new Thread() {
                public void run() {

                    Carplus3G constantes = ((Carplus3G) getApplicationContext());
                    rest = new RestClient(Carplus3G.URL);
                    rest.AddParam("empresa", constantes.getEmpresa());
                    rest.AddParam("accion", "getstatus");
                    rest.AddParam("firma", Carplus3G.SHA256("gst"+constantes.getMatricula()));
                    rest.AddParam("matricula", constantes.getMatricula());
                    rest.AddParam("id", constantes.getTerminalId());

                    try {
                        rest.Execute(RequestMethod.POST);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    respuesta = rest.getResponse();
                    handlerHiloInicio.post(btnEnviarBack);
                    miDialog.dismiss();
                }

            };
            hilo.start();
        }
    }

    // Manejador para el hilo de OnStart
    final Handler handlerHiloInicio = new Handler();

    // Función asignada tras el hilo de OnStart
    final Runnable btnEnviarBack = new Runnable() {
        public void run() {
            ProcesarHilo();
        }
    };

    private void ProcesarHilo() {

        String estado = "";
        String mensaje = "";

        if (!respuesta.equals("error")) {
            try {
                JSONObject json = new JSONObject(respuesta);

                estado = json.getString("status");
                mensaje = json.getString("statusMsg");

                if (estado.equals("1"))
                {
                    JSONObject contenido = json.getJSONObject("content");

                    String estado_marca = contenido.getString("marca");
                    String estado_modelo = contenido.getString("modelo");
                    String estado_grupo = contenido.getString("grupo");
                    String estado_color = contenido.getString("color");
                    String estado_contrato = contenido.getString("contrato");
                    String estado_ofiEnt = contenido.getString("ofiEnt");
                    String estado_ofiRec = contenido.getString("ofiRec");
                    String estado_aviso = contenido.getString("aviso");
                    String estado_telefono = contenido.getString("telefono");
                    String estado_email = contenido.getString("email");

                    if (!estado_aviso.equalsIgnoreCase("")) {
                        AlertDialog ad = new AlertDialog.Builder(this).create();
                        ad.setTitle(getString(R.string.notice));
                        ad.setCancelable(false);
                        ad.setMessage(estado_aviso);
                        ad.setButton(getString(R.string.accept), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        ad.setIcon(R.drawable.icon);
                        ad.show();
                    }

                    int estado_planstat = -1;
                    if (!contenido.getString("planstat").equals("")) {
                        estado_planstat = Integer.parseInt(contenido.getString("planstat"));
                    }

                    String estado_direccion = contenido.getString("direccion");
                    String estado_titular =	contenido.getString("titular");
                    String estado_salida = contenido.getString("salida");
                    String estado_entrada =	contenido.getString("entrada");
                    int estado_estado = -1;
                    if (!contenido.getString("estado").equals("")) {
                        estado_estado =	Integer.parseInt(contenido.getString("estado"));
                    }
                    int estado_existe = 0;
                    if (!contenido.getString("existe").equals("")) {
                        estado_existe =	contenido.getInt("existe");
                    }

                    if (estado_existe == 0) {

                        Toast.makeText(this, R.string.car_plate_noexist, Toast.LENGTH_SHORT).show();

                        Bundle b = new Bundle();
                        b.putString("tipo","estado");

                        Intent ir = new Intent(this,Scan.class);
                        ir.putExtras(b);
                        startActivity(ir);
                        this.finish();

                    } else {
                        txtMarcaEstado.setText(estado_marca);
                        txtModeloEstado.setText(estado_modelo);
                        txtGrupoEstado.setText(estado_grupo);
                        txtColorEstado.setText(estado_color);
                        txtDocumentoEstado.setText(estado_contrato);
                        txtDireccionEstado.setText(estado_direccion);
                        txtDepositarioEstado.setText(estado_titular);
                        txtF1Estado.setText(estado_salida);
                        txtF2Estado.setText(estado_entrada);
                        txtOfiEnt.setText(estado_ofiEnt);
                        txtOfiRec.setText(estado_ofiRec);
                        txtTelefono.setText(estado_telefono);
                        txtEmail.setText(estado_email);

                        LinearLayout contExtras = (LinearLayout) findViewById(R.id.contExtrasEstado);

                        switch (estado_estado) {
                            case 0: //Libre
                                btnLibreEstado.setBackgroundColor(getColor(R.color.verde));
                                //btnLibreEstado.setChecked(true);
                                btnLibreEstado.setClickable(false);
                                btnAlquiladoEstado.setEnabled(false);
                                btnTallerEstado.setEnabled(false);
                                btnSustraidoEstado.setEnabled(false);
                                btnBloqueadoEstado.setEnabled(false);

                                switch (estado_planstat) {
                                    case 1: //LIBRE SUCIO
                                        txtEstadoEstado.setVisibility(View.VISIBLE);
                                        txtEstadoDescEstado.setText(getString(R.string.free_dirty));
                                        txtEstadoDescEstado.setTextColor(Color.RED);
                                        btnLibreSucioDes.setVisibility(View.VISIBLE);
                                        btnPreparacion.setVisibility(View.VISIBLE);
                                        btnPreparacion.setText(getString(R.string.preparation_change));
                                        btnLibreLimpioDes.setVisibility(View.VISIBLE);
                                        break;
                                    case 2: //PREPARACION
                                        txtEstadoEstado.setVisibility(View.VISIBLE);
                                        txtEstadoDescEstado.setText(getString(R.string.into_preparation));
                                        txtEstadoDescEstado.setTextColor(Color.rgb(255, 127, 0));
                                        btnLibreSucioDes.setVisibility(View.VISIBLE);
                                        btnPreparacionDes.setVisibility(View.VISIBLE);
                                        btnLibreLimpio.setVisibility(View.VISIBLE);
                                        btnLibreLimpio.setText(getString(R.string.change_free_clean));
                                        break;
                                    case 0: //LIBRE LIMPIO
                                        txtEstadoEstado.setVisibility(View.VISIBLE);
                                        txtEstadoDescEstado.setText(getString(R.string.free_clean));
                                        txtEstadoDescEstado.setTextColor(Color.rgb(22, 199, 43));
                                        btnLibreSucio.setVisibility(View.VISIBLE);
                                        btnLibreSucio.setText(getString(R.string.change_free_dirty));
                                        btnPreparacionDes.setVisibility(View.VISIBLE);
                                        btnLibreLimpioDes.setVisibility(View.VISIBLE);
                                        break;
                                }
                                break;
                            case 1: //Alquilado
                                btnLibreEstado.setEnabled(false);
                                btnAlquiladoEstado.setBackgroundColor(getColor(R.color.verde));
                                btnAlquiladoEstado.setClickable(true);
                                btnTallerEstado.setEnabled(false);
                                btnSustraidoEstado.setEnabled(false);
                                btnBloqueadoEstado.setEnabled(false);

                                /* EXTRAS ES UN ARRAY Y LO MOSTRAMOS CON TOOGLE BUTTONS*/
                                String e = contenido.getString("extras");
                                if (!e.equals(""))
                                {
                                    JSONArray arrayExtras = new JSONArray(contenido.getString("extras"));
                                    for (int i = 0; i < arrayExtras.length(); i++) {
                                        JSONObject row = arrayExtras.getJSONObject(i);
                                        LinearLayout l = new LinearLayout(this);
                                        l.setGravity(Gravity.CENTER_VERTICAL);
                                        l.setPadding(1,1,1,1);
                                        l.setBackgroundResource(R.drawable.fondo_blanco_borde_gris);

                                        ImageView img = new ImageView (this);
                                        img.setImageResource(R.drawable.tick);
                                        img.setPadding(0, 0, 5, 0);

                                        TextView t = new TextView(this);
                                        t.setText(row.getString("desc"));
                                        t.setTextColor(Color.BLACK);
                                        t.setTextSize( 16);

                                        l.addView(img);
                                        l.addView(t);

                                        ToggleButton b = new ToggleButton(this);
                                        b.setTextOff(row.getString("desc"));
                                        b.setText(row.getString("desc"));
                                        b.setTextOn(row.getString("desc"));
                                        b.setChecked(true);

                                        contExtras.addView(l);
                                    }
                                }
                                break;
                            case 2: //Taller
                                btnLibreEstado.setEnabled(false);
                                btnAlquiladoEstado.setEnabled(false);
                                btnTallerEstado.setBackgroundColor(getColor(R.color.verde));
                                btnTallerEstado.setClickable(true);
                                btnSustraidoEstado.setEnabled(false);
                                btnBloqueadoEstado.setEnabled(false);
                                break;
                            case 3: //Sustraido
                                btnLibreEstado.setEnabled(false);
                                btnAlquiladoEstado.setEnabled(false);
                                btnSustraidoEstado.setBackgroundColor(getColor(R.color.verde));
                                btnSustraidoEstado.setClickable(true);
                                btnTallerEstado.setEnabled(false);
                                btnBloqueadoEstado.setEnabled(false);
                                break;
                            case 4: //Bloqueado
                                btnLibreEstado.setEnabled(false);
                                btnAlquiladoEstado.setEnabled(false);
                                btnBloqueadoEstado.setBackgroundColor(getColor(R.color.verde));
                                btnBloqueadoEstado.setClickable(true);
                                btnSustraidoEstado.setEnabled(false);
                                btnTallerEstado.setEnabled(false);
                                break;
                        }
                    }
                }
                else
                {
                    Toast.makeText(this, getString(R.string.error_happened)+": " +mensaje, Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            Carplus3G.dialogConexion(this);
        }
        miDialog.hide();
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, R.id.menuMenu, 1, getString(R.string.mnu_ppal)).setIcon(
                R.drawable.menu);
        menu.add(2, R.id.menuLogout,2, getString(R.string.change_user)).setIcon(
                R.drawable.user);
        menu.add(3, R.id.menuSalir, 3, getString(R.string.exit)).setIcon(R.drawable.exit);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = new Intent();
        getApplicationContext();

        switch (item.getItemId()) {

            case R.id.menuSalir:
                System.exit(0);
                break;
            case R.id.menuLogout:
                Carplus3G constantes = ((Carplus3G) getApplicationContext());
                constantes.setIniciadoSesion(false);
                i.setClass(this, Login.class);
                startActivity(i);
                this.finish();
                break;
            case R.id.menuMenu:
                i.setClass(this, MenuPrincipal.class);
                startActivity(i);
                this.finish();
                break;
        }
        return false;
    }

    // Deshabilito el botón de volver atrás
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return false;
    }

    public void btnLibreSucioClick(View view) {

        getApplicationContext();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.change_state_confirm)).setPositiveButton(getString(R.string.yes), dialogClickListenerLibreSucio)
                .setNegativeButton(getString(R.string.not), dialogClickListenerLibreSucio).show();
    }

    DialogInterface.OnClickListener dialogClickListenerLibreSucio = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            getApplicationContext();

            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    cambiaEstado("1");
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };

    public void btnPreparacionClick(View view) {

        getApplicationContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.change_state_confirm)).setPositiveButton(getString(R.string.yes), dialogClickListenerPreparacion)
                .setNegativeButton(getString(R.string.not), dialogClickListenerPreparacion).show();
    }

    DialogInterface.OnClickListener dialogClickListenerPreparacion = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            getApplicationContext();

            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    cambiaEstado("2");
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };

    public void btnNuevoVehiculoEstadoClick(View btn)
    {
        getApplicationContext();

        Bundle b = new Bundle();
        b.putString("tipo","estado");

        Intent i = new Intent();
        i.putExtras(b);
        i.setClass(this, Scan.class);
        startActivity(i);
        this.finish();
    }

    public void btnLibreLimpioClick(View view) {

        getApplicationContext();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.change_state_confirm)).setPositiveButton(getString(R.string.yes), dialogClickListenerLibreLimpio)
                .setNegativeButton(getString(R.string.not), dialogClickListenerLibreLimpio).show();
    }

    DialogInterface.OnClickListener dialogClickListenerLibreLimpio = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            getApplicationContext();

            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    cambiaEstado("0");
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };

    public void cambiaEstado (final String nuevoEstado)
    {

        if (!Carplus3G.cmpConexion(this))
        {
            Carplus3G.dialogInternet(this);
        }
        else
        {
            miDialog = new ProgressDialog(this);
            miDialog.setMessage(getString(R.string.sending_info_serv));
            miDialog.setTitle(getString(R.string.changing_state_veh));
            miDialog.setCancelable(false);
            miDialog.show();

            Thread hilo = new Thread() {
                public void run() {
                    Carplus3G constantes = ((Carplus3G) getApplicationContext());
                    rest = new RestClient(Carplus3G.URL);
                    rest.AddParam("empresa", constantes.getEmpresa());
                    rest.AddParam("accion", "setstatus");
                    rest.AddParam("firma", Carplus3G.SHA256("sst"+constantes.getMatricula()));
                    rest.AddParam("nuevoEstado",nuevoEstado);

                    rest.AddParam("matricula", constantes.getMatricula());
                    rest.AddParam("id", constantes.getTerminalId());

                    try {
                        rest.Execute(RequestMethod.POST);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    respuesta = rest.getResponse();
                    handlerHiloCambioEstado.post(CambioEstadoBack);
                    miDialog.dismiss();
                }

            };
            hilo.start();
        }
    }

    final Handler handlerHiloCambioEstado = new Handler();

    final Runnable CambioEstadoBack = new Runnable() {
        public void run() {
            ProcesarCambioEstado();
        }
    };

    private void ProcesarCambioEstado() {

        String estado = "";
        String mensaje = "";

        if (!respuesta.equals("error"))
        {

            try {

                JSONObject json = new JSONObject(respuesta);
                estado = json.getString("status");
                mensaje = json.getString("statusMsg");

                if (estado.equals("1"))
                {
                    JSONObject contenido = json.getJSONObject("content");
                    int estado_nuevo = contenido.getInt("nuevo_estado");

                    switch (estado_nuevo)
                    {
                        case 0: //Libre limpio
                            txtEstadoDescEstado.setText(getString(R.string.free_clean));
                            txtEstadoDescEstado.setTextColor(Color.rgb(22, 199, 43));
                            btnLibreSucio.setVisibility(View.VISIBLE);
                            btnLibreSucio.setText(getString(R.string.change_free_dirty));
                            btnLibreSucioDes.setVisibility(View.GONE);
                            btnPreparacion.setVisibility(View.GONE);
                            btnPreparacionDes.setVisibility(View.VISIBLE);
                            btnLibreLimpio.setVisibility(View.GONE);
                            btnLibreLimpioDes.setVisibility(View.VISIBLE);
                            break;
                        case 2: //Preparacion
                            txtEstadoDescEstado.setText(getString(R.string.into_preparation));
                            txtEstadoDescEstado.setTextColor(Color.rgb(255, 127, 0));
                            btnLibreSucio.setVisibility(View.GONE);
                            btnLibreSucioDes.setVisibility(View.VISIBLE);
                            btnPreparacion.setVisibility(View.GONE);
                            btnPreparacionDes.setVisibility(View.VISIBLE);
                            btnLibreLimpio.setVisibility(View.VISIBLE);
                            btnLibreLimpio.setText(getString(R.string.change_free_clean));
                            btnLibreLimpioDes.setVisibility(View.GONE);
                            break;
                        case 1: //Libre sucio
                            txtEstadoDescEstado.setText(getString(R.string.free_dirty));
                            txtEstadoDescEstado.setTextColor(Color.RED);
                            btnLibreSucio.setVisibility(View.GONE);
                            btnLibreSucioDes.setVisibility(View.VISIBLE);
                            btnPreparacion.setVisibility(View.VISIBLE);
                            btnPreparacion.setText(getString(R.string.preparation_change));
                            btnPreparacionDes.setVisibility(View.GONE);
                            btnLibreLimpio.setVisibility(View.GONE);
                            btnLibreLimpioDes.setVisibility(View.VISIBLE);
                            break;

                    }
                    Toast.makeText(this,R.string.correct_change_state, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, getString(R.string.produerror) + ": " + mensaje, Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        else
        {
            Carplus3G.dialogConexion(this);
        }
        miDialog.hide();
    }
}