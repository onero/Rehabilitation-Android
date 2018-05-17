package dk.adamino.rehabilitation.GUI;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import dk.adamino.rehabilitation.Callbacks.IFirebaseAuthenticationCallback;
import dk.adamino.rehabilitation.GUI.Model.FirebaseClientModel;
import dk.adamino.rehabilitation.GUI.Utils.AlarmService;
import dk.adamino.rehabilitation.GUI.Utils.NotificationService;
import dk.adamino.rehabilitation.R;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements IActivity, IFirebaseAuthenticationCallback {
    public static final String TAG = "RehabLogin";

    private FirebaseClientModel mFirebaseClientModel;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView, mLoginFormView;
    private Button mEmailSignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupViews();

        // Create reference to model
        mFirebaseClientModel = FirebaseClientModel.getInstance();

        // Cancel any notification
        NotificationService.cancelNotification();
        // Check if user want's daily notifications
        boolean shouldGetDailyNotifications = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("notifications_daily_exercises", true);
        if (shouldGetDailyNotifications) {
            // Get User preset time (if exists)
            SharedPreferences prefs = getSharedPreferences(getString(R.string.pref_key_set_notification_time), MODE_PRIVATE);
            String restoredTime = prefs.getString(getString(R.string.pref_key_notification_time_value), "");
            AlarmService alarmService = AlarmService.getInstance(this);
            // If user has not set a wish for a specific time
            if (restoredTime.equals("")) {
                // Set daily alarm from current time
                alarmService.setAlarmForOneDay();
            }
        }
    }

    @Override
    public void setupViews() {
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        // Set up the login form.
        mEmailView = findViewById(R.id.email);
        mPasswordView = findViewById(R.id.password);
        mEmailSignInButton = findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setEnabled(false);

        // Listeners
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        // Text watcher for listening to field inputs
        TextWatcher loginTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // check Fields For Empty Values
                checkFieldsForEmptyValues();
            }
        };
        mEmailView.addTextChangedListener(loginTextWatcher);
        mPasswordView.addTextChangedListener(loginTextWatcher);

    }

    /**
     * Check fields for input to enable login button
     */
    private void checkFieldsForEmptyValues() {
        String emailString = mEmailView.getText().toString();
        String passwordString = mPasswordView.getText().toString();

        if (emailString.equals("") || passwordString.equals("") || !isPasswordValid(passwordString)) {
            mEmailSignInButton.setEnabled(false);
        } else {
            mEmailSignInButton.setEnabled(true);
        }
    }

    /**
     * Check provided email validity
     *
     * @param email
     * @return
     */
    // TODO ALH: Refactor to BLL
    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    /**
     * Check password validity
     *
     * @param password
     * @return
     */
    private boolean isPasswordValid(String password) {
        return password.length() > 5;
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        // Validate email
        if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            return;
        }
        // Show a progress spinner, and kick off a background task to
        showProgress(true);
        // perform the user login attempt.
        mFirebaseClientModel.loginWithEmailAndPassword(email, password, this);
    }

    @Override
    public void onClientLoggedIn(String clientUid) {
        showProgress(false);
        mFirebaseClientModel.setCurrentClientUid(clientUid);
        Intent intent = ProfileActivity.newIntent(this);
        startActivity(intent);
    }

    @Override
    public void onFailedLogin(String error) {
        showProgress(false);
        Log.e(TAG, error);
        mEmailView.setError(error);
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Create Intent to navigate to this activity
     *
     * @param context
     * @return
     */
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }
}

