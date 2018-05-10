package dk.adamino.rehabilitation.DAL;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import dk.adamino.rehabilitation.Callbacks.IFirebaseAuthenticationCallback;

/**
 * Created by Adamino.
 */
public class FirebaseAuthenticate implements IFirebaseAuthenticate {

    private static final String TAG = "FirebaseAuth";

    private FirebaseAuth mAuth;

    public FirebaseAuthenticate() {
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void signInWithEmailAndPassword(String email, String password, final IFirebaseAuthenticationCallback callback) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            callback.onClientLoggedIn(mAuth.getCurrentUser().getUid());
                        } else {
                            callback.onFailedLogin(task.getException().getMessage());
                        }
                    }
                });
    }

    @Override
    public void signout() {
        mAuth.signOut();
    }
}
