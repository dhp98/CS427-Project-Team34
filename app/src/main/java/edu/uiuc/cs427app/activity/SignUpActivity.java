package edu.uiuc.cs427app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import edu.uiuc.cs427app.R;
import edu.uiuc.cs427app.data.entity.User;
import edu.uiuc.cs427app.data.repository.UserRepository;
import edu.uiuc.cs427app.util.ToastMessageHandler;

/**
 * Allows end user to sign up for using the application
 */
public class SignUpActivity extends AppCompatActivity {
    EditText userName, email, password, confirmPassword;
    Button signUp;

    /**
     * Performs initialization of all fragments
     *
     * @param savedInstanceState - bundle reference to restore
     *                           to a previous state using data store in the bundle -
     *                           is always null during first time creation of activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //assign variables for accessing values in UI elements
        email = findViewById(R.id.userEmail);
        password = findViewById(R.id.userPassword);
        confirmPassword = findViewById(R.id.confirmPassword);
        signUp = findViewById(R.id.signUp);

        // register a listener to handle the click events of sign up button
        signUp.setOnClickListener(new View.OnClickListener() {
            /**
             * Invoked when sign up button gets clicked
             * Leverage the same to save the registering user
             * information to DB - once entries are validated
             *
             * @param view
             */
            @Override
            public void onClick(View view) {
                // Initialize user model
                User user = new User();
                //Storing the values from UI to the model
                // Split email by the ampersand `@` character.
                // Edge case might be that the email provided contains multiple `@` characters.
                // In that case, the username can be obtained by splitting by `@` character and joining
                // all the elements of the array except the last one. This handles the normal case perfectly as well
                user.setEmailId(email.getText().toString().trim());
                user.setPassword(password.getText().toString().trim());
                user.setLoginName(email.getText().toString());
                user.setCreatedOn(new Date());
                user.setLastModified(new Date());
                user.setCurrentTheme(0);
                user.setLoggedIn(false);

                //if the details entered by the user are valid, add the model to the database
                if (isValidSignUp(user, confirmPassword.getText().toString().trim())) {
                    UserRepository userRepository = new UserRepository(getApplicationContext());
                    new Thread(() -> {
                        userRepository.insert(user);
                        runOnUiThread(() -> {
                            ToastMessageHandler.popToastMessage(getApplicationContext(),
                                    "Signed up successfully!",
                                    Toast.LENGTH_LONG);
                        });
                        startActivity(new Intent(SignUpActivity.this, SignInActivity.class).putExtra("userEmail", user.getEmailId()));
                    }).start();
                }
                //if the details entered by the user are invalid, do not add the model to the database
                else {
                    ToastMessageHandler.popToastMessage(getApplicationContext(),
                            "Something is not right. Please double check all the fields",
                            Toast.LENGTH_LONG);
                }
            }
        });
    }

    /**
     * Method for validating the inputs given by the user.
     *
     * @param userEntity      user data that is being checked for validity this data is user input in the sign up window
     * @param confirmPassword user input password that is being checked for validity
     * @return boolean indicating whether the sign up info is valid.
     */
    private Boolean isValidSignUp(User userEntity, String confirmPassword) {
        if (userEntity.getLoginName().isEmpty() || userEntity.getPassword().isEmpty()) {
            return false;
        } else return userEntity.getPassword().equals(confirmPassword);
    }

}