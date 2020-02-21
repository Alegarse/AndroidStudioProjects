package com.ajsb.a3enraya;

/**
 * Antonio José Sánchez Bujaldón
 * Programación Multimedia y de dispositivos Móviles
 * curso 2019/20
 */

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity
{
    public final static int DIM = 3 ;

    //
    private GridLayout gridLayout ;
    private TextView turno_panel ;

    //
    private List<Celda> celdas = null ;
    private PlayingObserver isPlaying ;
    private int fichas = 9 ;
    private int turno  = 1 ;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // instanciar los elementos del layout
        turno_panel = findViewById(R.id.turno) ;
        gridLayout  = findViewById(R.id.gridLayout) ;

        // creamos el objeto observable
        isPlaying = new PlayingObserver() ;
        isPlaying.addObserver((observable, o) ->
        {
            String mensaje = "" ;
            AlertDialog dialog;
            MaterialAlertDialogBuilder builder;

            // definimos las características del diálogo
            builder = new MaterialAlertDialogBuilder(GameActivity.this, R.style.miDialogo);

            // definimos el mensaje
            /*if (fichas==0) mensaje = getString(R.string.label_empate) ;
            else mensaje = getString(R.string.label_ganador) + turno + "!!" ;*/
            mensaje = (fichas==0)?getString(R.string.label_empate):
                                  getString(R.string.label_ganador) + turno + "!!" ;
            //
            builder.setMessage(mensaje);
            builder.setPositiveButton("Aceptar", (dialogInterface, i) ->
            {
                finish();
                return;
            });

            // crear y mostrar el diálogo
            dialog = builder.create();
            dialog.show();

        });

        // crear y añadir las celdas al Layout
        celdas = new ArrayList<Celda>() ;
        for(int fil=0; fil < DIM; fil++)
            for (int col=0; col < DIM; col++)
                celdas.add(new Celda(this, fil, col, gridLayout, (f, c, fic) -> celdaClicked(f,c,fic))) ;
    }

    /**
     * Sobrecargamos el método onBackPressed para evitar
     * que, durante la partida, el jugador pueda volver
     * a la pantalla de inicio.
     */
    @Override
    public void onBackPressed()
    {
        return ;
    }

    /**
     * Devolverá la ficha que se encuentra en la celda
     * indicada por los parámetros fil y col. Accedemos
     * a una celda, con fila fil y columna col, del
     * tablero 2D en el array unidimensional celdas,
     * utilizando la siguiente ecuación: (fil*DIM) + col
     *
     *
     * @param fil
     * @param col
     * @return
     */
    private Celda getCelda(int fil, int col)
    {
        return celdas.get((fil* DIM) + col) ;
    }

    /**
     * @param fil
     * @param col
     * @param ficha
     */
    private void celdaClicked(int fil, int col, int ficha)
    {
        if (ficha==0)
        {
            // asignamos la ficha a la celda
            getCelda(fil, col).setFicha(turno) ;

            // decrementamos el número de fichas
            fichas-- ;

            // comprobar si hay una situación de éxito
            if (comprobarExito(fil, col) || (fichas==0))
                isPlaying.setPlaying(false) ;

            // cambiar el turno
            turno = (turno%2) + 1 ;

            // mostrar en pantalla a qué jugador le toca
            turno_panel.setText(getText(R.string.label_turno) + " " + turno) ;
        }
    }

    /**
     * @param fil
     * @param col
     * @return
     */
    private boolean comprobarExito(int fil, int col)
    {
        // comprobar la fila
        if (comprobarFila(fil, col)) return true ;

        // comprobar la columna
        if (comprobarColumna(fil, col)) return true ;

        // comprobar la diagonal derecha
        if (comprobarDiagonalDer()) return true ;
        //if (((fil==0 && col==2) || (fil==1 && col==1) || (fil==2 && col==0)) && comprobarDiagonalDer()) return true ;

        // comprobar la diagonal izquierda
        if ((fil==col) && comprobarDiagonalIzq()) return true ;

        // no hay éxito
        return false ;
    }

    /**
     * @param fil
     * @param col
     * @return
     */
    private boolean comprobarFila(int fil, int col)
    {
        int i = 0 ;
        boolean isWinner = true ;

        while ((isWinner) && (i < DIM))
        {
            isWinner = (getCelda(fil,i).getFicha() == turno) ;
            i++ ;
        }

        return isWinner ;
    }

    /**
     * @param fil
     * @param col
     * @return
     */
    private boolean comprobarColumna(int fil, int col)
    {
        int i = 0 ;
        boolean isWinner = true ;

        while ((isWinner) && (i < DIM))
        {
            isWinner = (getCelda(i,col).getFicha() == turno) ;
            i++ ;
        }

        return isWinner ;
    }

    /**
     * @return
     */
    private boolean comprobarDiagonalDer()
    {
        int f = 2, c = 0 ;
        boolean isWinner = true ;

        while ((isWinner) && (f >= 0) && (c < DIM))
        {
            isWinner = (getCelda(f,c).getFicha() == turno) ;
            f-- ; c++ ;
        }

        return isWinner ;
    }

    /**
     * @return
     */
    private boolean comprobarDiagonalIzq()
    {
        int f = 0, c = 0 ;
        boolean isWinner = true ;

        while ((isWinner) && (f < DIM) && (c < DIM))
        {
            isWinner = (getCelda(f,c).getFicha() == turno) ;
            f++ ; c++ ;
        }

        return isWinner ;
    }
}
