package com.ags.maipets.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ags.maipets.R;
import com.ags.maipets.models.mascota;
import java.util.List;

public class MascotaAdapter extends RecyclerView.Adapter<MascotaAdapter.MascotaViewHolder> {

    private List<mascota> mascotas;

    public MascotaAdapter(List<mascota> mascotas) {
        this.mascotas = mascotas;
    }

    @NonNull
    @Override
    public MascotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mascota_card, parent, false);
        MascotaViewHolder holder = new MascotaViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MascotaViewHolder holder, int position) {
        mascota masc = mascotas.get(position);
        //holder.imagen.setImageDrawable(masc.getImagen());
        holder.nombre.setText(masc.getNombre());
        holder.tipo.setText(masc.getTipo());
        holder.raza.setText(masc.getRaza());
        holder.color.setText(masc.getColor());
        holder.fechaNac.setText(masc.getFechaNac());
    }

    @Override
    public int getItemCount() {
        return mascotas.size();
    }

    public static class MascotaViewHolder extends RecyclerView.ViewHolder{
        //public ImageView imagen;
        public TextView nombre, tipo, raza, color, fechaNac;


        public MascotaViewHolder(View v){
            super(v);
            //imagen = (ImageView) v.findViewById(R.id.aniImg);
            nombre = (TextView) v.findViewById(R.id.nombre);
            tipo = (TextView) v.findViewById(R.id.tipo);
            raza = (TextView) v.findViewById(R.id.raza);
            color = (TextView) v.findViewById(R.id.color);
            fechaNac = (TextView) v.findViewById(R.id.fechaNac);
        }
    }



}
