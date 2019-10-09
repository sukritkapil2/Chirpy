package com.example.wingly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText name;
    private EditText email;
    private EditText password;
    private Button register;
    private TextView goBack;
    private String nametext, emailtext, passtext;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.etRegisterName);
        email = findViewById((R.id.etRegisterEmail));
        password = findViewById((R.id.etRegisterPassword));
        register = findViewById(R.id.btnRegister);
        goBack = findViewById(R.id.tvGoBackLogin);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.setMessage("Registering..");

                nametext = name.getText().toString().trim();
                emailtext = email.getText().toString().trim();
                passtext = password.getText().toString().trim();

                if((nametext.equals(null)) || (emailtext.equals(null)) || (passtext.equals(null))) {
                    Toast.makeText(RegisterActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                }
                else {

                    progressDialog.show();
                    firebaseAuth.createUserWithEmailAndPassword(emailtext, passtext).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                progressDialog.dismiss();
                                sendUserData();
                                Toast.makeText(RegisterActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                                firebaseAuth.signOut();
                                finish();
                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            }
                            else {
                                progressDialog.dismiss();
                                Toast.makeText(RegisterActivity.this, "Failed! Try Again..", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });
    }

    private void sendUserData () {
        DatabaseReference myRoot = FirebaseDatabase.getInstance().getReference(firebaseAuth.getUid());
        UserProfile userProfile = new UserProfile(nametext, "Available");
        myRoot.setValue(userProfile);
    }
}
