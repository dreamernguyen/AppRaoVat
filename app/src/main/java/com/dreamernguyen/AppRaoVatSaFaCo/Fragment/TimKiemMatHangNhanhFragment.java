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
import com.dreamernguyen.AppRaoVatSaFaCo.DataBase.TimKiemDataBase;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.TimKiem;
import com.dreamernguyen.AppRaoVatSaFaCo.R;

public class TimKiemMatHangNhanhFragment extends Fragment {

    TimKiemNhanhAdapter timKiemNhanhAdapter;
    SearchView searchView;
    RecyclerView recyclerView;
    TextView tvBack;

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

        timKiemNhanhAdapter = new TimKiemNhanhAdapter(getContext());

        timKiemNhanhAdapter.setData(null);

        recyclerView.setAdapter(timKiemNhanhAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        nhanNut();



        SearchManager searchManager = (SearchManager) getContext().getSystemService(getContext().SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                timKiemNhanhAdapter.setData(TimKiemMatHangActivity.list);
                timKiemNhanhAdapter.getFilter().filter(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                timKiemNhanhAdapter.setData(TimKiemMatHangActivity.list);
                timKiemNhanhAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void nhanNut() {
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_timKiemMatHangNhanhFragment_to_timKiemMatHangKetQuaFragment);

                TimKiem timKiem = TimKiemMatHangActivity.timKiemTemp;
                timKiem.setTieuDe(searchView.getQuery().toString());

                TimKiemDataBase.getInstance(getContext()).timKiemDAO().UpdateTemp(timKiem);
            }
        });
    }

}
