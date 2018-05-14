package dk.adamino.rehabilitation.GUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dk.adamino.rehabilitation.BE.ExerciseInfo;
import dk.adamino.rehabilitation.GUI.Model.FirebaseClientModel;
import dk.adamino.rehabilitation.GUI.Model.YoutubeModel;
import dk.adamino.rehabilitation.R;

public class YoutubeListActivity extends AppCompatActivity {

    private YoutubeModel mYoutubeModel = YoutubeModel.getInstance();
    private FirebaseClientModel mFirebaseClientModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirebaseClientModel = FirebaseClientModel.getInstance();

        setContentView(R.layout.youtube_recycler);
        instanciateRecyclerView();

    }

    public void instanciateRecyclerView() {
        final RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final List<ExerciseInfo> exercises = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            exercises.add(new ExerciseInfo("Exercise: " + i,"Index finger", "15x3"));
        }

        mYoutubeModel.setExerciseInfo(exercises);

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

}
