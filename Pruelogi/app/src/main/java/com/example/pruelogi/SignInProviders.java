package com.example.pruelogi;

import androidx.annotation.NonNull;
import com.firebase.ui.auth.AuthUI;
import java.util.Arrays;
import java.util.List;

public class SignInProviders
{
    public final static int GOOGLE_PROVIDER   = 0 ;
    public final static int FACEBOOK_PROVIDER = 1 ;
    public final static int TWITTER_PROVIDER  = 2 ;
    public final static int GITHUB_PROVIDER   = 3 ;


    private static SignInProviders instance = null ;
    private List<AuthUI.IdpConfig> proveedores ;

    /**
     * Implementamos la clase utilizando el patrón Singleton que nos garantiza
     * que no existirá más de un objeto de tipo SignInProviders en la aplicación.
     */
    private void SignInProviders()
    {
        // definimos la lista de proveedores deseados
        proveedores = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build(),
                new AuthUI.IdpConfig.TwitterBuilder().build(),
                new AuthUI.IdpConfig.GitHubBuilder().build()) ;
    }

    /**
     * @return
     */
    public static SignInProviders getInstance()
    {
        // si la instancia no existe, la creo
        if (instance==null) instance = new SignInProviders() ;

        // devolvemos la instancia
        return instance ;
    }

    /**
     * @param provider
     * @return
     */
    public List<AuthUI.IdpConfig> getProvider(@NonNull int provider)
    {
        return Arrays.asList(proveedores.get(provider)) ;
    }
}