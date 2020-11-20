package com.example.carplus3g365v2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;

import com.example.carplus3g365v2.Modelos.Methods;
import com.example.carplus3g365v2.RestClient.RequestMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Damages extends AppCompatActivity {

    // Variables de uso global
    int ANCHO_ORG = 840;
    int ALTO_ORG = 610;
    int ANCHO_PANTALLA = 0;
    int ANCHO_IMAGEN = 0;
    int ALTO_IMAGEN = 0;

    public RestClient rest;
    public String respuesta = "";

    public ProgressDialog miDialog;
    public ImageView imgAlfa;
    public RelativeLayout relDanos;
    public ScrollView scroll;
    public Carplus3G constantes;
    public InputMethodManager imm;
    float touchInitialTime = 0;
    float touchCurrentTime = 0;
    public View danoActual;
    public String jsonDanos = "";
    public int contadorImagenesAdd = 0;
    public ArrayList<String[]> miArray = new ArrayList<String[]>();

    private Methods metodo;

    float xNuevo;
    float yNuevo;
    int tipoNuevo;
    float anchoImg = 0;
    float altoImg = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_damages);

        // Instanciamos los elementos de la actividad
        ImageButton menu_ppal = findViewById(R.id.menuPpalDAM);
        ImageButton salir = findViewById(R.id.salirDAM);

        metodo = new Methods(Damages.this);

        // Escuchadores de los botones
        menu_ppal.setOnClickListener(v -> metodo.iraMenuPpal());
        salir.setOnClickListener(v -> metodo.cerrarSesion());

        // Creamos la pantalla
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        ANCHO_PANTALLA = metrics.widthPixels;

        ANCHO_IMAGEN = ANCHO_PANTALLA;
        ALTO_IMAGEN = (ALTO_ORG * ANCHO_PANTALLA) / ANCHO_ORG;

        imgAlfa = findViewById(R.id.imgAlfaRomeo);
        relDanos = findViewById(R.id.relDanos);
        scroll = findViewById(R.id.scrollDamages);

        imgAlfa.getLayoutParams().height = ALTO_IMAGEN;
        imgAlfa.getLayoutParams().width = ANCHO_IMAGEN;

        // Convertirmos los dp a pixeles
        final float scale = getBaseContext().getResources().getDisplayMetrics().density;
        anchoImg = 840;
        altoImg = 610;

        imgAlfa.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent m) {

                float x = 0;
                float y = 0;
                float valorScroll = scroll.getScrollY() - 20;
                if (valorScroll < 0) {
                    valorScroll = 0;
                }

                //Se que la imagen tiene 16px entonces convierto eso DIP
                final int imgSize = (int) (24 * scale + 0.5f) / 2;

                switch (m.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        touchInitialTime = m.getDownTime();
                        break;
                    case MotionEvent.ACTION_UP:
                        touchCurrentTime = m.getEventTime();

                        x = m.getX();
                        y = m.getY();

                        final float x1 = x;
                        final float y1 = y;

                        if ((touchCurrentTime - touchInitialTime) > 350)
                        {
                            final Dialog dialog = new Dialog(Damages.this, R.style.FullHeightDialog);
                            dialog.setContentView(R.layout.add_danos_marbesol);

                            final LinearLayout roce = dialog.findViewById(R.id.l_roce);
                            final LinearLayout golpe = dialog.findViewById(R.id.l_golpe);
                            final LinearLayout aranazo = dialog.findViewById(R.id.l_aranazo);
                            final LinearLayout quemado = dialog.findViewById(R.id.l_quemado);
                            final LinearLayout roto = dialog.findViewById(R.id.l_roto);
                            final Button btnCancelarDano = dialog.findViewById(R.id.btnCancelarDanoMarbesol);

                            roce.setOnClickListener(v1 -> {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                                pinta_dano(x1, y1, 1);
                            });
                            golpe.setOnClickListener(v12 -> {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                                pinta_dano(x1, y1, 2);
                            });
                            aranazo.setOnClickListener(v13 -> {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                                pinta_dano(x1, y1, 3);
                            });
                            quemado.setOnClickListener(v14 -> {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                                pinta_dano(x1, y1, 4);
                            });
                            roto.setOnClickListener(v15 -> {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                                pinta_dano(x1, y1, 5);
                            });

                            btnCancelarDano.setOnClickListener(btn -> {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                            });

                            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                            Window window = dialog.getWindow();
                            lp.copyFrom(window.getAttributes());
                            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                            window.setAttributes(lp);
                            dialog.show();
                        }
                        break;
                }
                return true;
            }
        });
    }

    public void onStart() {
        super.onStart();

        Carplus3G constantes = ((Carplus3G) getApplicationContext());
        if (!constantes.cmpLastDate()) {
            constantes.setLastDate();
            constantes.setcargadoVersion(0);
            Intent i = new Intent(Damages.this,Login.class);
            startActivity(i);
            finish();
            return;
        }

        if (!Carplus3G.cmpConexion(this)) {
            Carplus3G.dialogInternet(this);

            Bundle b = new Bundle();
            b.putString("tipo", "marbesol");
            Intent i = new Intent(this,Scan.class);
            i.putExtras(b);
            startActivity(i);
            finish();
        } else {
            miDialog = new ProgressDialog(this);
            miDialog.setMessage(getString(R.string.getting_info_state_veh));
            miDialog.setTitle(getString(R.string.msginit));
            miDialog.setCancelable(false);
            miDialog.show();

            Thread hilo = new Thread() {
                public void run() {
                    Carplus3G constantes = ((Carplus3G) getApplicationContext());
                    rest = new RestClient(Carplus3G.URL);
                    rest.AddParam("empresa", constantes.getEmpresa());
                    rest.AddParam("accion", "get_danos_marbesol");
                    rest.AddParam("firma", Carplus3G.SHA256("gdm" + constantes.getMatricula()));
                    rest.AddParam("matricula", constantes.getMatricula());
                    rest.AddParam("id", constantes.getTerminalId());
                    rest.AddParam("usuario", String.valueOf(constantes.getCodUsuario()));

                    try {
                        rest.Execute(RequestMethod.POST);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    respuesta = rest.getResponse();
                    handlerHiloInicio.post(InicioBack);
                    miDialog.dismiss();
                }

            };
            hilo.start();
        }
    }

    final Handler handlerHiloInicio = new Handler();
    final Runnable InicioBack = new Runnable() {
        public void run() {
            ProcesarHilo();
        }
    };

    private void ProcesarHilo() {

        String estado = "";
        String mensaje = "";
        int croquis = 99;

        if (!respuesta.equals("error")) {

            try {
                JSONObject json = new JSONObject(respuesta);
                estado = json.getString("status");
                mensaje = json.getString("statusMsg");
                croquis = json.getInt("croquis");

                if (estado.equals("1") || estado.equals("2")) {

                    //Existe la matricula
                    if (estado.equals("1")) {

                        if (croquis == 99) {
                            Toast.makeText(this, R.string.cant_find_croquis, Toast.LENGTH_SHORT).show();
                            // Pasamos el parámetro para la seleccion de ruta en Scan.java
                            Bundle b = new Bundle();
                            b.putString("tipo", "marbesol");

                            Intent i = new Intent(this,Scan.class);
                            i.putExtras(b);
                            startActivity(i);
                            this.finish();
                        } else {
                            int resID = getResources().getIdentifier("plano"+ croquis, "drawable" ,Damages.this.getPackageName());
                            imgAlfa.setBackground(ResourcesCompat.getDrawable(getResources(),resID,null));

                            JSONArray array = new JSONArray(json.getString("content"));
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject row = array.getJSONObject(i);
                                Drawable d = null;

                                ImageView img = new ImageView(Damages.this);
                                img.setId(Integer.parseInt(row.getString("o")));
                                img.setTag(row.getString("o"));
                                img.setAdjustViewBounds(true);
                                img.setScaleType(ImageView.ScaleType.FIT_XY);

                                switch (Integer.parseInt(row.getString("t"))) {
                                    case 1:
                                        img.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.dano1, null));
                                        d = ResourcesCompat.getDrawable(getResources(), R.drawable.dano1, null);
                                        break;
                                    case 2:
                                        img.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.dano2, null));
                                        d = ResourcesCompat.getDrawable(getResources(), R.drawable.dano2, null);
                                        break;
                                    case 3:
                                        img.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.dano3, null));
                                        d = ResourcesCompat.getDrawable(getResources(), R.drawable.dano3, null);
                                        break;
                                    case 4:
                                        img.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.dano4, null));
                                        d = ResourcesCompat.getDrawable(getResources(), R.drawable.dano4, null);
                                        break;
                                    case 5:
                                        img.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.dano5, null));
                                        d = ResourcesCompat.getDrawable(getResources(), R.drawable.dano5, null);
                                        break;
                                }

                                int medidaImagen = d.getIntrinsicWidth();
                                img.setMaxHeight(medidaImagen);
                                img.setMaxWidth(medidaImagen);

                                float x = Integer.parseInt(row.getString("x"));
                                float y = Integer.parseInt(row.getString("y"));

                                //ESCALAMOS POSICION DE LA X e Y
                                float xf = 0;
                                float yf = 0;

                                xf = (ANCHO_IMAGEN * x) / ANCHO_ORG;
                                yf = (ALTO_IMAGEN * y) / ALTO_ORG;

                                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                                lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                                lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                                lp.setMargins((int) (xf) - (medidaImagen / 2), (int) (yf) - (medidaImagen / 2), 0, 0);
                                img.setLayoutParams(lp);
                                img.setOnLongClickListener(new View.OnLongClickListener() {

                                    public boolean onLongClick(View v) {
                                        danoActual = v;
                                        onLongClickDano();
                                        return false;
                                    }
                                });
                                relDanos.addView(img);
                            }
                        }
                    }

                    //No existe la matricula
                    if (estado.equals("2")) {
                        Toast.makeText(this, R.string.car_plate_noexist2, Toast.LENGTH_SHORT).show();

                        Bundle b = new Bundle();
                        b.putString("tipo", "marbesol");

                        Intent i = new Intent(this, Scan.class);
                        i.putExtras(b);
                        startActivity(i);
                        this.finish();
                    }
                } else {
                    Toast.makeText(this, getString(R.string.error_happened) + ": " + mensaje, Toast.LENGTH_LONG).show();

                    Bundle b = new Bundle();
                    b.putString("tipo", "marbesol");

                    Intent i = new Intent(this, Scan.class);
                    i.putExtras(b);
                    startActivity(i);
                    this.finish();
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        /* DESACTIVAMOS EL BOTON VOLVER DEL DISPOSITIVO */
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }

        return false;

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

    // Dialog para confirmar eliminación del daño
    private void onLongClickDano() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage(getString(R.string.delete_damage)).setPositiveButton(getString(R.string.yes), dialogClickListenerBorraDano)
                .setNegativeButton(getString(R.string.not), dialogClickListenerBorraDano).show();
    }

    // Escuchador para eliminar el daño
    DialogInterface.OnClickListener dialogClickListenerBorraDano = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            getApplicationContext();

            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:

                    miDialog = new ProgressDialog(Damages.this);
                    miDialog.setTitle(getString(R.string.deleting_damage));
                    miDialog.setMessage(getString(R.string.wait_demora));
                    miDialog.setCancelable(false);
                    miDialog.show();

                    Thread hilo = new Thread() {
                        public void run() {
                            Carplus3G constantes = ((Carplus3G) getApplicationContext());
                            rest = new RestClient(Carplus3G.URL);
                            rest.AddParam("empresa", constantes.getEmpresa());
                            rest.AddParam("accion", "del_danos_marbesol");
                            rest.AddParam("firma", Carplus3G.SHA256("ddm" + constantes.getMatricula()));
                            rest.AddParam("matricula", constantes.getMatricula());
                            rest.AddParam("id", constantes.getTerminalId());
                            rest.AddParam("identificador", String.valueOf(danoActual.getTag()));
                            rest.AddParam("usuario", String.valueOf(constantes.getCodUsuario()));

                            try {
                                rest.Execute(RequestMethod.POST);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            respuesta = rest.getResponse();
                            handlerHiloMarbesolDel.post(MarbesolDelBack);
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

    // Manejador del hilo de cierre del contrato
    final Handler handlerHiloMarbesolDel = new Handler();

    // Asignamos función trás el hilo
    final Runnable MarbesolDelBack = new Runnable() {
        public void run() {
            borrarDano();
        }
    };

    // Función para borrar el daño
    public void borrarDano() {
        String estado = "";
        String mensaje = "";

        if (!respuesta.equals("error")) {

            try {
                JSONObject json = new JSONObject(respuesta);

                estado = json.getString("status");
                mensaje = json.getString("statusMsg");

                if (estado.equals("1")) {
                    relDanos.removeView(danoActual);
                } else {
                    Toast.makeText(this, R.string.cant_delete_damage, Toast.LENGTH_LONG).show();
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

    // Pintamos la imagen en la posicion
    public void pinta_dano(float x, float y, int tipo) {
        xNuevo = x;
        yNuevo = y;
        tipoNuevo = tipo;

        //Antes de enviar los datos debo escalarlos a la resolucion original 840 x 610
        final Integer xf = (int) ((ANCHO_ORG * x) / ANCHO_IMAGEN);
        final Integer yf = (int) ((ALTO_ORG * y) / ALTO_IMAGEN);

        if (!Carplus3G.cmpConexion(Damages.this)) {
            Carplus3G.dialogInternet(Damages.this);
        } else {
            miDialog = new ProgressDialog(Damages.this);
            miDialog.setTitle(getString(R.string.sending_data_serv));
            miDialog.setMessage(getString(R.string.wait_demora));
            miDialog.setCancelable(false);
            miDialog.show();

            // Subida al servidor de los datos del daño
            Thread hilo = new Thread() {
                public void run() {
                    Carplus3G constantes = ((Carplus3G) getApplicationContext());
                    rest = new RestClient(Carplus3G.URL);
                    rest.AddParam("empresa", constantes.getEmpresa());
                    rest.AddParam("accion", "set_danos_marbesol");
                    rest.AddParam("firma", Carplus3G.SHA256("sdm" + constantes.getMatricula()));
                    rest.AddParam("matricula", constantes.getMatricula());
                    rest.AddParam("id", constantes.getTerminalId());
                    rest.AddParam("ancho", String.valueOf(ANCHO_IMAGEN));
                    rest.AddParam("alto", String.valueOf(ALTO_IMAGEN));
                    rest.AddParam("x", String.valueOf(xf));
                    rest.AddParam("y", String.valueOf(yf));
                    rest.AddParam("tipo", String.valueOf(tipoNuevo));
                    rest.AddParam("usuario", String.valueOf(constantes.getCodUsuario()));

                    try {
                        rest.Execute(RequestMethod.POST);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    respuesta = rest.getResponse();
                    handlerHiloMarbesolAdd.post(MarbesolAddBack);
                    miDialog.dismiss();
                }

            };
            hilo.start();
        }
    }

    // Manejador para el hilo del cierre del contrato
    final Handler handlerHiloMarbesolAdd = new Handler();

    // Asignamos función
    final Runnable MarbesolAddBack = new Runnable() {
        public void run() {
            anadirDano();
        }
    };

    // Función añadir daño
    public void anadirDano() {
        String estado = "";
        String mensaje = "";

        if (!respuesta.equals("error")) {

            try {
                JSONObject json = new JSONObject(respuesta);
                estado = json.getString("status");
                mensaje = json.getString("statusMsg");

                if (estado.equals("1")) {
                    Drawable d = null;
                    ImageView img = new ImageView(Damages.this);
                    img.setAdjustViewBounds(true);
                    img.setScaleType(ImageView.ScaleType.FIT_XY);
                    img.setTag(json.getString("content"));

                    switch (tipoNuevo) {
                        case 1:
                            img.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.dano1, null));
                            d = ResourcesCompat.getDrawable(getResources(), R.drawable.dano1, null);
                            break;
                        case 2:
                            img.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.dano2, null));
                            d = ResourcesCompat.getDrawable(getResources(), R.drawable.dano2, null);
                            break;
                        case 3:
                            img.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.dano3, null));
                            d = ResourcesCompat.getDrawable(getResources(), R.drawable.dano3, null);
                            break;
                        case 4:
                            img.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.dano4, null));
                            d = ResourcesCompat.getDrawable(getResources(), R.drawable.dano4, null);
                            break;
                        case 5:
                            img.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.dano5, null));
                            d = ResourcesCompat.getDrawable(getResources(), R.drawable.dano5, null);
                            break;
                    }

                    int medidaImagen = d.getIntrinsicWidth();
                    img.setMaxHeight(medidaImagen);
                    img.setMaxWidth(medidaImagen);

                    // Escalamos la posición de la X e Y
                    float xf = 0;
                    float yf = 0;

                    xf = xNuevo;
                    yf = yNuevo;

                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                    lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    lp.setMargins((int) (xf) - (medidaImagen / 2), (int) (yf) - (medidaImagen / 2), 0, 0);
                    img.setLayoutParams(lp);

                    img.setOnLongClickListener(new View.OnLongClickListener() {
                        public boolean onLongClick(View v) {
                            danoActual = v;
                            onLongClickDano();
                            return false;
                        }
                    });
                    relDanos.addView(img);
                } else { }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            Carplus3G.dialogConexion(this);
        }
        miDialog.hide();
    }
}