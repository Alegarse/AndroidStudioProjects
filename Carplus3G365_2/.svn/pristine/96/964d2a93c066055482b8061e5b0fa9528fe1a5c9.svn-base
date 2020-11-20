package com.example.carplus3g365v2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carplus3g365v2.Modelos.BitmapUtils;
import com.example.carplus3g365v2.Modelos.Methods;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.os.AsyncTask.SERIAL_EXECUTOR;

public class EscaneoDocumentos extends AppCompatActivity {

    // Variables de actividad
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int RESULT_LOAD_IMAGE = 2;
    public RestClient conexionHTTP;
    public Carplus3G CF;
    public String JSONDATA;
    public Spinner spinTipoDocumento;
    public EditText txtNumDocumento;
    public Button btnLocalizar, btnLimpiar, btnFotosAdd;
    public TextView lblSinDocumentos;
    public LinearLayout thumbnails;
    public asyncGetDocumento tareaGetDocumento;
    public asyncUploadDocumento tareaAddDocumento;
    public asyncBorrarDocumento tareaDelDocumento;
    public ProgressDialog miDialog;
    CharSequence option1, option2;
    protected CharSequence[] _options;
    int NUM_IMAGENES = 0;
    Bitmap base64bitmap = null;
    Bitmap currentBitmap = null;
    Integer currentBitmapId = 0;
    Integer itemSeleccionado = 0;
    private Integer MAX_PICTURE_WIDTH = 1100;
    private Integer MAX_PICTURE_HEIGHT = 620;
    private Integer JPEG_QUALITY = 67;
    private Integer BW = 1;
    private Integer MAX_PICTURES = 3;
    private Integer WATERMARK = 0;

    public Dialog dialogVisor;

