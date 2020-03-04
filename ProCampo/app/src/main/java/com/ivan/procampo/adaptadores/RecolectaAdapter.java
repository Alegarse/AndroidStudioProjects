package com.ivan.procampo.adaptadores;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ivan.procampo.R;
import com.ivan.procampo.modelos.Recolectas;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecolectaAdapter extends RecyclerView.Adapter<RecolectaAdapter.ViewHolder> {

    private int resource;
    private ArrayList<Recolectas> recolectasList;

    private int index;

    public RecolectaAdapter(ArrayList<Recolectas> recolectasList, int resource){
        this.recolectasList = recolectasList;
        this.resource = resource;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //Se crea la vista
        View view = LayoutInflater.from(parent.getContext()).inflate(resource , parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int index) { //Definimos los datos que queremos mostrar
        Recolectas recolecta = recolectasList.get(index);

        holder.textViewCultivo.setText(recolecta.getCultivoRecolecta());
        holder.textViewCodigo.setText(recolecta.getCodigoRecolecta());
        holder.textViewFecha.setText(recolecta.getFechaRecolecta());
        holder.textViewKilos.setText(recolecta.getKilosRecolecta());
        holder.textViewDat.setText(recolecta.getDATRecolecta());
        holder.textViewMatricula.setText(recolecta.getMatriculaRecolecta());



        holder.BindHolder(recolectasList.get(index)) ;
    }

    @Override
    public int getItemCount() { //numero de vistas obtenidas
        return recolectasList.size();
    }

    public int getIndex(){
        return index;
    }

    //////////////////////////

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        //Referencias

        private TextView textViewCultivo;
        private TextView textViewFecha;
        private TextView textViewKilos;
        private TextView textViewDat;
        private TextView textViewMatricula;
        private TextView textViewCodigo;


        public View view;

        public ViewHolder(View view){
            super(view);

            this.view = view;

            this.textViewCultivo = (TextView) view.findViewById(R.id.textoCultivoRecolecta);
            this.textViewCodigo = (TextView) view.findViewById(R.id.textoCodigoRecolecta);
            this.textViewFecha = (TextView) view.findViewById(R.id.textoFechaRecolecta);
            this.textViewKilos = (TextView) view.findViewById(R.id.textoKilosRecolecta);
            this.textViewDat = (TextView) view.findViewById(R.id.textoDatRecolecta);
            this.textViewMatricula = (TextView) view.findViewById(R.id.textoMatriculaRecolecta);



            view.setOnCreateContextMenuListener(this);
        }

        /**
         * Menu contextual
         * @param contextMenu
         * @param view
         * @param contextMenuInfo
         */
        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

        }

        public void BindHolder(Recolectas item) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    index = getAdapterPosition();


                    return false;
                }
            });
        }
    }

}
