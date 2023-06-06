package com.example.comicuniverse.AllFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.comicuniverse.AllInterface.InterFaceFavorite;
import com.example.comicuniverse.allAdapter.GridViewAdapter;
import com.example.comicuniverse.allModel.ComicModel;
import com.example.comicuniverse.allModel.FavoriteComic;
import com.example.comicuniverse.allModel.HistoryModel;
import com.exmple.comicuniverse.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment implements InterFaceFavorite {

    RecyclerView Recy_History;
    GridViewAdapter adapter;
    TextView tvTitlehis;
    ArrayList<ComicModel> modelArrayList;
    ArrayList<HistoryModel> historyModelArrayList;
    public HistoryFragment() {

    }

    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
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
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Recy_History=view.findViewById(R.id.id_RecyHistory);
        tvTitlehis=view.findViewById(R.id.tvTitlehis);
        GridLayoutManager manager=new GridLayoutManager(getActivity(),3,RecyclerView.VERTICAL,false);
        Recy_History.setLayoutManager(manager);
        getDataHistory();
        adapter=new GridViewAdapter(getActivity(),modelArrayList,this);
        Recy_History.setAdapter(adapter);
    }

    private void getDataHistory() {
        modelArrayList=new ArrayList<>();
        SharedPreferences preferences=getActivity().getSharedPreferences("Account", Context.MODE_PRIVATE);
        String idUser=preferences.getString("ID","");
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference("UserModel").child(idUser).child("HistoryModel");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ComicModel comicModel=snapshot.getValue(ComicModel.class);
                if (comicModel==null){
                    tvTitlehis.setVisibility(View.VISIBLE);
                }else {
                    tvTitlehis.setVisibility(View.INVISIBLE);
                }
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

    }

    @Override
    public void adDeleteFavorite(String id) {

    }
}