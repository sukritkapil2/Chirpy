package com.dream.chirpy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class NestRoom extends AppCompatActivity {

    TextView nest_name, creator_name;
    EditText message;
    ImageButton send;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nest_room);

        nest_name = findViewById(R.id.nest_name);
        creator_name = findViewById(R.id.creator);
        message = findViewById(R.id.editText);
        send = findViewById(R.id.imageButton);
        pd = new ProgressDialog(this);
        pd.setMessage("Loading Nest");

        String nestName = getIntent().getStringExtra("NEST_NAME");
        String creatorName = getIntent().getStringExtra("CREATOR");

        setNames(nestName, creatorName);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NestRoom.this, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setNames(String nestName, String creatorName) {
        pd.show();
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        nest_name.setText(nestName);
        creator_name.setText("By " + creatorName);
        pd.dismiss();
    }
}
