package dk.adamino.rehabilitation.GUI.Model;

import dk.adamino.rehabilitation.BE.Client;
import dk.adamino.rehabilitation.DAL.FirestoreDAO;
import dk.adamino.rehabilitation.DAL.IFirestore;

/**
 * Created by Adamino.
 */
public class ClientModel {

    private static ClientModel instance = null;

    private IFirestore mFirestoreDAO;
    private Client mClient;

    protected ClientModel() {
        mFirestoreDAO = new FirestoreDAO();
//        TODO ALH: Replace!
        String mockUID = "1";
        mClient = mFirestoreDAO.getClientById(mockUID);
    }

    public static ClientModel getInstance() {
        if(instance == null) {
            instance = new ClientModel();
        }
        return instance;
    }

    public Client getLoggedInClient() {
        return mClient;
    }
}
