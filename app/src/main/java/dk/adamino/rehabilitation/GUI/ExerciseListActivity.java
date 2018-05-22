package dk.adamino.rehabilitation.GUI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import java.util.ArrayList;
import java.util.List;

import dk.adamino.rehabilitation.BE.Client;
import dk.adamino.rehabilitation.BE.Exercise;
import dk.adamino.rehabilitation.Callbacks.IFirestoreClientCallback;
import dk.adamino.rehabilitation.GUI.Evaluations.MilestoneListActivity;
import dk.adamino.rehabilitation.GUI.Model.FirebaseClientModel;
import dk.adamino.rehabilitation.GUI.Model.FirebaseExerciseModel;
import dk.adamino.rehabilitation.GUI.Settings.SettingsActivity;
import dk.adamino.rehabilitation.R;

public class ExerciseListActivity extends AppCompatActivity implements IActivity, IFirestoreClientCallback {

    private static final String TAG = "ExerciseListActivity";

    private FirebaseExerciseModel mFirebaseExerciseModel;
    private FirebaseClientModel mFirebaseClientModel;

    private ExerciseRecyclerViewAdapter mExerciseAdapter;
    private RecyclerView mExerciseRecyclerView;

    private List<Exercise> mExercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_recycler);

        setupViews();

        // Gets the logged in client to get the exercises merged to the client.
        mFirebaseClientModel = FirebaseClientModel.getInstance();
        mFirebaseClientModel.loadLoggedInClientAsync(this);

        mExercises = new ArrayList<>();

        mFirebaseExerciseModel = FirebaseExerciseModel.getInstance();
        List<Exercise> exercises = mFirebaseExerciseModel.getExercises();
        updateUI(exercises);
    }

    /**
     * Menu bar.
     *
     * @param item
     * @return
     */
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
            case R.id.milestones:
                Intent milestonesIntent = MilestoneListActivity.newIntent(this);
                startActivity(milestonesIntent);
                return true;
            case R.id.signout:
                mFirebaseClientModel.logout();
                Toast.makeText(this, "You're logged out", Toast.LENGTH_SHORT).show();
                Intent logoutIntent = LoginActivity.newIntent(this);
                startActivity(logoutIntent);
                return true;
            case R.id.settings:
                startActivity(new Intent(SettingsActivity.newIntent(this)));
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
    }

    /**
     * Create Intent to navigate to this activity.
     *
     * @param context
     * @return
     */
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, ExerciseListActivity.class);
        return intent;
    }

    /**
     * The client found in Firestore.
     *
     * @param clientFound
     */
    @Override
    public void onClientResponse(Client clientFound) {
        mExercises = clientFound.rehabilitationPlan.exercises;
        updateUI(mExercises);
    }

    @Override
    public void setupViews() {
        mExerciseRecyclerView = findViewById(R.id.recycler);
        mExerciseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    /**
     * Holder for the RecyclerView.
     */
    private class ExerciseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitle, txtItemNr;
        private ImageView mImage;
        private Exercise mExercise;

        public ExerciseHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_exercise, parent, false));
            itemView.setOnClickListener(this);

            mTitle = itemView.findViewById(R.id.txtTitle);
            mImage = itemView.findViewById(R.id.imgView);
            txtItemNr = itemView.findViewById(R.id.txtItemNr);
        }

        /**
         * Sets the text in each bind.
         *
         * @param exercise
         * @param position
         */
        public void bind(Exercise exercise, int position) {
            mExercise = exercise;
            mTitle.setText(mExercise.getTitle());
            InputStream imageStream = itemView.getResources().openRawResource(R.raw.youtube_img);
            Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
            mImage.setImageBitmap(bitmap);
            txtItemNr.setText((position + 1) + ".");
        }

        /**
         * When clicking on a certain exercise we change the activity to YoutubeActivity with the
         * exercise.
         *
         * @param v
         */
        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            Intent intent = YoutubeActivity.newIntent(context, mExercise);
            context.startActivity(intent);
        }
    }


    /**
     * ExerciseRecyclerViewAdapter
     */
    private class ExerciseRecyclerViewAdapter extends RecyclerView.Adapter<ExerciseHolder> {
        private List<Exercise> mExercises;

        public ExerciseRecyclerViewAdapter(List<Exercise> exercises) {
            mExercises = exercises;
        }

        /**
         * Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to
         * represent an item.
         *
         * @param parent
         * @param viewType
         * @return
         */
        @Override
        public ExerciseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            return new ExerciseHolder(layoutInflater, parent);
        }

        /**
         * Called by RecyclerView to display the data at the specified position.
         * This method should update the contents of the itemView to reflect the item at the given position.
         *
         * @param holder
         * @param position
         */
        @Override
        public void onBindViewHolder(ExerciseHolder holder, int position) {
            Exercise exercise = mExercises.get(position);
            holder.bind(exercise, position);
        }

        /**
         * Returns the total number of items in the data set held by the adapter.
         *
         * @return
         */
        @Override
        public int getItemCount() {
            return mExercises.size();
        }

        /**
         * Sets the exercises in the adapter.
         *
         * @param exercises
         */
        public void setExercises(List<Exercise> exercises) {
            mExercises = exercises;
        }
    }
}
