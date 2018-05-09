package dk.adamino.rehabilitation.Callbacks;

/**
 * Created by Adamino.
 */
public interface IFirebaseAuthenticationCallback {

    /***
     * Handle client uid from login
     * @param clientUid
     */
    void onClientLoggedIn(String clientUid);
}
