package com.example.comicuniverse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.comicuniverse.AllFragment.AccountFragment;
import com.example.comicuniverse.AllFragment.AdminFragment;
import com.example.comicuniverse.AllFragment.FavoriteFragment;
import com.example.comicuniverse.AllFragment.HistoryFragment;
import com.example.comicuniverse.AllFragment.HomeFragment;
import com.example.comicuniverse.allModel.UserModel;
import com.exmple.comicuniverse.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottom_nav;
    UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottom_nav = findViewById(R.id.bottom_nav);
        Menu menu=bottom_nav.getMenu();
        MenuItem item=menu.findItem(R.id.action_admin);
        replaceFragment(new HomeFragment());
        Intent intent =getIntent();
        userModel = (UserModel) intent.getSerializableExtra("user");
        item.setVisible(userModel.isActive());
        bottom_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_Home:
                        replaceFragment(new HomeFragment());
                        break;
                    case R.id.action_History:
                        replaceFragment(new HistoryFragment());
                        break;
                    case R.id.action_Favorit:
                        replaceFragment(new FavoriteFragment());
                        break;
                    case R.id.action_account:
                        replaceFragment(new AccountFragment());
                        break;
                    case R.id.action_admin:
                        replaceFragment(new AdminFragment());
                        break;
                }
                return true;
            }
        });
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.id_frame, fragment);
        transaction.commit();
    }
}
