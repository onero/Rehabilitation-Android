package dk.adamino.rehabilitation.GUI.Model;

import dk.adamino.rehabilitation.Callbacks.IFirebaseAuthenticationCallback;
import dk.adamino.rehabilitation.Callbacks.IFirestoreCallback;
import dk.adamino.rehabilitation.DAL.FirebaseAuthenticate;
import dk.adamino.rehabilitation.DAL.FirestoreDAO;
import dk.adamino.rehabilitation.DAL.IFirestore;
import dk.adamino.rehabilitation.DAL.IFirebaseAuthenticate;

/**
 * Created by Adamino.
 */
public class FirebaseClientModel {

    private static FirebaseClientModel instance = null;

    private IFirebaseAuthenticate mIFirebaseAuthenticate;
    private IFirestore mFirestoreDAO;
    private String mCurrentClientUid;

    private FirebaseClientModel() {
        mFirestoreDAO = new FirestoreDAO();
        mIFirebaseAuthenticate = new FirebaseAuthenticate();
    }

    public static FirebaseClientModel getInstance() {
        if(instance == null) {
            instance = new FirebaseClientModel();
        }
        return instance;
    }

    public void setCurrentClientUid(String currentClientUid) {
        mCurrentClientUid = currentClientUid;
    }

    /**
     * Logout client
     */
    public void logout() {
        mCurrentClientUid = null;
        mIFirebaseAuthenticate.signout();
    }

    /**
     * Login user
     * @param email
     * @param password
     */
    public void loginWithEmailAndPassword(String email, String password, IFirebaseAuthenticationCallback callback) {
        mIFirebaseAuthenticate.signInWithEmailAndPassword(email, password, callback);
    }

    /***
     * Load currently loggedin client
     * @param response
     */
    public void loadLoggedInClientAsync(IFirestoreCallback response) {
        mFirestoreDAO.getClientByIdAsync(mCurrentClientUid, response);
    }
}
