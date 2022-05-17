package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.whatsappclone.adapters.ChatAdapter;
import com.example.whatsappclone.databinding.ActivityGroupChatBinding;
import com.example.whatsappclone.models.MessageModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class GroupChatActivity extends AppCompatActivity {
    ActivityGroupChatBinding binder;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder=ActivityGroupChatBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());
        getSupportActionBar().hide();
        final ArrayList<MessageModel> messageModels=new ArrayList<>();
        final ChatAdapter adapter=new ChatAdapter(messageModels,this);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();

        binder.rvChat.setAdapter(adapter);
        binder.rvChat.setLayoutManager(layoutManager);
        binder.ivBackChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GroupChatActivity.this,MainActivity.class));
            }
        });
        binder.tvUsernameChat.setText("Group Chat");
        binder.ivSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String cur_msg=binder.etMsg.getText().toString();
                if(cur_msg.isEmpty())
                {
                    binder.etMsg.setError("Empty");
                    return;
                }
                String sender_id=auth.getUid();
                final MessageModel model=new MessageModel(sender_id,cur_msg);
                model.setTime(new Date().getTime());
                binder.etMsg.setText("");
                database.getReference().child("Group Chat").push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                });
            }
        });
        binder.ivCallChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id=FirebaseAuth.getInstance().getUid();
                Toast.makeText(GroupChatActivity.this, id, Toast.LENGTH_SHORT).show();
            }
        });

        database.getReference().child("Group Chat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageModels.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    MessageModel model=snapshot1.getValue(MessageModel.class);
                    messageModels.add(model);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}