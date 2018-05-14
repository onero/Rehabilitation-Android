package dk.adamino.rehabilitation.GUI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dk.adamino.rehabilitation.BE.Milestone;
import dk.adamino.rehabilitation.R;

public class EvaluationActivity extends AppCompatActivity {

    private RecyclerView mEvaluationRecyclerView;
    private EvaluationAdapter mEvaluationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);

        mEvaluationRecyclerView = findViewById(R.id.crime_recycler_view);
        // Setup layout manager, to ensure that items can be positioned on the screen
        mEvaluationRecyclerView.setLayoutManager(new LinearLayoutManager(this)); // Using Linear to stack vertically

        updateUI();
    }

    public void updateUI() {
        // TODO ALH: Get milestones from model!
        List<Milestone> milestones = new ArrayList<>();
        Milestone testMilestone = new Milestone();
        testMilestone.mTitle = "Test";
        testMilestone.mPurpose = "Test Purpose";
        milestones.add(testMilestone);
        Milestone test2Milestone = new Milestone();
        test2Milestone.mTitle = "Test 2";
        test2Milestone.mPurpose = "Test 2 Purpose";
        milestones.add(test2Milestone);

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
    private class MilestoneHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mMilestoneTitle;
        private Milestone mMilestone;

        public MilestoneHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_milestone, parent, false));
            itemView.setOnClickListener(this);

            mMilestoneTitle = itemView.findViewById(R.id.milestone_title);
        }

        public void bind(Milestone milestone) {
            mMilestone = milestone;
            mMilestoneTitle.setText(mMilestone.mTitle);
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
            Milestone crime = mMilestones.get(position);
            holder.bind(crime);
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
