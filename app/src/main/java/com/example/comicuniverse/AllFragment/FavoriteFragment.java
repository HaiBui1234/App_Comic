package com.example.comicuniverse.AllFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comicuniverse.AllInterface.InterFaceFavorite;
import com.example.comicuniverse.allAdapter.GridViewAdapter;
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

public class FavoriteFragment extends Fragment implements InterFaceFavorite {
    RecyclerView RecyFavorite;
    GridViewAdapter adapter;
    TextView tvTitle;
    ArrayList<FavoriteComic> favoriteComics;
    ArrayList<ComicModel> modelArrayList;
    UserModel userModel;
    public FavoriteFragment() {

    }
    public static FavoriteFragment newInstance(String param1, String param2) {
        FavoriteFragment fragment = new FavoriteFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyFavorite=view.findViewById(R.id.id_RecyFavorite);
        tvTitle=view.findViewById(R.id.tvTitle);
        getDataFavorite();
        setLayoutFavorite();
    }

    private void setLayoutFavorite() {
        GridLayoutManager manager=new GridLayoutManager(getActivity(),3,RecyclerView.VERTICAL,false);
        RecyFavorite.setLayoutManager(manager);
        adapter=new GridViewAdapter(getActivity(),modelArrayList,this);
        RecyFavorite.setAdapter(adapter);
    }
    private void getDataFavorite() {
        modelArrayList=new ArrayList<>();
        favoriteComics=new ArrayList<>();
        Intent intent = getActivity().getIntent();
        userModel = (UserModel) intent.getSerializableExtra("user");
        FirebaseDatabase database =FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference("UserModel");
        reference.child(userModel.getId_User()).child("FavoriteComic").addChildEventListener(new ChildEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                FavoriteComic favorite=snapshot.getValue(FavoriteComic.class);
                if (favorite!=null) {
                    tvTitle.setVisibility(View.INVISIBLE);
                    favoriteComics.add(favorite);
                    modelArrayList.add(new ComicModel(favorite.getId_Comic(), favorite.getId_Category(), favorite.getTitle(), favorite.getDate(),
                            favorite.getImg_comic(), favorite.getName_Author(), favorite.getName_Comic(),favorite.getViewComic(),favorite.getChapterModels(),favorite.getDesScription(), false, favorite.isFavouriteComic()));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                FavoriteComic favorite=snapshot.getValue(FavoriteComic.class);
                if (favorite==null){
                    return;
                }
                for (int i=0;i<modelArrayList.size();i++){
                    if (favorite.getId_Comic().equals(modelArrayList.get(i).getId_Comic())){
                        modelArrayList.remove(modelArrayList.get(i));
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
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

    }

    @Override
    public void adDeleteFavorite(String id) {
        FirebaseDatabase database =FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference("UserModel");
        reference.child(userModel.getId_User()).child("FavoriteComic").child(id).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(getActivity(), "Đã xóa", Toast.LENGTH_SHORT).show();
            }
        });
    }
}