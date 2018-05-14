package dk.adamino.rehabilitation.GUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dk.adamino.rehabilitation.BE.Client;
import dk.adamino.rehabilitation.BE.Exercise;
import dk.adamino.rehabilitation.Callbacks.IExerciseFirestoreCallback;
import dk.adamino.rehabilitation.Callbacks.IFirestoreCallback;
import dk.adamino.rehabilitation.GUI.Model.FirebaseClientModel;
import dk.adamino.rehabilitation.GUI.Model.FirebaseExerciseModel;
import dk.adamino.rehabilitation.R;

public class YoutubeListActivity extends AppCompatActivity implements IFirestoreCallback, IExerciseFirestoreCallback{

    private FirebaseExerciseModel mFirebaseExerciseModel = FirebaseExerciseModel.getInstance();
    private FirebaseClientModel mFirebaseClientModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirebaseClientModel = FirebaseClientModel.getInstance();

        setContentView(R.layout.youtube_recycler);
        instanciateRecyclerView();

        mFirebaseClientModel.loadLoggedInClientAsync(this);

    }

    public void instanciateRecyclerView() {
        final RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /**
         * Incredible recyclerview adapter
         */
        YoutubeRecyclerViewAdapter adapter = new YoutubeRecyclerViewAdapter();

        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                Intent profileIntent = ProfileActivity.newIntent(this);
                startActivity(profileIntent);
                return true;
            case R.id.contact:
                Intent contactIntent = ContactActivity.newIntent(this);
                startActivity(contactIntent);
                return true;
            case R.id.signout:
                mFirebaseClientModel.logout();
                Toast.makeText(this, "You're logged out", Toast.LENGTH_SHORT).show();
                Intent logoutIntent = LoginActivity.newIntent(this);
                startActivity(logoutIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_exercise, menu);
        // Hide menu title (Takes up too much space!)
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        return true;
    }

    /**
     * Create Intent to navigate to this activity
     * @param context
     * @return
     */
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, YoutubeListActivity.class);
        return intent;
    }

    @Override
    public void onClientResponse(Client clientFound) {
        Log.d("Tissemand", "onClientResponse");
        mFirebaseExerciseModel.loadExercisesAsync(this, clientFound.rehabilitationPlan.exerciseIds);
    }


    @Override
    public void onExercisesResponse(List<Exercise> exerciseFound) {
        Log.d("Tissemand", "onExercisesResponse");
        mFirebaseExerciseModel.setExercises(exerciseFound);
    }
}
