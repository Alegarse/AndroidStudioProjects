package com.example.carplus3g365v2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carplus3g365v2.Adapters.serviciosAdapter;
import com.example.carplus3g365v2.Modelos.Methods;
import com.example.carplus3g365v2.Modelos.Service;
import com.example.carplus3g365v2.RestClient.RequestMethod;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MenuPrincipal extends AppCompatActivity {

    private RecyclerView listadoServicios;
    private ArrayList<Service> servicios;
    private serviciosAdapter servicioAdapter;
    private LinearLayoutManager llm;
    private TextView tituloServicio, descServicio;
    private ImageView imgServicio;

    private Methods metodo;

    // Variables de permisos
    private int permiso_cambioestado, permiso_reservas, permiso_estado, permiso_checkin, permiso_listados;
    private int permiso_danos, permiso_documentos, permiso_inspeccion, permiso_firma;

    // Variable de seleccion de función
    private int codigoFuncion = 0;

    public ProgressDialog miDialog;
    public RestClient rest;
    public String respuesta = "";
    private Carplus3G constantes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        // Solicitamos los permisos necesarios
        final int TODOS_PERMISOS = 1;
        String[] permisos = {
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };
        if (!tienePermisos(this, permisos)) {
            ActivityCompat.requestPermissions(this, permisos, TODOS_PERMISOS);
        }

        // Instanciamos elementos
        listadoServicios = findViewById(R.id.servDisponibles);
        tituloServicio = findViewById(R.id.tituloServicio);
        descServicio = findViewById(R.id.descServicio);
        imgServicio = findViewById(R.id.imageServicio);

        metodo = new Methods(MenuPrincipal.this);
        ImageButton salir = findViewById(R.id.salir);
        salir.setOnClickListener(v -> metodo.cerrarSesion());

        // Cargamos el RecyclerView de servicios
        listadoServicios.setLayoutManager(new LinearLayoutManager(this));

        servicios = new ArrayList<Service>();

        // RecyclerView Vertical
        llm = new LinearLayoutManager(MenuPrincipal.this);
        llm.setOrientation(RecyclerView.VERTICAL);
        listadoServicios.setLayoutManager(llm);

        servicioAdapter = new serviciosAdapter(getApplicationContext(),servicios);
        servicioAdapter.setServicios(servicios);
        servicioAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Service serv = servicios.get(listadoServicios.getChildAdapterPosition(view));
                goTo(serv);

            }
        });
        listadoServicios.setAdapter(servicioAdapter);

    }

    private void goTo(Service servicio) {
        String nombreServicio = servicio.getTitulo();
        Intent funcion = new Intent();
        Bundle b = new Bundle();
        int ejecutaActividad = 1;
        switch (nombreServicio) {
            case "Reservas":
                funcion = new Intent(MenuPrincipal.this, Reservar.class);
                break;
            case "Cambio de estado":
                funcion = new Intent(MenuPrincipal.this, CambioEstado.class);
                break;
            case "Estado":
                b.putString("tipo", "estado");
                funcion = new Intent(MenuPrincipal.this, Scan.class);
                funcion.putExtras(b);
                break;
            case "Listados":
                funcion = new Intent(MenuPrincipal.this, Listados.class);
                break;
            case "Daños":
                b.putString("tipo", "marbesol");
                funcion = new Intent(MenuPrincipal.this, Scan.class);
                funcion.putExtras(b);
                break;
            case "Escáner":
                funcion = new Intent(MenuPrincipal.this, EscaneoDocumentos.class);
                break;
            case "Check-In":
                b.putString("tipo", "checkin");
                funcion = new Intent(MenuPrincipal.this, Scan.class);
                funcion.putExtras(b);
                break;
            case "Inspección":
                funcion = new Intent (MenuPrincipal.this, Inspect.class);
                break;
            case "Firmar":
                funcion = new Intent(MenuPrincipal.this,Firmar.class);
                break;
            case "Cambio de vehículo":
                funcion = new Intent(MenuPrincipal.this, CambioVehiculo.class);
                break;
            case "Escaner":
            case "Firmas":
            case "Inspeccion":
                ejecutaActividad = 0;
                Toast.makeText(this,R.string.addOption,Toast.LENGTH_LONG).show();
                break;
        }
        if (ejecutaActividad == 1) { startActivity(funcion); }
    }

    @Override
    protected void onStart() {
        super.onStart();

        constantes = ((Carplus3G) getApplicationContext());
        if (!constantes.cmpLastDate()) {
            constantes.setLastDate();
            constantes.setcargadoVersion(0);
            Intent desconexion = new Intent(MenuPrincipal.this, Login.class);
            startActivity(desconexion);
            MenuPrincipal.this.finish();
            return;
        }

        int primeraVez = constantes.getCargadoPermisos();

        if (primeraVez == 1) //Ya hemos cargado los permisos
        {
            permiso_cambioestado = constantes.getPermisoCambioEstado();
            permiso_reservas = constantes.getPermisoReservas();
            permiso_estado = constantes.getPermisoEstado();
            permiso_checkin = constantes.getPermisoCheckin();
            permiso_listados = constantes.getPermisoListados();
            permiso_danos = constantes.getPermisoDanos();
            permiso_documentos = constantes.getPermisoDocumentos();
            permiso_inspeccion = constantes.getPermisoInspeccion();
            permiso_firma = constantes.getPermisoFirma();
            cargarServicios();

        } else {
            if (!Carplus3G.cmpConexion(this)) {
                Carplus3G.dialogInternet(this);
            } else {
                miDialog = new ProgressDialog(this);
                miDialog.setMessage(getString(R.string.msgserv));
                miDialog.setTitle(getString(R.string.checkpermisos));
                miDialog.setCancelable(false);
                miDialog.show();

                Thread hilo = new Thread() {
                    public void run() {
                        miDialog.dismiss();
                        Carplus3G constantes = ((Carplus3G) getApplicationContext());
                        rest = new RestClient(Carplus3G.URL);
                        rest.AddParam("empresa", constantes.getEmpresa());
                        rest.AddParam("firma", Carplus3G.SHA256("per" + constantes.getEmpresa()));
                        rest.AddParam("accion", "permisos");
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
    }

    final Handler handlerHiloInicio = new Handler();
    final Runnable btnEnviarBack = new Runnable() {
        public void run() {
            ProcesarHilo();
        }
    };
    private void ProcesarHilo() {
        constantes = ((Carplus3G) getApplicationContext());

        String estado = "";
        String mensaje = "";

        if (!respuesta.equals("error")) {
            try {

                JSONObject json = new JSONObject(respuesta);

                estado = json.getString("status");
                mensaje = json.getString("statusMsg");

                if (estado.equals("1")) {
                    JSONObject contenido = json.getJSONObject("content");
                    permiso_cambioestado = contenido.getInt("cambioestado");
                    permiso_reservas = contenido.getInt("reservas");
                    int permiso_reservasfoto = contenido.getInt("reservasfoto");
                    permiso_estado = contenido.getInt("estado");
                    permiso_checkin = contenido.getInt("checkin");
                    permiso_listados = contenido.getInt("listados");
                    int permiso_listado_entrega = contenido.getInt("listado_entrega");
                    int permiso_listado_recogida = contenido.getInt("listado_recogida");
                    int permiso_listado_asignados = contenido.getInt("listado_asignados");
                    int permiso_listado_recepcion = contenido.getInt("listado_recepcion");
                    permiso_inspeccion = contenido.getInt("inspeccion");
                    permiso_firma = contenido.getInt("firma");
                    int permiso_apartar = contenido.getInt("apartar");
                    int permiso_cerrar = contenido.getInt("cerrar");
                    int permiso_danos = contenido.getInt("danos");
                    permiso_documentos = contenido.getInt("documentos");
                    int permiso_cerrarKLMS = contenido.getInt("cerrar_con_kilometros");
                    int max_fotos = contenido.getInt("max_pictures");

                    constantes.setCargadoPermisos(1);
                    constantes.setPermisoCambioEstado(permiso_cambioestado);
                    constantes.setPermisoReservas(permiso_reservas);
                    constantes.setPermisoReservasFoto(permiso_reservasfoto);
                    constantes.setPermisoEstado(permiso_estado);
                    constantes.setPermisoCheckin(permiso_checkin);
                    constantes.setPermisoListados(permiso_listados);
                    constantes.setPermisoListadoRecogida(permiso_listado_recogida);
                    constantes.setPermisoListadoEntrega(permiso_listado_entrega);
                    constantes.setPermisoListadoAsignados(permiso_listado_asignados);
                    constantes.setPermisoRecepcionClientes(permiso_listado_recepcion);
                    constantes.setPermisoDanos(permiso_danos);
                    constantes.setPermisoApartar(permiso_apartar);
                    constantes.setPermisoCerrar(permiso_cerrar);
                    constantes.setPermisoCerrarKLMS(permiso_cerrarKLMS);
                    constantes.setPermisoDocumentos(permiso_documentos);
                    constantes.setPermisoInspeccion(permiso_inspeccion);
                    constantes.setPermisoFirma(permiso_firma);
                    constantes.setMaxPictures(max_fotos);

                    cargarServicios();

                } else {
                    Toast.makeText(this, R.string.produerror + mensaje, Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            Carplus3G.dialogConexion(this);
        }
        miDialog.dismiss();
        // Informamos al RecyclerView del cambio de datos
        servicioAdapter.notifyDataSetChanged();
    }


    private void cargarServicios() {

        // Creamos los servicios en activo
        Service reservas = new Service(getString(R.string.booking),getString(R.string.descbooking),R.drawable.reservas);
        Service cambEsta = new Service(getString(R.string.changestate),getString(R.string.descchangestate),R.drawable.cambioestado);
        Service estado = new Service(getString(R.string.state),getString(R.string.descstate),R.drawable.estado);
        Service listados = new Service(getString(R.string.lists),getString(R.string.desclists),R.drawable.listados);
        Service danos = new Service(getString(R.string.damage),getString(R.string.descdamage),R.drawable.danosmarbesol);
        Service escaner = new Service(getString(R.string.scan),getString(R.string.descscan),R.drawable.documentos);
        Service checkin = new Service(getString(R.string.checkin),getString(R.string.desccheckin),R.drawable.checking);
        Service inspeccion = new Service(getString(R.string.inspect),getString(R.string.descinspect),R.drawable.inspeccion);
        Service firmar = new Service(getString(R.string.to_sign),getString(R.string.descsign),R.drawable.firmar);
        Service cambiovehiculo = new Service(getString(R.string.changevehicle),getString(R.string.descchangevehicle),R.drawable.cambiocar);

        // Creamos los servicios como deshabilitados (Llevarán un toast de publicidad
        Service escaner_des = new Service("Escaner",getString(R.string.descscan),R.drawable.documentos_des);
        Service firmar_des = new Service("Firmas",getString(R.string.descsign),R.drawable.firmar_des);
        Service inspeccion_des = new Service("Inspeccion",getString(R.string.descinspect),R.drawable.inspeccion_des);

        // Cargamos los servicios al Array para la carga por el RecyclerView
        // Primero cargamos los que tiene permiso siempre
        servicios.add(reservas);
        servicios.add(cambEsta);
        servicios.add(estado);
        servicios.add(listados);
        servicios.add(danos);
        if (permiso_documentos == 1) {
            servicios.add(escaner);
        } else {
            servicios.add(escaner_des);
        }
        // Deshabilitado el 23-03-2017 por no usarse ya (Anterior app)
        //servicios.add(checkin);
        if (permiso_inspeccion == 1) {
            servicios.add(inspeccion);
        } else {
            servicios.add(inspeccion_des);
        }
        if (permiso_firma == 1) {
            servicios.add((firmar));
        } else {
            servicios.add(firmar_des);
        }
        // Deshabilitado en App original
        //servicios.add(cambiovehiculo);
    }

    public static boolean tienePermisos(Context context, String... permisos) {
        if (context != null && permisos != null) {
            for (String permiso : permisos) {
                if (ActivityCompat.checkSelfPermission(context, permiso) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}