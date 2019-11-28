package com.ags.maipets.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.ags.maipets.R;
import com.ags.maipets.models.mascota;
import java.util.List;

public class MascotaAdapter extends RecyclerView.Adapter<MascotaAdapter.MascotaViewHolder> {

    private List<mascota> items;

    public static class MascotaViewHolder extends RecyclerView.ViewHolder{
        public ImageView imagen;
        public TextView nombre;
        public TextView tipo;
        public TextView raza;
        public TextView color;
        public TextView fechaNac;

        public MascotaViewHolder(View v){
            super(v);
            imagen = (ImageView) v.findViewById(R.id.aniImg);
            nombre = (TextView) v.findViewById(R.id.nombre);
            tipo = (TextView) v.findViewById(R.id.tipo);
            raza = (TextView) v.findViewById(R.id.raza);
            color = (TextView) v.findViewById(R.id.color);
            fechaNac = (TextView) v.findViewById(R.id.fechaNac);
        }
    }

    public MascotaAdapter(List<mascota> items){
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public MascotaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.mascota_card, viewGroup, false);
        return new MascotaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MascotaViewHolder viewHolder, int i) {
        viewHolder.imagen.setImageResource(items.get(i).getImagen());
        viewHolder.nombre.setText(items.get(i).getNombre());
        viewHolder.tipo.setText(items.get(i).getTipo());
        viewHolder.raza.setText(items.get(i).getRaza());
        viewHolder.color.setText(items.get(i).getColor());
        viewHolder.fechaNac.setText(items.get(i).getFechaNac());
    }

}
