package dk.adamino.rehabilitation.DAL;

import dk.adamino.rehabilitation.Callbacks.IFirebaseAuthenticationCallback;

/**
 * Created by Adamino.
 */
public interface IFirebaseAuthenticate {

    /***
     * Sign in with provided email and password
     * @param email
     * @param password
     * @param callback
     */
    void signInWithEmailAndPassword(String email, String password, IFirebaseAuthenticationCallback callback);

    /**
     * Signout client
     */
    void signout();
}
