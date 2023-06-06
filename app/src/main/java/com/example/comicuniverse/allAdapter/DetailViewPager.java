package com.example.comicuniverse.allAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.comicuniverse.AllFragment.DetailComic.ChapterFragment;
import com.example.comicuniverse.AllFragment.DetailComic.InformationAuthor;

public class DetailViewPager extends FragmentStateAdapter {
    public DetailViewPager(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new InformationAuthor();
            case 1:
                return new ChapterFragment();
            default:
                return new InformationAuthor();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
