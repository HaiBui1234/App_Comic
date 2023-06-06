package com.example.comicuniverse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.comicuniverse.allAdapter.DetailViewPager;
import com.example.comicuniverse.allModel.ComicModel;
import com.exmple.comicuniverse.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class DetailComic extends AppCompatActivity {
    TabLayout tbDetail;
    ViewPager2 Vpdeatil;
    ImageView imgComic,imgBack;
    ComicModel Comic;
    DetailViewPager detailViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_comic);
        anhXa();
        Intent intent=getIntent();
        Comic= (ComicModel) intent.getExtras().getSerializable("Comic");
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Glide.with(this).load(Comic.getImg_comic()).error(R.drawable.ic_action_erron).into(imgComic);
        detailViewPager=new DetailViewPager(this);
        Vpdeatil.setAdapter(detailViewPager);
        new TabLayoutMediator(tbDetail, Vpdeatil, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Chi tiết");
                        break;
                    case 1:
                        tab.setText("Chapter");
                        break;
                }
            }
        }).attach();
    }
    private void anhXa(){
        imgComic=findViewById(R.id.imageDetail);
        imgBack=findViewById(R.id.id_Backs);
        tbDetail=findViewById(R.id.tbDetail);
        Vpdeatil=findViewById(R.id.Vp_Detail);
    }
}