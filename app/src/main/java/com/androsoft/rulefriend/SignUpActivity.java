package com.androsoft.rulefriend;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private EditText name;
    private EditText mobileNo;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;

    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name=findViewById(R.id.name);
        mobileNo=findViewById(R.id.mobileNo);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        confirmPassword=findViewById(R.id.confirmPassword);

        signUpButton=findViewById(R.id.user_sign_up_button);

        signUpButton.setOnClickListener(v ->
        {

            if(password.getText().toString().equals(confirmPassword.getText().toString())) {
                BackgroundTask backgroundTask = new BackgroundTask(this);
                backgroundTask.execute("register", email.getText().toString(),
                        name.getText().toString(),
                        mobileNo.getText().toString(),
                        password.getText().toString());
               // startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
            }

            else
            {
                confirmPassword.setError("Password is not matching");
            }
        });
    }
}
