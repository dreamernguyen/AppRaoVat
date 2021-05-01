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

import com.dreamernguyen.AppRaoVatSaFaCo.Activity.MatHangToiRaoActivity;
import com.dreamernguyen.AppRaoVatSaFaCo.Adapter.MatHangAdapter;
import com.dreamernguyen.AppRaoVatSaFaCo.R;


public class MatHangDaAnFragment extends Fragment {
    RecyclerView rvMatHang;
    MatHangAdapter matHangAdapter;
    SwipeRefreshLayout refreshLayout;
    TextView tvTrong;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mat_hang_da_an, container, false);
        tvTrong = view.findViewById(R.id.tvTrong);
        rvMatHang =  view.findViewById(R.id.rvMatHang);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        matHangAdapter = new MatHangAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        rvMatHang.setLayoutManager(linearLayoutManager);
        rvMatHang.setAdapter(matHangAdapter);
        if(MatHangToiRaoActivity.listDaAn.size() > 0){
            matHangAdapter.setData(MatHangToiRaoActivity.listDaAn);
        }else {
            tvTrong.setVisibility(View.VISIBLE);
            tvTrong.setText("Chưa có mặt hàng nào");
            rvMatHang.setVisibility(View.GONE);
        }
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MatHangToiRaoActivity.reloadMatHangToiRao(2);
            }
        });
        return view;
    }
}