package com.example.carplus3g365v2.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.carplus3g365v2.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.List;

public class SpecialAdapterDisponibles extends SimpleAdapter {

    public HashMap<String, String> map = new HashMap<String, String>();
    public List<HashMap<String, String>> datos;
    public Integer marcado = 0;


    public SpecialAdapterDisponibles(Context context, List<HashMap<String, String>> items, int resource, String[] from, int[] to) {
        super(context, items, resource, from, to);
        datos = items;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        map = datos.get(position);

        View view = super.getView(position, convertView, parent);
        TextView txtImporte = (TextView) view.findViewById(R.id.itemDispImporte);

        txtImporte.setText(fn_Con2Decimales(Double.valueOf(map.get("imp"))));

        marcado = Integer.valueOf(map.get("chk"));

        if (marcado == 1)
        {
            view.setBackgroundResource(R.drawable.degradado_naranja_linear_layout);
        }
        else
        {
            view.setBackgroundResource(R.drawable.degradado_gris_linear_layout_disponibles);
        }

        return view;

    }

    private String fn_Con2Decimales(Double num) {
        DecimalFormat formatter = new DecimalFormat("###,###,##0.00");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        formatter.setDecimalFormatSymbols(symbols);
        return formatter.format(num);
    }
}
