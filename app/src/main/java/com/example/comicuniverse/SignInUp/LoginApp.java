package com.example.comicuniverse.SignInUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.ConditionVariable;

import com.example.comicuniverse.BroadReserver.BroadcastReceiverComic;
import com.exmple.comicuniverse.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class LoginApp extends AppCompatActivity {
    ViewPagerLogin viewPagerAdapter;
    ViewPager2 Vp2LoginApp;
    TabLayout Tb_LoginAPP;
    BroadcastReceiverComic BroadcastReceiverComic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_app);
        Vp2LoginApp=findViewById(R.id.Vp_Login);
        Tb_LoginAPP=findViewById(R.id.Tb_Login);
        BroadcastReceiverComic=new BroadcastReceiverComic();
        viewPagerAdapter=new ViewPagerLogin(this);
        Vp2LoginApp.setAdapter(viewPagerAdapter);
        new TabLayoutMediator(Tb_LoginAPP, Vp2LoginApp, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("LOGIN");
                        break;
                    case 1:
                        tab.setText("SIGN UP");
                        break;
                }
            }
        }).attach();
    }
    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(BroadcastReceiverComic,filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(BroadcastReceiverComic);
    }
}
