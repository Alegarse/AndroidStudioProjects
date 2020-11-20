package com.example.carplus3g365v2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

import com.example.carplus3g365v2.Modelos.Methods;
import com.example.carplus3g365v2.RestClient.RequestMethod;

public class CambioEstado extends AppCompatActivity {

    // Variables de la actividad
    private EditText txtMatricula, txtReferencia, txtModelo, txtColor, txtDesde, txtHasta,txtFDes, txtFRec;
    private Button btnMatricula, btnLimpiar, btnConfirmar;
    private ImageButton menu_ppal, salir;
    private Spinner spEstadoActual, spOficina, spNuevoEstado, spZona;
    private TableRow contDesdeHasta1, contDesdeHasta2, contDesaparicion, contRecuperacion;
    private CardView cardDesdeHasta;
    private CheckBox chkCambioEstado;

    private Methods metodo;

    public ProgressDialog miDialog;
    public DatePickerDialog dialogFecha;
    public RestClient conexionHTTP;
    public Carplus3G CF;
    public String JSONDATA;
    public int FIELDFECHA;

    public ArrayList<String[]> sucursales = new ArrayList<String[]>();
    public ArrayList<String[]> zonas = new ArrayList<String[]>();

    private asyncGetMatricula tareaMatricula;
    public asyncCambioEstado tareaCambioEstado;

    public String CntStat = "";
    public String Matricula = "";
    public String MtrContrato = "";
    public String HRec = "";
    public String FechaMin = "";
    public String FechaMax = "";

    public int d;
    public int m;
    public int y;

