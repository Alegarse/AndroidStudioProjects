package com.example.carplus3g365v2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.carplus3g365v2.Modelos.Methods;
import com.example.carplus3g365v2.RestClient.RequestMethod;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Scan extends AppCompatActivity {

    public RestClient rest;
    public String respuesta = "";
    public ProgressDialog miDialog;
    public String tipo = "";
    private ImageButton menu_ppal, salir;
    public Button btnBuscar;

    private Methods metodo;

    public EditText txtMatricula;
    public InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matricula);

        // Instanciamos los elementos de la actividad
        menu_ppal = findViewById(R.id.menuPpalM);
        salir = findViewById(R.id.salirM);
        txtMatricula = findViewById(R.id.txtMatricula);
        btnBuscar = findViewById(R.id.btnBuscarVehiculo);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        metodo = new Methods(Scan.this);

        // Escuchadores de los botones
        menu_ppal.setOnClickListener(v -> metodo.iraMenuPpal());
        salir.setOnClickListener(v -> metodo.cerrarSesion());

        // Recibimos los datos que indican desde que intent venimos
        Bundle b = getIntent().getExtras();
        tipo = b.getString("tipo");

        // Escuchador para el EditText
        txtMatricula.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Oculto el teclado de pantalla
                    imm.hideSoftInputFromWindow(txtMatricula.getWindowToken(), 0);
                    // Realizo el click en el botón continuar
                    btnBuscar.performClick();
                    return true;
                }
                return false;
            }
        });
    }

    // Creamos el Menú inferior de Android
    /*------------------------------------------------------------------------------------------------*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, R.id.menuMenu, 1, getString(R.string.mnu_ppal)).setIcon(
                R.drawable.menu);
        menu.add(2, R.id.menuScan, 2, getString(R.string.read_qr_code)).setIcon(
                R.drawable.qr);
        menu.add(3, R.id.menuLogout,3, getString(R.string.change_user)).setIcon(
                R.drawable.user);
        menu.add(4, R.id.menuSalir, 4, getString(R.string.exit)).setIcon(R.drawable.exit);

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
            case R.id.menuScan:
                if (cmpPackages("Barcode Scanner")) {
                    i = new Intent("com.google.zxing.client.android.SCAN");
                    i.setPackage("com.google.zxing.client.android");
                    i.putExtra("SCAN_MODE", "QR_CODE_MODE");
                    startActivityForResult(i, 0);
                } else {
                    Toast.makeText(this, R.string.need_bar_scan_app,Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.menuMenu:
                i.setClass(this, MenuPrincipal.class);
                startActivity(i);
                this.finish();
                break;
        }
        return false;
    }

    Boolean cmpPackages(String Nombre) {
        PackageManager pm = this.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        @SuppressLint("WrongConstant") List<ResolveInfo> list = pm.queryIntentActivities(intent,
                PackageManager.PERMISSION_GRANTED);
        // Variable comprobación existencia
        boolean existe = false;

        for (ResolveInfo rInfo : list) {
            if (rInfo.activityInfo.applicationInfo.loadLabel(pm).toString()
                    .equals(Nombre)) {
                existe = true;
            }
        }
        return existe;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                txtMatricula.setText(contents);

            } else if (resultCode == RESULT_CANCELED) {
                // Maneja cuando se cancela
            }
        }
    }

    // Para desactivar el botón volver del dispositivo
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return false;
    }

    public void btnBuscarVehiculoClick(View btn) {
        getApplicationContext();

        String valor = txtMatricula.getText().toString();
        valor = valor.toUpperCase();

        if (!valor.equals("")) {

            Carplus3G constantes = ((Carplus3G) getApplicationContext());
            constantes.setMatricula(valor);

            if (tipo.equals("estado")) {
                Intent estado = new Intent(Scan.this, Estado.class);
                startActivity(estado);
                finish();
            }
            if (tipo.equals("checkin")) {
                cmpCheckin();
            }
            if (tipo.equals("marbesol")) {
                Intent danios = new Intent(Scan.this, Damages.class);
                startActivity(danios);
                this.finish();
            }
        } else {
            Toast.makeText(this, R.string.enter_car_plate, Toast.LENGTH_SHORT).show();
        }
    }


    // Función para comprobar que el Vehículo existe y se puede realizar el contrato
    private void cmpCheckin() {
        if (!Carplus3G.cmpConexion(this)) {
            Carplus3G.dialogInternet(this);
        } else {
            miDialog = new ProgressDialog(this);
            miDialog.setMessage(getString(R.string.msgserv));
            miDialog.setTitle(getString(R.string.checking_car_plate));
            miDialog.setCancelable(false);
            miDialog.show();

            /* LANZO UN HILO PARA LLAMAR AL SERVIDOR */
            Thread hilo = new Thread() {
                public void run() {

                    String valor = txtMatricula.getText().toString();
                    valor = valor.toUpperCase();
                    Carplus3G constantes = ((Carplus3G) getApplicationContext());
                    constantes.setMatricula(valor);

                    rest = new RestClient(Carplus3G.URL);
                    rest.AddParam("empresa", constantes.getEmpresa());
                    rest.AddParam("id", constantes.getTerminalId());
                    rest.AddParam("accion", "precheckin");
                    rest.AddParam("firma", Carplus3G.SHA256("pch"+valor));
                    rest.AddParam("matricula", valor);

                    try {
                        rest.Execute(RequestMethod.POST);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    respuesta = rest.getResponse();
                    handlerBtnBuscarVehiculoChekin.post(btnBuscarVehiculoCheckinBack);
                }
            };
            hilo.start();
        }
    }

    // Creamos el manejadro del hilo y asignamos la función a ejecutar tras este
    final Handler handlerBtnBuscarVehiculoChekin = new Handler();

    final Runnable btnBuscarVehiculoCheckinBack = new Runnable() {
        public void run() {
            btnEnviarProcesar();
        }
    };

    private void btnEnviarProcesar() {

        String estado = "";
        String mensaje = "";

        if (!respuesta.equals("error")) {

            try {
                JSONObject json = new JSONObject(respuesta);

                estado = json.getString("status");
                mensaje = json.getString("statusMsg");

                if (estado.equals("1")) {
                    JSONObject contenido = json.getJSONObject("content");

                    if (contenido.getString("existe").equals("1")) {

                        if (contenido.getString("esCerrable").equals("1")) {
                            Intent checkin = new Intent(Scan.this, Checkin.class);
                            startActivity(checkin);
                            this.finish();
                        } else {
                            Toast.makeText(this, R.string.close_contract_at_office,Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Carplus3G constantes = ((Carplus3G) getApplicationContext());
                        if (constantes.getEmpresa().equals("marbesol")) {
                            Toast.makeText(this, R.string.contract_not_opened,Toast.LENGTH_LONG).show();
                        } else {
                            preguntarEstado();
                        }
                    }
                } else {
                    Toast.makeText(this, R.string.error_happened + " " + mensaje,
                            Toast.LENGTH_LONG).show();
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

    public void preguntarEstado() {
        //Toast.makeText(this, "No hay contratos abiertos de esta matricula, o la fecha de recogida no es la de hoy.",Toast.LENGTH_LONG).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage(getString(R.string.contract_not_opened2)).setPositiveButton(getString(R.string.yes), preguntarEstadoListener)
                .setNegativeButton(getString(R.string.not),preguntarEstadoListener).show();
    }

    DialogInterface.OnClickListener preguntarEstadoListener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            getApplicationContext();

            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    Intent i = new Intent(Scan.this, Estado.class);
                    startActivity(i);
                    Scan.this.finish();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };

    public void btnScanClick(View btn) {

        getApplicationContext();

        if (cmpPackages("Barcode Scanner")) {
            Intent i = new Intent();
            i = new Intent("com.google.zxing.client.android.SCAN");
            i.setPackage("com.google.zxing.client.android");
            i.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(i, 0);
        } else {
            Toast.makeText(this, R.string.need_bar_scan_app,Toast.LENGTH_SHORT).show();
        }
    }
}