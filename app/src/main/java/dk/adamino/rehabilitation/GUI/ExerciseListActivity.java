package dk.adamino.rehabilitation.GUI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.InputStream;
import java.util.List;
import dk.adamino.rehabilitation.BE.Client;
import dk.adamino.rehabilitation.BE.Exercise;
import dk.adamino.rehabilitation.Callbacks.IExerciseFirestoreCallback;
import dk.adamino.rehabilitation.Callbacks.IFirestoreClientCallback;
import dk.adamino.rehabilitation.GUI.Model.FirebaseClientModel;
import dk.adamino.rehabilitation.GUI.Model.FirebaseExerciseModel;
import dk.adamino.rehabilitation.R;

public class ExerciseListActivity extends AppCompatActivity implements IActivity, IFirestoreClientCallback, IExerciseFirestoreCallback{

    private FirebaseExerciseModel mFirebaseExerciseModel;
    private FirebaseClientModel mFirebaseClientModel;

    private ExerciseRecyclerViewAdapter mExerciseAdapter;
    private RecyclerView mExerciseRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_recycler);

        setupViews();

        mFirebaseClientModel = FirebaseClientModel.getInstance();
        mFirebaseExerciseModel = FirebaseExerciseModel.getInstance();

        mFirebaseClientModel.loadLoggedInClientAsync(this);

        List<Exercise> exercises = mFirebaseExerciseModel.getExercises();
        updateUI(exercises);
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
        Log.d("UpdateUI", "Hit it!");
        if (mExerciseAdapter == null) {
            Log.d("UpdateUI", "If");
            mExerciseAdapter = new ExerciseRecyclerViewAdapter(exercises);
            mExerciseRecyclerView.setAdapter(mExerciseAdapter);
        } else {
            Log.d("UpdateUI", "Else");
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
        Log.d("UpdateUI", "ClientResponse");
        mFirebaseExerciseModel.loadExercisesAsync(this, clientFound.rehabilitationPlan.exerciseIds);
    }

    @Override
    public void onExercisesResponse(List<Exercise> exercisesFound) {
        Log.d("UpdateUI", "ExerciseResponse");
        updateUI(exercisesFound);
    }

    @Override
    public void setupViews() {
        mExerciseRecyclerView = findViewById(R.id.recycler);
        mExerciseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private class ExerciseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mAmount, mTitle, mRepetitions;
        private ImageView mImage;
        private Exercise mExercise;

        public ExerciseHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.row_item, parent, false));
            itemView.setOnClickListener(this);

            mAmount = itemView.findViewById(R.id.txtAmount);
            mTitle = itemView.findViewById(R.id.txtTitle);
            mImage = itemView.findViewById(R.id.imgView);
            mRepetitions = itemView.findViewById(R.id.txtRepetitions);
        }

        public void bind(Exercise exercise) {
            mExercise = exercise;
            mAmount.setText(mExercise.getAmount());
            mTitle.setText(mExercise.getTitle());
            mRepetitions.setText(mExercise.getRepetitions());
            InputStream imageStream = itemView.getResources().openRawResource(R.raw.youtube_img);
            Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
            mImage.setImageBitmap(bitmap);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), mExercise.getAmount(), Toast.LENGTH_SHORT).show();
            Context context = v.getContext();
            Intent intent = new Intent(context, YoutubeActivity.class);
            context.startActivity(intent);
        }
    }

    private class ExerciseRecyclerViewAdapter extends RecyclerView.Adapter<ExerciseHolder> {
        private List<Exercise> mExercises;

        public ExerciseRecyclerViewAdapter(List<Exercise> exercises) {
            mExercises = exercises;
        }

        @Override
        public ExerciseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            return new ExerciseHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(ExerciseHolder holder, int position) {
            Exercise exercise = mExercises.get(position);
            holder.bind(exercise);
        }

        @Override
        public int getItemCount() {
            return mExercises.size();
        }

        public void setExercises(List<Exercise> exercises) {
            mExercises = exercises;
        }
    }
}
