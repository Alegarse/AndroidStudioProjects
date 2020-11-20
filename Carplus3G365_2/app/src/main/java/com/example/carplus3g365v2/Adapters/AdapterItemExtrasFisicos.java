package com.example.carplus3g365v2.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.core.content.ContextCompat;

import com.example.carplus3g365v2.Inspect;
import com.example.carplus3g365v2.R;

import java.util.ArrayList;
import java.util.HashMap;


public class AdapterItemExtrasFisicos extends BaseAdapter{
    private static LayoutInflater inflater = null;
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;


    public AdapterItemExtrasFisicos(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data = d;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.extra_fisico_item, null);

        final int finalposition = position;
        final HashMap<String, String> datos = data.get(position);

        if (position % 2 == 0)
        {
            vi.setBackgroundColor(Color.WHITE);
        }
        else
        {
            vi.setBackgroundColor(Color.WHITE);
        }

        TextView txtNombre = vi.findViewById(R.id.itemExtraFisicoConcepto);
        final EditText txtSerie= vi.findViewById(R.id.txtSerieExtra);
        Button btncheck = vi.findViewById(R.id.itemExtraFisicoCheck);
        ToggleButton btnSiNo= vi.findViewById(R.id.itemExtraFisicoSiNo);
        ImageButton btnSiNoImg = vi.findViewById(R.id.itemExtraFisicoSiNoImg);


        String nombre = String.valueOf(datos.get("nom"));
        final int pedirSerie = Integer.parseInt(datos.get("pedirserie"));
        String serie = String.valueOf(datos.get("serie"));

        txtNombre.setText(nombre);
        txtSerie.setText(serie);

        btnSiNo.setOnCheckedChangeListener(null);
        btnSiNo.setOnClickListener(null);

        if (pedirSerie == 0){
            txtSerie.setVisibility(View.GONE);
        }else{
            txtSerie.setVisibility(View.VISIBLE);
        }

        // Para marcar todos extras/accesorios disponibles a la entrega
        if (Inspect.EN_ENTREGA == 1 && Inspect.EN_RECOGIDA == 0) {
            fn_EntregaVehiculo(btncheck,btnSiNoImg,vi.getContext());
        }
        // Para marcar todos los accesorios realizados ya los chequeos
        // Cambiará cuando se pueda leer de BBDD el parámetro yesno y se adecuará a lo que lea de ahí
        if (Inspect.ALL_VERIFIED == 1) {
            fn_EntregaVehiculo(btncheck,btnSiNoImg,vi.getContext());
        }

        btncheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Lanzar un dialog para saber si está presente el extra
                AlertDialog.Builder checkExtra = new AlertDialog.Builder(v.getContext());
                checkExtra.setMessage(activity.getString(R.string.check_inspect_extra));

                checkExtra.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((Inspect) activity).fn_ActualizaSeleccionExtras(String.valueOf(true),txtSerie.getText().toString(),finalposition,false);
                        ((Inspect) activity).fn_ActualizaCheck("E");
                        btnSiNo.setChecked(true);
                        btnSiNoImg.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(v.getContext(),R.color.transparente)));
                        btnSiNoImg.setPadding(6,6,6,6);
                        btnSiNoImg.setImageDrawable(ContextCompat.getDrawable(v.getContext(),R.drawable.tick));
                        volverVisible(btnSiNoImg);
                        setCheck(btncheck,v.getContext());
                    }
                });
                checkExtra.setNegativeButton(R.string.not, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((Inspect) activity).fn_ActualizaSeleccionExtras(String.valueOf(false),txtSerie.getText().toString(),finalposition,false);
                        ((Inspect) activity).fn_ActualizaCheck("E");
                        btnSiNo.setChecked(false);
                        btnSiNoImg.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(v.getContext(),R.color.transparente)));
                        btnSiNoImg.setPadding(6,6,6,6);
                        btnSiNoImg.setImageDrawable(ContextCompat.getDrawable(v.getContext(),R.drawable.redcross));
                        volverVisible(btnSiNoImg);
                        setCheck(btncheck,v.getContext());
                        agregarAObs(nombre, serie,0);
                    }
                });
                AlertDialog dialog = checkExtra.create();
                dialog.show();
            }
        });

        txtSerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(((Inspect) activity));
                builder.setTitle("Nº Serie del extra");

                final EditText input = new EditText(((Inspect) activity));
                input.setText(String.valueOf(datos.get("serie")));
                input.setMinLines(1);
                input.setMaxLines(1);
                input.setFilters(new InputFilter[] {new InputFilter.LengthFilter(6)});
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                builder.setView(input);

                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String cadena = input.getText().toString().trim();

                        if (cadena.equals(""))
                        {
                            ((Inspect) activity).fn_ActualizaSeleccionExtras("0",cadena,finalposition,true);
                        }
                        else
                        {
                            ((Inspect) activity).fn_ActualizaSeleccionExtras("1",cadena,finalposition,true);
                        }

                        ((Inspect) activity).imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
        return vi;
    }

    private void volverVisible(ImageButton boton) {
        boton.setVisibility(View.VISIBLE);
    }

    private void setCheck(Button botonC, Context c) {
        botonC.setVisibility(View.GONE);
        botonC.setEnabled(false);
    }

    public void fn_EntregaVehiculo (Button check, ImageButton btnSiNoImg, Context c) {
        check.setVisibility(View.GONE);
        volverVisible(btnSiNoImg);
        btnSiNoImg.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(c,R.color.transparente)));
        btnSiNoImg.setPadding(6,6,6,6);
        btnSiNoImg.setImageDrawable(ContextCompat.getDrawable(c,R.drawable.tick));
    }

    public void agregarAObs(String nombre, String serie, int cod) {
        ((Inspect) activity).fn_ActualizaObs(nombre,serie,cod);
    }

}
