package com.example.carplus3g365v2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.carplus3g365v2.Modelos.Methods;
import com.example.carplus3g365v2.RestClient.RequestMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Checkin extends AppCompatActivity {

    public RestClient rest;
    public String respuesta = "";
    public ProgressDialog miDialog;
    public TextView txtDocumentoCheckin;
    public TextView txtDepositarioCheckin;
    public TextView txtF1Checkin;
    public TextView txtF2Checkin;
    public TextView txtColorCheckin;

    private Spinner spinOctavos;
    private String btnPulsado = "";
    private EditText txtKms;
    private String[] octavos = {"0/8", "1/8", "2/8", "3/8", "4/8", "5/8", "6/8", "7/8", "8/8"};

    //Orden-Familia-Clave-Concepto
    private String[][] danos = {
            {"94", "ME**", "", "MECANICA"},
            {"95", "**ME", "MEM05001", "Bateria"},
            {"96", "**ME", "MEM05002", "Calefacción"},
            {"97", "**ME", "MEM12003", "Dirección"},
            {"98", "**ME", "MEM05004", "Embrague"},
            {"99", "**ME", "MEM05005", "Frenos"},
            {"100", "**ME", "MEM12006", "Limpia parabrisa"},
            {"101", "**ME", "MEM05007", "Motor"},
            {"102", "**ME", "MEM05008", "Refrigeración"},
            {"103", "**ME", "MEM05009", "Tuberias y carter"},
            {"104", "**ME", "MEM00010", "Otros."},
            {"106", "PRCH", "", "PARAGOLPES"},
            {"107", "**PR", "CHA27001", "Paragolpe delantero"},
            {"108", "**PR", "CHA26002", "Paragolpe trasero  "},
            {"109", "PTCH", "", "PUERTAS"},
            {"110", "**PT", "CHA08003", "Pta. Ant. Dcha."},
            {"111", "**PT", "CHA09004", "Pta. Ant. Izqda."},
            {"112", "**PT", "CHA21005", "Maletero Portón Tras."},
            {"113", "**PT", "CHA14006", "Pta. Post. Dcha."},
            {"114", "**PT", "CHA17007", "Pta. Post. Izqda."},
            {"115", "**PT", "CHA08008", "Cerradura Ant. Dcha."},
            {"116", "**PT", "CHA09009", "Cerradura Ant. Izqda."},
            {"117", "**PT", "CHA21010", "Cerradura Maletero"},
            {"118", "**PT", "CHA14011", "Cerradura Post. Dcha."},
            {"119", "**PT", "CHA17012", "Cerradura Post. Izqda."},
            {"120", "CACH", "", "CARROCERIA"},
            {"121", "**CA", "CHA06013", "Aleta Ant. Dcha."},
            {"122", "**CA", "CHA07014", "Aleta Ant. Izqda."},
            {"123", "**CA", "CHA20015", "Aleta Post. Dcha."},
            {"124", "**CA", "CHA22016", "Aleta Post. Izqda."},
            {"125", "**CA", "CHA27017", "Goma faldón"},
            {"126", "**CA", "CHA27018", "Paragolpe Ant."},
            {"127", "**CA", "CHA26019", "Paragolpe Post."},
            {"128", "**CA", "CHA13020", "Techo"},
            {"129", "**CA", "CHA05021", "Capó"},
            {"130", "LZCH", "", "LUCES"},
            {"131", "**LZ", "CHA02022", "Faro Ant. Izdo."},
            {"132", "**LZ", "CHA01023", "Faro Ant. Dcho."},
            {"133", "**LZ", "CHA25024", "Faro Post. Izdo."},
            {"134", "**LZ", "CHA24025", "Faro Post. Dcho."},
            {"135", "**LZ", "CHA02026", "Piloto Ant. Izqdo."},
            {"136", "**LZ", "CHA01027", "Piloto Ant. Dcho."},
            {"137", "**LZ", "CHA25028", "Piloto Post. Izqdo."},
            {"138", "**LZ", "CHA24029", "Piloto Post. Dcho."},
            {"139", "**LZ", "CHA27030", "Antinieblas"},
            {"140", "CRCH", "", "CRISTALES"},
            {"141", "**CR", "CHA12031", "Luna frontal"},
            {"142", "**CR", "CHA18032", "Luna Trasera"},
            {"143", "**CR", "CHA11033", "Luna Ant. Izqda."},
            {"144", "**CR", "CHA10034", "Luna Ant. Dcha"},
            {"145", "**CR", "CHA16035", "Luna Post. Izqda."},
            {"146", "**CR", "CHA15036", "Luna Post. Dcha"},
            {"147", "**CR", "CHA11037", "Espejo Retro. Izqdo."},
            {"148", "**CR", "CHA10038", "Espejo Retro. Dcho. "},
            {"149", "**CR", "CHA12039", "Espejo interior."},
            {"150", "NECH", "", "NEUMATICOS"},
            {"151", "**NE", "CHA04040", "Rueda Ant. Izqda."},
            {"152", "**NE", "CHA03041", "Rueda Ant. Dcha"},
            {"153", "**NE", "CHA23042", "Rueda Post. Izqda."},
            {"154", "**NE", "CHA19043", "Rueda Post. Dcha"},
            {"155", "**NE", "CHA21044", "Rueda repuesto"},
            {"156", "IN**", "", "INTERIORES"},
            {"157", "**IN", "INT12045", "Cinturones de seguridad"},
            {"158", "**IN", "INT12046", "Cuadro principal"},
            {"159", "**IN", "INT12047", "Volante"},
            {"160", "**IN", "INT12048", "Intermitente"},
            {"161", "**IN", "INT12049", "Claxon"},
            {"162", "**IN", "INT12050", "Pedales"},
            {"163", "**IN", "INT12051", "Cambios de marcha"},
            {"164", "**IN", "INT11052", "Asiento Ant. Izqdo."},
            {"165", "**IN", "INT10053", "Asiento Ant. Dcho."},
            {"166", "**IN", "INT16054", "Asiento Post. Izqdo."},
            {"167", "**IN", "INT15055", "Asiento Post. Dcho."},
            {"168", "**IN", "INT12056", "Guantera"},
            {"169", "**IN", "INT12057", "Cenicero"},
            {"170", "**IN", "INT12058", "Encendedor"},
            {"171", "**IN", "INT12059", "Radio y altavoces"},
            {"172", "**IN", "INT13060", "Techo y paredes"},
            {"173", "AC**", "", "ACCESORIOS"},
            {"174", "**AC", "ACC21061", "Rueda de repuesto"},
            {"175", "**AC", "ACC21062", "Tapacubos"},
            {"176", "**AC", "ACC13063", "Antena"},
            {"177", "**AC", "ACC21064", "Herramientas"},
            {"178", "**AC", "ACC12065", "Documentación"},
            {"179", "**AC", "ACC21066", "triángulos"},
            {"180", "**AC", "ACC12067", "Bandeja"},
            {"181", "**AC", "ACC13068", "Baca"},
            {"182", "**AC", "ACC13069", "Baby-seat"},
            {"183", "**AC", "ACC13070", "Booster"},
            {"184", "**AC", "ACC21071", "Gato"}
    };

    public ArrayList<String[]> miArray = new ArrayList<String[]>();
    public ArrayList<String[]> miArrayDanos = new ArrayList<String[]>();
    public ArrayList<String[]> miArrayZonas = new ArrayList<String[]>();
    public TextView txtMatriculaCheckin;
    public String estrellaActual = "";
    public View cajonDano;
    public String idActual;
    public String kmsCoche = "0";
    public float litrosDeposito = 0;
    public String litros = "0";
    public String jsonDanos = "";
    public String contrato = "";
    public Button btnCerrarContrato, btnApartar;
    public Integer zonaAparcamiento = 0;

    private Methods metodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin);

        // Instanciamos los elementos de la actividad
        ImageButton menu_ppal = findViewById(R.id.menuPpalCH);
        ImageButton salir = findViewById(R.id.salirCH);

        metodo = new Methods(Checkin.this);

        // Escuchadores de los botones
        menu_ppal.setOnClickListener(v -> metodo.iraMenuPpal());
        salir.setOnClickListener(v -> metodo.cerrarSesion());

        // Asignamos la matrícula
        Carplus3G constantes = ((Carplus3G) getApplicationContext());
        txtMatriculaCheckin = (TextView) findViewById(R.id.txtMatriculaCheckin);
        txtMatriculaCheckin.setText(constantes.getMatricula());

        txtDocumentoCheckin = (TextView) findViewById(R.id.txtDocumentoCheckin);
        txtDepositarioCheckin = (TextView) findViewById(R.id.txtDepositarioCheckin);
        txtF1Checkin = (TextView) findViewById(R.id.txtF1Checkin);
        txtF2Checkin = (TextView) findViewById(R.id.txtF2Checkin);
        txtColorCheckin = (TextView) findViewById(R.id.txtColorCheckin);

        btnCerrarContrato = (Button) findViewById(R.id.btnCerrarContrato);
        btnApartar = (Button) findViewById(R.id.btnApartar);

        txtKms = (EditText) findViewById(R.id.txtKms);
        txtKms.setText("0");
        txtKms.clearFocus();

        // Metemos datos del spinner de combustible
        spinOctavos = (Spinner) findViewById(R.id.spinOctavos);
        ArrayAdapter<CharSequence> octavosAdapter = new ArrayAdapter<CharSequence>(
                this, android.R.layout.simple_spinner_item);
        for (int i = 0; i < octavos.length; i++) {
            octavosAdapter.add(String.valueOf(octavos[i]));
        }
        octavosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinOctavos.setAdapter(octavosAdapter);

        ocultaEstrellas("rojo");
        ocultaEstrellas("amarillo");

        //Alterno entre el botón de cerrar contrato o apartar contrato
        if (constantes.getPermisoApartar() == 1) {
            btnApartar.setVisibility(View.VISIBLE);
        } else {
            btnApartar.setVisibility(View.GONE);
        }

        if (constantes.getPermisoCerrar() == 1) {
            btnCerrarContrato.setVisibility(View.VISIBLE);
        } else {
            btnCerrarContrato.setVisibility(View.GONE);
        }

        if (constantes.getPermisoCerrarKLMS() == 0) {
            txtKms.setFocusable(false);
            txtKms.setClickable(false);
        }
    }

    public void onStart() {
        super.onStart();

        Carplus3G constantes = ((Carplus3G) getApplicationContext());
        if (!constantes.cmpLastDate()) {
            constantes.setLastDate();
            constantes.setcargadoVersion(0);
            Intent i = new Intent();
            i.setClass(Checkin.this, Login.class);
            startActivity(i);
            Checkin.this.finish();
            return;
        }

        if (!Carplus3G.cmpConexion(this)) {
            Carplus3G.dialogInternet(this);
        } else {
            miDialog = new ProgressDialog(this);
            miDialog.setMessage(getString(R.string.msginit));
            miDialog.setTitle(getString(R.string.getting_contract_data));
            miDialog.setCancelable(false);
            miDialog.show();

            // Comprobamos permisos para las opciones del menú
            //Lanzamos hilo en llamada al servidor
            Thread hilo = new Thread() {
                public void run() {
                    Carplus3G constantes = ((Carplus3G) getApplicationContext());
                    rest = new RestClient(Carplus3G.URL);
                    rest.AddParam("empresa", constantes.getEmpresa());
                    rest.AddParam("accion", "getcheckin");
                    rest.AddParam("firma", Carplus3G.SHA256("gch"+constantes.getMatricula()));
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

    final Handler handlerHiloInicio = new Handler();
    final Runnable btnEnviarBack = new Runnable() {
        public void run() {
            ProcesarHilo();
        }
    };

    // Procesamos la función para la llamad al servidor
    private void ProcesarHilo() {

        String estado = "";
        String mensaje = "";

        if (!respuesta.equals("error")) {
            try {
                JSONObject json = new JSONObject(respuesta);

                estado = json.getString("status");
                mensaje = json.getString("statusMsg");

                if (estado.equals("1")) {
                    Carplus3G constantes = ((Carplus3G) getApplicationContext());
                    JSONObject contenido = json.getJSONObject("content");

                    contrato = contenido.getString("contrato");
                    String checkin_cliente = contenido.getString("cliente");
                    String checkin_entrega = contenido.getString("entrega");
                    String checkin_recogida = contenido.getString("recogida");
                    String checkin_color = contenido.getString("color");
                    String checkin_aviso = contenido.getString("aviso");

                    if (!checkin_aviso.equalsIgnoreCase("")) {
                        AlertDialog ad = new AlertDialog.Builder(this).create();
                        ad.setTitle(getString(R.string.notice));
                        ad.setCancelable(false);
                        ad.setMessage(checkin_aviso);
                        ad.setButton(getString(R.string.accept), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        ad.setIcon(R.drawable.icon);
                        ad.show();
                    }
                    txtDocumentoCheckin.setText(contrato);
                    txtDepositarioCheckin.setText(checkin_cliente);
                    txtF1Checkin.setText(checkin_entrega);
                    txtF2Checkin.setText(checkin_recogida);
                    txtColorCheckin.setText(checkin_color);

                    String checkin_otv = contenido.getString("gasOTV");

                    int spinnerPosition = Integer.parseInt(checkin_otv.substring(0,1));
                    spinOctavos.setSelection(spinnerPosition);

                    litrosDeposito = Float.parseFloat(contenido.getString("totDeposito"));

                    kmsCoche = contenido.getString("klms");

                    if (constantes.getPermisoCerrarKLMS() == 0) {
                        txtKms.setText(String.valueOf(Integer.parseInt(kmsCoche) + 1));
                    }

                    LinearLayout contExtras = findViewById(R.id.contExtrasCheckin);
                    LinearLayout contDanos = findViewById(R.id.contDanos);
                    // Originalmente TableRow en muestrario, cambiado a lineal
                    //TableRow contAccesorios = (TableRow) findViewById(R.id.contAccesorios);
                    LinearLayout contAccesorios = findViewById(R.id.linealEquipamiento);

                    //Obtenemos zonas
                    JSONArray arrayZonas = new JSONArray(contenido.getString("zonas"));
                    for (int i = 0; i < arrayZonas.length(); i++) {
                        JSONObject row = arrayZonas.getJSONObject(i);

                        String[] fila = new String[2];
                        fila[0] = row.getString("cod");
                        fila[1] = row.getString("nom");
                        miArrayZonas.add(fila);
                    }

                    // Los daños provienen de un array así que los guardamos en otro (miArrayDanos)
                    String d = contenido.getString("danos");
                    if (!d.equals("")) {
                        JSONArray array = new JSONArray(contenido.getString("danos"));
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject row = array.getJSONObject(i);

                            String[] fila = new String[5];
                            fila[0] = row.getString("id");
                            fila[1] = row.getString("codigo");
                            fila[2] = row.getString("desc");
                            fila[3] = "0";
                            fila[4] = "";
                            miArrayDanos.add(fila);

                            String pos = row.getString("codigo");
                            pos = pos.substring(3, 5);
                            String estrella = "pic" + pos + "y";

                            int resID = getResources().getIdentifier(estrella, "id", "com.carplus3g365");
                            ImageView image = (ImageView) findViewById(resID);
                            image.setVisibility(View.VISIBLE);

                            LinearLayout l = new LinearLayout(this);
                            l.setGravity(Gravity.CENTER_VERTICAL);
                            l.setPadding(1, 1, 1, 1);
                            l.setBackgroundResource(R.drawable.fondo_blanco_borde_gris);

                            ImageView img = new ImageView(this);
                            img.setImageResource(R.drawable.tick);
                            img.setPadding(0, 0, 5, 0);

                            TextView t = new TextView(this);
                            t.setText(row.getString("desc"));
                            t.setTextColor(Color.BLACK);
                            t.setTextSize(16);

                            l.addView(img);
                            l.addView(t);

                            contDanos.addView(l);
                        }
                    }

                    // Array de extras mostrado con botones
                    String e = contenido.getString("extras");
                    if (!e.equals("")) {
                        JSONArray arrayExtras = new JSONArray(contenido.getString("extras"));
                        for (int i = 0; i < arrayExtras.length(); i++) {
                            JSONObject row = arrayExtras.getJSONObject(i);

                            ToggleButton b = new ToggleButton(this);
                            b.setTextOff(row.getString("desc"));
                            b.setText(row.getString("desc"));
                            b.setTextOn(row.getString("desc"));
                            b.setChecked(true);

                            contExtras.addView(b);
                        }
                    }

                    // Array de accesorios mostrado con botones
                    contAccesorios.removeAllViews();

                    String acc = contenido.getString("accesorios");
                    if (!acc.equals("")) {

                        JSONArray arrayExtras = new JSONArray(contenido.getString("accesorios"));
                        for (int i = 0; i < arrayExtras.length(); i++) {
                            JSONObject row = arrayExtras.getJSONObject(i);

                            //Realizar algoritmia para cambiar cada boton
                            ToggleButton b = new ToggleButton(this);
                            b.setTextOff(row.getString("Accesorio"));
                            b.setText(row.getString("Accesorio"));
                            b.setTextOn(row.getString("Accesorio"));
                            b.setChecked(false);

                            b.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    if (isChecked) {
                                        buttonView.getBackground().setColorFilter(new LightingColorFilter(Color.GREEN, Color.BLACK));
                                    } else {
                                        buttonView.getBackground().clearColorFilter();
                                    }
                                }
                            });
                            contAccesorios.addView(b);
                        }
                    }

                    // Avisamos al usuario si se ha pasado en horas
                    String pasado_de_horas = contenido.getString("pasado_de_horas");
                    if (pasado_de_horas.equals("1")) {
                        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                        alertDialog.setTitle(getString(R.string.notice));
                        alertDialog.setMessage(getString(R.string.hour_return_passed));
                        alertDialog.setButton(getString(R.string.accept), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // here you can add functions
                            }
                        });
                        alertDialog.setIcon(R.drawable.icon);
                        alertDialog.show();
                    }
                } else {
                    Toast.makeText(this, getString(R.string.error_happened) + ": " + mensaje, Toast.LENGTH_LONG).show();
                    Intent i = new Intent();
                    i.setClass(this, MenuPrincipal.class);
                    startActivity(i);
                    this.finish();
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            Carplus3G.dialogConexion(this);
            //Aqui se ha producido un error al cargar los datos del contrato
            //debo de redirigir al menu principal y mostrar un mensaje de texto
            Toast.makeText(this, R.string.failed_charge_data_contract, Toast.LENGTH_SHORT).show();
            Intent i = new Intent();
            i.setClass(this, MenuPrincipal.class);
            startActivity(i);
            this.finish();
        }
        miDialog.hide();
    }

    private void ocultaEstrellas(String color) {

        for (int i = 1; i <= 27; i++) {
            String numero = "0";

            if (i < 10) {
                if (color.equals("rojo")) {
                    numero = "pic0" + i + "r";
                }
                if (color.equals("amarillo")) {
                    numero = "pic0" + i + "y";
                }

            } else {
                if (color.equals("rojo")) {
                    numero = "pic" + i + "r";
                }
                if (color.equals("amarillo")) {
                    numero = "pic" + i + "y";
                }
            }

            int resID = getResources().getIdentifier(numero, "id", "com.carplus3g365");
            ImageView image = findViewById(resID);
            image.setVisibility(View.INVISIBLE);
        }
    }

    // Menú inferior Android
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

    // Desactivar el botón atrás del movil
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return false;
    }

    // Para el botón nuevo vehículo
    public void btnNuevoVehiculoCheckinClick(View btn) {
        getApplicationContext();

        Bundle b = new Bundle();
        b.putString("tipo", "checkin");

        Intent i = new Intent(this, Scan.class);
        i.putExtras(b);
        startActivity(i);
        this.finish();
    }

    // Para el botón de añadir daños
    public void btnAddDanosClick(View btn) {
        getApplicationContext();

        final Dialog dialog = new Dialog(this, R.style.FullHeightDialog);
        dialog.setTitle(getString(R.string.title_add_damage));
        dialog.setContentView(R.layout.add_danos);

        final Button btnCancelarDano = (Button) dialog.findViewById(R.id.btnCancelarDano);
        final Button btnAdd = (Button) dialog.findViewById(R.id.BtnAddDano);
        final Spinner spinDet = (Spinner) dialog.findViewById(R.id.spinDet);
        final Spinner spinCat = (Spinner) dialog.findViewById(R.id.spinCat);
        final EditText txtDano = (EditText) dialog.findViewById(R.id.txtDano);
        final ArrayAdapter<CharSequence> catAdapter = new ArrayAdapter<CharSequence>(
                this, android.R.layout.simple_spinner_item);
        final ArrayAdapter<CharSequence> detAdapter = new ArrayAdapter<CharSequence>(
                this, android.R.layout.simple_spinner_item);

        for (int i = 0; i < danos.length; i++) {
            if (!String.valueOf(danos[i][1]).substring(0, 2).equals("**")) {
                catAdapter.add(String.valueOf(danos[i][3]));
                String[] fila = new String[4];
                fila[3] = String.valueOf(danos[i][3]);
                fila[2] = String.valueOf(danos[i][2]);
                fila[1] = String.valueOf(danos[i][1]);
                miArray.add(fila);
            }
        }
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCat.setAdapter(catAdapter);
        spinDet.setEnabled(false);
        spinCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> adapter, View view, int arg2, long arg3) {

                spinDet.setEnabled(true);
                detAdapter.clear();

                int valor = spinCat.getSelectedItemPosition();
                String[] familia = miArray.get(valor);
                String familiaValor = familia[1].toString();
                familiaValor = familiaValor.substring(0, 2);

                /*Cargamos los datos de los daos pero no los que ya esten incluidos*/
                for (int i = 0; i < danos.length; i++) {
                    boolean yaExiste = false;

                    for (Iterator<String[]> it = miArrayDanos.iterator(); it.hasNext(); ) {
                        String[] fila = it.next();
                        if (fila[2].equals(danos[i][3])) {
                            yaExiste = true;

                        }
                    }
                    if (!yaExiste) {
                        if (String.valueOf(danos[i][1]).substring(2, 4).equals(familiaValor)) {
                            detAdapter.add(String.valueOf(danos[i][3]));
                        }
                    }
                }
                detAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinDet.setAdapter(detAdapter);
            }
            public void onNothingSelected(AdapterView<?> adapter) {
                // TODO Auto-generated method stub
            }
        });

        btnCancelarDano.setOnClickListener(new View.OnClickListener() {
            public void onClick(View btn) {
                dialog.dismiss();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View btn) {
                // TODO Auto-generated method stub

                String desc = "";
                if (spinDet.getSelectedItem() != null) {
                    desc = spinDet.getSelectedItem().toString();
                }
                String clave = "";
                String id = "";

                for (int i = 0; i < danos.length; i++) {
                    if (String.valueOf(danos[i][3]).equals(desc)) {
                        clave = danos[i][2];
                        id = danos[i][0];
                    }
                }
                if (btnAddDanoClick(id, clave, desc, txtDano.getText().toString())) {
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    // Para el botón de añadir 1 daño
    private boolean btnAddDanoClick(String id, String cod, String desc, String descripcion) {

        boolean resultado = false;

        if (desc.equals("")) {
            Toast.makeText(this, R.string.damage_not_selected, Toast.LENGTH_SHORT).show();
        } else {
            if (descripcion.equals("")) {
                Toast.makeText(this, R.string.description_damage_needed, Toast.LENGTH_SHORT).show();
            } else {

                resultado = true;

                //	Muestro la estrella y el texto
                String pos = cod.substring(3, 5);
                String estrella = "pic" + pos + "r";

                int resID = getResources().getIdentifier(estrella, "id", "com.carplus3g365");
                ImageView image = findViewById(resID);
                image.setVisibility(View.VISIBLE);

                LinearLayout contDanos = findViewById(R.id.contDanos);

                LinearLayout l = new LinearLayout(this);
                l.setGravity(Gravity.CENTER_VERTICAL);
                l.setBackgroundResource(R.drawable.fondo_blanco_borde_gris);
                String[] tag = {pos, id};
                l.setTag(tag);

                ImageView img = new ImageView(this);
                img.setImageResource(R.drawable.redcross);
                img.setPadding(0, 0, 5, 0);

                TextView t = new TextView(this);
                t.setText(descripcion);
                t.setTextColor(Color.BLACK);
                t.setTextSize(16);

                l.addView(img);
                l.addView(t);

                contDanos.addView(l);

                //	Agrego a miArrayDanos dicho daño
                String[] fila = new String[5];
                fila[0] = id;
                fila[1] = cod;
                fila[2] = desc;
                fila[3] = "1";
                fila[4] = descripcion;
                miArrayDanos.add(fila);

                l.setOnLongClickListener(new View.OnLongClickListener() {
                    public boolean onLongClick(View layout) {
                        getApplicationContext();

                        //	Recojo el tag
                        String[] tag = (String[]) layout.getTag();
                        String pos = tag[0];
                        String id = tag[1];
                        idActual = id;
                        estrellaActual = "pic" + pos + "r";
                        cajonDano = layout;
                        cajonDano.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.fondo_blanco_borde_rojo,null));

                        onLongClickDano();
                        return false;
                    }
                });
            }
        }
        return resultado;
    }

    // Dialog de confirmación para borrar el daño
    private void onLongClickDano() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage(getString(R.string.delete_damage)).setPositiveButton(getString(R.string.yes), dialogClickListenerBorraDano)
                .setNegativeButton(getString(R.string.not), dialogClickListenerBorraDano).show();
    }

    // Escuchador del dialog anterior de borrar daño
    DialogInterface.OnClickListener dialogClickListenerBorraDano = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            getApplicationContext();

            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    borraDanoSi();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    cajonDano.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.fondo_blanco_borde_gris,null));
                    break;
            }
        }
    };

    // Función para respuesta si en borrar daño
    private void borraDanoSi() {
        int numeroEstrellas = 0;

        for (Iterator<String[]> it = miArrayDanos.iterator(); it.hasNext(); ) {
            String[] fila = it.next();
            String num = estrellaActual.substring(3, 5);
            String numFila = fila[1].substring(3, 5);

            if (num.equals(numFila)) {
                numeroEstrellas++;
            }

        }
        /* SOLO QUITO LA ESTRELLA ROJA SI ES EL UNICO DAñO QUE USA ESA ESTRELLA */
        if (numeroEstrellas == 1) {
            int resID = getResources().getIdentifier(estrellaActual, "id", "com.carplus3g365");
            ImageView image = (ImageView) findViewById(resID);
            image.setVisibility(View.INVISIBLE);
        }

        cajonDano.setVisibility(View.GONE);

        for (Iterator<String[]> it = miArrayDanos.iterator(); it.hasNext(); ) {
            String[] fila = it.next();
            if (fila[0].equals(idActual)) {
                it.remove();

            }

        }
    }

    public void btnCancelarCheckinClick(View view) {
        getApplicationContext();

        Intent cancelar = new Intent(Checkin.this, MenuPrincipal.class);
        startActivity(cancelar);
        finish();
    }

    public void btnCerrarContratoClick(View btn) {
        getApplicationContext();

        int kms = 0;
        boolean equipamiento = true;

        LinearLayout contAccesorios = findViewById(R.id.linealEquipamiento);

        for (int i = 0; i < contAccesorios.getChildCount(); i++) {
            View view = contAccesorios.getChildAt(i);

            if (view instanceof ToggleButton) {
                ToggleButton b = (ToggleButton) view;

                if (!b.isChecked()) {
                    equipamiento = false;
                }
            }
        }

        Carplus3G constantes = ((Carplus3G) getApplicationContext());
        if (!equipamiento && constantes.getEmpresa().equals("marbesol")) {
            Toast.makeText(this, R.string.equip_mark_need, Toast.LENGTH_SHORT).show();
            return;
        }

        if (txtKms.getText().toString().equals("")) {
            txtKms.setText("0");
            Toast.makeText(this, R.string.chk_kms, Toast.LENGTH_SHORT).show();
        } else {
            kms = Integer.parseInt(txtKms.getText().toString());

            int kmsContrato = Integer.parseInt(kmsCoche);
            //Toast.makeText(this, kmsContrato, Toast.LENGTH_SHORT).show();

            if (kms > (kmsContrato + 3000)) {
                Toast.makeText(this, R.string.too_kms, Toast.LENGTH_SHORT).show();
                return;
            }

            if (kms <= kmsContrato) {
                txtKms.setText("0");
                Toast.makeText(this, R.string.chk_kms, Toast.LENGTH_SHORT).show();
            } else {

                jsonDanos = "{\"content\":[";
                int veces = 0;

                for (Iterator<String[]> it = miArrayDanos.iterator(); it.hasNext(); ) {
                    String[] fila = it.next();
                    if (fila[3].equals("1")) {
                        //Toast.makeText(this, fila[4],Toast.LENGTH_SHORT).show();
                        try {

                            String str = "";
                            str = "{\"cod\":\"" + fila[1] + "\",\"desc\":\"" + fila[4] + "\"}";

                            if (veces > 0) {
                                str = "," + str;
                            }
                            jsonDanos = jsonDanos + str;
                            veces++;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                jsonDanos = jsonDanos + "]}";

                //Aqui vamos a mostrar el dialog para seleccionar donde se va a quedar aparcado el vehiculo
                final View botonPulsado = btn;

                final Dialog dgParking = new Dialog(Checkin.this, R.style.FullHeightDialog);
                dgParking.setContentView(R.layout.dg_sitparking);
                dgParking.setCancelable(true);
                dgParking.setCanceledOnTouchOutside(true);

                Button btnParkingOk = (Button) dgParking.findViewById(R.id.btnAceptarSitParking);
                final Spinner spinParking = (Spinner) dgParking.findViewById(R.id.spinSitParking);

                List<CharSequence> list = new ArrayList<CharSequence>();
                list.add(getString(R.string.select_park_zone));

                Iterator<String[]> iterator = miArrayZonas.iterator();
                while (iterator.hasNext()) {
                    String[] fila = iterator.next();

                    list.add(fila[1]);
                }

                ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(Checkin.this, android.R.layout.simple_spinner_item, list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinParking.setAdapter(adapter);


                btnParkingOk.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (spinParking.getSelectedItemPosition() == 0) {
                            Toast.makeText(Checkin.this, R.string.zone_park_need, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        //Le quito uno apra que cuadre con el indice en miarrayzonas
                        zonaAparcamiento = spinParking.getSelectedItemPosition() - 1;
                        dgParking.dismiss();

                        if (!Carplus3G.cmpConexion(Checkin.this)) {
                            Carplus3G.dialogInternet(Checkin.this);
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Checkin.this);
                            builder.setCancelable(false);


                            if (getResources().getResourceEntryName(botonPulsado.getId()).equals("btnApartar")) {
                                btnPulsado = "btnApartar";
                                builder.setMessage(getString(R.string.quest_apart_contract)).setPositiveButton(getString(R.string.yes), checkinListener)
                                        .setNegativeButton(getString(R.string.not), checkinListener).show();
                            }

                            if (getResources().getResourceEntryName(botonPulsado.getId()).equals("btnCerrarContrato")) {
                                btnPulsado = "btnCerrarContrato";
                                builder.setMessage(getString(R.string.quest_close_contract)).setPositiveButton(getString(R.string.yes), checkinListener)
                                        .setNegativeButton(getString(R.string.not), checkinListener).show();
                            }
                        }
                    }
                });
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                Window window = dgParking.getWindow();
                lp.copyFrom(window.getAttributes());
                lp.width = WindowManager.LayoutParams.FILL_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                window.setAttributes(lp);
                dgParking.show();
            }
        }
    }

    DialogInterface.OnClickListener checkinListener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            getApplicationContext();


            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:

                    miDialog = new ProgressDialog(Checkin.this);

                    if (btnPulsado.equals("btnApartar")) {
                        miDialog.setTitle(getString(R.string.apart_contract_ing));
                    }

                    if (btnPulsado.equals("btnCerrarContrato")) {
                        miDialog.setTitle(getString(R.string.closing_contract));
                    }

                    miDialog.setMessage(getString(R.string.wait_demora));
                    miDialog.setCancelable(false);
                    miDialog.show();

                    Thread hilo = new Thread() {
                        public void run() {

                            litros = calculaLitros(spinOctavos.getSelectedItemPosition());

                            Carplus3G constantes = ((Carplus3G) getApplicationContext());
                            rest = new RestClient(Carplus3G.URL);
                            rest.AddParam("empresa", constantes.getEmpresa());

                            //Si es apartar mandamos la accion de apartar el contrato sino,
                            //mandamos la accion de cerrar el contrato

                            if (btnPulsado.equals("btnApartar")) {
                                rest.AddParam("accion", "apartar_contrato");
                            }

                            if (btnPulsado.equals("btnCerrarContrato")) {
                                rest.AddParam("accion", "setcheckin");
                            }

                            rest.AddParam("firma", Carplus3G.SHA256("sch"+constantes.getMatricula()));
                            rest.AddParam("contrato", contrato);
                            rest.AddParam("matricula", constantes.getMatricula());
                            rest.AddParam("id", constantes.getTerminalId());
                            rest.AddParam("km", txtKms.getText().toString());
                            rest.AddParam("ltr", String.valueOf(litros));
                            rest.AddParam("ltrOTV", spinOctavos.getSelectedItem().toString());
                            rest.AddParam("danos", jsonDanos);
                            rest.AddParam("usuario", String.valueOf(constantes.getCodUsuario()));

                            String[] zona = miArrayZonas.get(zonaAparcamiento);
                            rest.AddParam("zona", String.valueOf(zona[0]));

                            Log.v("PARAMETROS", "EMPRESA=" + constantes.getEmpresa() + "CONTRATO=" + contrato + "MATRICULA=" + constantes.getMatricula() + "KM=" + txtKms.getText().toString() + "LTR" + String.valueOf(litros) + "OTV=" + spinOctavos.getSelectedItem().toString() + "DANOS=" + jsonDanos + "USUARIO=" + String.valueOf(constantes.getCodUsuario()));

                            try {
                                rest.Execute(RequestMethod.POST);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            respuesta = rest.getResponse();
                            handlerHiloCierreContrato.post(cierreContratoBack);
                            miDialog.dismiss();
                        }

                    };
                    hilo.start();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };

    final Handler handlerHiloCierreContrato = new Handler();
    final Runnable cierreContratoBack = new Runnable() {
        public void run() {
            cerrarContrato();
        }
    };

    private void cerrarContrato() {
        String estado = "";

        Log.v("RESPUESTA", respuesta);

        if (!respuesta.equals("error")) {
            try {

                JSONObject json = new JSONObject(respuesta);

                estado = json.getString("status");

                final Dialog dialog = new Dialog(this, R.style.FullHeightDialog);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.res_cierre_contrato);
                TextView t = (TextView) dialog.findViewById(R.id.txtResCierreContrato);
                Button btn = (Button) dialog.findViewById(R.id.btnResCierreContrato);

                if (estado.equals("1")) {
                    if (btnPulsado.equals("btnApartar")) {
                        t.setText(getString(R.string.contract_apart_suc));
                    }

                    if (btnPulsado.equals("btnCerrarContrato")) {
                        t.setText(getString(R.string.contract_closed_suc));
                    }

                    t.setTextColor(Color.rgb(22, 199, 43));

                    dialog.show();

                    btn.setOnClickListener(new View.OnClickListener() {

                        public void onClick(View arg0) {
                            dialog.dismiss();
                            metodo.iraMenuPpal();
                        }
                    });

                } else {
                    t.setTextColor(Color.RED);

                    if (btnPulsado.equals("btnApartar")) {
                        t.setText(getString(R.string.cant_apart_contract));
                    }
                    if (btnPulsado.equals("btnCerrarContrato")) {
                        t.setText(getString(R.string.cant_close_contract));
                    }
                    dialog.show();
                    btn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View arg0) {
                            dialog.dismiss();
                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Carplus3G.dialogConexion(this);
        }
        miDialog.hide();
    }

    // Devuelve litros según octavos
    public String calculaLitros(int octavos) {
        float l = (float) (0.0);

        if (octavos == 0) {
            l = 0;
        }
        if (octavos == 1) {
            l = (float) (litrosDeposito * 0.125);
        }
        if (octavos == 2) {
            l = (float) (litrosDeposito * 0.250);
        }
        if (octavos == 3) {
            l = (float) (litrosDeposito * 0.375);
        }
        if (octavos == 4) {
            l = (float) (litrosDeposito * 0.500);
        }
        if (octavos == 5) {
            l = (float) (litrosDeposito * 0.625);
        }
        if (octavos == 6) {
            l = (float) (litrosDeposito * 0.750);
        }
        if (octavos == 7) {
            l = (float) (litrosDeposito * 0.875);
        }
        if (octavos == 8) {
            l = litrosDeposito;
        }
        return String.valueOf(l);
    }
}