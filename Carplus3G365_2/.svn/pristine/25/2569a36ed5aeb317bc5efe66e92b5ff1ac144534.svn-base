package com.example.carplus3g365v2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;

import com.example.carplus3g365v2.Modelos.Methods;
import com.example.carplus3g365v2.RestClient.RequestMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ListadoAsignados extends AppCompatActivity {

    private Methods metodo;

    // Variables de la actividad
    public RestClient rest;
    public String respuesta = "";
    public ProgressDialog miDialog;
    public DatePickerDialog dialogFecha;

    public EditText txtF1;
    public Spinner spinSuc;

    public Button btnMostrarEntregas;
    private String[] dias = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    private String[][] meses = {{"01", "Enero"}, {"02", "Febrero"}, {"03", "Marzo"}, {"04", "Abril"}, {"05", "Mayo"}, {"06", "Junio"}, {"07", "Julio"}, {"08", "Agosto"}, {"09", "Septiembre"}, {"10", "Octubre"}, {"11", "Noviembre"}, {"12", "Diciembre"}};
    private String[] anios = {"2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021"};
    public ArrayList<String[]> sucursales = new ArrayList<String[]>();

    public ArrayList<HashMap<String, String>> resultadoEntregas = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_asignados);

        //Instanciamos los elementos
        ImageButton menu_ppal = findViewById(R.id.menuPpalLA);
        ImageButton salir = findViewById(R.id.salirLA);
        spinSuc = (Spinner) findViewById(R.id.spinSuc);
        txtF1 = (EditText) findViewById(R.id.txtF1);

        metodo = new Methods(ListadoAsignados.this);

        Calendar cal = new GregorianCalendar();
        int m = cal.get(Calendar.MONTH);
        m++;
        int y = cal.get(Calendar.YEAR);
        int d = cal.get(Calendar.DAY_OF_MONTH);

        String mes = "";
        if (m < 10) { mes = "0" + String.valueOf(m);
        } else { mes = String.valueOf(m); }

        String dia = "";
        if (d < 10) { dia = "0" + String.valueOf(d);
        } else { dia = String.valueOf(d); }

        // Escuchadores
        menu_ppal.setOnClickListener(v -> metodo.iraMenuPpal());
        salir.setOnClickListener(v -> metodo.cerrarSesion());
        txtF1.setOnClickListener(v -> txtF1Click(v));

        txtF1.setText(dia + "/" + mes + "/" + y);
    }

    public void onStart() {
        super.onStart();

        Carplus3G constantes = ((Carplus3G) getApplicationContext());
        if (!constantes.cmpLastDate()) {
            constantes.setLastDate();
            constantes.setcargadoVersion(0);
            Intent i = new Intent();
            i.setClass(ListadoAsignados.this, Login.class);
            startActivity(i);
            ListadoAsignados.this.finish();
            return;
        }

        // Comprobamos permisos para mostrar opciones de menú
        // Hilo de llamada al servidor
        if (!Carplus3G.cmpConexion(this)) {
            Carplus3G.dialogInternet(this);
        } else {
            miDialog = new ProgressDialog(this);
            miDialog.setMessage(getString(R.string.msgserv));
            miDialog.setTitle(getString(R.string.getting_list_suc));
            miDialog.setCancelable(false);
            miDialog.show();

            Thread hilo = new Thread() {
                public void run() {
                    Carplus3G constantes = ((Carplus3G) getApplicationContext());
                    rest = new RestClient(Carplus3G.URL);
                    rest.AddParam("empresa", constantes.getEmpresa());
                    rest.AddParam("id", constantes.getTerminalId());
                    rest.AddParam("accion", "listado_oficinas");
                    rest.AddParam("firma", Carplus3G.SHA256("lofc"+constantes.getEmpresa()));

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

    final Handler handlerHiloInicio = new Handler();

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

                if (estado.equals("1")) {

                    JSONObject contenido = json.getJSONObject("content");
                    JSONArray array = new JSONArray(contenido.getString("sucursales"));

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject row = array.getJSONObject(i);
                        String[] fila = new String[2];
                        fila[0] = row.getString("cod");
                        fila[1] = row.getString("tit");
                        sucursales.add(fila);
                    }

                    ArrayAdapter<CharSequence> sucAdapter = new ArrayAdapter<CharSequence>(
                            this, android.R.layout.simple_spinner_item);

                    for (Iterator<String[]> it = sucursales.iterator(); it.hasNext(); ) {
                        String[] fila = it.next();
                        sucAdapter.add(String.valueOf(fila[1]));
                    }

                    sucAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinSuc.setAdapter(sucAdapter);
                } else {
                    Toast.makeText(this, getString(R.string.error_happened)+" " + mensaje, Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Carplus3G.dialogConexion(this);
        }
        miDialog.hide();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, R.id.menuMenu, 1, getString(R.string.mnu_ppal)).setIcon(
                R.drawable.menu);
        menu.add(2, R.id.menuLogout, 2, getString(R.string.change_user)).setIcon(
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

    public void btnMostrarAsignadosClick(View view) {
        getApplicationContext();

        if (!Carplus3G.cmpConexion(this)) {
            Carplus3G.dialogInternet(this);
        } else {

            miDialog = new ProgressDialog(this);
            miDialog.setMessage(getString(R.string.getting_list_ent));
            miDialog.setTitle(getString(R.string.receiving_data));
            miDialog.setCancelable(false);
            miDialog.show();

            // Comprobamos permisos para mostrar opciones de menú
            // Hilo de llamada al servidor
            String f1 = txtF1.getText().toString().trim();

            final String dia = f1.substring(0, 2);
            final String mes = f1.substring(3, 5);
            final String anio = f1.substring(6, 10);

            final String suc = spinSuc.getSelectedItem().toString();
            String cod = "";

            for (Iterator<String[]> it = sucursales.iterator(); it.hasNext(); ) {
                String[] fila = it.next();
                if (suc.equals(fila[1])) {
                    cod = fila[0];
                }
            }

            final String codSuc = cod;

            Thread hilo = new Thread() {
                public void run() {
                    Carplus3G constantes = ((Carplus3G) getApplicationContext());
                    rest = new RestClient(Carplus3G.URL);
                    rest.AddParam("empresa", constantes.getEmpresa());
                    rest.AddParam("id", constantes.getTerminalId());
                    rest.AddParam("accion", "listado_asignados");
                    rest.AddParam("firma", Carplus3G.SHA256("las"+anio));
                    rest.AddParam("dia", dia);
                    rest.AddParam("mes", mes);
                    rest.AddParam("anio", anio);
                    rest.AddParam("suc", codSuc);

                    try {
                        rest.Execute(RequestMethod.POST);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    respuesta = rest.getResponse();
                    handlerEntregas.post(entregasBack);
                    miDialog.dismiss();
                }

            };
            hilo.start();
        }
    }

    final Handler handlerEntregas = new Handler();

    final Runnable entregasBack = new Runnable() {
        public void run() {
            añadirFilas();
        }
    };

    private void añadirFilas() {

        String estado = "";
        String mensaje = "";

        if (!respuesta.equals("error")) {

            try {
                JSONObject json = new JSONObject(respuesta);

                estado = json.getString("status");
                mensaje = json.getString("statusMsg");

                ListView list = (ListView) findViewById(R.id.contAsignados);

                if (estado.equals("1")) {

                    resultadoEntregas.clear();
                    String content = json.getString("content");

                    if (!content.equals("")) {

                        JSONArray datos = json.getJSONArray("content");

                        for (int i = 0; i < datos.length(); i++) {

                            JSONObject row = datos.getJSONObject(i);
                            //String nombre = row.getString("nombre");
                            String grupo = row.getString("grupo");
                            String matricula = row.getString("matricula");
                            //String kms = row.getString("kms");
                            String color = row.getString("color");
                            String modelo = row.getString("modelo");
                            String hora = row.getString("hora");
                            String contrato = row.getString("contrato");
                            String entregas_estado = row.getString("estado");

                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("matricula", matricula);
                            map.put("grupo", grupo);
                            map.put("color", color);
                            map.put("modelo", modelo);
                            map.put("hora", hora);
                            map.put("contrato", contrato);
                            map.put("estado_entrega", entregas_estado);
                            resultadoEntregas.add(map);
                        }
                    } else {
                        Toast.makeText(this, R.string.veh_not_found, Toast.LENGTH_LONG).show();
                    }

                    TextView t = (TextView) findViewById(R.id.txtAsignadosTotal);
                    t.setText(String.valueOf(resultadoEntregas.size()));

                    if (resultadoEntregas.size() > 0) {
                        SimpleAdapter listAdapter = new SimpleAdapter(this,
                                resultadoEntregas, R.layout.fila_list_view_asignados,
                                new String[]{"matricula", "grupo", "color", "modelo", "hora", "contrato", "estado_entrega"},
                                new int[]{R.id.txtMatriculaFila, R.id.txtGpFila, R.id.txtColorFila, R.id.txtModeloFila, R.id.txtHoraFila, R.id.txtContratoFila, R.id.txtEstadoEntregaFila});
                        list.setAdapter(listAdapter);
                    } else {
                        list.setAdapter(null);
                    }
                } else {
                    Toast.makeText(this, getString(R.string.error_happened) + " " + mensaje, Toast.LENGTH_LONG).show();
                    list.setAdapter(null);
                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            Carplus3G.dialogConexion(this);
        }
        miDialog.hide();
    }

    private void txtF1Click(View v) {

        int mYear = 0;
        int mMonth = 0;
        int mDay = 0;

        String f1 = txtF1.getText().toString().trim();

        if (f1.equals("")) {
            Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        } else {
            mYear = Integer.parseInt(f1.substring(6, 10));
            mMonth = Integer.parseInt(f1.substring(3, 5)) - 1;
            mDay = Integer.parseInt(f1.substring(0, 2));
        }

        dialogFecha = new DatePickerDialog(ListadoAsignados.this, datePickerListener, mYear, mMonth, mDay);
        dialogFecha.setTitle(getString(R.string.select_delivery_date));
        dialogFecha.show();
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // Se llama a este método al cerrar el diálogo
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {

            int dia = selectedDay;
            int mes = selectedMonth + 1;
            int anio = selectedYear;

            String sdia = "";
            String smes = "";
            String sanio = "";

            if (dia <= 9) { sdia = "0" + dia;
            } else { sdia = "" + dia; }

            if (mes <= 9) { smes = "0" + mes;
            } else { smes = "" + mes; }

            sanio = "" + anio;

            String fecha = sdia + "/" + smes + "/" + sanio;

            if (!txtF1.getText().toString().equals(fecha)) {
                ListView list = (ListView) findViewById(R.id.contAsignados);
                list.setAdapter(null);
                TextView t = (TextView) findViewById(R.id.txtAsignadosTotal);
                t.setText("0");
            }
            txtF1.setText(fecha);
        }
    };
}