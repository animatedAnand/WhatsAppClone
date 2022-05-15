package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.whatsappclone.adapters.FragmentAdapter;
import com.example.whatsappclone.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binder;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());
        auth=FirebaseAuth.getInstance();

        binder.viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        binder.tabLayout.setupWithViewPager(binder.viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.settings:
            {
                String id=auth.getUid();
                Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.log_out:
            {
                auth.signOut();
                startActivity(new Intent(MainActivity.this,SignInActivity.class));
                break;
            }
            case R.id.group_chat:
            {
                auth.signOut();
                startActivity(new Intent(MainActivity.this,GroupChatActivity.class));
                break;
            }
        }
        return true;
    }
}