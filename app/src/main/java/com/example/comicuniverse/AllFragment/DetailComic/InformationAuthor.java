package com.example.comicuniverse.AllFragment.DetailComic;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.comicuniverse.allModel.ComicModel;
import com.exmple.comicuniverse.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InformationAuthor#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InformationAuthor extends Fragment {
    TextView tvdescriptionDetail,tvView,tvNameAuthor;
    public InformationAuthor() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InformationAuthor.
     */
    // TODO: Rename and change types and number of parameters
    public static InformationAuthor newInstance(String param1, String param2) {
        InformationAuthor fragment = new InformationAuthor();
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
        return inflater.inflate(R.layout.fragment_information_author, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvdescriptionDetail=view.findViewById(R.id.descriptionDetail);
        tvNameAuthor=view.findViewById(R.id.NameAuthor);
        tvView=view.findViewById(R.id.Viewa);
        Intent intent=getActivity().getIntent();
        ComicModel Comic= (ComicModel) intent.getExtras().getSerializable("Comic");
        tvdescriptionDetail.setText(Comic.getDesScription());
        tvView.setText(String.valueOf(Comic.getViewComic()));
        tvNameAuthor.setText("Post by:"+Comic.getName_Author());
    }
}