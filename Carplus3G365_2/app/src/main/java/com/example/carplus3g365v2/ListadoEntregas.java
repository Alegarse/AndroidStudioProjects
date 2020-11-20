package com.example.carplus3g365v2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carplus3g365v2.Modelos.Methods;
import com.example.carplus3g365v2.RestClient.RequestMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;

public class ListadoEntregas extends AppCompatActivity {

    public RestClient rest;
    public String respuesta = "";
    public ProgressDialog miDialog;
    public DatePickerDialog dialogFecha;

    private CardView cabecera1, cabecera2, listadoEntregas;

    public EditText txtF1;
    public Spinner spinSuc;
    public CheckBox incContratos;
    public int d;
    public int m;
    public int y;

    public String diaSel = "";
    public String mesSel = "";
    public String anioSel = "";

    private TextView sucRed, fecRed, totRed;

    public Button limpiarListado;
    public ArrayList<String[]> sucursales = new ArrayList<String[]>();

    public ArrayList<HashMap<String, String>> resultadoRecogidas = new ArrayList<HashMap<String, String>>();
    public View itemSeleccionado;
    public String matriculaSeleccionada = "";
    public String notasEnviadas = "";

    public int ultimoSeleccionado = 0;
    public Dialog dialogAsignar;
    public Dialog dialogSeleccion;
    public Dialog dialogNotas;

    private ListView list;

