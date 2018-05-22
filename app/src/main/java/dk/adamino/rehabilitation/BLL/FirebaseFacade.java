package dk.adamino.rehabilitation.BLL;

import dk.adamino.rehabilitation.DAL.FirebaseAuthenticate;
import dk.adamino.rehabilitation.DAL.FirestoreDAO;

/**
 * Created by Adamino.
 */
public class FirebaseFacade {

    private static FirebaseFacade sInstance;

    public static FirebaseFacade getInstance() {
        if (sInstance == null) {
            sInstance = new FirebaseFacade();
        }
        return sInstance;
    }

    /**
     * Return the FirestoreDAO
     * @return
     */
    public FirestoreDAO getFirestoreDAO() {
        return FirestoreDAO.getInstance();
    }

    /**
     * Return the FirebaseAuthenticate
     * @return
     */
    public FirebaseAuthenticate getFirebaseAuthenticate() {
        return FirebaseAuthenticate.getInstance();
    }

}