    int RESPUESTA_PREGUNTA = 0;
    Boolean CARGANDO_MATRICULA = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambio_estado);

        // Conexión a la BBDD
        conexionHTTP = new RestClient(Carplus3G.URL);

        // Instanciamos las variables
        txtMatricula = findViewById(R.id.txtMatricula);
        txtReferencia = findViewById(R.id.txtReferencia);
        txtModelo = findViewById(R.id.txtModelo);
        txtColor = findViewById(R.id.txtColor);

        cardDesdeHasta = findViewById(R.id.cardDesdeHasta);
        contDesdeHasta1 = findViewById(R.id.DesdeHastaContainer1);
        contDesdeHasta2 = findViewById(R.id.DesdeHastaContainer2);
        contDesaparicion = findViewById(R.id.DesaparicionContainer);
        contRecuperacion = findViewById(R.id.RecuperacionContainer);
        txtDesde = findViewById(R.id.txtDesde);
        txtHasta = findViewById(R.id.txtHasta);
        txtFDes = findViewById(R.id.txtFDes);
        txtFRec = findViewById(R.id.txtFRec);

        spEstadoActual = findViewById(R.id.estadoActual);
        spNuevoEstado = findViewById(R.id.nuevoEstado);
        spOficina = findViewById(R.id.oficinaActual);
        spZona = findViewById(R.id.zonaSituacion);

        chkCambioEstado = findViewById(R.id.chkCambioEstado);

        // Instanciamos los botones
        menu_ppal = findViewById(R.id.menuPpalCE);
        salir = findViewById(R.id.salirCE);
        btnMatricula = findViewById(R.id.btnLocalizarMatricula);
        btnLimpiar = findViewById(R.id.limpiarCE);
        btnConfirmar = findViewById(R.id.confirmarCE);

        metodo = new Methods(CambioEstado.this);

        // Se usa solo para tener la información de su estado actual, no es accionable
        spEstadoActual.setEnabled(false);

        // CardView Desde Hasta
        cardDesdeHasta.setVisibility(View.GONE);
        contDesdeHasta1.setVisibility(View.GONE);
        contDesdeHasta2.setVisibility(View.GONE);
        contDesaparicion.setVisibility(View.GONE);
        contRecuperacion.setVisibility(View.GONE);

        ArrayAdapter<CharSequence> newEstados = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item){
            @Override
            public boolean isEnabled(int position)
            {
                int estado = spEstadoActual.getSelectedItemPosition();
                boolean activo = true;

                switch (estado)
                {
                    case 0:
                    case 5:
                        if (position == 0 || position == 2 || position == 3) {
                            activo = false;
                        } else { activo = true; }
                        break;
                    case 1:
                        if (position == 0 || position == 1 || position == 2 || position == 3) {
                            activo = false;
                        } else { activo = true; }
                        break;
                    case 2:
                        if (position != 0) {
                            activo = false;
                        } else { activo = true; }
                        break;
                    case 3:
                        if (CntStat.equals("A") && Matricula.equals(MtrContrato)) {
                            if (position != 3) {
                                activo = false;
                            } else { activo = true; }
                        } else {
                            if (position != 1) {
                                activo = false;
                            } else { activo = true; }
                        }
                        break;
                    case 4:
                        if (position != 1) {
                            activo = false;
                        } else { activo = true; }
                        break;

                }
                return activo;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent)
            {

                View spinnerview = super.getDropDownView(position, convertView, parent);
                TextView spinnertextview = (TextView) spinnerview;

                int estado = spEstadoActual.getSelectedItemPosition();

                // Para poner en grisaceo los modos no seleccionables
                switch (estado)
                {
                    case 0: case 5:
                    if (position == 0 || position == 2 || position == 3) {
                        spinnertextview.setTextColor(Color.parseColor("#d7d7d7"));
                    } else {
                        spinnertextview.setTextColor(Color.parseColor("#000000"));
                    }
                    break;
                    case 1:
                        if (position == 0 || position == 1 || position == 2 || position == 3) {
                            spinnertextview.setTextColor(Color.parseColor("#d7d7d7"));
                        } else {
                            spinnertextview.setTextColor(Color.parseColor("#000000"));
                        }
                        break;
                    case 2:
                        if (position != 0) {
                            spinnertextview.setTextColor(Color.parseColor("#d7d7d7"));
                        } else {
                            spinnertextview.setTextColor(Color.parseColor("#000000"));
                        }
                        break;
                    case 3:
                        if (CntStat.equals("A") && Matricula.equals(MtrContrato))
                        {
                            if (position != 3) {
                                spinnertextview.setTextColor(Color.parseColor("#d7d7d7"));
                            } else {
                                spinnertextview.setTextColor(Color.parseColor("#000000"));
                            }
                        } else {
                            if (position != 1) {
                                spinnertextview.setTextColor(Color.parseColor("#d7d7d7"));
                            } else {
                                spinnertextview.setTextColor(Color.parseColor("#000000"));
                            }
                        }
                        break;
                    case 4:
                        if (position != 1) {
                            spinnertextview.setTextColor(Color.parseColor("#d7d7d7"));
                        } else {
                            spinnertextview.setTextColor(Color.parseColor("#000000"));
                        }
                        break;
                }
                return spinnerview;
            }
        };

        newEstados.add("");
        newEstados.add("Libre");
        newEstados.add("Alquilado");
        newEstados.add("Taller");
        newEstados.add("Sustraido");
        newEstados.add("Bloqueado");
        //newEstados.add("Peritación");


        newEstados.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spNuevoEstado.setAdapter(newEstados);

        Calendar cal = new GregorianCalendar();
        m = cal.get(Calendar.MONTH);
        m++;
        y = cal.get(Calendar.YEAR);
        d = cal.get(Calendar.DAY_OF_MONTH);

        String mes;
        if (m < 10) {mes = "0" + m;
        } else { mes = String.valueOf(m); }

        String dia;
        if (d < 10) { dia = "0" + d;
        } else { dia = String.valueOf(d); }

        txtDesde.setText(dia+"/"+mes+"/"+y);
        txtHasta.setText(dia+"/"+mes+"/"+y);
        txtFDes.setText(dia+"/"+mes+"/"+y);
        txtFRec.setText(dia+"/"+mes+"/"+y);


        // Escuchadores de los botones
        menu_ppal.setOnClickListener(v -> metodo.iraMenuPpal());
        salir.setOnClickListener(v -> metodo.cerrarSesion());
        btnMatricula.setOnClickListener(view -> clickGetMatricula());
        btnLimpiar.setOnClickListener(view -> btnLimpiarClick());
        btnConfirmar.setOnClickListener(view -> btnAceptarClick());

        spOficina.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                findZona(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
        spNuevoEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                seleccionNuevoEstado(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        txtDesde.setOnClickListener(this::txtF1Click);
        txtHasta.setOnClickListener(this::txtF2Click);
        txtFDes.setOnClickListener(this::txtFDESClick);
        txtFRec.setOnClickListener(this::txtFRECClick);

    }

    public void onStart() {
        super.onStart();

        Carplus3G constantes = ((Carplus3G) getApplicationContext());
        if (!constantes.cmpLastDate()) {
            constantes.setLastDate();
            constantes.setcargadoVersion(0);

            Intent salir = new Intent(CambioEstado.this, Login.class);
            startActivity(salir);
            finish();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(1, R.id.menuLogout, 1, getString(R.string.change_user)).setIcon(
                R.drawable.user);
        menu.add(2, R.id.menuSalir, 2, getString(R.string.exit)).setIcon(R.drawable.exit);

        return super.onPrepareOptionsMenu(menu);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        getApplicationContext();

        switch (item.getItemId()) {

            case R.id.menuSalir:
                System.exit(0);
                break;
            case R.id.menuLogout:
                Carplus3G constantes = ((Carplus3G) getApplicationContext());
                constantes.setIniciadoSesion(false);
                Intent salir = new Intent(this, Login.class);
                startActivity(salir);
                finish();
                break;
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return false;
    }

    private class asyncGetMatricula extends AsyncTask<Void, Integer, Boolean> {

        String matricula;

        @Override
        protected void onPreExecute() {

            matricula = txtMatricula.getText().toString().trim().toUpperCase();
            txtMatricula.setText(matricula);
            miDialog = new ProgressDialog(CambioEstado.this);
            miDialog.setMessage(getString(R.string.getting_information));
            miDialog.setCancelable(false);
            miDialog.show();

        }

        @Override
        protected Boolean doInBackground(Void... params) {

            CF = ((Carplus3G) getApplicationContext());

            try {
                conexionHTTP.AddParam("accion", "cambioestado_get_mtr");
                conexionHTTP.AddParam("firma", Carplus3G.SHA256("cegm"+matricula));
                conexionHTTP.AddParam("empresa", CF.getEmpresa());
                conexionHTTP.AddParam("id", CF.getTerminalId());
                conexionHTTP.AddParam("matricula", matricula);
                conexionHTTP.Execute(RequestMethod.POST);
            } catch (Exception e) {
                // Controlamos la posible excepción
            }

            JSONDATA = conexionHTTP.getResponse();
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
        }

        @Override
        protected void onPostExecute(Boolean result) {

            if (result) {
                if (!JSONDATA.equals("error")) {

                    try {
                        JSONObject json = new JSONObject(JSONDATA);

                        int estado = json.getInt("status");
                        String mensaje = json.getString("statusMsg");
                        JSONObject content = json.getJSONObject("content");
                        String err = content.getString("err");
                        String errmsg = content.getString("errmsg");

                        if (estado == 1) {

                            if (err.equals("ok") ) {
                                JSONObject vehiculo = content.getJSONObject("veh");
                                JSONArray sucursales = content.getJSONArray("ofc");
                                JSONArray zonas = content.getJSONArray("zon");
                                pintarVehiculo(vehiculo,sucursales,zonas);
                                miDialog.dismiss();
                            } else {
                                miDialog.dismiss();
                                Toast.makeText(getApplicationContext(), errmsg, Toast.LENGTH_LONG).show();
                                limpiarVehiculo();
                            }

                        } else {
                            miDialog.dismiss();
                            Toast.makeText(getApplicationContext(), getString(R.string.error_happened) + " " + mensaje, Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), getString(R.string.error_2p) +" " + e.getMessage(), Toast.LENGTH_LONG).show();
                        metodo.iraMenuPpal();
                    }
                } else {
                    Carplus3G.dialogConexion(CambioEstado.this);
                    metodo.iraMenuPpal();
                }
            }
            miDialog.dismiss();
        }

        @Override
        protected void onCancelled() {
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class asyncCambioEstado extends AsyncTask<Void, Integer, Boolean> {

        String matricula, f1,f2,fdes,frec;
        Integer cambiositu = 0;
        Integer nuevaoficina,antiguoestado,nuevoestado,nuevazona;

        @Override
        protected void onPreExecute() {
            matricula = txtMatricula.getText().toString().trim();
            f1 = txtDesde.getText().toString().trim();
            f2 = txtHasta.getText().toString().trim();
            fdes = txtFDes.getText().toString().trim();
            frec = txtFRec.getText().toString().trim();

            if (chkCambioEstado.isChecked()) {
                cambiositu = 1;
            }

            String[] filaS = sucursales.get(spOficina.getSelectedItemPosition());
            String[] filaZ = zonas.get(spZona.getSelectedItemPosition());

            nuevaoficina = Integer.parseInt(filaS[0]);
            antiguoestado = spEstadoActual.getSelectedItemPosition();
            nuevoestado = spNuevoEstado.getSelectedItemPosition() - 1;
            nuevazona = Integer.parseInt(filaZ[0]);

            miDialog = new ProgressDialog(CambioEstado.this);
            miDialog.setMessage(getString(R.string.sending_information));
            miDialog.setCancelable(false);
            miDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            CF = ((Carplus3G) getApplicationContext());

            try {
                conexionHTTP.AddParam("accion", "cambioestado");
                conexionHTTP.AddParam("firma", Carplus3G.SHA256("cest"+matricula));
                conexionHTTP.AddParam("empresa", CF.getEmpresa());
                conexionHTTP.AddParam("id", CF.getTerminalId());
                conexionHTTP.AddParam("matricula", matricula);
                conexionHTTP.AddParam("f1", f1);
                conexionHTTP.AddParam("f2", f2);
                conexionHTTP.AddParam("fdes", fdes);
                conexionHTTP.AddParam("frec", frec);
                conexionHTTP.AddParam("cambiositu", String.valueOf(cambiositu));
                conexionHTTP.AddParam("nofi", String.valueOf(nuevaoficina));
                conexionHTTP.AddParam("oest", String.valueOf(antiguoestado));
                conexionHTTP.AddParam("nest", String.valueOf(nuevoestado));
                conexionHTTP.AddParam("nzon", String.valueOf(nuevazona));
                conexionHTTP.AddParam("respre", String.valueOf(RESPUESTA_PREGUNTA));
                conexionHTTP.AddParam("fmin", String.valueOf(FechaMin));
                conexionHTTP.AddParam("fmax", String.valueOf(FechaMax));
                conexionHTTP.AddParam("mtrcontrato", String.valueOf(MtrContrato));
                conexionHTTP.AddParam("hrec", String.valueOf(HRec));
                conexionHTTP.AddParam("usuario", String.valueOf(CF.getCodUsuario()));
                conexionHTTP.Execute(RequestMethod.POST);
            } catch (Exception e) {
                // Solo para capturar la excepción
            }

            JSONDATA = conexionHTTP.getResponse();
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
        }

        @Override
        protected void onPostExecute(Boolean result) {
            miDialog.dismiss();

            if (result) {
                if (!JSONDATA.equals("error")) {

                    try {
                        JSONObject json = new JSONObject(JSONDATA);

                        int estado = json.getInt("status");
                        String mensaje = json.getString("statusMsg");
                        JSONObject content = json.getJSONObject("content");
                        String err = content.getString("err");
                        String errmsg = content.getString("errmsg");

                        if (estado == 1) {

                            if (err.equals("ok") ) {
                                Toast.makeText(getApplicationContext(), R.string.procces_do_ok, Toast.LENGTH_LONG).show();
                                btnLimpiarClick();
                            } else {
                                if (err.equals("ce07")) {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(CambioEstado.this);
                                    builder.setTitle("");
                                    builder.setMessage(getString(R.string.peritar_));
                                    builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            RESPUESTA_PREGUNTA = 1;
                                            btnAceptarClick();
                                            dialog.dismiss();
                                        }
                                    });
                                    builder.setNegativeButton(getString(R.string.not), new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.dismiss();
                                        }
                                    });

                                    AlertDialog dialog = builder.create();
                                    dialog.setCancelable(false);
                                    dialog.show();
                                    return;
                                }
                                if (err.equals("ce09")) {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(CambioEstado.this);
                                    builder.setTitle("");
                                    builder.setMessage(getString(R.string.error_administrativo));
                                    builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            RESPUESTA_PREGUNTA = 2;
                                            btnAceptarClick();
                                            dialog.dismiss();
                                        }
                                    });
                                    builder.setNegativeButton(getString(R.string.not), new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.dismiss();
                                        }
                                    });

                                    AlertDialog dialog = builder.create();
                                    dialog.setCancelable(false);
                                    dialog.show();
                                    return;
                                }
                                Toast.makeText(getApplicationContext(), errmsg, Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), getString(R.string.error_happened)+" "+ mensaje, Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), getString(R.string.error_2p)+" "+ e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Carplus3G.dialogConexion(CambioEstado.this);
                    metodo.iraMenuPpal();
                }
            }
        }
        @Override
        protected void onCancelled() {
        }
    }

    private void clickGetMatricula() {
        if (txtMatricula.getText().toString().trim().equals("")) {
            Toast.makeText(CambioEstado.this,R.string.please_enter_enrollment,Toast.LENGTH_LONG).show();
            return;
        }
        tareaMatricula = new asyncGetMatricula();
        tareaMatricula.execute(null, null, null);
    }

    private void limpiarVehiculo() {
        txtMatricula.setText("");
        txtColor.setText("");
        txtReferencia.setText("");
        txtModelo.setText("");
    }

    private void pintarVehiculo(JSONObject vehiculo,JSONArray suc,JSONArray zon) {
        try {
            int positionSucursal = 0;
            int positionZona = 0;

            txtMatricula.setEnabled(false);
            btnMatricula.setEnabled(false);
            btnMatricula.setVisibility(View.GONE);

            txtColor.setText(vehiculo.getString("col"));
            txtModelo.setText(vehiculo.getString("mod"));

            CntStat = vehiculo.getString("cnts");
            Matricula = vehiculo.getString("mtr");
            MtrContrato = vehiculo.getString("mtrc");
            HRec = vehiculo.getString("hrec");
            FechaMin = vehiculo.getString("fmin");
            FechaMax = vehiculo.getString("fmax");

            if (!vehiculo.get("fbq").equals("")) {
                Toast.makeText(getApplicationContext(), getString(R.string.vehicle_blocked)+" "+vehiculo.getString("fbq"), Toast.LENGTH_LONG).show();
            }

            // Flota pasiva
            if (vehiculo.get("est").equals("P")) {
                spEstadoActual.setSelection(6);
                txtReferencia.setText("");
                contDesdeHasta1.setVisibility(View.GONE);
                contDesdeHasta2.setVisibility(View.GONE);
                spOficina.setEnabled(false);
                spNuevoEstado.setEnabled(false);
                spZona.setEnabled(false);
                btnConfirmar.setVisibility(View.GONE);
                return;
            }

            // Baja en flota
            if (vehiculo.get("est").equals("B")) {
                spEstadoActual.setSelection(7);
                txtReferencia.setText("");
                contDesdeHasta1.setVisibility(View.GONE);
                contDesdeHasta2.setVisibility(View.GONE);
                spOficina.setEnabled(false);
                spNuevoEstado.setEnabled(false);
                spZona.setEnabled(false);
                btnConfirmar.setVisibility(View.GONE);
                return;
            }

            CARGANDO_MATRICULA = true;

            sucursales.clear();
            for (int i = 0; i < suc.length(); i++) {
                JSONObject row = suc.getJSONObject(i);
                String[] fila = new String[2];
                fila[0] = row.getString("cod");

                if (Integer.valueOf(fila[0]) == Integer.valueOf(vehiculo.getString("off"))) {
                    positionSucursal = i;
                }

                fila[1] = row.getString("tit");
                sucursales.add(fila);
            }

            ArrayAdapter<CharSequence> sucAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);

            for (Iterator<String[]> it = sucursales.iterator(); it.hasNext(); ) {
                String[] fila = it.next();
                sucAdapter.add(String.valueOf(fila[1]));
            }

            sucAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spOficina.setAdapter(sucAdapter);

            //Segundo parametro false asi no invoca onItemSelected que llamaria a findZona
            spOficina.setSelection(positionSucursal,false);

            zonas.clear();
            for (int i = 0; i < zon.length(); i++) {
                JSONObject row = zon.getJSONObject(i);
                String[] fila = new String[3];
                fila[0] = row.getString("cod");
                fila[1] = row.getString("tit");
                fila[2] = row.getString("suc");

                if (Integer.valueOf(fila[0]) == Integer.valueOf(vehiculo.getString("zon"))) {
                    positionZona = i;
                }

                zonas.add(fila);
            }

            ArrayAdapter<CharSequence> zonAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item){
                @Override
                public boolean isEnabled(int position)
                {
                    String[] filaS = sucursales.get(spOficina.getSelectedItemPosition());
                    String[] filaZ = zonas.get(position);

                    if (Integer.valueOf(filaZ[2]) != Integer.valueOf(filaS[0])) {
                        return false;
                    } else {
                        return true;
                    }
                }

                @Override
                public View getDropDownView(int position, View convertView,ViewGroup parent)
                {
                    View spinnerview = super.getDropDownView(position, convertView, parent);
                    TextView spinnertextview = (TextView) spinnerview;

                    String[] filaS = sucursales.get(spOficina.getSelectedItemPosition());
                    String[] filaZ = zonas.get(position);

                    if (Integer.valueOf(filaZ[2]) != Integer.valueOf(filaS[0])) {
                        spinnertextview.setTextColor(Color.parseColor("#d7d7d7"));
                    } else {
                        spinnertextview.setTextColor(Color.parseColor("#000000"));
                    }
                    return spinnerview;
                }
            };

            for (Iterator<String[]> it = zonas.iterator(); it.hasNext(); ) {
                String[] fila = it.next();
                zonAdapter.add(String.valueOf(fila[1]));
            }

            zonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spZona.setAdapter(zonAdapter);
            spZona.setSelection(positionZona);

            switch (Integer.parseInt(vehiculo.getString("est"))) {
                //LIBRE
                case 0:
                    spEstadoActual.setSelection(0);
                    txtReferencia.setText("");
                    contDesdeHasta1.setVisibility(View.GONE);
                    contDesdeHasta2.setVisibility(View.GONE);
                    spOficina.setEnabled(true);
                    spNuevoEstado.setEnabled(true);
                    spZona.setEnabled(true);
                    btnConfirmar.setVisibility(View.VISIBLE);
                    findNuevoEstado();
                    break;
                //ALQUILADO
                case 1:
                    spEstadoActual.setSelection(1);
                    txtReferencia.setText(vehiculo.getString("cnt"));
                    contDesdeHasta1.setVisibility(View.GONE);
                    contDesdeHasta2.setVisibility(View.GONE);
                    spOficina.setEnabled(false);
                    spNuevoEstado.setEnabled(true);
                    spZona.setEnabled(true);
                    btnConfirmar.setVisibility(View.VISIBLE);
                    findNuevoEstado();
                    break;
                //TALLER
                case 2:
                    spEstadoActual.setSelection(2);
                    txtReferencia.setText(vehiculo.getString("tal"));
                    contDesdeHasta1.setVisibility(View.GONE);
                    contDesdeHasta2.setVisibility(View.GONE);
                    spOficina.setEnabled(false);
                    spNuevoEstado.setEnabled(true);
                    spZona.setEnabled(true);
                    btnConfirmar.setVisibility(View.VISIBLE);
                    findNuevoEstado();
                    break;
                //SUSTRAIDO
                case 3:
                    spEstadoActual.setSelection(3);
                    txtReferencia.setText(vehiculo.getString("cnt"));
                    contDesdeHasta1.setVisibility(View.GONE);
                    contDesdeHasta2.setVisibility(View.GONE);
                    spOficina.setEnabled(false);
                    spNuevoEstado.setEnabled(true);
                    spZona.setEnabled(false);
                    btnConfirmar.setVisibility(View.VISIBLE);
                    chkCambioEstado.setChecked(false);
                    chkCambioEstado.setEnabled(false);
                    findNuevoEstado();
                    break;
                //BLOQUEADO
                case 4:
                    spEstadoActual.setSelection(4);
                    txtReferencia.setText("");
                    contDesdeHasta1.setVisibility(View.GONE);
                    contDesdeHasta2.setVisibility(View.GONE);
                    spOficina.setEnabled(true);
                    spNuevoEstado.setEnabled(true);
                    spZona.setEnabled(true);
                    btnConfirmar.setVisibility(View.VISIBLE);
                    findNuevoEstado();
                    break;
                //PERITAR
                case 5:
                    spEstadoActual.setSelection(5);
                    txtReferencia.setText("");
                    contDesdeHasta1.setVisibility(View.GONE);
                    contDesdeHasta2.setVisibility(View.GONE);
                    contDesaparicion.setVisibility(View.GONE);
                    contRecuperacion.setVisibility(View.GONE);
                    spOficina.setEnabled(true);
                    spNuevoEstado.setEnabled(true);
                    spZona.setEnabled(true);
                    btnConfirmar.setVisibility(View.VISIBLE);
                    findNuevoEstado();
                    break;
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), getString(R.string.error_2p)+" "+ e.getMessage(), Toast.LENGTH_LONG).show();
            metodo.iraMenuPpal();
        }
    }

    private void findZona(Integer position) {
        if (CARGANDO_MATRICULA) {
            CARGANDO_MATRICULA = false;
            return;
        }

        String[] filaS = sucursales.get(position);
        int i = 0;
        int posZona = 0;

        for (Iterator<String[]> it = zonas.iterator(); it.hasNext(); ) {

            String[] filaZ = it.next();
            if (Integer.parseInt(filaS[0]) == Integer.parseInt(filaZ[2])) {
                posZona = i;
                break;
            }
            i++;
        }
        spZona.setSelection(posZona);
    }

    private void findNuevoEstado() {

        int estadoActual = spEstadoActual.getSelectedItemPosition();

        switch(estadoActual) {
            case 0:
            case 4:
            case 5:
                spNuevoEstado.setSelection(1);
                spNuevoEstado.setEnabled(true);
                break;
            case 1:
                spNuevoEstado.setSelection(4);
                spNuevoEstado.setEnabled(true);
                cardDesdeHasta.setVisibility(View.VISIBLE);
                contDesaparicion.setVisibility(View.VISIBLE);
                contRecuperacion.setVisibility(View.GONE);
                break;
            case 2:
                spNuevoEstado.setSelection(0);
                spNuevoEstado.setEnabled(false);
                break;
            case 3:
                if (CntStat.equals("A") && Matricula.equals(MtrContrato))
                {
                    spNuevoEstado.setSelection(2);
                }
                else
                {
                    spNuevoEstado.setSelection(1);
                }
                spNuevoEstado.setEnabled(false);
                cardDesdeHasta.setVisibility(View.VISIBLE);
                contDesaparicion.setVisibility(View.GONE);
                contRecuperacion.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void seleccionNuevoEstado(Integer position) {

        //Si el estado actual es sustraido
        if (spEstadoActual.getSelectedItemPosition() == 3) {
            cardDesdeHasta.setVisibility(View.VISIBLE);
            contDesaparicion.setVisibility(View.GONE);
            contRecuperacion.setVisibility(View.VISIBLE);
        } else {
            switch (position) {
                //LIBRE Y PERITACIÓN
                case 1:
                case 6:
                    cardDesdeHasta.setVisibility(View.GONE);
                    contDesdeHasta1.setVisibility(View.GONE);
                    contDesdeHasta2.setVisibility(View.GONE);
                    contDesaparicion.setVisibility(View.GONE);
                    contRecuperacion.setVisibility(View.GONE);
                    break;

                // SUSTRAIDO
                case 4:
                    cardDesdeHasta.setVisibility(View.VISIBLE);
                    contDesdeHasta1.setVisibility(View.GONE);
                    contDesdeHasta2.setVisibility(View.GONE);
                    contDesaparicion.setVisibility(View.VISIBLE);
                    contRecuperacion.setVisibility(View.GONE);

                    break;

                //BLOQUEADO
                case 5:
                    cardDesdeHasta.setVisibility(View.VISIBLE);
                    contDesdeHasta1.setVisibility(View.VISIBLE);
                    contDesdeHasta2.setVisibility(View.VISIBLE);
                    contDesaparicion.setVisibility(View.GONE);
                    contRecuperacion.setVisibility(View.GONE);
                    break;
            }
        }
    }

    private void txtF1Click(View v) {
        FIELDFECHA = 1;

        int mYear = 0;
        int mMonth = 0;
        int mDay = 0;

        String f = txtDesde.getText().toString().trim();

        if (f.equals("")) {
            Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        } else {
            mYear = Integer.parseInt(f.substring(6, 10));
            mMonth = Integer.parseInt(f.substring(3, 5)) - 1;
            mDay = Integer.parseInt(f.substring(0, 2));
        }

        dialogFecha = new DatePickerDialog(CambioEstado.this, datePickerListener, mYear, mMonth, mDay);
        dialogFecha.setTitle(getString(R.string.select_date_from));
        dialogFecha.show();
    }

    private void txtF2Click(View v) {
        FIELDFECHA = 2;
        String f = txtHasta.getText().toString().trim();

        int mYear = 0;
        int mMonth = 0;
        int mDay = 0;

        if (f.equals("")) {
            Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        } else {
            mYear = Integer.parseInt(f.substring(6, 10));
            mMonth = Integer.parseInt(f.substring(3, 5)) - 1;
            mDay = Integer.parseInt(f.substring(0, 2));
        }

        dialogFecha = new DatePickerDialog(CambioEstado.this, datePickerListener, mYear, mMonth, mDay);
        dialogFecha.setTitle(getString(R.string.select_date_to));
        dialogFecha.show();
    }

    private void txtFDESClick(View v) {
        FIELDFECHA = 3;
        String f = txtFDes.getText().toString().trim();

        int mYear = 0;
        int mMonth = 0;
        int mDay = 0;

        if (f.equals("")) {
            Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        } else {
            mYear = Integer.parseInt(f.substring(6, 10));
            mMonth = Integer.parseInt(f.substring(3, 5)) - 1;
            mDay = Integer.parseInt(f.substring(0, 2));
        }

        dialogFecha = new DatePickerDialog(CambioEstado.this, datePickerListener, mYear, mMonth, mDay);
        dialogFecha.setTitle(getString(R.string.select_date_dissapear));
        dialogFecha.show();
    }

    private void txtFRECClick(View v) {
        FIELDFECHA = 4;
        String f = txtFRec.getText().toString().trim();

        int mYear = 0;
        int mMonth = 0;
        int mDay = 0;

        if (f.equals("")) {
            Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        } else {
            mYear = Integer.parseInt(f.substring(6, 10));
            mMonth = Integer.parseInt(f.substring(3, 5)) - 1;
            mDay = Integer.parseInt(f.substring(0, 2));
        }

        dialogFecha = new DatePickerDialog(CambioEstado.this, datePickerListener, mYear, mMonth, mDay);
        dialogFecha.setTitle(getString(R.string.select_date_recu));
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

            switch (FIELDFECHA) {
                case 1:
                    txtDesde.setText(fecha);
                    break;
                case 2:
                    txtHasta.setText(fecha);
                    break;
                case 3:
                    txtFDes.setText(fecha);
                    break;
                case 4:
                    txtFRec.setText(fecha);
                    break;
            }
        }
    };

    private void btnLimpiarClick() {

        RESPUESTA_PREGUNTA = 0;

        CntStat = "";
        Matricula = "";
        MtrContrato = "";
        HRec = "";
        FechaMin = "";
        FechaMax = "";

        txtMatricula.setText("");
        txtMatricula.setEnabled(true);
        txtReferencia.setText("");
        txtModelo.setText("");
        txtColor.setText("");
        txtDesde.setText("");
        txtHasta.setText("");
        txtFDes.setText("");
        txtFRec.setText("");

        btnMatricula.setEnabled(true);
        btnMatricula.setVisibility(View.VISIBLE);

        chkCambioEstado.setChecked(false);
        chkCambioEstado.setEnabled(true);

        spEstadoActual.setSelection(0);
        spOficina.setSelection(0);
        spNuevoEstado.setSelection(0);
        spZona.setSelection(0);

        contDesdeHasta1.setVisibility(View.GONE);
        contDesdeHasta2.setVisibility(View.GONE);
        contDesaparicion.setVisibility(View.GONE);
        contRecuperacion.setVisibility(View.GONE);

        Calendar cal = new GregorianCalendar();
        m = cal.get(Calendar.MONTH);
        m++;
        y = cal.get(Calendar.YEAR);
        d = cal.get(Calendar.DAY_OF_MONTH);

        String mes = "";
        if (m < 10) { mes = "0" + m;
        } else { mes = String.valueOf(m); }

        String dia = "";
        if (d < 10) { dia = "0" + d;
        } else { dia = String.valueOf(d); }

        txtDesde.setText(dia+"/"+mes+"/"+y);
        txtHasta.setText(dia+"/"+mes+"/"+y);
        txtFDes.setText(dia+"/"+mes+"/"+y);
        txtFRec.setText(dia+"/"+mes+"/"+y);

    }

    private void btnAceptarClick() {
        if (txtMatricula.isEnabled()) {
            Toast.makeText(CambioEstado.this,R.string.please_insert_mat,Toast.LENGTH_SHORT).show();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(CambioEstado.this);

        builder.setTitle("");
        builder.setMessage(getString(R.string.sure_to_go));

        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                tareaCambioEstado = new asyncCambioEstado();
                tareaCambioEstado.execute(null, null, null);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(getString(R.string.not), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

}