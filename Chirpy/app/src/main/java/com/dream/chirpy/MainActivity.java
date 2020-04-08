package com.dream.chirpy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {
    Button make, join;
    ImageButton sign_out;
    GoogleSignInClient mGoogleSignInClient;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        make = findViewById(R.id.make_btn);
        join = findViewById(R.id.join_btn);
        sign_out = findViewById(R.id.sign_out);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        pd = new ProgressDialog(this);
        pd.setMessage("Logging Out");

        Toast.makeText(MainActivity.this, "Welcome " + getIntent().getStringExtra("NAME"), Toast.LENGTH_SHORT).show();

        make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MakeNest.class);
                intent.putExtra("NAME", getIntent().getStringExtra("NAME"));
                intent.putExtra("EMAIL", getIntent().getStringExtra("EMAIL"));
                startActivity(intent);
            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, JoinNest.class);
                intent.putExtra("NAME", getIntent().getStringExtra("NAME"));
                intent.putExtra("EMAIL", getIntent().getStringExtra("EMAIL"));
                startActivity(intent);
            }
        });

        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.sign_out:
                        signOut();
                        break;
                }
            }
        });
    }

    private void signOut() {
        pd.show();
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(MainActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                pd.dismiss();
                startActivity(new Intent(MainActivity.this, SignIn.class));
            }
        });
    }

    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
