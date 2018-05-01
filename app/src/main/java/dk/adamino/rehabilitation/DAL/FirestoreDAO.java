package dk.adamino.rehabilitation.DAL;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import dk.adamino.rehabilitation.BE.Client;
import dk.adamino.rehabilitation.GUI.IFirestoreResponse;

/**
 * Created by Adamino.
 */
public class FirestoreDAO implements IFirestore {

    private static final String TAG = "DAL";

    private static String USERS_COLLECTION = "Clients";

    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

    @Override
    public void getClientById(String clientUID, final IFirestoreResponse response) {
        DocumentReference docRef = mFirestore.collection(USERS_COLLECTION).document(clientUID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Client client = new Client();
                        client.uid = document.getId();
                        client.fullName = document.getString("fullName");
                        client.email = document.getString("email");
                        client.phone = document.getString("phone");
                        client.address = document.getString("address");
                        response.onClientResponse(client);
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
}
