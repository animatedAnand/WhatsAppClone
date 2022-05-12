package com.example.whatsappclone.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappclone.R;
import com.example.whatsappclone.models.User;
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
        //holder.tv_last_msg.setText(cur_user.getLast_msg());

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
