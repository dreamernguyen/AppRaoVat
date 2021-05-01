package com.dreamernguyen.AppRaoVatSaFaCo.Fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dreamernguyen.AppRaoVatSaFaCo.Adapter.ThongBaoAdapter;
import com.dreamernguyen.AppRaoVatSaFaCo.ApiService;
import com.dreamernguyen.AppRaoVatSaFaCo.LocalDataManager;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.ThongBao;
import com.dreamernguyen.AppRaoVatSaFaCo.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ThongBaoFragment extends Fragment {
    RecyclerView rvThongBao;
    ThongBaoAdapter thongBaoAdapter;
    SwipeRefreshLayout refreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thong_bao, container, false);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        rvThongBao= view.findViewById(R.id.rvThongBao);
        thongBaoAdapter = new ThongBaoAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        rvThongBao.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        rvThongBao.addItemDecoration(itemDecoration);
        rvThongBao.setAdapter(thongBaoAdapter);
        loadThongBao();
        return view;
    }
    public void loadThongBao(){
        Call<List<ThongBao>> call = ApiService.apiService.danhSachThongBao(LocalDataManager.getIdNguoiDung());
        call.enqueue(new Callback<List<ThongBao>>() {
            @Override
            public void onResponse(Call<List<ThongBao>> call, Response<List<ThongBao>> response) {
                refreshLayout.setRefreshing(false);
                if(response.body().size() >0){
                    thongBaoAdapter.setData(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ThongBao>> call, Throwable t) {
                Log.d("loadThongBao", "onFailure: "+t.getMessage());
                Toast.makeText(getContext(),"Lỗi load bài viết theo dõi !\n" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}