package dk.adamino.rehabilitation.GUI.Evaluations;

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

import dk.adamino.rehabilitation.BE.Visit;
import dk.adamino.rehabilitation.R;

public class VisitListActivity extends AppCompatActivity {

    private RecyclerView mVisitRecyclerView;
    private VisitAdapter mVisitAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_list);

        mVisitRecyclerView = findViewById(R.id.visit_recycler_view);
        // Setup layout manager, to ensure that items can be positioned on the screen
        mVisitRecyclerView.setLayoutManager(new LinearLayoutManager(this)); // Using Linear to stack vertically

        List<Visit> visits = new ArrayList<>();
        Visit test = new Visit();
        test.note = "test";
        visits.add(test);
        updateUI(visits);
    }

    /**
     * Instantiate view
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
            mVisitTitle.setText(mVisit.note);
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

