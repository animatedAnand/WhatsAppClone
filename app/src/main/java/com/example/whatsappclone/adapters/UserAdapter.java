package com.example.whatsappclone.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappclone.PersonalChatActivity;
import com.example.whatsappclone.R;
import com.example.whatsappclone.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    ArrayList<User> list;
    Context context;

    public UserAdapter(ArrayList<User> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.user_layout_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User cur_user=list.get(position);
        Picasso.get().load(cur_user.getProfile_picture()).placeholder(R.drawable.ic_profile).into(holder.civ_profile);
        holder.tv_user_name.setText(cur_user.getName());
        FirebaseDatabase.getInstance().getReference().child("Chats")
                .child(FirebaseAuth.getInstance().getUid()+cur_user.getUserid()).orderByChild("time")
                .limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren())
                {
                    for (DataSnapshot snapshot1:snapshot.getChildren())
                    {
                        holder.tv_last_msg.setText(snapshot1.child("text").getValue().toString());
                    }
                }
                else
                {
                    holder.tv_last_msg.setText("Start chatting..");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, PersonalChatActivity.class);
                intent.putExtra("receiver_id",cur_user.getUserid());
                intent.putExtra("receiver_name",cur_user.getName());
                intent.putExtra("receiver_pic",cur_user.getProfile_picture());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView civ_profile;
        TextView tv_user_name,tv_last_msg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            civ_profile=itemView.findViewById(R.id.civ_profile_chat);
            tv_user_name=itemView.findViewById(R.id.tv_user_name);
            tv_last_msg=itemView.findViewById(R.id.tv_last_msg);
        }
    }
}
