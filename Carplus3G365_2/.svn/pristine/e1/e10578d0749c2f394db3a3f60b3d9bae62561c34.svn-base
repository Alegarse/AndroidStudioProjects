package com.example.carplus3g365v2.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.carplus3g365v2.R;

import java.util.HashMap;
import java.util.List;

public class SpecialAdapter extends SimpleAdapter {
	
	public SpecialAdapter(Context context, List<HashMap<String, String>> items, int resource, String[] from, int[] to) {
		super(context, items, resource, from, to);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
	  View view = super.getView(position, convertView, parent);
	  TextView t = (TextView) view.findViewById(R.id.txtRecogidasDebe);
	  String texto = (String) t.getText();
	  
	  if (texto.equals("debe")) {
		  view.setBackgroundResource(R.drawable.degradado_rojo_linear_layout);
	  } else {
		  view.setBackgroundResource(R.drawable.degradado_gris_linear_layout);
	  }
	  return view;
	}
}
