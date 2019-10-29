package com.ags.flixnet.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ags.flixnet.R;
import com.ags.flixnet.modelos.Serie;

import java.util.List;

/*public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.SeriesHolder> {

    private List<Serie> series;
    //private Context contexto;

    public SeriesAdapter(Context List<Serie> list) {
        series = list;
    } // Guardo lo que me pasan



    @NonNull
    @Override
    //Este es el contenedor que necesitaremos
    public SeriesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        // Inflamos el layout que vamos a utilizar para item
        View vista = LayoutInflater.from(contexto)
                                    .inflate(R.layout.item_layout,parent,false);

        // Creo objeto de tuipo SeriesHolder y le indico el layout a usar para mostrar los elementos de la lista

        SeriesHolder sh = new SeriesHolder(vista)

        return null;
    }



    @Override
    public void onBindViewHolder(@NonNull SeriesHolder holder, int position)
    {

    }

    /**
     *
     * @return el numero de items que tiene la lista
     */

/*
    @Override
    public int getItemCount()
    {
        return series.size();
    }

    public class SeriesHolder extends RecyclerView.ViewHolder{

        private TextView titulo;
        private ImageView poster;

        public SeriesHolder(@NonNull View itemView)
        {
            super(itemView);

            titulo = itemView.findViewById(R.id.itemTitle);
            poster = itemView.findViewById(R.id.itemPoster);
        }

        public void BindHolder (Serie item)
        {
            titulo.setText(item.getTitulo());
        }
    }
}
*/