package com.dreamernguyen.AppRaoVatSaFaCo.Fragment;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dreamernguyen.AppRaoVatSaFaCo.Activity.TimKiemMatHangActivity;
import com.dreamernguyen.AppRaoVatSaFaCo.Adapter.TimKiemNhanhAdapter;
import com.dreamernguyen.AppRaoVatSaFaCo.Adapter.TimKiemNhanhLichSuAdapter;
import com.dreamernguyen.AppRaoVatSaFaCo.DataBase.TimKiemDataBase;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.TimKiem;
import com.dreamernguyen.AppRaoVatSaFaCo.R;

import java.util.List;

public class TimKiemMatHangNhanhFragment extends Fragment {

    TimKiemNhanhAdapter timKiemNhanhAdapter;
    SearchView searchView;
    RecyclerView recyclerView;
    TextView tvBack;

    TimKiemNhanhLichSuAdapter timKiemNhanhLichSuAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tim_kiem_nhanh, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchView = view.findViewById(R.id.sv);
        recyclerView = view.findViewById(R.id.rv);
        tvBack = view.findViewById(R.id.tvBack);

        setRVLichSu();

        nhanNut();


        SearchManager searchManager = (SearchManager) getContext().getSystemService(getContext().SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                setRVTimKiem();
                timKiemNhanhAdapter.setData(TimKiemMatHangActivity.list);
                TimKiem timKiem = TimKiemMatHangActivity.timKiemTemp;
                timKiem.setTieuDe(searchView.getQuery().toString());
                TimKiemDataBase.getInstance(getContext()).timKiemDAO().UpdateTemp(timKiem);
                Log.d("TAG", "onClick: "+ timKiem);

                timKiem = new TimKiem(searchView.getQuery().toString(), "", "", "", "", "LichSu");
                TimKiem timKiem2 = TimKiemDataBase.getInstance(getContext()).timKiemDAO().checkExists(timKiem.getTieuDe(),"LichSu");

                if (timKiem2 !=null&& !timKiem2.getTieuDe().isEmpty()) {


                }else {
                    TimKiemDataBase.getInstance(getContext()).timKiemDAO().createTemp(timKiem);

                }
                timKiemNhanhAdapter.getFilter().filter(query);




                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                setRVTimKiem();
//                timKiemNhanhAdapter.setData(TimKiemMatHangActivity.list);
//                timKiemNhanhAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void nhanNut() {
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimKiem timKiem = TimKiemMatHangActivity.timKiemTemp;
                timKiem.setTieuDe(searchView.getQuery().toString());
                TimKiemDataBase.getInstance(getContext()).timKiemDAO().UpdateTemp(timKiem);
                Log.d("TAG", "onClick: "+ timKiem);

                timKiem = new TimKiem(searchView.getQuery().toString(), "", "", "", "", "LichSu");
                TimKiem timKiem2 = TimKiemDataBase.getInstance(getContext()).timKiemDAO().checkExists(timKiem.getTieuDe(),"LichSu");

                if (timKiem2 !=null&& !timKiem2.getTieuDe().isEmpty()) {
                    Navigation.findNavController(v).navigate(R.id.action_timKiemMatHangNhanhFragment_to_timKiemMatHangKetQuaFragment);
                    return;
                }

                TimKiemDataBase.getInstance(getContext()).timKiemDAO().createTemp(timKiem);
                Navigation.findNavController(v).navigate(R.id.action_timKiemMatHangNhanhFragment_to_timKiemMatHangKetQuaFragment);

            }
        });
    }


    public void setRVLichSu(){

        timKiemNhanhLichSuAdapter = new TimKiemNhanhLichSuAdapter(getContext());

        timKiemNhanhLichSuAdapter.setData(TimKiemDataBase.getInstance(getContext()).timKiemDAO().findList("LichSu"));

        Log.d("TAG", "setRVLichSu: "+TimKiemDataBase.getInstance(getContext()).timKiemDAO().findList("LichSu"));
        recyclerView.setAdapter(timKiemNhanhLichSuAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }
    public void setRVTimKiem(){

        timKiemNhanhAdapter = new TimKiemNhanhAdapter(getContext());

        recyclerView.setAdapter(timKiemNhanhAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }
}
