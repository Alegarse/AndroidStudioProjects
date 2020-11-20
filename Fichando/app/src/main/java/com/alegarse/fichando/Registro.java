package com.alegarse.fichando;

import java.io.Serializable;

public class Registro implements Serializable {
    private String ID_REGISTRO, DATOS_HORA,SITUACION;
    private String LOCATION_REG;

    public Registro(String ID_REGISTRO, String SITUACION, String DATOS_HORA, String LOCATION_REG) {
        this.ID_REGISTRO = ID_REGISTRO;
        this.SITUACION = SITUACION;
        this.DATOS_HORA = DATOS_HORA;
        this.LOCATION_REG = LOCATION_REG;
    }

    public Registro(String DATOS_HORA, String LOCATION_REG) {
        this.DATOS_HORA = DATOS_HORA;
        this.LOCATION_REG = LOCATION_REG;
    }

    public String getID_REGISTRO() {
        return ID_REGISTRO;
    }

    public void setID_REGISTRO(String ID_REGISTRO) {
        this.ID_REGISTRO = ID_REGISTRO;
    }

    public String getDATOS_HORA() {
        return DATOS_HORA;
    }

    public void setDATOS_HORA(String DATOS_HORA) {
        this.DATOS_HORA = DATOS_HORA;
    }

    public String getSITUACION() {
        return SITUACION;
    }

    public void setSITUACION(String SITUACION) {
        this.SITUACION = SITUACION;
    }

    public String getLOCATION_REG() {
        return LOCATION_REG;
    }

    public void setLOCATION_REG(String LOCATION_REG) {
        this.LOCATION_REG = LOCATION_REG;
    }
}
