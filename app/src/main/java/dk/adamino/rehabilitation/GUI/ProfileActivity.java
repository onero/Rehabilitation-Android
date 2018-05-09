package dk.adamino.rehabilitation.GUI;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import dk.adamino.rehabilitation.BE.Client;
import dk.adamino.rehabilitation.Callbacks.IFirestoreCallback;
import dk.adamino.rehabilitation.GUI.Model.FirebaseClientModel;
import dk.adamino.rehabilitation.R;

public class ProfileActivity extends AppCompatActivity
        implements IFirestoreCallback, IActivity {
    public static final String TAG = "GUI";

    private TextView mName, mPhone, mEmail, mDiagnosis, mGoal;

    private FirebaseClientModel mFirebaseClientModel;
    private NotificationService mNotificationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mFirebaseClientModel = FirebaseClientModel.getInstance();

        setupViews();

        // Load logged in client async
        mFirebaseClientModel.loadLoggedInClientAsync(this);

        // Create reference to Notification service
        mNotificationService = new NotificationService();

        // TODO ALH: Move to ExerciseActivity, when it is implemented!
        // Setup notification alarm
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, NotificationService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + 15000,
                AlarmManager.INTERVAL_DAY,
                pendingIntent);
        Log.d("Alarm", "Alarm ready");
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
