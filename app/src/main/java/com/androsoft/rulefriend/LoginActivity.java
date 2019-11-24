package com.androsoft.rulefriend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private EditText emailAddress;
    private EditText password;
    private Button signInButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailAddress=findViewById(R.id.email);
        password=findViewById(R.id.password);
        signInButton=findViewById(R.id.user_sign_in_button);
        registerButton=findViewById(R.id.user_register_button);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.startActivity(new Intent(LoginActivity.this.getApplicationContext(), SignUpActivity.class));
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BackgroundTask(LoginActivity.this.getApplicationContext()).execute("login", emailAddress.getText().toString(),
                        password.getText().toString());
                //LoginActivity.this.startActivity(new Intent(LoginActivity.this.getApplicationContext(), MainActivity.class));

            }
        });
    }

}
