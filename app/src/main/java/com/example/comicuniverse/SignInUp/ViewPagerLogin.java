package com.example.comicuniverse.SignInUp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerLogin extends FragmentStateAdapter {
    public ViewPagerLogin(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new LoginFragment();
            case 1:
                return new SingUpFragment();
            default:
                return new LoginFragment();
        }

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
