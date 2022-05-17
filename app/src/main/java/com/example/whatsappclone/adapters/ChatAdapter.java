package com.example.whatsappclone.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappclone.R;
import com.example.whatsappclone.models.MessageModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter {
    ArrayList<MessageModel> messageModels;
    Context context;
    String recId;

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context, String recId) {
        this.messageModels = messageModels;
        this.context = context;
        this.recId = recId;
    }

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==0)
        {
            View view= LayoutInflater.from(context).inflate(R.layout.outgoing_chat_item_layout,parent,false);
            return new SenderViewHolder(view);
        }
        else
        {
            View view= LayoutInflater.from(context).inflate(R.layout.incoming_chat_item_layout,parent,false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(messageModels.get(position).getuID().equals(FirebaseAuth.getInstance().getUid()))
        return  0;
        else  return 1;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageModel cur_msg=messageModels.get(position);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(context).setTitle("Delete")
                        .setMessage("Are you sure to delete")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseDatabase database=FirebaseDatabase.getInstance();
                                String sender_room=FirebaseAuth.getInstance().getUid()+recId;
                                database.getReference().child("Chats").child(sender_room)
                                        .child(cur_msg.getMsg_id()).setValue(null);
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       dialogInterface.dismiss();
                    }
                }).show();

                return false;
            }
        });

        if(holder.getClass()==SenderViewHolder.class)
        {
            ((SenderViewHolder) holder).tv_outgoing_msg.setText(cur_msg.getText());
        }
        else
        {
            ((ReceiverViewHolder) holder).tv_incoming_msg.setText(cur_msg.getText());
        }
    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder{
        TextView tv_incoming_msg,tv_incoming_msg_time;
        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_incoming_msg=itemView.findViewById(R.id.tv_incoming_msg);
            tv_incoming_msg_time=itemView.findViewById(R.id.tv_incoming_msg_time);
        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_outgoing_msg,tv_outgoing_msg_time;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_outgoing_msg=itemView.findViewById(R.id.tv_incoming_msg);
            tv_outgoing_msg_time=itemView.findViewById(R.id.tv_incoming_msg_time);
        }
    }
}
