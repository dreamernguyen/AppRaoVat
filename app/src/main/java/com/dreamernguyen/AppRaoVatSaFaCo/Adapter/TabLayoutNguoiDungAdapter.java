package com.dreamernguyen.AppRaoVatSaFaCo.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.dreamernguyen.AppRaoVatSaFaCo.Fragment.NguoiDungDuocTheoDoiFragment;
import com.dreamernguyen.AppRaoVatSaFaCo.Fragment.NguoiDungDangTheoDoiFragment;

public class TabLayoutNguoiDungAdapter extends FragmentStatePagerAdapter {
    private int duocTheoDoi,dangTheoDoi;

    public TabLayoutNguoiDungAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }
    public void setNumber(int duocTheoiDoi, int dangTheoDoi){
        this.duocTheoDoi = duocTheoiDoi;
        this.dangTheoDoi = dangTheoDoi;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new NguoiDungDuocTheoDoiFragment();
            case 1:
                return new NguoiDungDangTheoDoiFragment();
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
                return "Người theo dõi : " +duocTheoDoi;
            case 1:
                return "Đang theo dõi : " +dangTheoDoi;
            default:
                return "Người theo dõi : " +dangTheoDoi;
        }
    }
}