    private String ruta_imagen;
    private Methods metodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escaneo_documentos);

        // Instanciamos botones superiores
        ImageButton menu_ppal = findViewById(R.id.menuPpalED);
        ImageButton salir = findViewById(R.id.salirED);

        metodo = new Methods(EscaneoDocumentos.this);

        option1 = getString(R.string.fromCamera);
        option2 = getString(R.string.from_gal);
        _options = new CharSequence[]{option1, option2};

        Carplus3G constantes = ((Carplus3G) getApplicationContext());

        // Sobreescribimos las variables declaradas
        JPEG_QUALITY = 65;
        MAX_PICTURE_WIDTH = 1333;
        MAX_PICTURE_HEIGHT = 750;
        MAX_PICTURES = constantes.getMaxPictures();
        BW = 0;
        WATERMARK = 1;

        conexionHTTP = new RestClient(Carplus3G.URL);

        spinTipoDocumento = findViewById(R.id.spinTipoDocumentos);
        txtNumDocumento = findViewById(R.id.txtNumeroDocumento);
        btnLocalizar = findViewById(R.id.btnLocalizarDocumento);
        btnLimpiar = findViewById(R.id.btnLimpiarDocumento);
        btnFotosAdd = findViewById(R.id.btnHacerFotos);
        thumbnails = findViewById(R.id.thumbnailsDocumentos);
        lblSinDocumentos = findViewById(R.id.txtSinFotos);

        btnLimpiar.setVisibility(View.GONE);

        // Escuchadores
        menu_ppal.setOnClickListener(v -> metodo.iraMenuPpal());
        salir.setOnClickListener(v -> metodo.cerrarSesion());
        spinTipoDocumento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fn_Limpiar();
            }
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        btnLocalizar.setOnClickListener(view -> {

            if (txtNumDocumento.getText().toString().trim().equals("")) {
                Toast.makeText(EscaneoDocumentos.this, R.string.doc_number_required, Toast.LENGTH_SHORT).show();
                return;
            }
            tareaGetDocumento = new asyncGetDocumento();
            tareaGetDocumento.execute(null, null, null);
        });

        //Click del boton limpiar
        btnLimpiar.setOnClickListener(view -> fn_Limpiar());

        //Click del boton hacer fotos
        btnFotosAdd.setOnClickListener(view -> btnCamaraClick());
    }

    public void onStart() {
        super.onStart();

        Carplus3G constantes = ((Carplus3G) getApplicationContext());
        if (!constantes.cmpLastDate()) {
            constantes.setLastDate();
            constantes.setcargadoVersion(0);
            Intent i = new Intent(EscaneoDocumentos.this, Login.class);
            startActivity(i);
            finish();
            return;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_TAKE_PHOTO:

                    try {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inPreferredConfig = Bitmap.Config.RGB_565;
                        Bitmap bitmap = BitmapFactory.decodeFile(ruta_imagen, options);
                        currentBitmap = redimensiones(bitmap);
                        tareaAddDocumento = new asyncUploadDocumento(currentBitmap, null);
                        tareaAddDocumento.execute(null, null, null);

                    } catch (Exception e) {
                        Toast.makeText(EscaneoDocumentos.this, R.string.e_proc_photo, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case RESULT_LOAD_IMAGE:
                    if (data != null) {
                        try {
                            if (data.getData() != null) {

                                Uri uri = data.getData();
                                int fotos = thumbnails.getChildCount();

                                // Limito numero de fotos
                                if (fotos + 1 > (MAX_PICTURES + 1)) {
                                    Toast.makeText(EscaneoDocumentos.this, getString(R.string.onlyAllowed) + " " + String.valueOf(MAX_PICTURES) + " " + getString(R.string.onlyAllowed_2), Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                tareaAddDocumento = new asyncUploadDocumento(null, uri);
                                tareaAddDocumento.executeOnExecutor(SERIAL_EXECUTOR);

                            } else {
                                if (data.getClipData() != null) {

                                    ClipData mClipData = data.getClipData();

                                    int fotos = thumbnails.getChildCount();
                                    int fotosSeleccionadas = mClipData.getItemCount();

                                    // Limito numero de fotos
                                    if (fotos + fotosSeleccionadas > (MAX_PICTURES + 1)) {
                                        Toast.makeText(EscaneoDocumentos.this, getString(R.string.onlyAllowed) + " " + String.valueOf(MAX_PICTURES) + " " + getString(R.string.onlyAllowed_2), Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    for (int i = 0; i < mClipData.getItemCount(); i++) {
                                        ClipData.Item item = mClipData.getItemAt(i);
                                        final Uri uri = item.getUri();

                                        new asyncUploadDocumento(null, uri).executeOnExecutor(SERIAL_EXECUTOR);
                                    }
                                }
                            }
                        } catch (Exception e) {
                            Toast.makeText(EscaneoDocumentos.this, R.string.e_proc_photo, Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
            }
        }
    }

    // Deshabilitamos el botón volver atrás del movil
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return false;
    }

    // Métodos asincronos para trabajar con los documentos
    private class asyncGetDocumento extends AsyncTask<Void, Object, Boolean> {

        Integer toastMensajeId = 0;
        String toastMensajeString = "";

        String tipodoc = "";
        String documento = "0";


        @Override
        protected void onPreExecute() {
            miDialog = new ProgressDialog(EscaneoDocumentos.this);
            miDialog.setMessage(getString(R.string.checking_doc));
            miDialog.setCancelable(false);
            miDialog.show();

            documento = txtNumDocumento.getText().toString().trim();
            switch (spinTipoDocumento.getSelectedItemPosition()) {
                case 0:
                    tipodoc = "C";
                    break;
                case 1:
                    tipodoc = "R";
                    break;
                case 2:
                    tipodoc = "P";
                    break;
                case 3:
                    tipodoc = "E";
                    break;
            }

        }

        @Override
        protected Boolean doInBackground(Void... params) {

            CF = ((Carplus3G) getApplicationContext());

            try {
                conexionHTTP.AddParam("accion", "get_documentos");
                conexionHTTP.AddParam("empresa", CF.getEmpresa());
                conexionHTTP.AddParam("id", CF.getTerminalId());
                conexionHTTP.AddParam("documento", documento);
                conexionHTTP.AddParam("firma", Carplus3G.SHA256("gdo" + documento));
                conexionHTTP.AddParam("tipo", tipodoc);
                conexionHTTP.Execute(RestClient.RequestMethod.POST);
            } catch (Exception e) {
            }

            JSONDATA = conexionHTTP.getResponse();

            if (!JSONDATA.equals("error")) {

                try {
                    JSONObject json = new JSONObject(JSONDATA);

                    int estado = json.getInt("status");
                    String mensaje = json.getString("statusMsg");

                    if (estado == 1) {
                        JSONObject content = json.getJSONObject("content");
                        int existe = Integer.parseInt(content.getString("existe"));
                        JSONArray imagenes = content.getJSONArray("imgs");

                        if (existe == 1) {
                            toastMensajeId = -1;

                            for (int i = 0, size = imagenes.length(); i < size; i++) {
                                JSONObject docu = imagenes.getJSONObject(i);
                                int id = Integer.parseInt(docu.getString("id"));
                                String img64 = docu.getString("doc");

                                BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                                bitmapOptions.inJustDecodeBounds = false;
                                bitmapOptions.inSampleSize = 1;
                                bitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565;

                                base64bitmap = null;
                                byte[] decodedByte = Base64.decode(img64, 0);
                                base64bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length, bitmapOptions);

                                Object[] data = {base64bitmap, id};
                                publishProgress(data);
                            }
                        } else {
                            toastMensajeId = 1;
                            toastMensajeString = getString(R.string.cant_find_doc);
                            return null;
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

        protected void onProgressUpdate(Object[] values) {
            addFoto((Bitmap) values[0], (Integer) values[1]);
            thumbnails.invalidate();
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            JSONDATA = "";
            tareaGetDocumento = null;

            switch (toastMensajeId) {
                case -1:
                    spinTipoDocumento.setEnabled(false);
                    txtNumDocumento.setEnabled(false);
                    btnLimpiar.setVisibility(View.VISIBLE);
                    btnLocalizar.setVisibility(View.GONE);
                    break;
                case 1:
                case 2:
                    Toast.makeText(getApplicationContext(), toastMensajeString, Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    metodo.iraMenuPpal();
                    break;
                case 4:
                    Carplus3G.dialogConexion(EscaneoDocumentos.this);
                    metodo.iraMenuPpal();
                    break;
            }
            miDialog.dismiss();
        }

        @Override
        protected void onCancelled() {
        }
    }

    private class asyncUploadDocumento extends AsyncTask<Void, Object, Boolean> {
        Integer toastMensajeId = 0;
        String toastMensajeString = "";

        ProgressDialog dialogUpload;
        String tipodoc = "";
        String documento = "0";
        String imagen = "";
        Bitmap nuevaImagen = null;
        Uri uriImagen = null;

        public asyncUploadDocumento(Bitmap bmp, Uri uri) {
            super();
            // do stuff
            nuevaImagen = bmp;
            uriImagen = uri;
        }

        @Override
        protected void onPreExecute() {
            dialogUpload = new ProgressDialog(EscaneoDocumentos.this);
            dialogUpload.setMessage(getString(R.string.uploading_doc));
            dialogUpload.setCancelable(false);
            dialogUpload.show();

            documento = txtNumDocumento.getText().toString().trim();
            switch (spinTipoDocumento.getSelectedItemPosition()) {
                case 0:
                    tipodoc = "C";
                    break;
                case 1:
                    tipodoc = "R";
                    break;
                case 2:
                    tipodoc = "P";
                    break;
                case 3:
                    tipodoc = "E";
                    break;
            }
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            if (nuevaImagen == null) {
                try {
                    nuevaImagen = decodeUri(uriImagen);
                    nuevaImagen = redimensiones(nuevaImagen);
                } catch (Exception e) {
                    String txt = e.getMessage();
                }
            }

            //Aqui lo convertimos a base64 para enviarlo
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            nuevaImagen.compress(Bitmap.CompressFormat.JPEG, JPEG_QUALITY, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();

            imagen += Base64.encodeToString(byteArray, Base64.DEFAULT);

            CF = ((Carplus3G) getApplicationContext());

            try {
                conexionHTTP.AddParam("accion", "add_documentos");
                conexionHTTP.AddParam("empresa", CF.getEmpresa());
                conexionHTTP.AddParam("id", CF.getTerminalId());
                conexionHTTP.AddParam("documento", documento);
                conexionHTTP.AddParam("firma", Carplus3G.SHA256("ado" + documento));
                conexionHTTP.AddParam("tipo", tipodoc);
                conexionHTTP.AddParam("imagen", imagen);
                conexionHTTP.Execute(RestClient.RequestMethod.POST);
            } catch (Exception e) {
            }

            JSONDATA = conexionHTTP.getResponse();

            if (!JSONDATA.equals("error")) {

                try {
                    JSONObject json = new JSONObject(JSONDATA);

                    int estado = json.getInt("status");
                    String mensaje = json.getString("statusMsg");

                    if (estado == 1) {
                        JSONObject content = json.getJSONObject("content");
                        int doc_id = Integer.parseInt(content.getString("docid"));

                        Object[] data = {nuevaImagen, doc_id};
                        publishProgress(data);
                        toastMensajeId = -1;
                        toastMensajeString = getString(R.string.doc_upload_succ);
                    } else {
                        toastMensajeId = 1;
                        toastMensajeString = getString(R.string.error_happened) + " " + mensaje;
                        return null;
                    }
                } catch (Exception e) {
                    toastMensajeId = 2;
                    toastMensajeString = "Error: " + e.getMessage();
                    return null;
                    //volverAlMenuPrincipal();
                }
            } else {
                toastMensajeId = 3;
                return null;
            }
            return true;
        }


        protected void onProgressUpdate(Object[] values) {
            addFoto((Bitmap) values[0], (Integer) values[1]);
            thumbnails.invalidate();
            values = null;
            //nuevaImagen.recycle();
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            JSONDATA = "";
            tareaAddDocumento = null;

            switch (toastMensajeId) {
                case -1:
                case 1:
                    Toast.makeText(getApplicationContext(), toastMensajeString, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(getApplicationContext(), toastMensajeString, Toast.LENGTH_SHORT).show();
                    metodo.iraMenuPpal();
                    break;
                case 3:
                    Carplus3G.dialogConexion(EscaneoDocumentos.this);
                    metodo.iraMenuPpal();
                    break;
            }
            dialogUpload.dismiss();
        }
        @Override
        protected void onCancelled() {
        }
    }

    private class asyncBorrarDocumento extends AsyncTask<Void, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            miDialog = new ProgressDialog(EscaneoDocumentos.this);
            miDialog.setMessage(getString(R.string.deleting_doc));
            miDialog.setCancelable(false);
            miDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            CF = ((Carplus3G) getApplicationContext());

            try {
                conexionHTTP.AddParam("accion", "del_documentos");
                conexionHTTP.AddParam("firma", Carplus3G.SHA256("ddo" + String.valueOf(currentBitmapId)));
                conexionHTTP.AddParam("empresa", CF.getEmpresa());
                conexionHTTP.AddParam("id", CF.getTerminalId());
                conexionHTTP.AddParam("documento_id", String.valueOf(currentBitmapId));
                conexionHTTP.Execute(RestClient.RequestMethod.POST);
            } catch (Exception e) {
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

                        if (estado == 1) {
                            for (int x = 0; x < thumbnails.getChildCount(); x++) {
                                View v = thumbnails.getChildAt(x);
                                if (v.getId() != R.id.txtSinFotos) {
                                    if (v.getTag() == currentBitmapId) {
                                        thumbnails.removeView(v);
                                    }
                                }
                            }
                            if (thumbnails.getChildCount() == 1) {
                                lblSinDocumentos.setVisibility(View.VISIBLE);
                            }
                            Toast.makeText(getApplicationContext(), R.string.doc_del_succ, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), getString(R.string.error_happened)+" " + mensaje, Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), getString(R.string.error_2p)+" " + e.getMessage(), Toast.LENGTH_LONG).show();
                        metodo.iraMenuPpal();
                    }
                } else {
                    Carplus3G.dialogConexion(EscaneoDocumentos.this);
                    metodo.iraMenuPpal();
                }
            }
            currentBitmapId = 0;
        }
        @Override
        protected void onCancelled() {
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void addFoto(Bitmap bmp, final Integer tag) {

        try {

            int maxWidth = thumbnails.getWidth();
            int maxHeight;

            Matrix matrix = new Matrix();

            if (bmp.getWidth() > bmp.getHeight()) {
                matrix.postRotate(90);
                bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
            }

            // Correción para mantener visualización sin agrandar al agregar. En carga de BBDD se vería bien
            float scale2 = Math.min(((float) 731 / bmp.getWidth()), ((float) 456 / bmp.getHeight()));

            matrix = new Matrix();
            matrix.postScale(scale2, scale2);

            Bitmap bitmap2 = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);

            ImageView iBtn = new ImageView(this);
            iBtn.setClickable(true);
            iBtn.setImageBitmap(bmp);
            iBtn.setTag(tag);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(bitmap2.getWidth(), bitmap2.getHeight());
            layoutParams.bottomMargin = 25;
            iBtn.setLayoutParams(layoutParams);
            iBtn.setScaleType(ImageView.ScaleType.FIT_XY);

            lblSinDocumentos.setVisibility(View.GONE);
            thumbnails.addView(iBtn);

            //Highlight al tocar
            iBtn.setOnTouchListener((view, motionEvent) -> {
                ImageView img = (ImageView) view;
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    img.setColorFilter(Color.CYAN, PorterDuff.Mode.MULTIPLY);
                }
                if (motionEvent.getAction() != MotionEvent.ACTION_DOWN) {
                    img.clearColorFilter();
                }
                return false;
            });

            iBtn.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View thumb) {

                    dialogVisor = new Dialog(EscaneoDocumentos.this, R.style.FullHeightDialog);
                    dialogVisor.setContentView(R.layout.dg_borrar_foto);
                    dialogVisor.setCanceledOnTouchOutside(true);
                    dialogVisor.setCancelable(true);

                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    Window window = dialogVisor.getWindow();
                    lp.copyFrom(window.getAttributes());
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    window.setAttributes(lp);

                    dialogVisor.show();

                    Button ok = dialogVisor.findViewById(R.id.btnOkVisor);
                    Button borrar = dialogVisor.findViewById(R.id.btnBorrarVisor);
                    ImageView img = dialogVisor.findViewById(R.id.imagenVisor);

                    ImageView btn = (ImageView) thumb;
                    Bitmap bmp = ((BitmapDrawable) btn.getDrawable()).getBitmap();
                    img.setImageBitmap(bmp);

                    ok.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            dialogVisor.dismiss();
                        }

                    });

                    borrar.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            currentBitmapId = tag;

                            tareaDelDocumento = new asyncBorrarDocumento();
                            tareaDelDocumento.execute(null, null, null);
                            dialogVisor.dismiss();
                        }
                    });
                    return false;
                }
            });

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {

        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 140;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        int scaleX = width_tmp / MAX_PICTURE_WIDTH;
        int scaleY = height_tmp / MAX_PICTURE_HEIGHT;

        if (scaleX > 1 && scaleX > scaleY) {
            scale = scaleX;
        }

        if (scaleY > 1 && scaleY > scaleX) {
            scale = scaleY;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);

    }

    private Bitmap redimensiones(Bitmap miBitmap) {
        miBitmap =  BitmapUtils.redim(miBitmap,MAX_PICTURE_HEIGHT,MAX_PICTURE_WIDTH);

        //Convertir a Escala de Grises
        if (BW == 1) {
            miBitmap = BitmapUtils.bitmapToGrayScale(miBitmap);
        }
        if (WATERMARK == 1) {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String date = df.format(Calendar.getInstance().getTime());
            miBitmap = BitmapUtils.addWaterMark(miBitmap, date);
        }
        return miBitmap;
    }

    private void fn_Limpiar() {
        spinTipoDocumento.setEnabled(true);

        txtNumDocumento.setEnabled(true);
        txtNumDocumento.setText("");

        btnLimpiar.setVisibility(View.GONE);
        btnLocalizar.setVisibility(View.VISIBLE);

        int nViews = thumbnails.getChildCount();

        if (nViews >= 2) {
            thumbnails.removeViews(1, thumbnails.getChildCount() - 1);
        }

        lblSinDocumentos.setVisibility(View.VISIBLE);
    }

    private void btnCamaraClick() {

        int fotos = thumbnails.getChildCount();

        //Si no esta deshabilitado, es que no estoy trabajando sobre un documento en concreto
        if (spinTipoDocumento.isEnabled()) {
            Toast.makeText(EscaneoDocumentos.this, R.string.please_find_doc_first, Toast.LENGTH_SHORT).show();
            return;
        }

        // Limito numero de fotos
        if (fotos >= (MAX_PICTURES + 1)) {
            Toast.makeText(EscaneoDocumentos.this, getString(R.string.onlyAllowed)+" " + String.valueOf(MAX_PICTURES) + " " + getString(R.string.onlyAllowed_2), Toast.LENGTH_SHORT).show();
            return;
        }

        itemSeleccionado = 0;

        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.add_photo))
                .setCancelable(false)
                .setSingleChoiceItems(_options, 0, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        itemSeleccionado = which;
                    }
                })
                .setPositiveButton(getString(R.string.accept), new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                        if (itemSeleccionado == 0) {
                            // Abrimos la cámara del movil
                            if (Carplus3G.hasBackFacingCamera()) {
                                intentImg();
                            } else {
                                Toast.makeText(EscaneoDocumentos.this, R.string.no_back_camera, Toast.LENGTH_LONG).show();
                            }
                        } else {
                            intentImg();
                        }
                    }

                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                }).create().show();

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        ruta_imagen = image.getAbsolutePath();
        return image;
    }

    private void intentImg() {

        if (itemSeleccionado == 0) {
            // Venimos desde opción de hacer fotografía
            Intent cameraImgIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (cameraImgIntent.resolveActivity(getPackageManager()) != null) {
                // Creamos archivo donde se meterá la fotografía
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    // No se ha podido crear al archivo de imagen
                }
                // Continuamos si el archivo se ha creado correctamente
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(getApplicationContext(),
                            BuildConfig.APPLICATION_ID +".provider",
                            photoFile);
                    cameraImgIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    // Lanzamos el intent de la cámara
                    startActivityForResult(cameraImgIntent, REQUEST_TAKE_PHOTO);
                }
            }
        } else {
            // Venismos desde la opción de seleccionar de galería
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE);
        }
    }

}