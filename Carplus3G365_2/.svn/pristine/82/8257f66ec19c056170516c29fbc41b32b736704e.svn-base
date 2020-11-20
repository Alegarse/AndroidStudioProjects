package com.example.carplus3g365v2.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.core.content.ContextCompat;

import com.example.carplus3g365v2.Inspect;
import com.example.carplus3g365v2.R;

import java.util.ArrayList;
import java.util.HashMap;

import static android.view.View.VISIBLE;


public class AdapterItemEquipamientos extends BaseAdapter {
    private static LayoutInflater inflater = null;
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;


    public AdapterItemEquipamientos(Activity a, ArrayList<HashMap<String, String>> d) {
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
            vi = inflater.inflate(R.layout.equipamiento_item, null);

        final int finalposition = position;
        HashMap<String, String> datos = data.get(position);

        if (position % 2 == 0)
        {
            vi.setBackgroundColor(Color.WHITE);
        }
        else
        {
            vi.setBackgroundColor(Color.WHITE);
        }

        TextView txtNombre = vi.findViewById(R.id.itemEquipamientoConcepto);
        Button btncheck = vi.findViewById(R.id.itemEquipamientoCheck);
        ToggleButton btnSiNo= vi.findViewById(R.id.itemEquipamientoSiNo);
        ImageButton btnSiNoImg = vi.findViewById(R.id.itemEquipamientoSiNoImg);

        String nombre = String.valueOf(datos.get("nom"));

        txtNombre.setText(nombre);

        btnSiNo.setOnCheckedChangeListener(null);


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

                btncheck.setBackgroundColor(ContextCompat.getColor(v.getContext(),R.color.verdeVivo));
                btncheck.setText(R.string.yes);
                btncheck.setEnabled(false);

                //Lanzar un dialog para saber si está presente el extra
                AlertDialog.Builder checkExtra = new AlertDialog.Builder(v.getContext());
                checkExtra.setMessage(activity.getString(R.string.check_inspect_extra));

                checkExtra.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((Inspect) activity).fn_ActualizaSeleccionEquipamientos(String.valueOf(true),finalposition);
                        ((Inspect) activity).fn_ActualizaCheck("A");
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
                        ((Inspect) activity).fn_ActualizaSeleccionEquipamientos(String.valueOf(false),finalposition);
                        ((Inspect) activity).fn_ActualizaCheck("A");
                        btnSiNo.setChecked(false);
                        btnSiNoImg.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(v.getContext(),R.color.transparente)));
                        btnSiNoImg.setPadding(6,6,6,6);
                        btnSiNoImg.setImageDrawable(ContextCompat.getDrawable(v.getContext(),R.drawable.redcross));
                        volverVisible(btnSiNoImg);
                        setCheck(btncheck,v.getContext());
                        agregarAObs(nombre, "",1);
                    }
                });
                AlertDialog dialog = checkExtra.create();
                dialog.show();
            }
        });

        return vi;
    }

    private void volverVisible(ImageButton boton) {
        boton.setVisibility(VISIBLE);
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
