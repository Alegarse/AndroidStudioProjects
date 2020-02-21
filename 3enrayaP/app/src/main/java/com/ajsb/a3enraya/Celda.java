package com.ajsb.a3enraya;

/**
 * Antonio José Sánchez Bujaldón
 * Programación Multimedia y de dispositivos Móviles
 * curso 2019/20
 */

import android.content.Context;
import android.view.Gravity;
import android.widget.GridLayout;

import androidx.appcompat.widget.AppCompatImageView;

public class Celda extends AppCompatImageView
{
    private int fil ;
    private int col ;

    /**
     * Si ficha es 0, la celda está vacía.
     * Si ficha es 1, la celda es del jugador 1
     * Si ficha es 2, la celda es del jugador 2
     */
    private int ficha = 0 ;

    /**
     * @param context
     * @param fil
     * @param col
     * @param layout
     */
    public Celda(Context context, int fil, int col,
                 GridLayout layout,
                 OnCeldaListener listener)
    {
        super(context) ;

        //
        this.fil = fil;
        this.col = col;

        //
        GridLayout.LayoutParams params = new GridLayout.LayoutParams() ;
        params.width      = 300 ;
        params.height     = 300 ;
        params.rowSpec    = GridLayout.spec(fil) ;
        params.columnSpec = GridLayout.spec(col) ;
        params.setGravity(Gravity.CENTER_HORIZONTAL) ;
        params.setMargins(35, 35, 20, 20) ;

        // asignamos las propiedades al ImageView
        //setImageDrawable(context.getDrawable(R.drawable.ic_circulo)) ;
        this.setLayoutParams(params) ;
        this.setBackgroundColor(context.getColor(R.color.lightGray));
        this.setOnClickListener((v) -> listener.onClick(fil, col, ficha)) ;

        layout.addView(this) ;
    }

    /**
     * @return
     */
    public int getFicha() { return ficha ; }

    /**
     * @param fic
     */
    public void setFicha(int fic)
    {
        // guardamos la ficha
        this.ficha = fic ;

        //
        if (ficha==1) setImageDrawable(getContext().getDrawable(R.drawable.ic_equis)) ;
        else          setImageDrawable(getContext().getDrawable(R.drawable.ic_circulo)) ;
    }

    /**
     */
    public interface OnCeldaListener
    {
        void onClick (int fil, int col, int ficha) ;
    }
}
