package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.whatsappclone.databinding.ActivitySettingsBinding;
import com.example.whatsappclone.models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class SettingsActivity extends AppCompatActivity {
    ActivitySettingsBinding binder;
    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder=ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());
        getSupportActionBar().hide();

        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        storage=FirebaseStorage.getInstance();

        binder.ivBackSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this,MainActivity.class));
            }
        });

        binder.ivPlusProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,47);
            }
        });

        binder.btSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_name=binder.etUserName.getText().toString();
                String user_bio=binder.etBio.getText().toString();
                HashMap<String,Object> hashMap=new HashMap<>();
                hashMap.put("name",user_name);
                hashMap.put("bio",user_bio);
                database.getReference().child("Users").child(auth.getUid())
                        .updateChildren(hashMap);
                Toast.makeText(SettingsActivity.this, "Profile updated", Toast.LENGTH_SHORT).show();
            }
        });

        database.getReference().child("Users").child(auth.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User cur_user=snapshot.getValue(User.class);
                Picasso.get().load(cur_user.getProfile_picture())
                        .placeholder(R.drawable.ic_profile).into(binder.civProfileSettings);
                binder.etUserName.setText(cur_user.getName());
                binder.etBio.setText(cur_user.getBio());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null)
        {
            Uri uri=data.getData();
            binder.civProfileSettings.setImageURI(uri);

            final StorageReference reference=storage.getReference().child("Profile Pictures")
                    .child(auth.getUid());
            reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                           database.getReference().child("Users").child(auth.getUid())
                                   .child("profile_picture").setValue(uri.toString());
                            Toast.makeText(SettingsActivity.this, "Profile picture updated", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }
}