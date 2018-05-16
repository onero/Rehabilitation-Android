package dk.adamino.rehabilitation.GUI.Model;

import dk.adamino.rehabilitation.Callbacks.IFirebaseAuthenticationCallback;
import dk.adamino.rehabilitation.Callbacks.IFirestoreClientCallback;
import dk.adamino.rehabilitation.Callbacks.IFirestoreMilestoneCallback;
import dk.adamino.rehabilitation.DAL.FirebaseAuthenticate;
import dk.adamino.rehabilitation.DAL.FirestoreDAO;
import dk.adamino.rehabilitation.DAL.IFirebaseAuthenticate;
import dk.adamino.rehabilitation.DAL.IFirestore;

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

    /**
     * Set the id of thecurrent logged in cluent
     * @param currentClientUid
     */
    public void setCurrentClientUid(String currentClientUid) {
        mCurrentClientUid = currentClientUid;
    }

    /**
     * Get current client milestones
     * @param callback
     * @return
     */
    public void getClientMilestones(IFirestoreMilestoneCallback callback) {
        mFirestoreDAO.getMilestonesByClientId(mCurrentClientUid, callback);
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
    public void loadLoggedInClientAsync(IFirestoreClientCallback response) {
        mFirestoreDAO.getClientById(mCurrentClientUid, response);
    }
}
