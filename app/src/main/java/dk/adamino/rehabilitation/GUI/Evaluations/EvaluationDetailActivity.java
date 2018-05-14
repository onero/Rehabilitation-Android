package dk.adamino.rehabilitation.GUI.Evaluations;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import dk.adamino.rehabilitation.BE.Milestone;
import dk.adamino.rehabilitation.BE.Visit;
import dk.adamino.rehabilitation.R;

public class EvaluationDetailActivity extends AppCompatActivity {

    private static Milestone sMilestone;
    private static Visit sVisit;

    private TextView mVisitDate, mMilestoneTitle, mPurpose, mNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation_detail);

        mVisitDate = findViewById(R.id.txtVisitDate);
        mMilestoneTitle = findViewById(R.id.txtMilestoneTitle);
        mPurpose = findViewById(R.id.txtPurpose);
        mNote = findViewById(R.id.txtNote);

        if (sMilestone != null) {
            mMilestoneTitle.setText(sMilestone.title);
            mVisitDate.setText(sVisit.getDate());
            mPurpose.setText(sMilestone.purpose);
            mNote.setText(sVisit.note);
        }
    }

    /**
     * Create Intent to navigate to this activity
     * @param context
     * @return
     */
    public static Intent newIntent(Context context, Milestone milestone, Visit visit) {
        Intent intent = new Intent(context, EvaluationDetailActivity.class);
        // TODO ALH: Refactor
        sMilestone = milestone;
        sVisit = visit;
        return intent;
    }
}
