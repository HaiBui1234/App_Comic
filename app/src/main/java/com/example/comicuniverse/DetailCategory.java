package com.example.comicuniverse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comicuniverse.AllInterface.InterFaceFavorite;
import com.example.comicuniverse.allAdapter.GridViewAdapter;
import com.example.comicuniverse.allModel.CategoryModel;
import com.example.comicuniverse.allModel.ComicModel;
import com.example.comicuniverse.allModel.FavoriteComic;
import com.example.comicuniverse.allModel.UserModel;
import com.exmple.comicuniverse.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class DetailCategory extends AppCompatActivity implements InterFaceFavorite {
    RecyclerView recyclerView;
    GridViewAdapter adapter;
    ImageView imgBack;
    TextView tvNameCate;
    ArrayList<ComicModel> modelArrayList;
    CategoryModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_category);
        recyclerView=findViewById(R.id.RecyComic);
        imgBack=findViewById(R.id.backHome);
        tvNameCate=findViewById(R.id.tvnameDetailCate);
        GridLayoutManager manager=new GridLayoutManager(this,3,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        getDataComic();
        adapter =new GridViewAdapter(this,modelArrayList,this);
        recyclerView.setAdapter(adapter);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tvNameCate.setText(model.getName_Catagory());
    }

    private void getDataComic() {
        modelArrayList=new ArrayList<>();
        Intent intent=getIntent();
        model= (CategoryModel) intent.getSerializableExtra("Category");
        Log.d("xxx", "onCreate: "+model.getName_Catagory());
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference("ComicModel");
        Query query=reference.orderByChild("id_Category").equalTo(model.getId_Catagory());
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ComicModel comicModel=snapshot.getValue(ComicModel.class);
                if (comicModel==null){
                    return;
                }
                Log.d("zzz", "onChildAdded: "+comicModel.getName_Comic());
                modelArrayList.add(comicModel);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    public void addFavorite(FavoriteComic favoriteComic, String id) {
        Intent intent = getIntent();
        UserModel userModel = (UserModel) intent.getSerializableExtra("user");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("UserModel");
        reference.child(userModel.getId_User()).child("FavoriteComic").child(id).setValue(favoriteComic, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(DetailCategory.this, "abc", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void adDeleteFavorite(String id) {

    }
}