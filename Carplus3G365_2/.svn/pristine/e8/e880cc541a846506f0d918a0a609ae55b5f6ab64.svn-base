package com.example.carplus3g365v2.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carplus3g365v2.Modelos.Service;
import com.example.carplus3g365v2.R;

import java.util.ArrayList;

public class serviciosAdapter extends RecyclerView.Adapter<serviciosAdapter.ServicioViewHolder>
        implements View.OnClickListener{

    ArrayList<Service> servicios;
    Context context;

    private View.OnClickListener listener;

    private int position;

    public serviciosAdapter(Context c,ArrayList<Service> s) {
        this.context = c;
        this.servicios = s;
    }

    public void setServicios(ArrayList<Service> listaServicios) {
        this.servicios = servicios;
    }


    @Override
    public ServicioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // View para desplazamiento vertical
        View view = LayoutInflater.from(context).inflate(R.layout.servicios_card_v,parent,false);
        view.setOnClickListener(this);
        return new ServicioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull serviciosAdapter.ServicioViewHolder holder, int position) {

        // Si es una opción deshabilitada, realizamos estos cambios
        String tituloDes = servicios.get(position).getTitulo();
        switch (tituloDes){
            case "Escaner":
            case "Firmas":
            case "Inspeccion":
                holder.contenedorServicio.setBackgroundResource(R.color.colorBaseDes);
                holder.titServicio.setTextColor(Color.rgb(142,139,139));
                holder.descServicio.setTextColor(Color.rgb(142,139,139));
                holder.imgServicio.setImageAlpha(80);
        }

        Service serv = servicios.get(position);
        holder.titServicio.setText(servicios.get(position).getTitulo());
        holder.descServicio.setText(servicios.get(position).getDescripcion());
        holder.imgServicio.setImageResource(servicios.get(position).getImagenServicio());
        holder.BindHolder(servicios.get(position));
        holder.view.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return servicios.size();
    }
    public int getIndex(){
        return position;
    }

    public void setOnClickListener (View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    public class ServicioViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        ConstraintLayout contenedorServicio;
        TextView titServicio, descServicio;
        ImageView imgServicio;
        public View view;

        public ServicioViewHolder(View v){

            super(v);
            this.view = v;
            contenedorServicio = v.findViewById(R.id.contenedor_servicio);
            titServicio = v.findViewById(R.id.tituloServicio);
            descServicio = v.findViewById(R.id.descServicio);
            imgServicio = v.findViewById(R.id.imageServicio);
            v.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {}

        public void BindHolder (Service servicio){
            view.setOnClickListener(v -> {

            });
            itemView.setOnLongClickListener(v -> {
                position = getAdapterPosition();
                return false;
            });
        }
    }
}
