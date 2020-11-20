package com.example.carplus3g365v2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carplus3g365v2.Adapters.SpecialAdapterMeeting;
import com.example.carplus3g365v2.Modelos.Methods;
import com.example.carplus3g365v2.RestClient.RequestMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ListadoMeeting extends AppCompatActivity {

    private Methods metodo;

    // Variables de la actividad
    public RestClient rest;
    public String respuesta = "";
    public ProgressDialog miDialog;
    public Dialog MeetingDialog;

    public Spinner spinSuc;
    public CheckBox incluir;

    public Button btnMostrarRecepciones;
    public Button btnBuscarReserva;
    public ArrayList<String[]> sucursales = new ArrayList<String[]>();

    public ArrayList<HashMap<String, String>> resultadoRecepciones = new ArrayList<HashMap<String, String>>();
    public View itemSeleccionado;
    public String matriculaSeleccionada = "";
    private asignarRecepcion tareaAsignar = null;
    private buscarReserva tareaBuscar = null;

    private String f_reserva = "";
    private String f_hh = "";
    private String f_mm = "";

    private String reserva_a_buscar = "";

    public int indice_seleccionado = 0;

    public InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_meeting);

        // Instanciamos
        ImageButton menu_ppal = findViewById(R.id.menuPpalLM);
        ImageButton salir = findViewById(R.id.salirLM);
        spinSuc = findViewById(R.id.spinSucRec);
        incluir = findViewById(R.id.chk1);

        metodo = new Methods(ListadoMeeting.this);

        btnBuscarReserva = findViewById(R.id.btnBuscarRes);

        // Escuchadores
        menu_ppal.setOnClickListener(v -> metodo.iraMenuPpal());
        salir.setOnClickListener(v -> metodo.cerrarSesion());
        btnBuscarReserva.setOnClickListener((View.OnClickListener) v -> {
            final Dialog d = new Dialog(ListadoMeeting.this,R.style.FullHeightDialog);
            d.setCancelable(true);
            d.setContentView(R.layout.meeting_find);

            final EditText t = (EditText) d.findViewById(R.id.txtFindMeeting);
            final Button b1 = (Button) d.findViewById(R.id.btnMeetingOk);
            Button b2 = (Button) d.findViewById(R.id.btnMeetingCancel);

            imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

            // Al pulsar en el EditText
            t.setOnKeyListener(new View.OnKeyListener() {
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN)
                            && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        // Oculto el teclado de la pantalla
                        imm.hideSoftInputFromWindow(t.getWindowToken(), 0);
                        // Realizo click en el botón directamente
                        b1.performClick();
                        return true;
                    }
                    return false;
                }
            });

            b1.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    EditText t = (EditText) d.findViewById(R.id.txtFindMeeting);
                    String res = t.getText().toString();
                    if (res.length() == 0)
                    {
                        Toast.makeText(ListadoMeeting.this, R.string.enter_res_number, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    reserva_a_buscar = res;
                    tareaBuscar = new buscarReserva();
                    tareaBuscar.execute(null,null,null);
                    d.dismiss();
                }
            });

            b2.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    d.dismiss();
                }
            });
            d.show();
        });
        spinSuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                ListView list = (ListView) findViewById(R.id.contMeeting);
                TextView t = (TextView) findViewById(R.id.txtRecogidasTotal);
                t.setText("0");
                list.setAdapter(null);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
        incluir.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                ListView list = (ListView) findViewById(R.id.contMeeting);
                TextView t = (TextView) findViewById(R.id.txtRecogidasTotal);
                t.setText("0");
                list.setAdapter(null);
            }
        });
    }

    public void onStart() {
        super.onStart();

        Carplus3G constantes = ((Carplus3G) getApplicationContext());
        if (!constantes.cmpLastDate())
        {
            constantes.setLastDate();
            constantes.setcargadoVersion(0);
            Intent i = new Intent();
            i.setClass(ListadoMeeting.this, Login.class);
            startActivity(i);
            ListadoMeeting.this.finish();
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

                if (estado.equals("1"))
                {

                    JSONObject contenido = json.getJSONObject("content");
                    JSONArray array = new JSONArray(contenido.getString("sucursales"));

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject row = array.getJSONObject(i);
                        String[] fila = new String[2];
                        fila[0] = row.getString("cod");
                        fila[1] = row.getString("tit");
                        sucursales.add(fila);
                    }

                    ArrayAdapter<CharSequence> sucAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);

                    for (Iterator<String[]> it = sucursales.iterator(); it.hasNext();) {
                        String[] fila = it.next();
                        sucAdapter.add(String.valueOf(fila[1]));

                    }
                    sucAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinSuc.setAdapter(sucAdapter);
                } else {
                    Toast.makeText(this, getString(R.string.error_happened)+" " + mensaje,Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
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
        /* DESACTIVAMOS EL BOTON VOLVER DEL DISPOSITIVO */
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return false;
    }


    public void btnMostrarRecepcionesClick(View btn) {
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
            final String suc = spinSuc.getSelectedItem().toString();
            String cod = "";

            for (Iterator<String[]> it = sucursales.iterator(); it.hasNext();) {
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
                    rest.AddParam("accion", "listado_recepciones");
                    rest.AddParam("firma", Carplus3G.SHA256("lrep"+codSuc));
                    rest.AddParam("suc", codSuc);
                    rest.AddParam("res", "");

                    int inc = 0;

                    if (incluir.isChecked()) {
                        inc = 1;
                    }

                    rest.AddParam("inc", Integer.toString(inc));

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

                ListView list = findViewById(R.id.contMeeting);
                list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    public boolean onItemLongClick(AdapterView<?> arg0,View arg1, int arg2, long arg3)
                    {
                        HashMap<String, String> map = new HashMap<String, String>();
                        map = resultadoRecepciones.get(arg2);
                        indice_seleccionado = arg2;

                        if (Integer.parseInt(map.get("orden")) > 0)
                        {
                            MeetingDialog = new Dialog(ListadoMeeting.this,R.style.FullHeightDialog);
                            MeetingDialog.setCancelable(true);
                            MeetingDialog.setContentView(R.layout.res_meeting);

                            TextView t = MeetingDialog.findViewById(R.id.txtOrden);
                            Button b = MeetingDialog.findViewById(R.id.btnResMeeting);

                            t.setText(map.get("orden"));
                            b.setOnClickListener(new View.OnClickListener(){

                                public void onClick(View v) {
                                    MeetingDialog.dismiss();
                                }
                            });
                            MeetingDialog.show();
                        } else {
                            final Dialog dialog = new Dialog(ListadoMeeting.this,R.style.FullHeightDialog);
                            dialog.setCancelable(true);
                            dialog.setContentView(R.layout.meeting_add);

                            TextView nres = dialog.findViewById(R.id.txtMeetingNumRes);

                            nres.setText("Reserva: "+map.get("res"));
                            f_reserva = map.get("res");

                            Button btnSi = dialog.findViewById(R.id.btnMeetingOk);
                            Button btnNo = dialog.findViewById(R.id.btnMeetingCancel);

                            btnSi.setOnClickListener(new View.OnClickListener(){
                                public void onClick(View arg0) {
                                    EditText th = dialog.findViewById(R.id.txtMeetingHoras);
                                    EditText tm = dialog.findViewById(R.id.txtMeetingMinutos);

                                    String h = th.getText().toString();
                                    String m = tm.getText().toString();


                                    if (h.length() == 0) {
                                        Toast.makeText(ListadoMeeting.this, R.string.novalid_hour, Toast.LENGTH_SHORT).show();
                                        return;
                                    }


                                    if (m.length() == 0) {
                                        Toast.makeText(ListadoMeeting.this, R.string.novalid_mins, Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    int hh = Integer.parseInt(h);
                                    int mm = Integer.parseInt(m);

                                    if (hh < 0 || hh > 23) {
                                        Toast.makeText(ListadoMeeting.this, R.string.novalid_hour, Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    if (mm < 0 || mm > 59) {
                                        Toast.makeText(ListadoMeeting.this, R.string.novalid_mins, Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    f_hh = h;
                                    f_mm = m;

                                    dialog.dismiss();

                                    tareaAsignar = new asignarRecepcion();
                                    tareaAsignar.execute(null,null,null);
                                }
                            });

                            btnNo.setOnClickListener(new View.OnClickListener(){
                                public void onClick(View arg0) {
                                    dialog.cancel();
                                }
                            });
                            dialog.show();
                        }
                        return false;
                    }
                });
                if (estado.equals("1")) {

                    resultadoRecepciones.clear();
                    String content = json.getString("content");

                    if (!content.equals("")) {

                        JSONArray datos = json.getJSONArray("content");

                        for (int i = 0; i < datos.length(); i++) {

                            JSONObject row = datos.getJSONObject(i);
                            String res = row.getString("res");
                            String hora = row.getString("hora");
                            String gt = row.getString("gt");
                            String vuelo = row.getString("vuelo");
                            String agencia = row.getString("agencia");
                            String cliente = row.getString("cliente");
                            String phone = row.getString("phone");
                            String notas = row.getString("notas");
                            String orden = row.getString("orden");

                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("res", res);
                            map.put("hora", hora);
                            map.put("gt", gt);
                            map.put("vuelo", vuelo);
                            map.put("agencia", agencia);
                            map.put("cliente", cliente);
                            map.put("phone", phone);
                            map.put("notas", notas);
                            map.put("orden", orden);
                            resultadoRecepciones.add(map);
                        }
                    } else {
                        Toast.makeText(this, R.string.data_not_found, Toast.LENGTH_LONG).show();
                    }
                    TextView t = (TextView) findViewById(R.id.txtRecogidasTotal);
                    t.setText(String.valueOf(resultadoRecepciones.size()));

                    if (resultadoRecepciones.size() > 0) {

                        SpecialAdapterMeeting listAdapter = new SpecialAdapterMeeting(this,	resultadoRecepciones,	R.layout.fila_list_view_meeting,
                                new String[] { "res", "hora", "gt", "vuelo",
                                        "agencia", "cliente" }, new int[] {
                                R.id.txtReservaMeeting,
                                R.id.txtHoraMeeting,
                                R.id.txtGpMeeting,
                                R.id.txtVueloMeeting,
                                R.id.txtAgenciaMeeting,
                                R.id.txtClienteMeeting});

                        list.setAdapter(listAdapter);
                    } else {
                        list.setAdapter(null);
                    }
                } else {
                    Toast.makeText(this, getString(R.string.error_happened)+" " + mensaje, Toast.LENGTH_LONG).show();
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
    };

    private class buscarReserva extends AsyncTask<String, String, String> {

        String suc = "";
        Integer inc = 0;

        protected void onPreExecute()
        {
            miDialog = new ProgressDialog(ListadoMeeting.this);
            miDialog.setTitle(getString(R.string.procesing_search));
            miDialog.setMessage(getString(R.string.sending_data_serv));
            miDialog.setCancelable(false);
            miDialog.show();

            suc = spinSuc.getSelectedItem().toString();

            if (incluir.isChecked()) {
                inc = 1;
            }
        }

        @Override
        protected String doInBackground(String... params) {


            String cod = "";

            for (Iterator<String[]> it = sucursales.iterator(); it.hasNext();) {
                String[] fila = it.next();
                if (suc.equals(fila[1])) {
                    cod = fila[0];
                }
            }
            final String codSuc = cod;

            Carplus3G constantes = ((Carplus3G) getApplicationContext());
            rest = new RestClient(Carplus3G.URL);
            rest.AddParam("empresa", constantes.getEmpresa());
            rest.AddParam("id", constantes.getTerminalId());
            rest.AddParam("accion", "listado_recepciones");
            rest.AddParam("firma", Carplus3G.SHA256("lrep"+codSuc));
            rest.AddParam("suc", codSuc);
            rest.AddParam("res", reserva_a_buscar);
            rest.AddParam("inc", inc.toString());

            try {
                rest.Execute(RequestMethod.POST);
            } catch (Exception e) {
                e.printStackTrace();
            }

            respuesta = rest.getResponse();
            return null;
        }

        protected void onPostExecute(String resultado) {
            String estado = "";
            String mensaje = "";


            if (!respuesta.equals("error")) {

                try {
                    JSONObject json = new JSONObject(respuesta);

                    estado = json.getString("status");
                    mensaje = json.getString("statusMsg");

                    ListView list = findViewById(R.id.contMeeting);
                    list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        public boolean onItemLongClick(AdapterView<?> arg0,View arg1, int arg2, long arg3)
                        {
                            HashMap<String, String> map = new HashMap<String, String>();
                            map = resultadoRecepciones.get(arg2);
                            indice_seleccionado = arg2;

                            if (Integer.parseInt(map.get("orden")) > 0)
                            {
                                MeetingDialog = new Dialog(ListadoMeeting.this,R.style.FullHeightDialog);
                                MeetingDialog.setCancelable(true);
                                MeetingDialog.setContentView(R.layout.res_meeting);

                                TextView t = MeetingDialog.findViewById(R.id.txtOrden);
                                Button b = MeetingDialog.findViewById(R.id.btnResMeeting);

                                t.setText(map.get("orden"));
                                b.setOnClickListener(new View.OnClickListener(){

                                    public void onClick(View v) {
                                        MeetingDialog.dismiss();
                                    }
                                });
                                MeetingDialog.show();
                            } else {
                                final Dialog dialog = new Dialog(ListadoMeeting.this,R.style.FullHeightDialog);
                                dialog.setCancelable(true);
                                dialog.setContentView(R.layout.meeting_add);

                                TextView nres = dialog.findViewById(R.id.txtMeetingNumRes);

                                nres.setText("Reserva: "+map.get("res"));
                                f_reserva = map.get("res");

                                Button btnSi = dialog.findViewById(R.id.btnMeetingOk);
                                Button btnNo = dialog.findViewById(R.id.btnMeetingCancel);

                                btnSi.setOnClickListener(new View.OnClickListener(){
                                    public void onClick(View arg0) {
                                        EditText th = dialog.findViewById(R.id.txtMeetingHoras);
                                        EditText tm = dialog.findViewById(R.id.txtMeetingMinutos);

                                        String h = th.getText().toString();
                                        String m = tm.getText().toString();

                                        if (h.length() == 0) {
                                            Toast.makeText(ListadoMeeting.this, R.string.novalid_hour, Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                        if (m.length() == 0) {
                                            Toast.makeText(ListadoMeeting.this, R.string.novalid_mins, Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                        int hh = Integer.parseInt(h);
                                        int mm = Integer.parseInt(m);

                                        if (hh < 0 || hh > 23) {
                                            Toast.makeText(ListadoMeeting.this, R.string.novalid_hour, Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                        if (mm < 0 || mm > 59) {
                                            Toast.makeText(ListadoMeeting.this, R.string.novalid_mins, Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                        f_hh = h;
                                        f_mm = m;

                                        dialog.dismiss();

                                        tareaAsignar = new asignarRecepcion();
                                        tareaAsignar.execute(null,null,null);
                                    }
                                });

                                btnNo.setOnClickListener(new View.OnClickListener(){
                                    public void onClick(View arg0) {
                                        dialog.cancel();
                                    }
                                });
                                dialog.show();
                            }
                            return false;
                        }
                    });

                    if (estado.equals("1")) {

                        resultadoRecepciones.clear();
                        String content = json.getString("content");

                        if (!content.equals("")) {

                            JSONArray datos = json.getJSONArray("content");

                            for (int i = 0; i < datos.length(); i++) {

                                JSONObject row = datos.getJSONObject(i);
                                String res = row.getString("res");
                                String hora = row.getString("hora");
                                String gt = row.getString("gt");
                                String vuelo = row.getString("vuelo");
                                String agencia = row.getString("agencia");
                                String cliente = row.getString("cliente");
                                String phone = row.getString("phone");
                                String notas = row.getString("notas");
                                String orden = row.getString("orden");

                                HashMap<String, String> map = new HashMap<String, String>();
                                map.put("res", res);
                                map.put("hora", hora);
                                map.put("gt", gt);
                                map.put("vuelo", vuelo);
                                map.put("agencia", agencia);
                                map.put("cliente", cliente);
                                map.put("phone", phone);
                                map.put("notas", notas);
                                map.put("orden", orden);
                                resultadoRecepciones.add(map);
                            }
                        } else {
                            Toast.makeText(ListadoMeeting.this, R.string.data_not_found, Toast.LENGTH_LONG).show();
                        }
                        TextView t = findViewById(R.id.txtRecogidasTotal);
                        t.setText(String.valueOf(resultadoRecepciones.size()));

                        if (resultadoRecepciones.size() > 0)
                        {
                            SpecialAdapterMeeting listAdapter = new SpecialAdapterMeeting(ListadoMeeting.this,	resultadoRecepciones,	R.layout.fila_list_view_meeting,
                                    new String[] { "res", "hora", "gt", "vuelo",
                                            "agencia", "cliente" }, new int[] {
                                    R.id.txtReservaMeeting,
                                    R.id.txtHoraMeeting,
                                    R.id.txtGpMeeting,
                                    R.id.txtVueloMeeting,
                                    R.id.txtAgenciaMeeting,
                                    R.id.txtClienteMeeting});
                            list.setAdapter(listAdapter);
                        } else {
                            list.setAdapter(null);
                        }
                    } else {
                        Toast.makeText(ListadoMeeting.this,  getString(R.string.error_happened) + " " + mensaje,
                                Toast.LENGTH_LONG).show();
                        list.setAdapter(null);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                Carplus3G.dialogConexion(ListadoMeeting.this);
            }
            miDialog.hide();
            tareaBuscar = null;
        }
    }

    private class asignarRecepcion extends AsyncTask<String, String, String>{

        protected void onPreExecute() {
            miDialog = new ProgressDialog(ListadoMeeting.this);
            miDialog.setTitle(getString(R.string.procesing_rec));
            miDialog.setMessage(getString(R.string.sending_data_serv));
            miDialog.setCancelable(false);
            miDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            Carplus3G constantes = ((Carplus3G) getApplicationContext());
            rest = new RestClient(Carplus3G.URL);
            rest.AddParam("empresa", constantes.getEmpresa());
            rest.AddParam("id", constantes.getTerminalId());
            rest.AddParam("accion", "meeting_add");
            rest.AddParam("firma", Carplus3G.SHA256("madd"+f_reserva));
            rest.AddParam("reserva", f_reserva);
            rest.AddParam("hh", f_hh);
            rest.AddParam("mm", f_mm);

            try {
                rest.Execute(RequestMethod.POST);
            } catch (Exception e) {
                e.printStackTrace();
            }
            respuesta = rest.getResponse();
            return null;
        }

        protected void onPostExecute(String resultado) {
            String mensaje = "";
            String estado = "";
            String numero_orden = "";

            if (!respuesta.equals("error")) {
                try
                {
                    JSONObject json = new JSONObject(respuesta);

                    estado = json.getString("status");
                    mensaje = json.getString("statusMsg");

                    if (estado.equals("1")) {
                        JSONArray datos = json.getJSONArray("content");

                        for (int i = 0; i < datos.length(); i++) {
                            JSONObject row = datos.getJSONObject(i);
                            numero_orden = row.getString("orden");
                        }
                    } else {
                        Toast.makeText(ListadoMeeting.this,  getString(R.string.error_happened) + " " + mensaje,Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                if (estado.equals("1")) {
                    // Barramos la fila
                    ListView list = findViewById(R.id.contMeeting);
                    resultadoRecepciones.remove(indice_seleccionado);

                    SpecialAdapterMeeting listAdapter = new SpecialAdapterMeeting(ListadoMeeting.this,	resultadoRecepciones,	R.layout.fila_list_view_meeting,
                            new String[] { "res", "hora", "gt", "vuelo",
                                    "agencia", "cliente" }, new int[] {
                            R.id.txtReservaMeeting,
                            R.id.txtHoraMeeting,
                            R.id.txtGpMeeting,
                            R.id.txtVueloMeeting,
                            R.id.txtAgenciaMeeting,
                            R.id.txtClienteMeeting});
                    if (resultadoRecepciones.size() > 0) {
                        list.setAdapter(listAdapter);
                    } else {
                        list.setAdapter(null);
                    }
                    MeetingDialog = new Dialog(ListadoMeeting.this,R.style.FullHeightDialog);
                    MeetingDialog.setCancelable(true);
                    MeetingDialog.setContentView(R.layout.res_meeting);
                    TextView t = MeetingDialog.findViewById(R.id.txtOrden);
                    Button b = MeetingDialog.findViewById(R.id.btnResMeeting);
                    t.setText(numero_orden);
                    b.setOnClickListener(new View.OnClickListener(){

                        public void onClick(View v) {
                            MeetingDialog.dismiss();
                        }
                    });
                    MeetingDialog.show();
                }
            } else {
                Carplus3G.dialogConexion(ListadoMeeting.this);
            }
            miDialog.hide();
            tareaAsignar = null;
        }
    }
}