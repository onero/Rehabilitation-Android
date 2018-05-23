package dk.adamino.rehabilitation.GUI.Evaluations;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import dk.adamino.rehabilitation.BE.Milestone;
import dk.adamino.rehabilitation.BE.Visit;
import dk.adamino.rehabilitation.GUI.IActivity;
import dk.adamino.rehabilitation.GUI.Model.MilestoneModel;
import dk.adamino.rehabilitation.R;

public class EvaluationDetailActivity extends AppCompatActivity implements IActivity {

    private TextView mVisitDate, mMilestoneTitle, mPurpose, mNote;

    private MilestoneModel mMilestoneModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation_detail);

        setupViews();

        mMilestoneModel = MilestoneModel.getInstance();

        instantiateData();
    }

    @Override
    public void setupViews() {
        mVisitDate = findViewById(R.id.txtVisitDate);
        mMilestoneTitle = findViewById(R.id.txtMilestoneTitle);
        mPurpose = findViewById(R.id.txtPurpose);
        mNote = findViewById(R.id.txtNote);
    }

    /**
     * Setup data for the views
     */
    private void instantiateData() {
        Milestone currentMileStone = mMilestoneModel.getCurrentMileStone();
        Visit currentVisit = mMilestoneModel.getCurrentVisit();
        if (mMilestoneModel.getCurrentMileStone() != null) {
            mMilestoneTitle.setText(currentMileStone.title);
            mPurpose.setText(currentMileStone.purpose);
            mVisitDate.setText(currentVisit.getDateAsUppercaseString());
            mNote.setText(currentVisit.note);
        }
    }

    /**
     * Create Intent to navigate to this activity
     * @param context
     * @return
     */
    public static Intent newIntent(Context context) {
        return new Intent(context, EvaluationDetailActivity.class);
    }
}
