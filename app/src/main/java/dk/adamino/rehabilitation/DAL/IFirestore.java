package dk.adamino.rehabilitation.DAL;
import java.util.List;
import dk.adamino.rehabilitation.Callbacks.IExerciseFirestoreCallback;
import dk.adamino.rehabilitation.Callbacks.IFirestoreClientCallback;
import dk.adamino.rehabilitation.Callbacks.IFirestoreMilestoneCallback;

/**
 * Created by Adamino.
 */
public interface IFirestore {
    /**
     * Retrieve a client from Firestore
     * @param
     * @return Client
     */
    void getExercisesByIds(List<String> ids, final IExerciseFirestoreCallback exerciseCallback);
    void getClientByIdAsync(String uid, IFirestoreClientCallback firestoreCallback);
    /**
     * Retrieve client milestones
     * @param currentClientUid
     * @param callback
     */
    void getClientMilestones(String currentClientUid, IFirestoreMilestoneCallback callback);
}
