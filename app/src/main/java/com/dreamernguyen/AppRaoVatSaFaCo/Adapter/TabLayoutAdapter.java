package com.dreamernguyen.AppRaoVatSaFaCo.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.dreamernguyen.AppRaoVatSaFaCo.Fragment.KhamPhaFragment;
import com.dreamernguyen.AppRaoVatSaFaCo.Fragment.TheoDoiFragment;

public class TabLayoutAdapter extends FragmentStatePagerAdapter {

    public TabLayoutAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new KhamPhaFragment();
            case 1:
                return new TheoDoiFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Khám phá 🌏";
            case 1:
                return "Theo Dõi 🔗";
            default:
                return "Khám phá 🌏";
        }

    }
}
