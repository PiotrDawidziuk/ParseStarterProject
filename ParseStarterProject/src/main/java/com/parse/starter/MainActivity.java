/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  Boolean signUpModeActive = true;
  TextView loginTextView;

  @Override
  public void onClick(View view) {
    if (view.getId() == R.id.loginTextView) {

      Button signUpButton = findViewById(R.id.signUpButton);

      if (signUpModeActive) {
        signUpModeActive = false;
        signUpButton.setText("Login");
        loginTextView.setText("or, Sign Up");
      } else {
        signUpModeActive = true;
        signUpButton.setText("Sign Up");
        loginTextView.setText("or, Login");
      }

    }
  }

  public void signUpClicked(View view) {
    EditText usernameEditText = findViewById(R.id.usernameEditText);
    EditText passwordEditText = findViewById(R.id.passwordEditText);

    if (usernameEditText.getText().toString().matches("") || passwordEditText.getText().toString().matches("")) {
      Toast.makeText(this, "A username and a password are required.",Toast.LENGTH_SHORT).show();

    } else {
      if (signUpModeActive) {
        ParseUser user = new ParseUser();
        user.setUsername(usernameEditText.getText().toString());
        user.setPassword(passwordEditText.getText().toString());

        user.signUpInBackground(new SignUpCallback() {
          @Override
          public void done(ParseException e) {
            if (e == null) {
              Log.i("Signup", "Success");
            } else {
              Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
          }
        });
      } else {
        // Login
        ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
          @Override
          public void done(ParseUser user, ParseException e) {
            if (user != null) {
              Log.i("Login","ok!");
            } else {
              Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
          }
        });
      }
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    loginTextView = findViewById(R.id.loginTextView);
    loginTextView.setOnClickListener(this);

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

}