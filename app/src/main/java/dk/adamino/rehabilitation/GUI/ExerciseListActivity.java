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

import java.util.List;

import dk.adamino.rehabilitation.BE.Client;
import dk.adamino.rehabilitation.BE.Exercise;
import dk.adamino.rehabilitation.Callbacks.IExerciseFirestoreCallback;
import dk.adamino.rehabilitation.Callbacks.IFirestoreCallback;
import dk.adamino.rehabilitation.GUI.Model.FirebaseClientModel;
import dk.adamino.rehabilitation.GUI.Model.FirebaseExerciseModel;
import dk.adamino.rehabilitation.R;

public class ExerciseListActivity extends AppCompatActivity implements IActivity, IFirestoreCallback, IExerciseFirestoreCallback{

    private FirebaseExerciseModel mFirebaseExerciseModel;
    private FirebaseClientModel mFirebaseClientModel;
    private ExerciseRecyclerViewAdapter mExerciseAdapter;
    private RecyclerView mExerciseRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_recycler);
        mFirebaseClientModel = FirebaseClientModel.getInstance();
        mFirebaseExerciseModel = FirebaseExerciseModel.getInstance();
        setupViews();
        mFirebaseClientModel.loadLoggedInClientAsync(this);
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
     * Instantiate view
     *
     * @param exercises
     */
    public void updateUI(List<Exercise> exercises) {
        if (mExerciseAdapter == null) {
            mExerciseAdapter = new ExerciseRecyclerViewAdapter(exercises);
            mExerciseRecyclerView.setAdapter(mExerciseAdapter);
        } else {
            mExerciseAdapter.setExercises(exercises);
            mExerciseAdapter.notifyDataSetChanged();
        }

        mExerciseAdapter = new ExerciseRecyclerViewAdapter(exercises);
        mExerciseRecyclerView.setAdapter(mExerciseAdapter);
    }

    /**
     * Create Intent to navigate to this activity
     * @param context
     * @return
     */
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, ExerciseListActivity.class);
        return intent;
    }

    @Override
    public void onClientResponse(Client clientFound) {
        mFirebaseExerciseModel.loadExercisesAsync(this, clientFound.rehabilitationPlan.exerciseIds);
    }


    @Override
    public void onExercisesResponse(List<Exercise> exerciseFound) {
        updateUI(exerciseFound);
    }

    @Override
    public void setupViews() {
        mExerciseRecyclerView = findViewById(R.id.recycler);
        mExerciseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
