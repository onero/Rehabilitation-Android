package dk.adamino.rehabilitation.GUI;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import dk.adamino.rehabilitation.GUI.Evaluations.MilestoneListActivity;
import dk.adamino.rehabilitation.GUI.Model.FirebaseClientModel;
import dk.adamino.rehabilitation.GUI.Settings.SettingsActivity;
import dk.adamino.rehabilitation.R;

public class ContactActivity extends AppCompatActivity implements IActivity {

    private TextView txtAddress;
    private FirebaseClientModel mFirebaseClientModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        mFirebaseClientModel = FirebaseClientModel.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_contact, menu);
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

    /**
     * Create Intent to navigate to this activity
     * @param context
     * @return
     */
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, ContactActivity.class);
        return intent;
    }

    @Override
    public void setupViews() {
        txtAddress = findViewById(R.id.txtAddress);
    }

    /**
     * Navigate to address
     * @param view
     */
    public void onAddressClicked(View view) {
        // TODO ALH: When more locations is a possibility, make the parsing dynamic!
        Intent navigationIntent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("google.navigation:q=an+Finsensgade 35+Esbjerg"));
        startActivity(navigationIntent);
    }
}
