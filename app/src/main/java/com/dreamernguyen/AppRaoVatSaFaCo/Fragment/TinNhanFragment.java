package com.dreamernguyen.AppRaoVatSaFaCo.Fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamernguyen.AppRaoVatSaFaCo.Activity.QuetQR;
import com.dreamernguyen.AppRaoVatSaFaCo.Activity.TrangCaNhanActivity;
import com.dreamernguyen.AppRaoVatSaFaCo.Adapter.LienHeAdapter;
import com.dreamernguyen.AppRaoVatSaFaCo.Adapter.NguoiDungAdapter;
import com.dreamernguyen.AppRaoVatSaFaCo.ApiService;
import com.dreamernguyen.AppRaoVatSaFaCo.LocalDataManager;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.NguoiDung;
import com.dreamernguyen.AppRaoVatSaFaCo.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TinNhanFragment extends Fragment {
    RecyclerView rvLienHe;
    LienHeAdapter lienHeAdapter;
    ImageView btnQuetMa;
    SwipeRefreshLayout refreshLayout;
    TextView tvTrong;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tin_nhan, container, false);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        rvLienHe = view.findViewById(R.id.rvLienHe);
        lienHeAdapter = new LienHeAdapter(getContext());
        btnQuetMa = view.findViewById(R.id.btnQuetMa);
        tvTrong = view.findViewById(R.id.tvTrong);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        rvLienHe.setLayoutManager(linearLayoutManager);
        rvLienHe.setAdapter(lienHeAdapter);
        loadLienHe();
        btnQuetMa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quetQR();
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadLienHe();
            }
        });

        return view;
    }
    public void loadLienHe(){
        Call<List<NguoiDung>> call = ApiService.apiService.danhSachLienHe(LocalDataManager.getIdNguoiDung());
        call.enqueue(new Callback<List<NguoiDung>>() {
            @Override
            public void onResponse(Call<List<NguoiDung>> call, Response<List<NguoiDung>> response) {
                List<NguoiDung> list = response.body();
                if(list.size() > 0){
                    lienHeAdapter.setData(list);
                    rvLienHe.setVisibility(View.VISIBLE);
                }else {
                    rvLienHe.setVisibility(View.GONE);
                    tvTrong.setVisibility(View.VISIBLE);
                    tvTrong.setText("Bạn chưa liên hệ với ai !. \nCó nhiều người bạn trên SAFACO đang đợi bạn mở lời đấy, liên hệ với họ ngay nào !");
                }
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<NguoiDung>> call, Throwable t) {
                Log.d("loadLienHe", "onFailure : "+t.getMessage());
                Toast.makeText(getActivity(),"Lỗi load liên hệ \n"+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void quetQR(){
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(this);
//        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt("Quét mã QR");
//        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setBeepEnabled(true);
//        integrator.setBarcodeImageEnabled(true);
        integrator.setOrientationLocked(true);
        integrator.setCaptureActivity(QuetQR.class);
        integrator.initiateScan();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data );
        if(intentResult.getContents() != null){
            Intent i = new Intent(getContext(), TrangCaNhanActivity.class);
            i.putExtra("idNguoiDung",intentResult.getContents());
            startActivity(i);
        }else {
            Toast.makeText(getContext(), "Quét thất bại ! Vui lòng thử lại ! ", Toast.LENGTH_SHORT).show();
        }
    }

}