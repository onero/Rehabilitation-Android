package dk.adamino.rehabilitation.GUI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

import dk.adamino.rehabilitation.BE.Client;
import dk.adamino.rehabilitation.Callbacks.IFirestoreCallback;
import dk.adamino.rehabilitation.GUI.Model.FirebaseClientModel;
import dk.adamino.rehabilitation.R;

public class ProfileActivity extends AppCompatActivity implements IFirestoreCallback {
    public static final String TAG = "GUI";

    private TextView mName, mPhone, mEmail, mDiagnosis;

    private FirebaseClientModel mClientModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mClientModel = FirebaseClientModel.getInstance();

        mName = findViewById(R.id.txtName);
        mPhone = findViewById(R.id.txtPhone);
        mEmail = findViewById(R.id.txtEmail);
        mDiagnosis = findViewById(R.id.txtDiagnosis);

        // Load logged in client async
        mClientModel.loadLoggedInClientAsync(this);
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        // Hide menu title (Takes up too much space!)
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        return true;
    }

    @Override
    public void onClientResponse(Client clientFound) {
        setClientInformation(clientFound);
    }
}
