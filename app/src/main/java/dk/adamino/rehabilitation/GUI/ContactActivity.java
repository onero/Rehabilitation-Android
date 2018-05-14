package dk.adamino.rehabilitation.GUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import dk.adamino.rehabilitation.GUI.Evaluations.MilestoneListActivity;
import dk.adamino.rehabilitation.GUI.Model.FirebaseClientModel;
import dk.adamino.rehabilitation.R;

public class ContactActivity extends AppCompatActivity {

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
        // Hide menu title (Takes up too much space!)
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                Intent contactIntent = ProfileActivity.newIntent(this);
                startActivity(contactIntent);
                return true;
            case R.id.signout:
                mFirebaseClientModel.logout();
                Toast.makeText(this, "You're logged out", Toast.LENGTH_SHORT).show();
                Intent logoutIntent = LoginActivity.newIntent(this);
                startActivity(logoutIntent);
                return true;
            case R.id.milestones:
                Intent milestonesIntent = MilestoneListActivity.newIntent(this);
                startActivity(milestonesIntent);
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
}
