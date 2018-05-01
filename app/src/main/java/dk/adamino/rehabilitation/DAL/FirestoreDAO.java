package dk.adamino.rehabilitation.DAL;

import dk.adamino.rehabilitation.BE.Client;

/**
 * Created by Adamino.
 */
public class FirestoreDAO implements IFirestore {

    @Override
    public Client getClientById(String uid) {
//        TODO ALH: Replace
        Client mockClient = new Client();
        mockClient.uid = "1";
        mockClient.address = "Test Address";
        mockClient.email = "Test@email.dk";
        mockClient.name = "Test Hansen";
        return mockClient;
    }
}
