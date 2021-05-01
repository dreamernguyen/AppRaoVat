package com.dreamernguyen.AppRaoVatSaFaCo.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.dreamernguyen.AppRaoVatSaFaCo.Fragment.KhamPhaFragment;
import com.dreamernguyen.AppRaoVatSaFaCo.Fragment.MatHangChoDuyetFragment;
import com.dreamernguyen.AppRaoVatSaFaCo.Fragment.MatHangDaAnFragment;
import com.dreamernguyen.AppRaoVatSaFaCo.Fragment.MatHangDangRaoFragment;
import com.dreamernguyen.AppRaoVatSaFaCo.Fragment.TheoDoiFragment;

public class TabLayoutMatHangAdapter extends FragmentStatePagerAdapter {
    private int dangRao,dangCho,daAn;
    public TabLayoutMatHangAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }
    public void setNumber(int dangRao, int dangCho, int daAn){
        this.dangRao = dangRao;
        this.dangCho = dangCho;
        this.daAn = daAn;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MatHangDangRaoFragment();
            case 1:
                return new MatHangChoDuyetFragment();
            case 2:
                return new MatHangDaAnFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Đang rao ("+dangRao+")";
            case 1:
                return "Chờ duyệt ("+dangCho+")";
            case 2 :
                return "Đã ẩn ("+daAn+")";
            default:
                return "Đang rao ("+dangRao+")";
        }
    }
}
