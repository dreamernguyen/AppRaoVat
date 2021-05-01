package com.dreamernguyen.AppRaoVatSaFaCo.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamernguyen.AppRaoVatSaFaCo.Adapter.TabLayoutAdapter;
import com.dreamernguyen.AppRaoVatSaFaCo.R;
import com.google.android.material.tabs.TabLayout;

public class TrangChuFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trang_chu, container, false);
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.home_viewpager);
        TabLayoutAdapter tabLayoutAdapter = new TabLayoutAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(tabLayoutAdapter);
        tabLayout.setTabTextColors(getResources().getColor(R.color.black_overlay),getResources().getColor(R.color.main_pink));
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
}