    private Methods metodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_entregas);

        // Instanciamos los botones superiores
        ImageButton menu_ppal = findViewById(R.id.menuPpalLE);
        ImageButton salir = findViewById(R.id.salirLE);

        limpiarListado = findViewById(R.id.btnLimpiarListado);
        sucRed = findViewById(R.id.txtSuc);
        fecRed = findViewById(R.id.txtFec);
        totRed = findViewById(R.id.txtTot);
        cabecera1 = findViewById(R.id.cardListadoRecogidas1);
        cabecera2 = findViewById(R.id.cardListadoEntregas2);
        cabecera2.setVisibility(View.GONE);
        listadoEntregas = findViewById(R.id.cardListadoEntregas);
        listadoEntregas.setVisibility(View.GONE);

        metodo = new Methods(ListadoEntregas.this);

        // Escuchadores para ambos botones
        menu_ppal.setOnClickListener(v -> metodo.iraMenuPpal());
        salir.setOnClickListener(v -> metodo.cerrarSesion());
        limpiarListado.setOnClickListener(v -> limpiarListado());

        spinSuc = findViewById(R.id.spinSucEnt);
        incContratos = findViewById(R.id.chk);

        txtF1 = findViewById(R.id.txtF1);

        txtF1.setOnClickListener(v -> txtF1Click(v));

        Calendar cal = new GregorianCalendar();
        m = cal.get(Calendar.MONTH);
        m++;
        y = cal.get(Calendar.YEAR);
        d = cal.get(Calendar.DAY_OF_MONTH);

        String mes = "";
        if (m < 10) { mes = "0" + String.valueOf(m);
        } else { mes = String.valueOf(m); }

        String dia = "";
        if (d < 10) { dia = "0" + String.valueOf(d);
        } else { dia = String.valueOf(d); }

        txtF1.setText(dia+"/"+mes+"/"+y);
        fecRed.setText(dia+"/"+mes+"/"+y);
    }

    private void limpiarListado() {
        cabecera1.setVisibility(View.VISIBLE);
        cabecera2.setVisibility(View.GONE);
        TextView t = findViewById(R.id.txtEntregasTotal);
        t.setText("0");
        list.setAdapter(null);
        listadoEntregas.setVisibility(View.GONE);
    }

    public void onStart() {
        super.onStart();

        Carplus3G constantes = ((Carplus3G) getApplicationContext());
        if (!constantes.cmpLastDate()) {
            constantes.setLastDate();
            constantes.setcargadoVersion(0);
            Intent i = new Intent();
            i.setClass(ListadoEntregas.this, Login.class);
            startActivity(i);
            ListadoEntregas.this.finish();
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
                    JSONArray array = new JSONArray(
                            contenido.getString("sucursales"));

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject row = array.getJSONObject(i);
                        String[] fila = new String[2];
                        fila[0] = row.getString("cod");
                        fila[1] = row.getString("tit");
                        sucursales.add(fila);
                    }

                    ArrayAdapter<CharSequence> sucAdapter = new ArrayAdapter<CharSequence>(
                            this, android.R.layout.simple_spinner_item);

                    for (Iterator<String[]> it = sucursales.iterator(); it
                            .hasNext(); ) {
                        String[] fila = it.next();
                        sucAdapter.add(String.valueOf(fila[1]));
                    }
                    sucAdapter
                            .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinSuc.setAdapter(sucAdapter);

                } else {
                    Toast.makeText(this, getString(R.string.error_happened)+" " + mensaje,
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, R.id.menuMenu, 1, getString(R.string.mnu_ppal))
                .setIcon(R.drawable.menu);
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

    public void btnMostrarEntregasClick(View btn) {
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
            diaSel = dia;
            final String mes = f1.substring(3, 5);
            mesSel = mes;
            final String anio = f1.substring(6, 10);
            anioSel = anio;

            if (spinSuc.getSelectedItem().toString().contains("TODAS")){
                sucRed.setText("TODAS");
            }
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
                    rest.AddParam("accion", "listado_entregas");
                    rest.AddParam("firma", Carplus3G.SHA256("len"+anio));
                    rest.AddParam("dia", dia);
                    rest.AddParam("mes", mes);
                    rest.AddParam("anio", anio);
                    rest.AddParam("suc", codSuc);

                    int inc = 0;

                    if (incContratos.isChecked())
                    { inc = 1; }

                    rest.AddParam("inc", String.valueOf(inc));

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
            anadirFilas();
        }
    };

    private void anadirFilas() {

        String estado = "";
        String mensaje = "";

        if (!respuesta.equals("error")) {

            try {
                JSONObject json = new JSONObject(respuesta);

                estado = json.getString("status");
                mensaje = json.getString("statusMsg");

                list = findViewById(R.id.contEntregas);

                list.setOnItemLongClickListener((AdapterView.OnItemLongClickListener) (arg0, arg1, arg2, arg3) -> {

                    final HashMap<String, String> datos = resultadoRecogidas.get(arg2);
                    ultimoSeleccionado = arg2;

                    //Si es un contrato no cambiamos las notas
                    if (datos.get("tip").equals("C"))
                    {
                        return false;
                    }

                    dialogSeleccion = new Dialog(ListadoEntregas.this, R.style.FullHeightDialog);
                    dialogSeleccion.setContentView(R.layout.mtr_notas);
                    dialogSeleccion.setCancelable(false);
                    dialogSeleccion.show();

                    Button bt1 = dialogSeleccion.findViewById(R.id.btnCancelarSeleccion);
                    Button bt2 = dialogSeleccion.findViewById(R.id.btnNotasSeleccion);
                    Button bt3 = dialogSeleccion.findViewById(R.id.btnMatriculaSeleccion);

                    bt3.setVisibility(View.GONE);

                    bt1.setOnClickListener(arg013 -> {
                        getApplicationContext();
                        dialogSeleccion.dismiss();
                    });

                    bt2.setOnClickListener((View.OnClickListener) arg014 -> {
                        getApplicationContext();
                        dialogSeleccion.dismiss();

                        dialogNotas = new Dialog(ListadoEntregas.this, R.style.FullHeightDialog);
                        dialogNotas.setContentView(R.layout.cambiar_notas);
                        dialogNotas.setCancelable(false);
                        dialogNotas.show();

                        Button b1 = (Button) dialogNotas.findViewById(R.id.btnCancelarNotas);
                        Button b2 = (Button) dialogNotas.findViewById(R.id.btnCambiarNotas);
                        final EditText t = (EditText) dialogNotas.findViewById(R.id.txtNotas);

                        t.setText(datos.get("obs"));

                        b1.setOnClickListener(arg01 -> {
                            getApplicationContext();
                            dialogNotas.dismiss();
                        });

                        b2.setOnClickListener(arg012 -> btnGuardarNotasClick(datos.get("res"), t.getText().toString()));
                    });
                    return false;
                });

                if (estado.equals("1")) {

                    resultadoRecogidas.clear();
                    String content = json.getString("content");

                    if (!content.equals("")) {
                        JSONArray datos = json.getJSONArray("content");

                        for (int i = 0; i < datos.length(); i++) {

                            JSONObject row = datos.getJSONObject(i);
                            String res = row.getString("res");
                            String tip = row.getString("tip");
                            String oev = row.getString("oev");
                            String hora = row.getString("hor");
                            String gp = row.getString("gp");
                            String gp2 = row.getString("gp2");
                            String dia = row.getString("dia");
                            String mat = row.getString("mat");
                            String cli = row.getString("cli");
                            String lug = row.getString("lug");
                            String obs = row.getString("obs");

                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("res", res);
                            map.put("tip", tip);
                            map.put("oev", oev);
                            map.put("hor", hora);
                            map.put("gp", gp);
                            map.put("gp2", gp2);
                            map.put("dia", dia);
                            map.put("mat", mat);
                            map.put("cli", cli);
                            map.put("lug", lug);
                            map.put("obs", obs);
                            resultadoRecogidas.add(map);
                        }
                    } else {
                        Toast.makeText(this, R.string.veh_not_found,Toast.LENGTH_LONG).show();
                    }

                    TextView t = findViewById(R.id.txtEntregasTotal);
                    t.setText(String.valueOf(resultadoRecogidas.size()));
                    totRed.setText(String.valueOf(resultadoRecogidas.size()));

                    if (resultadoRecogidas.size() > 0) {
                        SimpleAdapter listAdapter = new SimpleAdapter(this,
                                resultadoRecogidas,
                                R.layout.fila_list_view_entregas,
                                new String[]{"oev", "hor", "gp", "gp2",
                                        "dia", "mat", "cli", "lug", "obs"}, new int[]{
                                R.id.txtSucFilaEntregas,
                                R.id.txtHoraFilaEntregas,
                                R.id.txtGpFilaEntregas,
                                R.id.txtGp2FilaEntregas,
                                R.id.txtDiasFilaEntregas,
                                R.id.txtMatriculaFilaEntregas,
                                R.id.txtClienteFilaEntregas,
                                R.id.txtLugarFilaEntregas,
                                R.id.txtObsFilaEntregas});

                        list.setAdapter(listAdapter);

                        //Cambiamos la visualización para mejor manejo
                        listadoEntregas.setVisibility(View.VISIBLE);
                        cabecera1.setVisibility(View.GONE);
                        cabecera2.setVisibility(View.VISIBLE);

                    } else {
                        list.setAdapter(null);
                    }
                } else {
                    Toast.makeText(this, getString(R.string.error_happened)+" " + mensaje,
                            Toast.LENGTH_LONG).show();
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


    public void btnGuardarNotasClick(final String reserva, final String notas) {
        getApplicationContext();

        final HashMap<String, String> fila = resultadoRecogidas.get(ultimoSeleccionado);

        if (notas.equals("")) {
            Toast.makeText(this, R.string.toast_save_note,Toast.LENGTH_LONG).show();
            return;
        }
        if (!Carplus3G.cmpConexion(this)) {
            Carplus3G.dialogInternet(this);
        } else {
            notasEnviadas = notas;

            miDialog = new ProgressDialog(this);
            miDialog.setMessage(getString(R.string.changing_notes));
            miDialog.setTitle(getString(R.string.sending_data_serv));
            miDialog.setCancelable(false);
            miDialog.show();

            Thread hilo = new Thread() {
                public void run() {
                    Carplus3G constantes = ((Carplus3G) getApplicationContext());
                    rest = new RestClient(Carplus3G.URL);
                    rest.AddParam("empresa", constantes.getEmpresa());
                    rest.AddParam("id", constantes.getTerminalId());
                    rest.AddParam("accion", "cambiar_notas");
                    rest.AddParam("firma", Carplus3G.SHA256("cno"+notasEnviadas));
                    rest.AddParam("notas", notasEnviadas);
                    rest.AddParam("reserva", reserva);

                    try {
                        rest.Execute(RequestMethod.POST);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    respuesta = rest.getResponse();
                    handlerHiloNotas.post(NotasBack);
                    miDialog.dismiss();
                }

            };
            hilo.start();
        }
    }

    final Handler handlerHiloNotas = new Handler();

    final Runnable NotasBack = new Runnable() {
        public void run() {
            ProcesarHiloNotas();
        }
    };

    private void ProcesarHiloNotas() {

        String estado = "";
        String mensaje = "";

        if (!respuesta.equals("error")) {
            try {

                JSONObject json = new JSONObject(respuesta);

                estado = json.getString("status");
                mensaje = json.getString("statusMsg");

                if (estado.equals("1")) {

                    HashMap<String, String> fila = resultadoRecogidas.get(ultimoSeleccionado);
                    fila.put("obs", notasEnviadas);

                    resultadoRecogidas.set(ultimoSeleccionado, fila);

                    ListView list = (ListView) findViewById(R.id.contEntregas);
                    list.setAdapter(null);
                    if (resultadoRecogidas.size() > 0) {
                        SimpleAdapter listAdapter = new SimpleAdapter(this,
                                resultadoRecogidas,
                                R.layout.fila_list_view_entregas,
                                new String[]{"oev", "hor", "gp", "gp2",
                                        "dia", "mat", "cli", "lug", "obs"}, new int[]{
                                R.id.txtSucFilaEntregas,
                                R.id.txtHoraFilaEntregas,
                                R.id.txtGpFilaEntregas,
                                R.id.txtGp2FilaEntregas,
                                R.id.txtDiasFilaEntregas,
                                R.id.txtMatriculaFilaEntregas,
                                R.id.txtClienteFilaEntregas,
                                R.id.txtLugarFilaEntregas,
                                R.id.txtObsFilaEntregas});

                        list.setAdapter(listAdapter);

                        dialogNotas.dismiss();

                        Toast.makeText(this, R.string.change_note_ok,
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, R.string.cant_change_notes,Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, getString(R.string.error_happened) + " " + mensaje,
                            Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Toast.makeText(this, e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        } else {
            Carplus3G.dialogConexion(this);
        }
        notasEnviadas = "";
        miDialog.hide();
    };


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

        dialogFecha = new DatePickerDialog(ListadoEntregas.this, datePickerListener, mYear, mMonth, mDay);
        dialogFecha.setTitle(getString(R.string.select_date_e));
        dialogFecha.show();
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
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

            if (!txtF1.getText().toString().equals(fecha))
            {
                ListView list = (ListView) findViewById(R.id.contEntregas);
                list.setAdapter(null);
                TextView t = (TextView) findViewById(R.id.txtEntregasTotal);
                t.setText("0");
            }
            txtF1.setText(fecha);
            fecRed.setText(fecha);
        }
    };
}