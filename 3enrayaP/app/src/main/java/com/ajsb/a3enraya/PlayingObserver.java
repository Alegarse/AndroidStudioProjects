package com.ajsb.a3enraya;

/**
 * Antonio José Sánchez Bujaldón
 * Programación Multimedia y de dispositivos Móviles
 * curso 2019/20
 */

import java.util.Observable;

public class PlayingObserver extends Observable
{
    private boolean isPlaying = true ;

    /**
     * @return
     */
    public boolean isPlaying() { return isPlaying ; }

    /**
     * @param playing
     */
    public void setPlaying(boolean playing)
    {
        this.isPlaying = playing ;

        // fijar y notificar el cambio
        setChanged() ;
        notifyObservers() ;
    }

}
