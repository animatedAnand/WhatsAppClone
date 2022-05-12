package com.example.whatsappclone.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.whatsappclone.R;
import com.example.whatsappclone.adapters.UserAdapter;
import com.example.whatsappclone.databinding.FragmentChatBinding;
import com.example.whatsappclone.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatFragment extends Fragment {
    FragmentChatBinding binder;
    ArrayList<User>list=new ArrayList<>();
    FirebaseDatabase database;

    public ChatFragment() {
        // Required empty public constructor
    }
 @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binder=FragmentChatBinding.inflate(inflater, container, false);

     database=FirebaseDatabase.getInstance();
     UserAdapter adapter=new UserAdapter(list,getContext());
     binder.rvChat.setAdapter(adapter);
     LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
     binder.rvChat.setLayoutManager(layoutManager);

     database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
             list.clear();
             for (DataSnapshot dataSnapshot:snapshot.getChildren())
             {
                 User cur_user=dataSnapshot.getValue(User.class);
                 cur_user.getUserid(dataSnapshot.getKey());
                 list.add(cur_user);
             }
             adapter.notifyDataSetChanged();
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
     });


        return binder.getRoot();
    }
}