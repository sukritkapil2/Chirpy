package com.dream.chirpy;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class MessageAdapter extends FirestoreRecyclerAdapter<Message, MessageAdapter.MessageHolder> {

    public MessageAdapter(@NonNull FirestoreRecyclerOptions<Message> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MessageHolder holder, int position, @NonNull Message model) {
//        holder.name.setText(model.getUserName());
//        holder.message.setText(model.getMessage());
//        holder.time.setText(model.getTimeSent().toDate().toString());

        String Name = ((Activity) holder.itemView.getContext()).getIntent().getStringExtra("NAME");

        if(model.getUserName().equals(Name)) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.weight = 1.0f;
            params.gravity = Gravity.RIGHT;
            params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

            holder.name.setText(model.getUserName());
            holder.name.setLayoutParams(params);
            holder.time.setText(model.getTimeSent().toDate().toString());

            holder.message.setText(model.getMessage());
            holder.message.setLayoutParams(params);

            holder.time.setLayoutParams(params);

            holder.card.setLayoutParams(params2);
            holder.card.setCardBackgroundColor(Color.rgb(101, 147, 245));
        }

        else {
            holder.name.setText(model.getUserName());
            holder.message.setText(model.getMessage());
            holder.time.setText(model.getTimeSent().toDate().toString());
        }
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_card, parent, false);
        return new MessageHolder(view);
    }

    public class MessageHolder extends RecyclerView.ViewHolder {

        TextView name, message, time;
        CardView card;

        public MessageHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            message = itemView.findViewById(R.id.message);
            time = itemView.findViewById(R.id.time);
            card = itemView.findViewById(R.id.card);
        }
    }
}
