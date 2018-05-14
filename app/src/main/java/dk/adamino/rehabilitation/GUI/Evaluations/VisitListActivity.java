package dk.adamino.rehabilitation.GUI.Evaluations;

import android.content.Context;
import android.content.Intent;
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
import dk.adamino.rehabilitation.BE.Visit;
import dk.adamino.rehabilitation.R;

public class VisitListActivity extends AppCompatActivity {

    private static Milestone sMilestone;
    private RecyclerView mVisitRecyclerView;
    private VisitAdapter mVisitAdapter;
    private TextView mNoVisits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_list);

        mVisitRecyclerView = findViewById(R.id.visit_recycler_view);
        mNoVisits = findViewById(R.id.txtNoVisits);
        // Setup layout manager, to ensure that items can be positioned on the screen
        mVisitRecyclerView.setLayoutManager(new LinearLayoutManager(this)); // Using Linear to stack vertically

        List<Visit> visits = new ArrayList<>();
        if (sMilestone.visits != null) {
            visits = sMilestone.visits;
            // Hide no visits message
            mNoVisits.setVisibility(View.INVISIBLE);
        } else {
            // Display no visits message
            mNoVisits.setVisibility(View.VISIBLE);
        }
        updateUI(visits);
    }

    /**
     * Instantiate view
     *
     * @param visits
     */
    public void updateUI(List<Visit> visits) {
        if (mVisitAdapter == null) {
            mVisitAdapter = new VisitAdapter(visits);
            mVisitRecyclerView.setAdapter(mVisitAdapter);
        } else {
            mVisitAdapter.setMilestones(visits);
            mVisitAdapter.notifyDataSetChanged();
        }

        mVisitAdapter = new VisitAdapter(visits);
        mVisitRecyclerView.setAdapter(mVisitAdapter);
    }

    /**
     * Create Intent to navigate to this activity
     *
     * @param context
     * @return
     */
    public static Intent newIntent(Context context, Milestone milestone) {
        Intent intent = new Intent(context, VisitListActivity.class);
        // TODO ALH: This can definitely be refactored!
        sMilestone = milestone;
        return intent;
    }

    private class VisitHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mVisitTitle;
        private Visit mVisit;

        public VisitHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_visit, parent, false));
            itemView.setOnClickListener(this);

            mVisitTitle = itemView.findViewById(R.id.visit_title);
        }

        public void bind(Visit visit) {
            mVisit = visit;
            mVisitTitle.setText(mVisit.getDate());
        }

        @Override
        public void onClick(View view) {
            // TODO ALH: Navigate to visit list!
        }
    }

    private class VisitAdapter extends RecyclerView.Adapter<VisitHolder> {
        private List<Visit> mVisits;

        private VisitAdapter(List<Visit> visits) {
            mVisits = visits;
        }

        @Override
        public VisitHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            return new VisitHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(VisitHolder holder, int position) {
            Visit visit = mVisits.get(position);
            holder.bind(visit);
        }

        @Override
        public int getItemCount() {
            return mVisits.size();
        }

        public void setMilestones(List<Visit> visits) {
            mVisits = visits;
        }
    }
}
