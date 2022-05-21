package vpj.tucarnetdigital.com.Data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import vpj.tucarnetdigital.com.Data.Model.User;

public class SessionPreferences {
    private static final String PREFS_NAME = "SESSION";
    //Constantes session
    private  static  final  String PREF_NOMBRE="PREF_NAME";
    private  static  final  String PREF_ID="PREF_ID";
    private  static  final  String PREF_EMAIL="PREF_EMAIL";
    private  static  final  String PREF_SESSION="PREF_SESSION";

    private final SharedPreferences prefs;

    public SessionPreferences(Context ctx) {
        prefs = ctx.getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public boolean estaLogeado(){
        return prefs.getBoolean(PREF_SESSION, false);
    }

    public void guardarUsuario(User user){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREF_NOMBRE, user.getName());
        editor.putString(PREF_EMAIL, user.getEmail());
        editor.putInt(PREF_ID, user.getId());
        editor.putBoolean(PREF_SESSION, true);


        editor.apply();
    }

    public void cerrarSesion(){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREF_NOMBRE, null);
        editor.putString(PREF_EMAIL, null);
        editor.putInt(PREF_ID, 0);
        editor.putBoolean(PREF_SESSION, false);


        editor.apply();
    }

    public User getUsuario(){
        User objUser = new User();
        objUser.setName(prefs.getString(PREF_NOMBRE, ""));
        objUser.setEmail(prefs.getString(PREF_EMAIL, ""));
        objUser.setId(prefs.getInt(PREF_ID, 0));

        return objUser;
    }
}
