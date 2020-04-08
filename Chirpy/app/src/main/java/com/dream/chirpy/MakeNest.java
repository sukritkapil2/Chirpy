package com.dream.chirpy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MakeNest extends AppCompatActivity {

    EditText nest_name, passcode;
    Button make_nest;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    CollectionReference nests = firebaseFirestore.collection("nests");
    CollectionReference birds = firebaseFirestore.collection("birds");
    String creatorName, creatorEmail;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_nest);

        nest_name = findViewById(R.id.nest_name);
        make_nest = findViewById(R.id.make_nest);
        creatorName = getIntent().getStringExtra("NAME");
        creatorEmail = getIntent().getStringExtra("EMAIL");
        passcode = findViewById(R.id.passcode);
        pd = new ProgressDialog(this);
        pd.setMessage("Making Space for Your Nest");

        make_nest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.show();
                String nestField = nest_name.getText().toString();
                String passField = passcode.getText().toString();

                if(nestField.length() == 0) {
                    Toast.makeText(MakeNest.this, "Please enter nest name", Toast.LENGTH_SHORT).show();
                }
                else {
                    Map<String, Object> nest = new HashMap<>();
                    nest.put("name", nestField);
                    nest.put("creatorName", creatorName);
                    nest.put("creatorEmail", creatorEmail);
                    nest.put("timeCreated", Timestamp.now());
                    nest.put("passcode", passField);

                    String nestID = creatorEmail;

                    nests.document(nestID).set(nest).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            pd.dismiss();
                            Toast.makeText(MakeNest.this, "Nest Created", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MakeNest.this, NestRoom.class);
                            intent.putExtra("EMAIL", creatorEmail);
                            intent.putExtra("NAME", creatorName);
                            startActivity(intent);
                        }
                    });

                }
            }
        });
    }
}
