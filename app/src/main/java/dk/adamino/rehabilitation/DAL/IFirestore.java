package dk.adamino.rehabilitation.DAL;

import java.util.List;

import dk.adamino.rehabilitation.Callbacks.IExerciseFirestoreCallback;
import dk.adamino.rehabilitation.Callbacks.IFirestoreCallback;

/**
 * Created by Adamino.
 */
public interface IFirestore {
    /**
     * Retrieve a client from Firestore
     * @param uid
     * @return Client
     */
    void getClientByIdAsync(String uid, IFirestoreCallback firestoreCallback);
    void getExercisesByIds(List<String> ids, final IExerciseFirestoreCallback exerciseCallback);
    }
