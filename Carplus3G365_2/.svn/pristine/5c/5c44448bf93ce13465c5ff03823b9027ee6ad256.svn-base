package com.example.carplus3g365v2;


import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.hardware.Camera;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;

import java.io.File;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Carplus3G extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }


    public static String version = "1.020";

    public static String URL = "http://carplus3g.carplus365.com/android/actions_android.php";
    //public static String URL = "http://192.168.1.55/3G365/android/actions_android.php";
    //public static String URL = "http://192.168.1.55/_android/SW3G365/android/actions_android.php";

    private int lastFecha = 0;

    public void setLastDate()
    {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd", Locale.US);
        Date now = new Date();
        lastFecha = Integer.parseInt(sdfDate.format(now));
    }

    public boolean cmpLastDate()
    {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd", Locale.US);
        Date now = new Date();

        int fecha = Integer.parseInt(sdfDate.format(now));


        if (fecha != lastFecha)
        {
            return false;
        }
        else
        {
            return true;
        }
    }


    private int codUsuario = 0;

    public void setCodUsuario(int valor) {
        codUsuario = valor;
    }

    public int getCodUsuario() {
        return codUsuario;
    }


    private String nombreUsuario = "";

    public void setNombreUsuario(String valor) {
        nombreUsuario = valor;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }


    private static Boolean iniciadoSesion = false;

    public void setIniciadoSesion(Boolean valor) {
        iniciadoSesion = valor;
    }

    public Boolean getIniciadoSesion() {
        return iniciadoSesion;
    }

    private String terminalID = "";

    public void setTerminalId(String valor) {
        terminalID = valor;
    }

    public String getTerminalId() {
        return terminalID;
    }

    private String matricula = "";

    public void setMatricula(String valor) {
        matricula = valor;
    }

    public String getMatricula() {
        return matricula;
    }

    private String empresa = "";

    public void setEmpresa(String valor) {
        empresa = valor;
    }

    public String getEmpresa() {
        return empresa;
    }

    private String empresaNombre = "";

    public void setEmpresaNombre(String valor) {
        empresaNombre = valor;
    }

    public String getEmpresaNombre() {
        return empresaNombre;
    }


    private int cargadoPermisos = 0;

    private int cerrarKLMS = 0;

    public void setPermisoCerrarKLMS(int valor) {
        cerrarKLMS = valor;
    }

    public int getPermisoCerrarKLMS() {
        return cerrarKLMS;
    }


    public void setCargadoPermisos(int valor) {
        cargadoPermisos = valor;
    }

    public int getCargadoPermisos() {
        return cargadoPermisos;
    }

    private int permisoEstado = 0;

    public void setPermisoEstado(int valor) {
        permisoEstado = valor;
    }

    public int getPermisoEstado() {
        return permisoEstado;
    }

    private int permisoCerrar = 0;

    public void setPermisoCerrar(int valor) {
        permisoCerrar = valor;
    }

    public int getPermisoCerrar() {
        return permisoCerrar;
    }


    private int permisoApartar = 0;

    public void setPermisoApartar(int valor) {
        permisoApartar = valor;
    }

    public int getPermisoApartar() {
        return permisoApartar;
    }

    private int permisoRecepcionClientes = 0;

    public void setPermisoRecepcionClientes(int valor) {
        permisoRecepcionClientes = valor;
    }

    public int getPermisoRecepcionClientes() {
        return permisoRecepcionClientes;
    }

    private int permisoDanos = 0;

    public void setPermisoDanos(int valor) {
        permisoDanos = valor;
    }

    public int getPermisoDanos() {
        return permisoDanos;
    }

    private int permisoCheckin = 0;

    public void setPermisoCheckin(int valor) {
        permisoCheckin = valor;
    }

    public int getPermisoCheckin() {
        return permisoCheckin;
    }

    private int permisoListados = 0;

    public void setPermisoListados(int valor) {
        permisoListados = valor;
    }

    public int getPermisoListados() {
        return permisoListados;
    }

    private int permisoListadoEstrega = 0;

    public void setPermisoListadoEntrega(int valor) {
        permisoListadoEstrega = valor;
    }

    public int getPermisoListadoEntrega() {
        return permisoListadoEstrega;
    }

    private int permisoListadoRecogida = 0;

    public void setPermisoListadoRecogida(int valor) {
        permisoListadoRecogida = valor;
    }

    public int getPermisoListadoRecogida() {
        return permisoListadoRecogida;
    }

    private int permisoListadoAsignados = 0;

    public void setPermisoListadoAsignados(int valor) {
        permisoListadoAsignados = valor;
    }

    public int getPermisoListadoAsignados() {
        return permisoListadoAsignados;
    }

    private int permisoReservas = 0;

    public void setPermisoReservas(int valor) {
        permisoReservas = valor;
    }

    public int getPermisoReservas() {
        return permisoReservas;
    }

    private int permisoReservasFoto = 0;

    public void setPermisoReservasFoto(int valor) {
        permisoReservasFoto = valor;
    }

    public int getPermisoReservasFoto() {
        return permisoReservasFoto;
    }


    private int permisoDocumentos = 0;

    public void setPermisoDocumentos(int valor) {
        permisoDocumentos = valor;
    }

    public int getPermisoDocumentos() {
        return permisoDocumentos;
    }


    private int permisoCambioEstado = 0;

    public void setPermisoCambioEstado(int valor) {
        permisoCambioEstado = valor;
    }
    public int getPermisoCambioEstado() {
        return permisoCambioEstado;
    }

    private int permisoInspeccion = 0;

    public void setPermisoInspeccion(int valor) {
        permisoInspeccion = valor;
    }
    public int getPermisoInspeccion() {
        return permisoInspeccion;
    }

    private int permisoFirma = 0;

    public void setPermisoFirma(int valor) {
        permisoFirma = valor;
    }
    public int getPermisoFirma() {
        return permisoFirma;
    }

    private int cargadoVersion = 0;

    public void setcargadoVersion(int valor) {
        cargadoVersion = valor;
    }

    public int getcargadoVersion() {
        return cargadoVersion;
    }

    private int max_pictures = 3;

    public void setMaxPictures(int valor) {
        max_pictures = valor;
    }

    public int getMaxPictures() {
        return max_pictures;
    }

    private boolean erasePass = true;

    public boolean getErasePass() {
        return this.erasePass;
    }

    public void setErasePass(boolean erase) {
        this.erasePass = erase;
    }

    public static boolean cmpConexion(Context ctx) {
        boolean bTieneConexion = false;
        ConnectivityManager connec = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo[] redes = connec.getAllNetworkInfo();

        for (int i = 0; i < redes.length; i++) {
            if (redes[i].isAvailable() && redes[i].isConnected() && redes[i] != null) {
                bTieneConexion = true;
            }
        }

        return bTieneConexion;
    }

    /* ---------------------------------------------------------------------------------------- */
    /* FUNCION MUESTRA EL DIALOG DE LA CONEXION AL SERVIDOR */
    /* ---------------------------------------------------------------------------------------- */
    public static void dialogConexionLogin(Context ctx) {
        Handler mHandler = new Handler();
        final Dialog dialog = new Dialog(ctx, R.style.FullHeightDialog);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.sin_conexion);
        dialog.show();

        mHandler.postDelayed(new Runnable() {
            public void run() {
                dialog.dismiss();
                System.exit(0);
            }
        }, 2500);
    }

    /* ---------------------------------------------------------------------------------------- */
    /* FUNCION MUESTRA EL DIALOG DE LA CONEXION AL SERVIDOR */
    /* ---------------------------------------------------------------------------------------- */
    public static void dialogConexion(Context ctx) {
        Handler mHandler = new Handler();
        final Dialog dialog = new Dialog(ctx, R.style.FullHeightDialog);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.sin_conexion);
        dialog.show();

        //Modificacion
        dialog.dismiss();

        mHandler.postDelayed(new Runnable() {
            public void run() {
                dialog.dismiss();
            }
        }, 2500);
    }

    /* ---------------------------------------------------------------------------------------- */
    /* FUNCION MUESTRA EL DIALOG DE LA CONEXION A INTERNET */
    /* ---------------------------------------------------------------------------------------- */
    public static void dialogInternet(Context ctx) {
        Handler mHandler = new Handler();
        final Dialog dialog = new Dialog(ctx, R.style.FullHeightDialog);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.sin_internet);
        dialog.show();

        mHandler.postDelayed(new Runnable() {
            public void run() {
                dialog.dismiss();
            }
        }, 2500);
    }

    /* ---------------------------------------------------------------------------------------- */
    /* FUNCION QUE BORRA UN DIRECTORIO */
    /* ---------------------------------------------------------------------------------------- */
    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }

    public static int getSdkVersion() {
        return android.os.Build.VERSION.SDK_INT;
    }

    private static boolean checkCameraFacing(final int facing) {
        if (getSdkVersion() < Build.VERSION_CODES.LOLLIPOP) { // Cambiado a Lollipop
            return false;
        }

        final int cameraCount = Camera.getNumberOfCameras();
        Camera.CameraInfo info = new Camera.CameraInfo();
        for (int i = 0; i < cameraCount; i++) {
            Camera.getCameraInfo(i, info);
            if (facing == info.facing) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasBackFacingCamera() {
        final int CAMERA_FACING_BACK = 0;
        return checkCameraFacing(CAMERA_FACING_BACK);
    }

    public static String SHA256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
