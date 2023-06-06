package com.example.comicuniverse.AllFragment.ManagerComic;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdmin extends FragmentStateAdapter {
    public ViewPagerAdmin(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new AddCatagoryFragment();
            case 1:
                return new AddComicFragment();
            case 2:
                return new AddChapterFragment();
            default:
                return new AddCatagoryFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
