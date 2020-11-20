package com.example.carplus3g365v2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carplus3g365v2.Modelos.Methods;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class Firmar extends AppCompatActivity {

    private Methods metodo;

    public asyncGetContrato tareaGetContrato;
    public asyncSaveFirma tareaSaveFirma;

    public RestClient conexionHTTP;
    public Carplus3G CF;
    public String JSONDATA;

    public ProgressDialog miDialog;

    public EditText txtNumDocumento;
    public LinearLayout firmaCliente;
    public Button btnLocalizar, btnLimpiar, btnAcepto, btnNoAcepto, btnLimpiarFirmaCliente;
    public TextView lblNombreCliente,lblOcupacion,txtOcupacion,lblKilometros,txtKilometros;
    public TextView lblDescuento,txtDescuento,lblExtras,txtExtras,lblCombustible,txtCombustible;
    public TextView lblFianza, txtFianza;
    public TextView lblTotal,txtTotal,lblCondiciones,lblObservaciones;
    public TextView txtObservaciones;

    private TextView tituloFirma, tituloResumen;

    public String txtFirmar1, txtFirmar2, txtFirmar3, txtFirmar4, txtFirmar5, txtFirmar6;

    public Bitmap base64bitmap = null;

    boolean HA_FIRMADO_CLIENTE = false;

    private Paint mmPaint;
    private MaskFilter mmEmboss;
    private MaskFilter mmBlur;

    public String cliente,email,idioma,ocupacion,kilometros,descuento,extras,gasolina,fianza,total,firma;
    public String observaciones;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firmar);

        // Instanciamos botones superiores
        // Variables de los botones superiores
        ImageButton menu_ppal = findViewById(R.id.menuPpalF);
        ImageButton salir = findViewById(R.id.salirF);

        metodo = new Methods(Firmar.this);

        txtFirmar1 = getString(R.string.txtFirmar1);
        txtFirmar2 = getString(R.string.txtFirmar2);
        txtFirmar3 = getString(R.string.txtFirmar3);
        txtFirmar4 = getString(R.string.to_finalice);
        txtFirmar5 = getString(R.string.cancel);
        txtFirmar6 = getString(R.string.txtFirmar6);

        // Escuchadores de los botones
        salir.setOnClickListener(v -> metodo.cerrarSesion());
        menu_ppal.setOnClickListener(v -> metodo.iraMenuPpal());

        conexionHTTP = new RestClient(Carplus3G.URL);

        tituloResumen = findViewById(R.id.tituloResumen);
        tituloFirma = findViewById(R.id.tituloFirma);

        txtNumDocumento = findViewById(R.id.txtNumeroDocumento);
        firmaCliente = findViewById(R.id.firmaCliente);
        btnLocalizar = findViewById(R.id.btnLocalizarDocumento);
        btnLimpiar = findViewById(R.id.btnLimpiarDocumento);
        btnAcepto = findViewById(R.id.btnAcepto);
        btnNoAcepto = findViewById(R.id.btnNoAcepto);
        btnLimpiarFirmaCliente = findViewById(R.id.btnLimpiarFirma);


        lblNombreCliente = findViewById(R.id.lblNombreCliente);
        lblOcupacion = findViewById(R.id.lblOcupacion);
        txtOcupacion = findViewById(R.id.txtOcupacion);
        lblKilometros = findViewById(R.id.lblKilometros);
        txtKilometros = findViewById(R.id.txtKilometros);
        lblDescuento = findViewById(R.id.lblDescuento);
        txtDescuento = findViewById(R.id.txtDescuento);
        lblExtras = findViewById(R.id.lblExtras);
        txtExtras = findViewById(R.id.txtExtras);
        lblCombustible = findViewById(R.id.lblCombustible);
        txtCombustible = findViewById(R.id.txtCombustible);
        lblFianza = findViewById(R.id.lblFianza);
        txtFianza = findViewById(R.id.txtFianza);
        lblTotal = findViewById(R.id.lblTotal);
        txtTotal = findViewById(R.id.txtTotal);
        lblCondiciones = findViewById(R.id.lblCondiciones);
        lblObservaciones = findViewById(R.id.lblObservaciones);
        txtObservaciones = findViewById(R.id.txtObservaciones);

        btnLimpiar.setVisibility(View.GONE);

        firmaCliente.bringToFront();

        inicializarFirma(firmaCliente);

        btnLocalizar.setOnClickListener(view -> {
            if (txtNumDocumento.getText().toString().trim().equals("")) {
                Toast.makeText(getApplicationContext(), R.string.contract_number_required, Toast.LENGTH_SHORT).show();
                return;
            }
            tareaGetContrato = new asyncGetContrato();
            tareaGetContrato.execute(null, null, null);
        });

        btnLimpiar.setOnClickListener(view -> fn_Limpiar());

        btnAcepto.setOnClickListener(view -> {
            if (!HA_FIRMADO_CLIENTE) {
                Toast.makeText(getApplicationContext(), txtFirmar1, Toast.LENGTH_SHORT).show();
                return;
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(Firmar.this);
            builder.setCancelable(false);
            builder.setMessage(txtFirmar2).setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    try {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Firmar.this);
                        builder.setTitle(txtFirmar3);


                        final EditText input = new EditText(Firmar.this);
                        input.setText(email);
                        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                        builder.setView(input);

                        builder.setPositiveButton(txtFirmar4, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                email  = input.getText().toString();
                                dialog.cancel();

                                tareaSaveFirma = new asyncSaveFirma();
                                tareaSaveFirma.execute(null, null, null);
                            }
                        });
                        builder.setNegativeButton(txtFirmar5, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        builder.show();
                    } catch (Exception e) {
                        // Usado para capturar la excepción solamente
                    }
                }
            }).setNegativeButton(getString(R.string.not), (dialogInterface, i) -> {
            }).show();

        });

        btnNoAcepto.setOnClickListener(view -> fn_Limpiar());

        btnLimpiarFirmaCliente.setOnClickListener(view -> inicializarFirma(firmaCliente));

    }

    // Método para deshabilitar el botón atrás del movil
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return false;
    }

    public void inicializarFirma(LinearLayout l) {
        if (l == firmaCliente) {
            HA_FIRMADO_CLIENTE = false;
        }
        l.removeAllViews();

        Paint mmPaint = new Paint();
        mmPaint.setAntiAlias(true);
        mmPaint.setDither(true);
        mmPaint.setColor(Color.BLUE);
        mmPaint.setStyle(Paint.Style.STROKE);
        mmPaint.setStrokeJoin(Paint.Join.BEVEL);
        mmPaint.setStrokeCap(Paint.Cap.BUTT);
        mmPaint.setStrokeWidth(2.5f);

        mmEmboss = new EmbossMaskFilter(new float[]{1, 1, 1}, 0.4f, 6, 3.5f);

        mmBlur = new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL);

        FingerPaint s = new FingerPaint(getApplicationContext(), mmPaint, l);
        s.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        s.setBackgroundColor(Color.TRANSPARENT);
        l.addView(s);

        l.setBackground(null);
        l.setBackgroundColor(Color.WHITE);
    }

    private String fn_ConviertePngJpeg(String base64origen) {
        String base64destino = "";

        byte[] bytes;
        Bitmap bmpFirma;
        ByteArrayOutputStream byteArrayOutputStream;
        byte[] byteArray;

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inJustDecodeBounds = false;
        bitmapOptions.inSampleSize = 1;
        bitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565;

        byteArrayOutputStream = new ByteArrayOutputStream();
        bytes = Base64.decode(base64origen, 0);
        bmpFirma = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, bitmapOptions);
        bmpFirma.compress(Bitmap.CompressFormat.JPEG, 91, byteArrayOutputStream);
        byteArray = byteArrayOutputStream.toByteArray();
        base64destino = Base64.encodeToString(byteArray, Base64.DEFAULT);

        return base64destino;
    }

    public void fn_PrintDatos() {

        if (!firma.isEmpty())
        {
            Toast.makeText(Firmar.this,R.string.contract_already_signed,Toast.LENGTH_SHORT).show();
            fn_Limpiar();
            return;
        }

        fn_Traducir(idioma);

        lblNombreCliente.setText(cliente);
        txtOcupacion.setText(ocupacion);
        txtKilometros.setText(kilometros);
        txtDescuento.setText(descuento);
        txtExtras.setText(extras);
        txtCombustible.setText(gasolina);
        txtFianza.setText(fianza);
        txtTotal.setText(total);
        txtObservaciones.setText(observaciones);

        txtNumDocumento.setEnabled(false);
        btnLimpiar.setVisibility(View.VISIBLE);
        btnLocalizar.setVisibility(View.GONE);
    }

    public void fn_Limpiar() {
        txtNumDocumento.setEnabled(true);
        txtNumDocumento.setText("");

        btnLimpiar.setVisibility(View.GONE);
        btnLocalizar.setVisibility(View.VISIBLE);

        cliente = "";
        email = "";
        ocupacion = "";
        kilometros = "";
        descuento = "";
        extras = "";
        gasolina = "";
        fianza = "";
        total = "";
        firma = "";
        observaciones = "";

        HA_FIRMADO_CLIENTE = false;

        inicializarFirma(firmaCliente);

        fn_Traducir("ES");

        cliente = "";
        email = "";
        idioma = "";
        ocupacion = "0,00";
        kilometros = "0,00";
        descuento = "0,00";
        extras = "0,00";
        gasolina = "0,00";
        fianza = "0,00";
        total = "0,00";

        lblNombreCliente.setText(cliente);
        txtOcupacion.setText(ocupacion);
        txtKilometros.setText(kilometros);
        txtDescuento.setText(descuento);
        txtExtras.setText(extras);
        txtCombustible.setText(gasolina);
        txtFianza.setText(fianza);
        txtTotal.setText(total);
    }

    public void fn_Traducir(String idioma) {

        if (idioma.equalsIgnoreCase("ES"))
        {
            lblNombreCliente.setText(getString(R.string.name_and_surname));
            lblOcupacion.setText(getString(R.string.occupation));
            lblKilometros.setText(getString(R.string.kilometers));
            lblDescuento.setText(getString(R.string.discount));
            lblExtras.setText(getString(R.string.extras));
            lblFianza.setText(getString(R.string.deposit));
            lblCombustible.setText(getString(R.string.fuel));
            lblTotal.setText(getString(R.string.amount));
            lblObservaciones.setText(getString(R.string.observations));
            // Evito el null en el campo de texto observaciones
            if (observaciones.equals("null")) { observaciones = getString(R.string.not_obs_to_show);}
            lblCondiciones.setText(getString(R.string.txt_validate_client_sign));
            btnAcepto.setText(getString(R.string.firma_accept));
            btnNoAcepto.setText(getString(R.string.firma_noaccept));
            btnLimpiarFirmaCliente.setText(getString(R.string.clean));
            txtFirmar1 = getString(R.string.txtFirmar1);
            txtFirmar2 = getString(R.string.txtFirmar2);
            txtFirmar3 = getString(R.string.txtFirmar3);
            txtFirmar4 = getString(R.string.to_finalice);
            txtFirmar5 = getString(R.string.cancel);
            txtFirmar6 = getString(R.string.txtFirmar6);

        }

        if (idioma.equalsIgnoreCase("EN"))
        {
            tituloResumen.setText(R.string.en_tituloResumen);
            tituloFirma.setText(R.string.en_tituloFirma);
            lblNombreCliente.setText(getString(R.string.en_lblNombreCliente));
            lblOcupacion.setText(getString(R.string.en_lblOcupacion));
            lblKilometros.setText(getString(R.string.en_lblKilometros));
            lblDescuento.setText(getString(R.string.en_lblDescuento));
            lblExtras.setText(getString(R.string.extras)); // Untran
            lblFianza.setText(getString(R.string.en_lblFianza));
            lblCombustible.setText(getString(R.string.en_lblCombustible));
            lblTotal.setText(getString(R.string.en_lblTotal));
            lblObservaciones.setText(getString(R.string.en_lblObservaciones));
            // Evito el null en el campo de texto observaciones
            if (observaciones.equals("null")) { observaciones = getString(R.string.en_not_obs_to_show);}
            lblCondiciones.setText(getString(R.string.en_lblCondiciones));
            btnAcepto.setText(getString(R.string.en_btnAcepto));
            btnNoAcepto.setText(getString(R.string.en_btnNoAcepto));
            btnLimpiarFirmaCliente.setText(getString(R.string.en_btnLimpiarFirmaCliente));
            txtFirmar1 = getString(R.string.en_txtFirmar1);
            txtFirmar2 = getString(R.string.en_txtFirmar2);
            txtFirmar3 = getString(R.string.en_txtFirmar3);
            txtFirmar4 = getString(R.string.en_txtFirmar4);
            txtFirmar5 = getString(R.string.en_btnNoAcepto);//
            txtFirmar6 = getString(R.string.en_txtFirmar6);
        }
    }

    private Bitmap fn_Overlay(Bitmap bmp1, Bitmap bmp2) {
        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bmp1, new Matrix(), null);
        canvas.drawBitmap(bmp2, new Matrix(), null);
        return bmOverlay;
    }

    public class FingerPaint extends View {

        private static final float TOUCH_TOLERANCE = 1;
        private Bitmap mBitmap;
        private Canvas mCanvas;
        private Path mPath;
        private Paint mBitmapPaint;
        private Paint mPaint;
        private LinearLayout contenedor;
        private float mX, mY;

        public FingerPaint(Context c, Paint mPaint, LinearLayout l) {
            super(c);
            this.mPaint = mPaint;
            this.contenedor = l;
            mPath = new Path();
            mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.TRANSPARENT);
            canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
            canvas.drawPath(mPath, mPaint);
        }

        private void touch_start(float x, float y) {
            getParent().requestDisallowInterceptTouchEvent(true);
            mPath.reset();
            mPath.moveTo(x, y);
            mX = x;
            mY = y;
            mPath.quadTo(mX, mY, mX - 0.2f, mY - 0.2f);
        }

        private void touch_move(float x, float y) {
            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);
            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
                mX = x;
                mY = y;
            }
        }

        private void touch_up() {
            mPath.lineTo(mX, mY);
            // commit the path to our offscreen
            mCanvas.drawPath(mPath, mPaint);
            // kill this so we don't double draw
            mPath.reset();
            getParent().requestDisallowInterceptTouchEvent(false);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (this.contenedor == firmaCliente) {
                        HA_FIRMADO_CLIENTE = true;
                    }
                    touch_start(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (this.contenedor == firmaCliente) {
                        HA_FIRMADO_CLIENTE = true;
                    }
                    touch_move(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    if (this.contenedor == firmaCliente) {
                        HA_FIRMADO_CLIENTE = true;
                    }
                    touch_up();
                    invalidate();
                    break;
            }
            return true;
        }
    }

    private class asyncGetContrato extends AsyncTask<Void, String, Boolean> {

        Integer toastMensajeId = 0;
        String toastMensajeString = "";
        String documento = "0";

        @Override
        protected void onPreExecute() {
            inicializarFirma(firmaCliente);

            miDialog = new ProgressDialog(Firmar.this);
            miDialog.setMessage(getString(R.string.checkin_contract));
            miDialog.setCancelable(false);
            miDialog.show();
            documento = txtNumDocumento.getText().toString().trim();
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            CF = ((Carplus3G) getApplicationContext());
            try {
                conexionHTTP.AddParam("accion", "get_firma_contrato");
                conexionHTTP.AddParam("empresa", CF.getEmpresa());
                conexionHTTP.AddParam("id", CF.getTerminalId());
                conexionHTTP.AddParam("documento", documento);
                conexionHTTP.AddParam("firma", Carplus3G.SHA256("gfc" + documento));
                conexionHTTP.Execute(RestClient.RequestMethod.POST);
            } catch (Exception e) {
                // Capturamos posible excepción
            }

            JSONDATA = conexionHTTP.getResponse();
            if (!JSONDATA.equals("error")) {

                try {
                    JSONObject json = new JSONObject(JSONDATA);
                    int estado = json.getInt("status");
                    String mensaje = json.getString("statusMsg");

                    if (estado == 1)
                    {
                        JSONObject content = json.getJSONObject("content");
                        int err = Integer.parseInt(content.getString("err"));

                        switch (err) {
                            default:
                            case 0:
                                toastMensajeId = 1;
                                toastMensajeString = getString(R.string.cant_find_contract);
                                break;
                            case 1:
                                toastMensajeId = -1;
                                cliente = String.valueOf(content.getString("cli"));
                                email = String.valueOf(content.getString("ema"));
                                idioma = String.valueOf(content.getString("idi"));
                                ocupacion = String.valueOf(content.getString("ocu"));
                                kilometros = String.valueOf(content.getString("klm"));
                                descuento = String.valueOf(content.getString("des"));
                                extras = String.valueOf(content.getString("ext"));
                                gasolina = String.valueOf(content.getString("gas"));
                                fianza = String.valueOf(content.getString("fia"));
                                total = String.valueOf(content.getString("tot"));
                                observaciones = String.valueOf(content.getString("obs"));
                                String imagen = String.valueOf(content.getString("img"));
                                firma = imagen;
                                publishProgress(imagen);
                                break;
                            case 2:
                                toastMensajeId = 1;
                                toastMensajeString = getString(R.string.need_client_driver);
                                break;
                        }
                    } else {
                        toastMensajeId = 2;
                        toastMensajeString = getString(R.string.error_happened) + " " + mensaje;
                        return null;
                    }
                } catch (Exception e) {
                    toastMensajeId = 3;
                    toastMensajeString = getString(R.string.error_2p) + " " + e.getMessage();
                    return null;
                }
            } else {
                toastMensajeId = 4;
                return null;
            }

            return true;
        }

        protected void onProgressUpdate(String imagen) {
            //Opciones para mostrar la firma
            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
            bitmapOptions.inJustDecodeBounds = false;
            bitmapOptions.inSampleSize = 1;
            bitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565;

            byte[] bytesFirmaUsuario = Base64.decode(imagen, 0);
            Bitmap firma1 = BitmapFactory.decodeByteArray(bytesFirmaUsuario, 0, bytesFirmaUsuario.length, bitmapOptions);
            BitmapDrawable ob1 = new BitmapDrawable(getResources(), firma1);
            firmaCliente.setBackground(ob1);

            super.onProgressUpdate(imagen);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            JSONDATA = "";
            tareaGetContrato = null;

            switch (toastMensajeId) {
                case -1:
                    fn_PrintDatos();
                    break;
                case 1:
                case 2:
                    fn_Limpiar();
                    Toast.makeText(getApplicationContext(), toastMensajeString, Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    metodo.iraMenuPpal();
                    break;
                case 4:
                    Carplus3G.dialogConexion(Firmar.this);
                    metodo.iraMenuPpal();
                    break;
            }
            miDialog.dismiss();
        }

        @Override
        protected void onCancelled() {
        }
    }

    private class asyncSaveFirma extends AsyncTask<Void, Object, Boolean> {

        String documento = "0";
        Bitmap bgCli,bmpFirmaCliente;
        ByteArrayOutputStream byteArrayOutputStreamCliente;
        byte[] byteArrayCliente;
        String base64Cliente;

        @Override
        protected void onPreExecute() {

            miDialog = new ProgressDialog(Firmar.this);
            miDialog.setMessage(getString(R.string.saving_sign));
            miDialog.setCancelable(false);
            miDialog.show();

            documento = txtNumDocumento.getText().toString();

            firmaCliente.setDrawingCacheEnabled(true);
            firmaCliente.buildDrawingCache();
            bmpFirmaCliente = firmaCliente.getDrawingCache();
            bgCli = Bitmap.createBitmap(bmpFirmaCliente.getWidth(), bmpFirmaCliente.getHeight(), bmpFirmaCliente.getConfig());  // Create another image the same size
            bgCli.eraseColor(Color.WHITE);
            bmpFirmaCliente = fn_Overlay(bgCli, bmpFirmaCliente);
            byteArrayOutputStreamCliente = new ByteArrayOutputStream();
            bmpFirmaCliente.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStreamCliente);
            byteArrayCliente = byteArrayOutputStreamCliente.toByteArray();
            base64Cliente = Base64.encodeToString(byteArrayCliente, Base64.DEFAULT);
            firmaCliente.destroyDrawingCache();
            firmaCliente.setDrawingCacheEnabled(false);

            base64Cliente = fn_ConviertePngJpeg(base64Cliente);
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            CF = ((Carplus3G) getApplicationContext());

            try {

                conexionHTTP.AddParam("accion", "add_firma_contrato");
                conexionHTTP.AddParam("empresa", CF.getEmpresa());
                conexionHTTP.AddParam("id", CF.getTerminalId());
                conexionHTTP.AddParam("documento", documento);
                conexionHTTP.AddParam("email", email);
                conexionHTTP.AddParam("usuario", String.valueOf(CF.getCodUsuario()));
                conexionHTTP.AddParam("imagen", base64Cliente);
                conexionHTTP.AddParam("firma", Carplus3G.SHA256("afc" + documento));
                conexionHTTP.Execute(RestClient.RequestMethod.POST);
            } catch (Exception e) {
                // Usado solamente para capturar la excepción
            }
            JSONDATA = conexionHTTP.getResponse();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {

            if (!JSONDATA.equals("error")) {
                try {
                    JSONObject json = new JSONObject(JSONDATA);
                    int estado = json.getInt("status");
                    String mensaje = json.getString("statusMsg");

                    if (estado == 1) {
                        Toast.makeText(getApplicationContext(), txtFirmar6, Toast.LENGTH_LONG).show();
                        fn_Limpiar();
                    } else {
                        Toast.makeText(getApplicationContext(),  getString(R.string.error_happened) + " " + mensaje,Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),getString(R.string.error_2p) + " " + e.getMessage(),Toast.LENGTH_SHORT).show();
                    metodo.iraMenuPpal();
                }
            } else {
                Carplus3G.dialogConexion(Firmar.this);
                metodo.iraMenuPpal();
            }
            miDialog.dismiss();
            tareaSaveFirma = null;
            JSONDATA = "";
        }

        @Override
        protected void onCancelled() {
        }
    }
}