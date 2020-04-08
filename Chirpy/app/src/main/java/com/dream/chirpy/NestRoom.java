package com.dream.chirpy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.lang.reflect.Field;
import java.sql.Time;

public class NestRoom extends AppCompatActivity {

    TextView nest_name, creator_name;
    EditText message;
    ImageButton send, leave;
    ProgressDialog pd, pd2;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    CollectionReference nests = firebaseFirestore.collection("nests");
    CollectionReference messages;
    MessageAdapter messageAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nest_room);

        nest_name = findViewById(R.id.nest_name);
        creator_name = findViewById(R.id.creator);
        message = findViewById(R.id.editText);
        send = findViewById(R.id.imageButton);
        leave = findViewById(R.id.leave);
        recyclerView = findViewById(R.id.recycler_view);
        pd = new ProgressDialog(this);
        pd.setMessage("Loading Nest");
        pd2 = new ProgressDialog(this);
        pd2.setMessage("Leaving Nest");

        String nestName = getIntent().getStringExtra("NEST_NAME");
        String creatorName = getIntent().getStringExtra("CREATOR");

        setNames(nestName, creatorName);

        setUpRecyclerView();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messageField = message.getText().toString();
                messages = firebaseFirestore.collection(getIntent().getStringExtra("NEST-ID"));

                if(messageField.length() == 0) {
                    Toast.makeText(NestRoom.this, "Enter a message", Toast.LENGTH_SHORT).show();
                }else {
                    Message m = new Message(getIntent().getStringExtra("NAME"), messageField, Timestamp.now());
                    messages.add(m).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(NestRoom.this, "Message Sent", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd2.show();
                pd2.setCanceledOnTouchOutside(false);
                pd2.setCancelable(false);
                nests.document(getIntent().getStringExtra("NEST-ID")).update("birds", FieldValue.arrayRemove(getIntent().getStringExtra("EMAIL"))).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        pd2.dismiss();
                        Intent intent = new Intent(NestRoom.this, MainActivity.class);
                        intent.putExtra("NAME", getIntent().getStringExtra("NAME"));
                        startActivity(intent);
                    }
                });
            }
        });
    }

    private void setUpRecyclerView() {

        messages = firebaseFirestore.collection(getIntent().getStringExtra("NEST-ID"));

        Query query = messages.orderBy("timeSent");

        FirestoreRecyclerOptions<Message> options = new FirestoreRecyclerOptions.Builder<Message>()
                .setQuery(query, Message.class)
                .build();

        messageAdapter = new MessageAdapter(options);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(messageAdapter);
    }

    private void setNames(String nestName, String creatorName) {
        pd.show();
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        nest_name.setText(nestName);
        creator_name.setText("By " + creatorName);
        pd.dismiss();
    }

    @Override
    protected void onStart() {
        super.onStart();
        messageAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        messageAdapter.stopListening();
    }
}
