package com.example.carplus3g365v2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.carplus3g365v2.RestClient.RequestMethod;

public class Login extends AppCompatActivity {

    // Variables de la interfaz
    private Button entrar, limpiar;
    private EditText emailUsuario, passwordUsuario;
    private InputMethodManager imm;
    public String respuesta = "";
    public RestClient rest;
    private Carplus3G constantes;


    private ProgressDialog miDialog;
    private ProgressDialog miDialogActualizacion;

    private String ultima_version_org = "";
    private descargarActualizacion tareaDescarga = null;
    private asyncDesvincular tareaDesvincular;

    // Se realiza cuando se lanza y crea la aplicación
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // Comprobaciones iniciales al crear la actividad

        // Resolución de pantalla
        checkRes();

        // Verificamos si hay conexión de internet
        if (!Carplus3G.cmpConexion(this)) {
            String msg = getString(R.string.need_net);
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
            this.finish();
        }

        // Establecemos el id del terminal
        Carplus3G constantes = (Carplus3G)getApplicationContext();
        constantes.setLastDate();
        constantes.setTerminalId(String.valueOf(Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID)));

        // Instanciamos componentes
        entrar = findViewById(R.id.enviar);
        limpiar = findViewById(R.id.limpiaDatos);
        //V Variantes boton apagar
        ImageButton salir = findViewById(R.id.salir);
        ImageButton desvincular = findViewById(R.id.desvincular);
        emailUsuario = findViewById(R.id.emailLogin);
        passwordUsuario = findViewById(R.id.passLogin);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        // Desactivamos el botón de desvincular 22-10-2020
        desvincular.setEnabled(false);
        desvincular.setVisibility(View.GONE);

        // Para pruebas de desarrollo
        emailUsuario.setText("a");
        passwordUsuario.setText("64902.");

        // Escuchador para pulsación de teclas en EditText email y password
        emailUsuario.setOnKeyListener((v, keyCode, event) -> {

            if ((event.getAction() == KeyEvent.ACTION_DOWN)
                    && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                // Realizo el clic en ese texto
                passwordUsuario.performClick();
                return true;
            }
            return false;
        });
        passwordUsuario.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                // Oculto el teclado en la pantalla
                imm.hideSoftInputFromWindow(passwordUsuario.getWindowToken(), 0);
                entrar.performClick();
                return true;
            }
            return false;
        });

        // Escuchador para botón entrar
        entrar.setOnClickListener(v -> btnEnviarClick(v));

        // Escuchador del botón limpiar campos
        limpiar.setOnClickListener(v -> {
            getApplicationContext();
            emailUsuario.setText("");
            passwordUsuario.setText("");
            emailUsuario.requestFocus();
        });

        // Escuchador del botón salir (Logout)
        salir.setOnClickListener(v -> {
            getApplicationContext();
            //Carplus3G.deleteDir(this.getCacheDir());
            System.exit(0);
        });

        // Escuchador del botón desvincular
        desvincular.setVisibility(View.GONE);
        desvincular.setOnClickListener(view -> btnDesvincularClick());
    }

    // Cuando arrancamos la aplicación
    @Override
    protected void onStart() {
        super.onStart();

        // Asignamos los elementos de la clase Carplus3G
        Carplus3G constantes = ((Carplus3G) getApplicationContext());

        if (!constantes.cmpLastDate()) {
            constantes.setLastDate();
            constantes.setcargadoVersion(0);
        }

        cargarUltimoEmailUsuario();

        if (constantes.getErasePass()) {
            guardarUltimoPasswordUsuario("");
        } else {
            cargarUltimoPasswordUsuario();
        }


        // Comporbamos datos para inicion de sesión automático

        int comprobadoVersion = constantes.getcargadoVersion();

        if (comprobadoVersion == 0) {

            if (!Carplus3G.cmpConexion(this)) {
                Carplus3G.dialogInternet(this);
            } else {
                miDialog = new ProgressDialog(this);
                miDialog.setTitle(getString(R.string.titleinit));
                miDialog.setMessage(getString(R.string.msginit));
                miDialog.setCancelable(false);
                miDialog.show();

                // Verificamos los permisos a la hora de mostrar el menú de opciones

                // Realizo la llamada a un servidor a través de un hilo
                Thread hilo = new Thread() {
                    public void run() {
                        Carplus3G constantes = ((Carplus3G) getApplicationContext());
                        rest = new RestClient(Carplus3G.URL);
                        rest.AddParam("empresa", "ninguna");
                        rest.AddParam("accion", "acceso");
                        rest.AddParam("firma", Carplus3G.SHA256("acc" + constantes.getTerminalId()));
                        rest.AddParam("marca", Build.MANUFACTURER);
                        rest.AddParam("modelo", Build.PRODUCT);
                        rest.AddParam("id", constantes.getTerminalId());

                        try {
                            rest.Execute(RequestMethod.POST);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        respuesta = rest.getResponse();
                        handlerHiloInicio.post(hiloInicioBack);
                        miDialog.dismiss();
                    }

                };
                hilo.start();
            }
        } else {
            if (this.checkAutologin()) {
                entrar.performClick();
            }
        }
    }

    // Variables de hilo (manejador de hilo (Handler))
    final Handler handlerHiloInicio = new Handler();

    // A realizar tras ejecucuión del hilo
    final Runnable hiloInicioBack = new Runnable() {
        public void run() {
            ProcesarHilo();
        }
    };

    // Función que se realiza trás ejecutar el hilo
    private void ProcesarHilo() {
        Carplus3G constantes = ((Carplus3G) getApplicationContext());

        String estado = "";
        String mensaje = "";

        if (!respuesta.equals("error")) {
            try {

                JSONObject json = new JSONObject(respuesta);

                estado = json.getString("status");
                mensaje = json.getString("statusMsg");
                mensaje = mensaje + "";

                if (estado.equals("1")) {
                    JSONObject contenido = json.getJSONObject("content");

                    String empresa = contenido.getString("empresa");
                    String empresanom = contenido.getString("empresanom");
                    ultima_version_org = contenido.getString("lastversion");
                    String activo = contenido.getString("activo");
                    String activo_terminal = contenido.getString("activo_t");

                    if (empresa.equals("0") || empresa.equals("")) {
                        identificaTerminal();
                        return;
                    }

                    // Al estar registrado muestro el boton de desvincular
                    // Desactivado el 22-10-2020
                    //desvincular.setVisibility(View.VISIBLE);

                    if (activo.equals("1")) {
                        /*Si no hay empresa cerramos la aplicacion*/
                        if (empresa.equals("")) {
                            Toast.makeText(this, R.string.noidentiterm, Toast.LENGTH_LONG).show();
                            this.finish();
                            return;
                        }

                        if (activo_terminal.equals("0")) {
                            Toast.makeText(this, R.string.accesblock, Toast.LENGTH_LONG).show();
                            this.finish();
                            return;
                        }

                        int version_actual = 0;
                        int ultima_version = 0;

                        String[] version_actual_string = String.valueOf(Carplus3G.version).split("\\.");
                        version_actual = Integer.parseInt(version_actual_string[1]);

                        String[] ultima_version_string = ultima_version_org.split("\\.");
                        ultima_version = Integer.parseInt(ultima_version_string[1]);

                        //Si hay mas de 2 numeros de diferencia no dejo actualizar
                        if (ultima_version - version_actual >= 3) {
                            Toast.makeText(this, R.string.actumanual, Toast.LENGTH_LONG).show();
                            this.finish();
                            return;
                        }

                        if (ultima_version > version_actual) {
                            tareaDescarga = new descargarActualizacion(Login.this);
                            tareaDescarga.execute(null, null, null);
                        } else {
                            constantes.setEmpresa(empresa);
                            constantes.setEmpresaNombre(empresanom);
                            constantes.setcargadoVersion(1);
                        }
                    } else {
                        Toast.makeText(this, R.string.canuse, Toast.LENGTH_LONG).show();
                        this.finish();
                        return;
                    }
                } else {
                    Toast.makeText(this, R.string.errorinicio, Toast.LENGTH_LONG).show();
                    this.finish();
                    return;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Carplus3G.dialogConexionLogin(this);
        }
        miDialog.dismiss();
    }

    // Creamos un manejador para el hilo del proceso enviar
    final Handler handlerBtnEnviar = new Handler();

    // Método al hacer click en el botón enviar
    public void btnEnviarClick(View btn) {
        getApplicationContext();

        if (!Carplus3G.cmpConexion(this)) {
            Carplus3G.dialogInternet(this);
        } else {
            miDialog = new ProgressDialog(this);
            miDialog.setMessage(getString(R.string.msgserv));
            miDialog.setTitle(getString(R.string.titleserv));
            miDialog.setCancelable(false);
            miDialog.show();

            // Conectamos al servidor a través de un hilo
            Thread hilo = new Thread() {
                public void run() {
                    miDialog.dismiss();
                    Carplus3G constantes = ((Carplus3G) getApplicationContext());
                    rest = new RestClient(Carplus3G.URL);
                    rest.AddParam("empresa", constantes.getEmpresa());
                    rest.AddParam("accion", "login");
                    rest.AddParam("firma", Carplus3G.SHA256("log" + emailUsuario.getText().toString()));
                    rest.AddParam("nombre", emailUsuario.getText().toString());
                    rest.AddParam("pass", passwordUsuario.getText().toString());
                    rest.AddParam("id", constantes.getTerminalId());
                    rest.AddParam("version", Carplus3G.version);
                    rest.AddParam("vers", String.valueOf(Build.VERSION.SDK_INT ));
                    rest.AddParam("modelo", Build.MANUFACTURER + " " + Build.PRODUCT);

                    try {
                        rest.Execute(RequestMethod.POST);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    respuesta = rest.getResponse();
                    handlerBtnEnviar.post(btnEnviarBack);
                    miDialog.dismiss();
                }
            };
            hilo.start();
        }
    }

    // Asginamos la función a realizar tras la apertura del hilo
    final Runnable btnEnviarBack = new Runnable() {
        public void run() {
            btnEnviarProcesar();
        }
    };

    // Procesamos tras la ejecución del hilo del botón enviar
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

                    if (contenido.getString("cod").equals("-1")) {
                        Toast.makeText(this, R.string.user_no_exist,Toast.LENGTH_SHORT).show();
                        limpiar.performClick();
                    } else {
                        Carplus3G constantes = ((Carplus3G) getApplicationContext());
                        constantes.setCodUsuario(Integer.parseInt(contenido.getString("cod")));
                        constantes.setIniciadoSesion(true);

                        guardarUltimoEmailUsuario(emailUsuario.getText().toString());
                        guardarUltimoPasswordUsuario(passwordUsuario.getText().toString());
                        constantes.setErasePass(false);

                        Intent entrada = new Intent(this,MenuPrincipal.class);
                        entrada.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(entrada);
                        Toast.makeText(this, R.string.welcome,Toast.LENGTH_SHORT).show();
                        this.finish();
                    }

                } else {
                    Toast.makeText(this, getString(R.string.error_happened) + " " + mensaje,Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Carplus3G.dialogConexion(this);
        }
        miDialog.dismiss();
    }

    public void identificaTerminal() {
        // miDialog.hide();

        Dialog dialog = new Dialog(this, R.style.FullHeightDialog);
        dialog.setContentView(R.layout.registrar_app);
        dialog.setCancelable(false);
        dialog.show();

        Button b1 = dialog.findViewById(R.id.btnCancelarRegistrar);
        EditText t0 = dialog.findViewById(R.id.txtAndroidId);

        Carplus3G constantes = ((Carplus3G) getApplicationContext());
        t0.setText(constantes.getTerminalId());

        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                btnCancelarActivacionClick();
            }
        });
        // Suprimido el método onClick para el registro automático
    }

    // Método para la cancelación del registro de terminal
    public void btnCancelarActivacionClick() {
        getApplicationContext();
        Carplus3G.deleteDir(this.getCacheDir());
        System.exit(0);
    }

    // Verificamos la resolución necesaria de pantalla para la app
    private void checkRes() {

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float ancho = metrics.widthPixels;
        float alto = metrics.heightPixels;

        if ((ancho < 800) && (alto < 480)) { // Mínimo 800x400px
            Toast.makeText(
                    this,
                    R.string.respantalla,
                    Toast.LENGTH_LONG).show();
            this.finish();
        }
    }

    // Para la descarga de la actualización de la app
    private class descargarActualizacion extends AsyncTask<String, Integer, String> {
        private Context context;

        public descargarActualizacion(Context context) {
            this.context = context;
        }

        protected void onPreExecute() {
            miDialogActualizacion = new ProgressDialog(Login.this);
            miDialogActualizacion.setTitle(getString(R.string.new_act_available));
            miDialogActualizacion.setMessage(getString(R.string.new_act_available_msg));
            miDialogActualizacion.setIndeterminate(true);
            miDialogActualizacion.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            miDialogActualizacion.setCancelable(false);
            miDialogActualizacion.show();
        }

        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            miDialogActualizacion.setIndeterminate(false);
            miDialogActualizacion.setMax(100);
            miDialogActualizacion.setProgress(progress[0]);
        }

        @Override
        protected String doInBackground(String... sUrl) {

            String estado = "ok";

            // Bloqueamos uso de CPU para evitar fallo por pulsación de botón encendido
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            // Para no extenderlo más allá de la duración del intent, ponemos un tiempo máximo de 10 minutos
            wl.acquire(10*60*1000L /*10 minutes*/);

            try {
                InputStream input = null;
                OutputStream output = null;
                HttpURLConnection connection = null;
                try {
                    URL url = new URL("http://carplus3g.carplus365.com/updates/" + ultima_version_org + ".apk");
                    //    URL url = new URL("http://192.168.1.55/_android/SW3G365//updates/" + ultima_version_org + ".apk");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();

                    // expect HTTP 200 OK, so we don't mistakenly save error report
                    // instead of the file
                    if (connection.getResponseCode() != HttpURLConnection.HTTP_OK)
                        return "Server returned HTTP " + connection.getResponseCode() + " " + connection.getResponseMessage();

                    // this will be useful to display download percentage
                    // might be -1: server did not report the length
                    int fileLength = connection.getContentLength();

                    // download the file
                    input = connection.getInputStream();
                    output = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/carplus3G365act.apk");

                    byte data[] = new byte[4096];
                    long total = 0;
                    int count;
                    while ((count = input.read(data)) != -1) {
                        // allow canceling with back button
                        if (isCancelled())
                            estado = "bad";
                        total += count;
                        // publishing the progress....
                        if (fileLength > 0) // only if total length is known
                            publishProgress((int) (total * 100 / fileLength));
                        output.write(data, 0, count);
                    }
                } catch (Exception e) {
                    estado = "bad";
                } finally {
                    try {
                        if (output != null)
                            output.close();
                        if (input != null)
                            input.close();
                    } catch (IOException ignored) {
                    }

                    if (connection != null)
                        connection.disconnect();
                }
            } finally {
                wl.release();

            }

            return estado;
        }

        protected void onPostExecute(String result) {
            miDialogActualizacion.dismiss();
            if (result.equals("ok")) {
                File file = new File(Environment.getExternalStorageDirectory().getPath(), "carplus3G365act.apk");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                startActivity(intent);
                Login.this.finish();
            } else {
                Toast.makeText(context, R.string.erroractu, Toast.LENGTH_SHORT).show();
            }

        }

    }

    // Método ejecutado al pulsar el botón desvincular y contestar si
    private void btnDesvincularClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);

        builder.setTitle(getString(R.string.titledesvinc));
        builder.setMessage(getString(R.string.msgdesvinc));

        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Cerramos el diálogo e inicializamos la desvinculación
                dialog.dismiss();
                tareaDesvincular = new asyncDesvincular();
                tareaDesvincular.execute(null, null, null);
            }
        });

        builder.setNegativeButton(getString(R.string.not), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    // Método de desvincular el terminal de la licencia contratada
    private class asyncDesvincular extends AsyncTask<Void, Integer, Boolean> {

        @Override
        protected void onPreExecute() {

            miDialog = new ProgressDialog(Login.this);
            miDialog.setMessage(getString(R.string.unlinking));
            miDialog.setCancelable(false);
            miDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            constantes = ((Carplus3G) getApplicationContext());

            try {
                rest.AddParam("accion", "desvincular");
                rest.AddParam("firma", Carplus3G.SHA256("dvi" + constantes.getTerminalId()));
                rest.AddParam("id", constantes.getTerminalId());
                rest.Execute(RequestMethod.POST);
            } catch (Exception e) {
                // Usado solo para capturar la excepción
            }
            return true;
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
        }

        @Override
        protected void onPostExecute(Boolean result) {
            miDialog.dismiss();
            getApplicationContext();
            Carplus3G.deleteDir(Login.this.getCacheDir());
            System.exit(0);
        }

        @Override
        protected void onCancelled() {
        }
    }

    // Métodos para cargar y guardar el usuario y la contraseña en la sesión de usuario
    // Nos valemos de SharedPreferences para compartir datos entre actividades
    private void cargarUltimoEmailUsuario()
    {
        SharedPreferences sharedPref = Login.this.getPreferences(Context.MODE_PRIVATE);
        String email = sharedPref.getString("lastEmail","");

        if (email.trim().equals(""))
        {
            emailUsuario.requestFocus();
        }
        else {
            emailUsuario.setText(email);
            passwordUsuario.requestFocus();
        }
    }

    private void guardarUltimoEmailUsuario(String email)
    {
        SharedPreferences sharedPref = Login.this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("lastEmail",email);
        editor.commit();

    }

    private void cargarUltimoPasswordUsuario()
    {
        SharedPreferences sharedPref = Login.this.getPreferences(Context.MODE_PRIVATE);
        String password = sharedPref.getString("lastPassword","");


        if (password.trim().equals(""))
        {
            passwordUsuario.requestFocus();
        }
        else {
            passwordUsuario.setText(password);
        }
    }

    private void guardarUltimoPasswordUsuario(String password)
    {
        SharedPreferences sharedPref = Login.this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("lastPassword", password);
        editor.commit();

    }

    private boolean checkAutologin() {

        SharedPreferences sharedPref = Login.this.getPreferences(Context.MODE_PRIVATE);
        if (sharedPref.getString("lastPassword", "") == null || sharedPref.getString("lastPassword", "").isEmpty() ||
                sharedPref.getString("lastEmail", "") == null || sharedPref.getString("lastEmail", "").isEmpty()) {
            return false;
        }
        return true;
    }

    // Para deshabilitar el botón virtual de volver del dispositivo
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0, R.id.menuSalir, 1, getString(R.string.exit)).setIcon(R.drawable.exit);
        menu.add(0, R.id.menuInfo, 2, getString(R.string.system_info)).setIcon(
                R.drawable.infobtn);

        return super.onPrepareOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menuSalir:
                getApplicationContext();

                System.exit(0);
                break;

            case R.id.menuInfo:
                Carplus3G constantes = ((Carplus3G) getApplicationContext());
                final Dialog dialog = new Dialog(this, R.style.FullHeightDialog);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.res_cierre_contrato);
                TextView t = (TextView) dialog
                        .findViewById(R.id.txtResCierreContrato);
                Button btn = (Button) dialog
                        .findViewById(R.id.btnResCierreContrato);
                t.setTextSize(18);
                t.setText("Empresa: "
                        + constantes.getEmpresaNombre()
                        + "\n\n"
                        + "Terminal ID: "
                        + String.valueOf(constantes.getTerminalId())
                        + "\n\n"
                        + "Version: " + Carplus3G.version
                        + "\n\n"
                        + "Marca: " + Build.MANUFACTURER
                        + "\n\n"
                        + "Modelo: " + Build.PRODUCT);
                t.setTextColor(Color.rgb(0, 0, 205));
                dialog.show();
                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View arg0) {
                        dialog.dismiss();
                    }
                });
                break;
        }
        return false;
    }
}