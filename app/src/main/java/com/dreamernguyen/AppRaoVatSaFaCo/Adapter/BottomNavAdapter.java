package com.dreamernguyen.AppRaoVatSaFaCo.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.dreamernguyen.AppRaoVatSaFaCo.Fragment.CaNhanFragment;
import com.dreamernguyen.AppRaoVatSaFaCo.Fragment.GianHangFragment;
import com.dreamernguyen.AppRaoVatSaFaCo.Fragment.ThongBaoFragment;
import com.dreamernguyen.AppRaoVatSaFaCo.Fragment.TinNhanFragment;
import com.dreamernguyen.AppRaoVatSaFaCo.Fragment.TrangChuFragment;

public class BottomNavAdapter extends FragmentStatePagerAdapter {

    public BottomNavAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return new TrangChuFragment();
            case 1 : return new GianHangFragment();
            case 2 : return new TinNhanFragment();
            case 3 : return new ThongBaoFragment();
            case 4 : return new CaNhanFragment();


        }
        return null;
    }

    @Override
    public int getCount() {
        return 5;
    }
}
