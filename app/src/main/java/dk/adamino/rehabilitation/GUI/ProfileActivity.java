package dk.adamino.rehabilitation.GUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import dk.adamino.rehabilitation.BE.Client;
import dk.adamino.rehabilitation.Callbacks.IFirestoreClientCallback;
import dk.adamino.rehabilitation.GUI.Evaluations.MilestoneListActivity;
import dk.adamino.rehabilitation.GUI.Model.FirebaseClientModel;
import dk.adamino.rehabilitation.GUI.Settings.SettingsActivity;
import dk.adamino.rehabilitation.R;

public class ProfileActivity extends AppCompatActivity
        implements IFirestoreClientCallback, IActivity {
    public static final String TAG = "GUI";

    private TextView mName, mPhone, mEmail, mDiagnosis, mGoal;

    private FirebaseClientModel mFirebaseClientModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mFirebaseClientModel = FirebaseClientModel.getInstance();

        setupViews();

        // Load logged in client async
        mFirebaseClientModel.loadLoggedInClientAsync(this);

    }

    @Override
    public void setupViews() {
        mName = findViewById(R.id.txtName);
        mPhone = findViewById(R.id.txtPhone);
        mEmail = findViewById(R.id.txtEmail);
        mDiagnosis = findViewById(R.id.txtDiagnosis);
        mGoal = findViewById(R.id.txtGoal);
    }

    /**
     * When data is loaded, set client information
     */
    private void setClientInformation(Client loggedInClient) {
        // Client info
        mName.setText(loggedInClient.fullName);
        mPhone.setText(loggedInClient.phone);
        mEmail.setText(loggedInClient.email);

        // Rehabilitation Plan
        mDiagnosis.setText(loggedInClient.rehabilitationPlan.diagnosis);
        mGoal.setText(loggedInClient.rehabilitationPlan.goal);

        Log.d("LoggedInClient", loggedInClient.rehabilitationPlan.exerciseIds.get(0));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_profile, menu);
        // Hide menu title (Takes up too much space!)
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
            case R.id.exercises:
                Intent exerciseIntent = ExerciseListActivity.newIntent(this);
                startActivity(exerciseIntent);
                return true;
            case R.id.milestones:
                Intent milestonesIntent = MilestoneListActivity.newIntent(this);
                startActivity(milestonesIntent);
                return true;
            case R.id.settings:
                startActivity(new Intent(SettingsActivity.newIntent(this)));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClientResponse(Client clientFound) {
        setClientInformation(clientFound);
    }

    /**
     * Create Intent to navigate to this activity
     * @param context
     * @return
     */
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, ProfileActivity.class);
        return intent;
    }
}
