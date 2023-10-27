package edu.uiuc.cs427app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import edu.uiuc.cs427app.R;
import edu.uiuc.cs427app.activity.SignInActivity;
import edu.uiuc.cs427app.activity.SignUpActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Landing page based on which the user is redirected to login or signup based on his choice
 */
public class MainActivityFrontPage extends AppCompatActivity implements View.OnClickListener {
    /**
     * Render landing screen layout with login and signup buttons
     *
     * @param savedInstanceState If you save the state of the application in a bundle (typically
     *                           non-persistent, dynamic data in onSaveInstanceState), it can be passed back to onCreate if
     *                           the activity needs to be recreated (e.g., orientation change) so that you don't lose this
     *                           prior information. If no data was supplied, savedInstanceState is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_front_page);
        // Setting variables for login and signup
        Button signup = findViewById(R.id.gotosignupbutton);
        Button login = findViewById(R.id.loginbutton);
        signup.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    /**
     * Click handler method for signup and login buttons.
     * When either of them is clicked, create appropriate intent and redirect to Signup or SignIn Activity
     *
     * @param view Current Android layout view in context
     */
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            //case for redirecting user to signup
            case R.id.gotosignupbutton:
                intent = new Intent(this, SignUpActivity.class);
                intent.putExtra("fromStartScreen", true);
                startActivity(intent);
                break;
            //case for redirecting user to login
            case R.id.loginbutton:
                intent = new Intent(this, SignInActivity.class);
                startActivity(intent);
                break;
        }

    }
}



