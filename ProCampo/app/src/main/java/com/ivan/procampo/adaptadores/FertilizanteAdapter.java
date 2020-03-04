package com.ivan.procampo.adaptadores;

import android.app.MediaRouteButton;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ivan.procampo.R;
import com.ivan.procampo.modelos.Fertilizantes;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FertilizanteAdapter extends RecyclerView.Adapter<FertilizanteAdapter.MyViewHolder> {

    Context context;
    ArrayList<Fertilizantes> fertilizantes;

    public FertilizanteAdapter(Context c, ArrayList<Fertilizantes> f){
        context = c;
        fertilizantes = f;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.list_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nombre.setText(fertilizantes.get(position).getNombre());
        holder.precio.setText(fertilizantes.get(position).getPrecio());
        Picasso.get().load(fertilizantes.get(position).getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return fertilizantes.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nombre,precio;
        ImageView image;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                nombre = itemView.findViewById(R.id.nombre_text);
                precio = itemView.findViewById(R.id.precio_text);
                image = itemView.findViewById(R.id.fertilizantePic);
            }
        }
}
