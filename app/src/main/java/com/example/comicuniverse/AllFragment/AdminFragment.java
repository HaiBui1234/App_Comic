package com.example.comicuniverse.AllFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.comicuniverse.AllFragment.ManagerComic.ViewPagerAdmin;
import com.exmple.comicuniverse.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class AdminFragment extends Fragment {
    private TabLayout Tb_Admin;
    private ViewPager2 Vp_Admin;
    private ViewPagerAdmin mViewPagerAdmin;
    public AdminFragment() {
        // Required empty public constructor
    }

    public static AdminFragment newInstance() {
        AdminFragment fragment = new AdminFragment();
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
        return inflater.inflate(R.layout.fragment_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Tb_Admin=view.findViewById(R.id.Tb_admin);
        Vp_Admin=view.findViewById(R.id.Vp_Admin);
        mViewPagerAdmin=new ViewPagerAdmin(getActivity());
        Vp_Admin.setAdapter(mViewPagerAdmin);
        new TabLayoutMediator(Tb_Admin, Vp_Admin, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Catagory");
                        break;
                    case 1:
                        tab.setText("Comic");
                        break;
                    case 2:
                        tab.setText("Chapter");
                        break;
                }
            }
        }).attach();
    }
}