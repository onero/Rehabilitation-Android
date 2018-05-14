package dk.adamino.rehabilitation.GUI.Evaluations;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import dk.adamino.rehabilitation.BE.Milestone;
import dk.adamino.rehabilitation.Callbacks.IFirestoreMilestoneCallback;
import dk.adamino.rehabilitation.GUI.Model.FirebaseClientModel;
import dk.adamino.rehabilitation.R;

public class EvaluationActivity extends AppCompatActivity implements IFirestoreMilestoneCallback {

    private RecyclerView mEvaluationRecyclerView;
    private EvaluationAdapter mEvaluationAdapter;
    private ProgressBar mProgressBar;
    private TextView txtLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);

        mEvaluationRecyclerView = findViewById(R.id.evaluation_recycler_view);
        // Setup layout manager, to ensure that items can be positioned on the screen
        mEvaluationRecyclerView.setLayoutManager(new LinearLayoutManager(this)); // Using Linear to stack vertically

        mProgressBar = findViewById(R.id.progressBar);
        // Start out progress bar in spinning mode
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.animate();

        txtLoading = findViewById(R.id.txtLoading);
        // Start out by displaying loading
        txtLoading.setVisibility(View.VISIBLE);

        // Call Firestore to get data
        FirebaseClientModel.getInstance().getClientMilestones(this);
    }

    /**
     * Instantiate view
     * @param milestones
     */
    public void updateUI(List<Milestone> milestones) {
        if (mEvaluationAdapter == null) {
            mEvaluationAdapter = new EvaluationAdapter(milestones);
            mEvaluationRecyclerView.setAdapter(mEvaluationAdapter);
        } else {
            mEvaluationAdapter.setMilestones(milestones);
            mEvaluationAdapter.notifyDataSetChanged();
        }

        mEvaluationAdapter = new EvaluationAdapter(milestones);
        mEvaluationRecyclerView.setAdapter(mEvaluationAdapter);
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
            // TODO ALH: Navigate to visit list!
        }
    }

    private class EvaluationAdapter extends RecyclerView.Adapter<MilestoneHolder> {
        private List<Milestone> mMilestones;

        private EvaluationAdapter(List<Milestone> milestones) {
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
