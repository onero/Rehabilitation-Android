package dk.adamino.rehabilitation.GUI.Evaluations;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import dk.adamino.rehabilitation.BE.Milestone;
import dk.adamino.rehabilitation.Callbacks.IFirestoreMilestoneCallback;
import dk.adamino.rehabilitation.GUI.ContactActivity;
import dk.adamino.rehabilitation.GUI.ExerciseListActivity;
import dk.adamino.rehabilitation.GUI.IActivity;
import dk.adamino.rehabilitation.GUI.LoginActivity;
import dk.adamino.rehabilitation.GUI.Model.FirebaseClientModel;
import dk.adamino.rehabilitation.GUI.ProfileActivity;
import dk.adamino.rehabilitation.GUI.Settings.SettingsActivity;
import dk.adamino.rehabilitation.R;

public class MilestoneListActivity extends AppCompatActivity implements IActivity, IFirestoreMilestoneCallback {

    private RecyclerView mMilestoneRecyclerView;
    private MilestoneAdapter mMilestoneAdapter;
    private ProgressBar mProgressBar;
    private TextView txtLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milestone_list);

        setupViews();

        // Call Firestore to get data
        FirebaseClientModel.getInstance().getClientMilestones(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_milestones, menu);
        // Hide menu title (Takes up too much space!)
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                Intent profileIntent = ProfileActivity.newIntent(this);
                startActivity(profileIntent);
                return true;
            case R.id.exercises:
                Intent exerciseIntent = ExerciseListActivity.newIntent(this);
                startActivity(exerciseIntent);
                return true;
            case R.id.contact:
                Intent contactIntent = ContactActivity.newIntent(this);
                startActivity(contactIntent);
                return true;
            case R.id.signout:
                FirebaseClientModel.getInstance().logout();
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
    public void setupViews() {
        mMilestoneRecyclerView = findViewById(R.id.evaluation_recycler_view);
        // Setup layout manager, to ensure that items can be positioned on the screen
        LinearLayoutManager layoutManager = new LinearLayoutManager(this); // Using Linear to stack vertically
        // Add a divider between items
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mMilestoneRecyclerView.getContext(),
                layoutManager.getOrientation());
        mMilestoneRecyclerView.addItemDecoration(dividerItemDecoration);
        mMilestoneRecyclerView.setLayoutManager(layoutManager);

        mProgressBar = findViewById(R.id.progressBar);
        // Start out progress bar in spinning mode
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.animate();

        txtLoading = findViewById(R.id.txtLoading);
        // Start out by displaying loading
        txtLoading.setVisibility(View.VISIBLE);
    }

    /**
     * Create Intent to navigate to this activity
     *
     * @param context
     * @return
     */
    public static Intent newIntent(Context context) {
        return new Intent(context, MilestoneListActivity.class);
    }

    /**
     * Instantiate view
     *
     * @param milestones
     */
    public void updateUI(List<Milestone> milestones) {
        if (mMilestoneAdapter == null) {
            mMilestoneAdapter = new MilestoneAdapter(milestones);
            mMilestoneRecyclerView.setAdapter(mMilestoneAdapter);
        } else {
            mMilestoneAdapter.setMilestones(milestones);
            mMilestoneAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onMilestoneResponse(List<Milestone> milestones) {
        // Hide loading info
        mProgressBar.setVisibility(View.INVISIBLE);
        txtLoading.setVisibility(View.INVISIBLE);
        // Send data to ui
        updateUI(milestones);
    }

    private class MilestoneHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mMilestoneTitle;
        private Milestone mMilestone;

        public MilestoneHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_milestone, parent, false));
            itemView.setOnClickListener(this);

            mMilestoneTitle = itemView.findViewById(R.id.milestone_title);
        }

        public void bind(Milestone milestone, int position) {
            mMilestone = milestone;
            // Create title as it should be shown in list
            // Add 1 to position to start at number 1 instead of 0!
            String listEntityTitle = (position + 1) + " - " + milestone.title;
            mMilestoneTitle.setText(listEntityTitle);
        }

        @Override
        public void onClick(View view) {
            Intent visitIntent = VisitListActivity.newIntent(view.getContext(), mMilestone);
            startActivity(visitIntent);
        }
    }

    private class MilestoneAdapter extends RecyclerView.Adapter<MilestoneHolder> {
        private List<Milestone> mMilestones;

        private MilestoneAdapter(List<Milestone> milestones) {
            mMilestones = milestones;
        }

        @Override
        public MilestoneHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            return new MilestoneHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(MilestoneHolder holder, int position) {
            Milestone milestone = mMilestones.get(position);
            holder.bind(milestone, position);
        }

        @Override
        public int getItemCount() {
            return mMilestones.size();
        }

        public void setMilestones(List<Milestone> milestones) {
            mMilestones = milestones;
        }
    }
}
