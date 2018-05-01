package dk.adamino.rehabilitation.GUI.Model;

import dk.adamino.rehabilitation.Callbacks.IFirestoreCallback;
import dk.adamino.rehabilitation.DAL.FirestoreDAO;
import dk.adamino.rehabilitation.DAL.IFirestore;

/**
 * Created by Adamino.
 */
public class FirebaseClientModel {

    private static FirebaseClientModel instance = null;

    private IFirestore mFirestoreDAO;

    protected FirebaseClientModel() {
        mFirestoreDAO = new FirestoreDAO();

    }

    public static FirebaseClientModel getInstance() {
        if(instance == null) {
            instance = new FirebaseClientModel();
        }
        return instance;
    }

    /**
     * Get currently logged in client
     * @param response
     */
    public void loadLoggedInClientAsync(IFirestoreCallback response) {
        // TODO ALH: Replace!
        String adamUID = "7fdjYuWZC1ZQD4npgb1YG3kfNK02";
        mFirestoreDAO.getClientByIdAsync(adamUID, response);
    }
}
