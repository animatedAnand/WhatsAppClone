package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.whatsappclone.adapters.ChatAdapter;
import com.example.whatsappclone.databinding.ActivityPersonalChatBinding;
import com.example.whatsappclone.models.MessageModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class PersonalChatActivity extends AppCompatActivity {
    ActivityPersonalChatBinding binder;
    FirebaseDatabase database;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder=ActivityPersonalChatBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());
        getSupportActionBar().hide();

        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();

        String sender_id=auth.getUid();
        String receiver_id=getIntent().getStringExtra("receiver_id");
        String receiver_name=getIntent().getStringExtra("receiver_name");
        String receiver_pic=getIntent().getStringExtra("receiver_pic");

        binder.tvUsernameChat.setText(receiver_name);
        Picasso.get().load(receiver_pic).placeholder(R.drawable.ic_profile).into(binder.civProfileChat);

        binder.ivBackChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PersonalChatActivity.this,MainActivity.class));
            }
        });

        final ArrayList<MessageModel> messageModels=new ArrayList<>();
        final ChatAdapter chatAdapter=new ChatAdapter(messageModels,this);
        binder.rvChat.setAdapter(chatAdapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binder.rvChat.setLayoutManager(layoutManager);

        final String sender_room=sender_id+receiver_id;
        final String receiver_room=receiver_id+sender_id;

        database.getReference().child("Chats").child(sender_room).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageModels.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren())
                {
                    MessageModel model=snapshot1.getValue(MessageModel.class);
                    messageModels.add(model);
                }
                chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binder.ivSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cur_msg=binder.etMsg.getText().toString();
                MessageModel model=new MessageModel(sender_id,cur_msg);
                model.setTime(new Date().getTime());
                binder.etMsg.setText("");

                database.getReference().child("Chats").child(sender_room)
                        .push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        database.getReference().child("Chats").child(receiver_room)
                                .push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });
                    }
                });
            }
        });
    }
}