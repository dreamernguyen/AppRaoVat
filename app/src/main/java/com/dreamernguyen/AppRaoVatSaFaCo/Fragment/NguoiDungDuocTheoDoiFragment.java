package com.dreamernguyen.AppRaoVatSaFaCo.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreamernguyen.AppRaoVatSaFaCo.Activity.NguoiTheoDoiActivity;
import com.dreamernguyen.AppRaoVatSaFaCo.Adapter.NguoiDungAdapter;
import com.dreamernguyen.AppRaoVatSaFaCo.R;


public class NguoiDungDuocTheoDoiFragment extends Fragment {
    RecyclerView rvNguoiDung;
    NguoiDungAdapter nguoiDungAdapter;
    SwipeRefreshLayout refreshLayout;
    TextView tvTrong;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nguoi_dung_duoc_theo_doi, container, false);
        tvTrong = view.findViewById(R.id.tvTrong);
        rvNguoiDung =  view.findViewById(R.id.rvNguoiDung);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        nguoiDungAdapter = new NguoiDungAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        rvNguoiDung.setLayoutManager(linearLayoutManager);
        rvNguoiDung.setAdapter(nguoiDungAdapter);
        if(NguoiTheoDoiActivity.listDuocTheoDoi.size() > 0){
            nguoiDungAdapter.setData(NguoiTheoDoiActivity.listDuocTheoDoi);
        }else {
            tvTrong.setVisibility(View.VISIBLE);
            tvTrong.setText("Không có người dùng nào !");
            rvNguoiDung.setVisibility(View.GONE);
        }
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                NguoiTheoDoiActivity.reloadNguoiTheoDoi(0);
            }
        });
        return view;
    }
}