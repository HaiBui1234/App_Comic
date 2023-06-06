package com.example.comicuniverse.AllFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.comicuniverse.AllInterface.InterFaceFavorite;
import com.example.comicuniverse.SearchActivity;
import com.example.comicuniverse.allAdapter.ListComicAdapter;
import com.example.comicuniverse.allAdapter.ViewpagerSlideAdapter;
import com.example.comicuniverse.allModel.CategoryModel;
import com.example.comicuniverse.allModel.ComicModel;
import com.example.comicuniverse.allModel.FavoriteComic;
import com.example.comicuniverse.allModel.ListComic;
import com.example.comicuniverse.allModel.UserModel;
import com.exmple.comicuniverse.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment implements InterFaceFavorite {
    private ViewPager Vp_Slideauto;
    TextView ed_Search;
    private CircleIndicator Cir_slide3;
    private ListComicAdapter mListComicAdapter;
    ViewpagerSlideAdapter SlideAdapter;
    private ArrayList<ListComic> ListData;
    private ArrayList<ComicModel> modelArrayList;
    private ArrayList<ComicModel> modelArrayListSlide;
    private ArrayList<CategoryModel> modelArrayListCate;
    RecyclerView Rc_Home;
    UserModel userModel;
    private Timer mTimer;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        Log.d("zzz", "onViewCreated: "+userModel.getUserName());
        Rc_Home = view.findViewById(R.id.Rc_HomeComic);
        Vp_Slideauto = view.findViewById(R.id.Vp_Slide);
        Cir_slide3 = view.findViewById(R.id.Cr_slide);
        ed_Search=view.findViewById(R.id.ed_Search);
        ed_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),SearchActivity.class));
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        Rc_Home.setLayoutManager(manager);
        getdataComic();
        Intent intent = getActivity().getIntent();
        userModel = (UserModel) intent.getSerializableExtra("user");
        ListData.add(new ListComic(ListComicAdapter.HORZONTAL,"Thể Loại",null,modelArrayListCate));
        ListData.add(new ListComic(ListComicAdapter.VERTICAL,"Tất cả truyện", modelArrayList,null));
        mListComicAdapter = new ListComicAdapter(getActivity(),userModel,this);
        mListComicAdapter.setdataList(ListData);
        Rc_Home.setAdapter(mListComicAdapter);
        autoSlide();
    }

    public void getdataComic() {
        modelArrayList = new ArrayList<>();
        modelArrayListCate=new ArrayList<>();
        ListData = new ArrayList<>();
        DatabaseReference reference=database.getReference("ComicModel");
        DatabaseReference referenceCategory=database.getReference("CategoryModel");
        referenceCategory.addChildEventListener(new ChildEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                CategoryModel model=snapshot.getValue(CategoryModel.class);
                Log.d("aaa", "onChildAdded: "+model.getName_Catagory());
                modelArrayListCate.add(model);
                mListComicAdapter.notifyDataSetChanged();
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

        reference.addChildEventListener(new ChildEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ComicModel comicModel=snapshot.getValue(ComicModel.class);
                modelArrayList.add(comicModel);
                mListComicAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ComicModel comicModel=snapshot.getValue(ComicModel.class);
                if (comicModel==null){
                    return;
                }
                for (int i=0;i<modelArrayList.size();i++){
                    modelArrayList.set(i,comicModel);
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
    public void autoSlide() {
        modelArrayListSlide=new ArrayList<>();
        DatabaseReference reference=database.getReference("ComicModel");
        Query query =reference.orderByChild("viewComic").limitToLast(5);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ComicModel comicModel=snapshot.getValue(ComicModel.class);
                modelArrayListSlide.add(comicModel);
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
        SlideAdapter = new ViewpagerSlideAdapter(getActivity());
        SlideAdapter.setdataSLide(modelArrayListSlide);
        Vp_Slideauto.setAdapter(SlideAdapter);
        Cir_slide3.setViewPager(Vp_Slideauto);
        SlideAdapter.registerDataSetObserver(Cir_slide3.getDataSetObserver());
        if (mTimer == null) {
            mTimer = new Timer();
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        SlideAdapter.notifyDataSetChanged();
                        int currenPossition = Vp_Slideauto.getCurrentItem();
                        if (currenPossition == modelArrayListSlide.size() - 1) {
                            Vp_Slideauto.setCurrentItem(0);
                        } else {
                            Vp_Slideauto.setCurrentItem(currenPossition + 1);
                        }
                    }
                });
            }
        },500, 3000);
    }

    @Override
    public void addFavorite(FavoriteComic favoriteComic,String id) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("UserModel");
        reference.child(userModel.getId_User()).child("FavoriteComic").child(id).setValue(favoriteComic, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(getActivity(), "Đã thêm", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void adDeleteFavorite(String id) {

    }
}