package com.dream.chirpy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class JoinNest extends AppCompatActivity {

    EditText nest_name, passcode;
    Button join;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    CollectionReference nests = firebaseFirestore.collection("nests");
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_nest);

        nest_name = findViewById(R.id.nest_name2);
        passcode = findViewById(R.id.passcode2);
        join = findViewById(R.id.join_nest);
        pd = new ProgressDialog(this);
        pd.setMessage("Searching Through Nests");

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.show();
                pd.setCanceledOnTouchOutside(false);
                pd.setCancelable(false);
                final String nameField = nest_name.getText().toString();
                final String passField = passcode.getText().toString();

                if(nameField.length() == 0) {
                    Toast.makeText(JoinNest.this, "Enter Creator Email", Toast.LENGTH_SHORT).show();
                }
                else if(passField.length() == 0) {
                    Toast.makeText(JoinNest.this, "Enter Pass-code", Toast.LENGTH_SHORT).show();
                }
                else {
                    nests.document(nameField).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            nests.document(nameField).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    String pass = documentSnapshot.get("passcode").toString();

                                    if(pass.equals(passField)) {

                                        Intent intent = new Intent(JoinNest.this, NestRoom.class);
                                        intent.putExtra("NEST_NAME", documentSnapshot.get("name").toString());
                                        intent.putExtra("CREATOR", documentSnapshot.get("creatorName").toString());
                                        intent.putExtra("NEST-ID", nameField);
                                        intent.putExtra("EMAIL", getIntent().getStringExtra("EMAIL"));
                                        intent.putExtra("NAME", getIntent().getStringExtra("NAME"));

                                        nests.document(nameField).update("birds", FieldValue.arrayUnion(getIntent().getStringExtra("EMAIL")));

                                        pd.dismiss();
                                        Toast.makeText(JoinNest.this, "Nest Matched", Toast.LENGTH_SHORT).show();

                                        startActivity(intent);
                                    }
                                    else {
                                        pd.dismiss();
                                        Toast.makeText(JoinNest.this, "Wrong Pass-code!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                    Toast.makeText(JoinNest.this, "Failed to Fetch Data!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(JoinNest.this, "No Nest Found!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
