package com.example.wingly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangeStatusActivity extends AppCompatActivity {

    private Button available;
    private Button dnd;
    private Button notInRoom;
    private Button sleeping;
    private Button play;
    private Button emergency;
    private Button dining;
    private Button library;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private DatabaseReference myRootRef = FirebaseDatabase.getInstance().getReference();
    String username, userstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_status);

        available = findViewById(R.id.btnAvailable);
        dnd = findViewById(R.id.btnDnd);
        notInRoom = findViewById(R.id.btnNotInRoom);
        sleeping = findViewById(R.id.btnSleep);
        play = findViewById(R.id.btnPlay);
        emergency = findViewById(R.id.btnEmergency);
        dining = findViewById(R.id.btnDining);
        library = findViewById(R.id.btnLibrary);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        available.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference myChildRef = myRootRef.child(firebaseAuth.getUid()).child("status");
                myChildRef.setValue("Available");
                Toast.makeText(ChangeStatusActivity.this, "You are Available now!", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(ChangeStatusActivity.this, StatusActivity.class));
            }
        });

        dnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference myChildRef = myRootRef.child(firebaseAuth.getUid()).child("status");
                myChildRef.setValue("DND");
                Toast.makeText(ChangeStatusActivity.this, "No one would disturb you!", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(ChangeStatusActivity.this, StatusActivity.class));
            }
        });

        notInRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference myChildRef = myRootRef.child(firebaseAuth.getUid()).child("status");
                myChildRef.setValue("Not In Room");
                Toast.makeText(ChangeStatusActivity.this, "Changed to Not in Room!", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(ChangeStatusActivity.this, StatusActivity.class));
            }
        });

        sleeping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference myChildRef = myRootRef.child(firebaseAuth.getUid()).child("status");
                myChildRef.setValue("Sleeping");
                Toast.makeText(ChangeStatusActivity.this, "Wish you a Sound Sleep!", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(ChangeStatusActivity.this, StatusActivity.class));
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference myChildRef = myRootRef.child(firebaseAuth.getUid()).child("status");
                myChildRef.setValue("Let's Play/Chill");
                Toast.makeText(ChangeStatusActivity.this, "Wohooo!", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(ChangeStatusActivity.this, StatusActivity.class));
            }
        });

        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference myChildRef = myRootRef.child(firebaseAuth.getUid()).child("status");
                myChildRef.setValue("EMERGENCY!");
                Toast.makeText(ChangeStatusActivity.this, "Displaying EMERGENCY!", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(ChangeStatusActivity.this, StatusActivity.class));
            }
        });

        dining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference myChildRef = myRootRef.child(firebaseAuth.getUid()).child("status");
                myChildRef.setValue("Dining");
                Toast.makeText(ChangeStatusActivity.this, "Happy Eating!", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(ChangeStatusActivity.this, StatusActivity.class));
            }
        });

        library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference myChildRef = myRootRef.child(firebaseAuth.getUid()).child("status");
                myChildRef.setValue("In the Library");
                Toast.makeText(ChangeStatusActivity.this, "Focus Brother!", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(ChangeStatusActivity.this, StatusActivity.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
                startActivity(new Intent(ChangeStatusActivity.this, StatusActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
