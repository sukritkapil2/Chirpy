package com.example.wingly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private Button send;
    private EditText gmail;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        send = findViewById(R.id.btnReset);
        gmail = findViewById(R.id.etGmail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        firebaseAuth = FirebaseAuth.getInstance();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = gmail.getText().toString().trim();
                firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(ForgotPasswordActivity.this, "Password Reset Link Sent!", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(ForgotPasswordActivity.this, MainActivity.class));
                        }
                        else {
                            Toast.makeText(ForgotPasswordActivity.this, "Failed! Try Again..", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
                startActivity(new Intent(ForgotPasswordActivity.this, MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
