package dk.adamino.rehabilitation.GUI.Model;

import dk.adamino.rehabilitation.BE.Client;
import dk.adamino.rehabilitation.DAL.FirestoreDAO;
import dk.adamino.rehabilitation.DAL.IFirestore;
import dk.adamino.rehabilitation.GUI.IFirestoreResponse;

/**
 * Created by Adamino.
 */
public class ClientModel {

    private static ClientModel instance = null;

    private IFirestore mFirestoreDAO;
    private Client mClient;

    protected ClientModel() {
        mFirestoreDAO = new FirestoreDAO();

    }

    public static ClientModel getInstance() {
        if(instance == null) {
            instance = new ClientModel();
        }
        return instance;
    }

    public void getLoggedInClient(IFirestoreResponse response) {
        //        TODO ALH: Replace!
        String adamUID = "7fdjYuWZC1ZQD4npgb1YG3kfNK02";
        mFirestoreDAO.getClientById(adamUID, response);
    }
}
