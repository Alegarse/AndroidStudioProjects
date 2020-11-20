package com.example.carplus3g365v2.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import com.example.carplus3g365v2.R;

import java.util.HashMap;
import java.util.List;

public class SpecialAdapterMeeting extends SimpleAdapter {
	HashMap<String, String> map = new HashMap<String, String>();
	List<HashMap<String, String>> datos;
	
	
	public SpecialAdapterMeeting(Context context, List<HashMap<String, String>> items, int resource, String[] from, int[] to) {
		super(context, items, resource, from, to);
		datos = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
	  map = datos.get(position);
	  
	  View view = super.getView(position, convertView, parent);
	  
	  if (Integer.parseInt(map.get("orden")) > 0) {
		  view.setBackgroundResource(R.drawable.degradado_rojo_linear_layout);
	  } else {
		  view.setBackgroundResource(R.drawable.degradado_gris_linear_layout);
	  }

	  return view;
	}
}
