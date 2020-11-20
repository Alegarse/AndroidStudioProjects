package com.alegarse.fichando;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "session_user";
    String SESSION_MAIL = "session_mail";
    String SESSION_PASS = "session_pass";
    String id, name, mail, pass;

    public SessionManagement (Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(Usuario user) {

        // Guarda sesion de usuario cuando se logea
        id = user.getUid();
        name = user.getNombre();
        editor.putString(SESSION_KEY,name).commit();
        mail = user.getEmail();
        editor.putString(SESSION_MAIL,mail);
        pass = user.getContrasena();
        editor.putString(SESSION_PASS,pass);
    }

    public String getSession () {
        // Devuelve el id de usuario cuya sesión está guardada
        return sharedPreferences.getString(SESSION_KEY, "Usuario");
    }
    public String getMail () {
        // Devuelve el id de usuario cuya sesión está guardada
        return sharedPreferences.getString(SESSION_MAIL, "Mail");
    }
    public String getPass () {
        // Devuelve el id de usuario cuya sesión está guardada
        return sharedPreferences.getString(SESSION_PASS, "Password");
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void removeSession () {
        editor.putString(SESSION_KEY, "Usuario").commit();
    }
}
