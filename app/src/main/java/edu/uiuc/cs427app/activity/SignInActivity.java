package edu.uiuc.cs427app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import edu.uiuc.cs427app.R;
import edu.uiuc.cs427app.data.entity.User;
import edu.uiuc.cs427app.data.repository.UserRepository;
import edu.uiuc.cs427app.util.ToastMessageHandler;

/**
 *  Allows 
 *  user to sign in using
 *  email and password.
 */
public class SignInActivity extends AppCompatActivity {
    EditText email, password;
    Button logIn;

    /**
     * Performs initialization of all fragments
     * @param savedInstanceState - bundle reference to restore
     * to a previous state using data store in the bundle -
     * is always null during first time creation of activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //assign variables for accessing values in UI elements
        setContentView(R.layout.activity_sign_in);
        email = findViewById(R.id.userEmail);
        password = findViewById(R.id.userPassword);
        logIn = findViewById(R.id.userLogin);
        
        // Process the intent Payload
        String userEmail = getIntent().getStringExtra("userEmail");
        String logoutMessage = getIntent().getStringExtra("logoutMessage");

        if (logoutMessage != null && !logoutMessage.isEmpty()) {
            runOnUiThread(() -> {Toast.makeText(getApplicationContext(), logoutMessage, Toast.LENGTH_SHORT).show();});
        }

        //Check if email is not empty
        if (userEmail != null && !userEmail.isEmpty()) {
            email.setText(userEmail);
        }

        // adds a on click listener handler
        logIn.setOnClickListener(view -> {
            //Store email and password entered by user in varibles
            String userEmail1 = email.getText().toString().trim();
            String userPassword = password.getText().toString().trim();

            //Base case to check if the email and password are not null
            if (userEmail1.isEmpty() || userPassword.isEmpty()) {
                ToastMessageHandler.popToastMessage(getApplicationContext(),
                        "Email and password are required",
                        Toast.LENGTH_SHORT);
            } 
            //if the details are not empty process the login for user
            else {
                Log.d("LOGIN_ACTIVITY", "Login button was clicked");
                Intent mainIntent = new Intent(SignInActivity.this, MainActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                UserRepository userRepository = new UserRepository(getApplicationContext());

                // check if the entered username and password belongs to a user.
                // This is done by registering an observer that notifies
                // when user information in LiveData gets updated.
                userRepository.login(userEmail1, userPassword).observe(SignInActivity.this, new Observer<User>() {
                    /**
                     * Called when user is got from DB and live data gets created/modified.
                     * @param user -- user entity representing the currently sign in user.
                     */
                    @Override
                    public void onChanged(User user) {
                        //if the email and password does not belong to a user 
                        if (user == null) {
                            runOnUiThread(() ->ToastMessageHandler.popToastMessage(getApplicationContext(),
                                    "Username and/or password don't match",
                                    Toast.LENGTH_LONG));
                        } 
                        //if the email and password are valid
                        else {
                            int userTheme = user.getCurrentTheme();
                            mainIntent.putExtra("userTheme", userTheme);
                            mainIntent.putExtra("userEmail", user.getEmailId());
                            mainIntent.putExtra("userName", user.getLoginName());
                            user.setLoggedIn(true);
                            user.setLastLoggedIn(new Date());
                            userRepository.update(user).addListener(() -> startActivity(mainIntent), ContextCompat.getMainExecutor(getApplicationContext()));
                        }
                    }
                });
            }
        });
    }
}
