package com.example.comicuniverse.AllFragment.DetailComic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.comicuniverse.AllInterface.InterfaceChapter;
import com.example.comicuniverse.allAdapter.DetailAdapter;
import com.example.comicuniverse.allModel.ChapterModel;
import com.example.comicuniverse.allModel.ComicModel;
import com.exmple.comicuniverse.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ChapterFragment extends Fragment implements InterfaceChapter {
    ArrayList<ChapterModel> chapterModelArrayList;
    ArrayList<ComicModel> modelArrayList;
    DetailAdapter detailAdapter;
    RecyclerView RecyDetail;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference reference=database.getReference("ComicModel");
    ComicModel Comic;
    String idUser="";
    public ChapterFragment() {
        // Required empty public constructor
    }

    public static ChapterFragment newInstance(String param1, String param2) {
        ChapterFragment fragment = new ChapterFragment();
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
        return inflater.inflate(R.layout.fragment_chapter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyDetail=view.findViewById(R.id.RecyDetail);
        SharedPreferences preferences=getActivity().getSharedPreferences("Account", Context.MODE_PRIVATE);
        idUser=preferences.getString("ID","");
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        RecyDetail.setLayoutManager(manager);
        RecyclerView.ItemDecoration decoration=new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        RecyDetail.addItemDecoration(decoration);
        chapterModelArrayList=new ArrayList<>();
        getDataChapter();
        detailAdapter=new DetailAdapter(getActivity(),chapterModelArrayList,this);
        RecyDetail.setAdapter(detailAdapter);
        modelArrayList=new ArrayList<>();
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ComicModel comicModel=snapshot.getValue(ComicModel.class);
                modelArrayList.add(comicModel);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            ComicModel comicModel=snapshot.getValue(ComicModel.class);
            if (comicModel==null){
                return;
            }
                for (int i=0;i<modelArrayList.size();i++) {
                    if (comicModel.getId_Comic().equals(modelArrayList.get(i).getId_Comic())){
                        modelArrayList.set(i,comicModel);
                        break;
                    }
                }
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
    private void getDataChapter() {
        Intent intent=getActivity().getIntent();
        Comic= (ComicModel) intent.getExtras().getSerializable("Comic");
        reference.child(Comic.getId_Comic()).child("ChapterModel").addChildEventListener(new ChildEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ChapterModel model=snapshot.getValue(ChapterModel.class);
                chapterModelArrayList.add(model);
                detailAdapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
             ChapterModel model=snapshot.getValue(ChapterModel.class);
             if (model==null||chapterModelArrayList==null){
                 return;
             }
                for (int i=0;i<chapterModelArrayList.size();i++) {
                    if (model.getId_Chapter().equals(chapterModelArrayList.get(i).getId_Chapter())){
                        chapterModelArrayList.set(i,model);
                    }
                }
                detailAdapter.notifyDataSetChanged();
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
    public void UpdateChapter(ChapterModel model) {

        for (ComicModel comicModel:modelArrayList) {
            if (comicModel.getId_Comic().equals(Comic.getId_Comic())){
                int ViewCo=comicModel.getViewComic()+1;
                reference.child(Comic.getId_Comic()).child("viewComic").setValue(ViewCo);
                break;
            }
        }
        model.setViewChapter(model.getViewChapter()+1);
        reference.child(Comic.getId_Comic()).child("ChapterModel").child(model.getId_Chapter()).setValue(model);
    }

    @Override
    public void addHistory() {
        DatabaseReference reference=database.getReference("UserModel");
        reference.child(idUser).child("HistoryModel").child(Comic.getId_Comic()).setValue(Comic);
    }
}