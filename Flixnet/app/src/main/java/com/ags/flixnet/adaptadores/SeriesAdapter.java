package com.ags.flixnet.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ags.flixnet.R;
import com.ags.flixnet.modelos.Serie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.listaHolder>
{
    private List<Serie> lista ;
    private Context contexto ;

    /**
     * @param ctx
     */
public SeriesAdapter(Context ctx)
        {
        lista   = new ArrayList<>() ;
        contexto = ctx ;
        }

/**
 * seteamos la lista de datos y le notificamos el cambio
 * @param datos
 */
public void setLista(List<Serie> datos)
        {
        lista = datos ;
        notifyDataSetChanged() ;
        }

/**
 * @param parent
 * @param viewType
 * @return
 */
@NonNull
@Override
public listaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
        // inflamos el layout que vamos a utilizar para ítem
        View vista = LayoutInflater.from(contexto)
        .inflate(R.layout.item_layout, parent, false) ;

        // creamos el contenedor y le indicamos qué layout debe utilizar
        // para mostrar los elementos de la lista.
        return new listaHolder(vista) ;
        }

/**
 * @param holder
 * @param position
 */
@Override
public void onBindViewHolder(@NonNull listaHolder holder, int position)
        {
        holder.BindHolder(lista.get(position)) ;
        }

/**
 * @return
 */
@Override
public int getItemCount()
        {
        return lista.size() ;
        }

public class listaHolder extends RecyclerView.ViewHolder
{
    private TextView titulo ;
    private ImageView poster ;

    /**
     * @param itemView
     */
    public listaHolder(@NonNull View itemView)
    {
        super(itemView) ;

        titulo = itemView.findViewById(R.id.itemTitle) ;
        poster = itemView.findViewById(R.id.itemPoster) ;
    }

    /**
     * @param item
     */
    public void BindHolder(Serie item)
    {
        // Mostramos el título de la serie
        titulo.setText(item.getTitulo()) ;

        // mostramos el póster de la serie
        Picasso.get()
                .load(item.getCartel())
                .resize(300, 444)
                .into(poster) ;
    }
}

}