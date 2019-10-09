package com.example.wingly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StatusActivity extends AppCompatActivity {

    private Button logout;
    private Button check;
    private Button change;
    private int num = 0;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference newRef = FirebaseDatabase.getInstance().getReference();
    private TextView playStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        logout = findViewById(R.id.btnLogout);
        check = findViewById(R.id.btnCheckStatus);
        change = findViewById(R.id.btnChangeStatus);
        playStatus = findViewById(R.id.tvPlayStatus);
        firebaseAuth = FirebaseAuth.getInstance();

        newRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                    String play = userProfile.getStatus();
                    if(play.equals("Let's Play/Chill")) {
                        num++;
                    }
                }
                playStatus.setText("Guys to Play/Chill: " + num);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(StatusActivity.this, MainActivity.class));
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(StatusActivity.this, CheckStatusActivity.class));
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(StatusActivity.this, ChangeStatusActivity.class));
            }
        });
    }
}
