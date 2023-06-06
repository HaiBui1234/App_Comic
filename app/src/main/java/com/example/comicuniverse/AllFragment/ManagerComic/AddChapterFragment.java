package com.example.comicuniverse.AllFragment.ManagerComic;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.comicuniverse.allModel.ChapterModel;
import com.example.comicuniverse.allModel.ComicModel;
import com.exmple.comicuniverse.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddChapterFragment extends Fragment {
    SimpleDateFormat dateFormat=new SimpleDateFormat("MM/dd/YYYY");
    Date date=new Date();
    Spinner SP_idComic;
    EditText edNameChapter, edlinkChapter;
    Button btnaddChapter;
    ArrayList<ComicModel> comicModelArrayList;
    ArrayAdapter<ComicModel> adapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("ComicModel");
    public AddChapterFragment() {
        // Required empty public constructor
    }

    public static AddChapterFragment newInstance() {
        return new AddChapterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_chapter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SP_idComic = view.findViewById(R.id.id_idComic);
        edNameChapter = view.findViewById(R.id.id_nameChapter);
        edlinkChapter = view.findViewById(R.id.id_linkchapter);
        btnaddChapter = view.findViewById(R.id.id_addChapter);
        getDataComic();
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, comicModelArrayList);
        SP_idComic.setAdapter(adapter);
        btnaddChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addChapter();
            }
        });
    }

    private void getDataComic() {
        comicModelArrayList = new ArrayList<>();
        reference = database.getReference("ComicModel");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (comicModelArrayList != null) {
                    comicModelArrayList.clear();
                }
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    ComicModel comicModel = snapshot1.getValue(ComicModel.class);
                    comicModelArrayList.add(comicModel);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addChapter() {
        String name = edNameChapter.getText().toString();
        String link = edlinkChapter.getText().toString();
        ComicModel modelComic = (ComicModel) SP_idComic.getSelectedItem();
        String idChapter = reference.push().getKey();
        ChapterModel model = new ChapterModel(idChapter, name, link, dateFormat.format(date),0);
         //ChapterModel
        if (idChapter==null){
            return;
        }
        reference.child(modelComic.getId_Comic()).child("ChapterModel").child(idChapter).setValue(model, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(getActivity(), "Thanh cong", Toast.LENGTH_SHORT).show();
            }
        });


    }
}