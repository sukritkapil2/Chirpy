package com.dream.chirpy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MakeNest extends AppCompatActivity {

    EditText nest_name;
    Button make_nest;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    CollectionReference nests = firebaseFirestore.collection("nests");
    CollectionReference birds = firebaseFirestore.collection("birds");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_nest);

        nest_name = findViewById(R.id.nest_name);
        make_nest = findViewById(R.id.make_nest);

        make_nest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nestField = nest_name.getText().toString();

                if(nestField.length() == 0) {
                    Toast.makeText(MakeNest.this, "Please enter nest name", Toast.LENGTH_SHORT).show();
                }
                else {
                    Map<String, Object> nest = new HashMap<>();
                    nest.put("Name", nestField);

                    nest.put("timeCreated", Timestamp.now());

                }
            }
        });
    }
}
