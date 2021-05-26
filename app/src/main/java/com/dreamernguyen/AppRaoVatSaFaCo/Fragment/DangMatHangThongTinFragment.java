package com.dreamernguyen.AppRaoVatSaFaCo.Fragment;

import android.app.SearchManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dreamernguyen.AppRaoVatSaFaCo.Activity.DangMatHangActivity;
import com.dreamernguyen.AppRaoVatSaFaCo.Adapter.DiaChiAdapter;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.Quan;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.Tinh;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.Xa;
import com.dreamernguyen.AppRaoVatSaFaCo.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class DangMatHangThongTinFragment extends Fragment {

    SearchView searchView;
    TextView tv;
    ImageView tvBack;
    ListView lvThongTin;
    ArrayAdapter<String> adapter;
    String[] DanhMuc;
    String[] DanhMucBatDongSan;
    String[] DanhMucXeCo;
    String[] DanhMucDoDienTu;
    String[] DanhMucSach;
    String[] DanhMucThoiTrang;
    RecyclerView rvDiaChi;
    DiaChiAdapter diaChiAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dang_mat_hang_thong_tin, container, false);

        searchView = view.findViewById(R.id.sv);
        tvBack = view.findViewById(R.id.imgBack);
        lvThongTin = view.findViewById(R.id.lvThongTin);
        rvDiaChi = view.findViewById(R.id.rvDiaChi);

        DanhMuc = view.getResources().getStringArray(R.array.DanhMuc);
        DanhMucBatDongSan = view.getResources().getStringArray(R.array.DanhMucBatDongSan);
        DanhMucXeCo = view.getResources().getStringArray(R.array.DanhMucXeCo);
        DanhMucDoDienTu = view.getResources().getStringArray(R.array.DanhMucDoDienTu);
        DanhMucSach = view.getResources().getStringArray(R.array.DanhMucSach);
        DanhMucThoiTrang = view.getResources().getStringArray(R.array.DanhMucThoiTrang);


        themThongTin(DangMatHangActivity.viTri);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void themThongTin(int a) {
        switch (a) {


            case 0:
                searchView.setVisibility(View.GONE);
                tvBack.setOnClickListener(v -> {
                    Navigation.findNavController(v).navigate(R.id.actionThongTinToDanhMuc);
                });
                if (DangMatHangActivity.DanhMuc.isEmpty()) {
                    adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, DanhMuc);
                    lvThongTin.setAdapter(adapter);
                    lvThongTin.setOnItemClickListener((parent, view, position, id) -> {
                        DangMatHangActivity.DanhMuc = lvThongTin.getItemAtPosition(position).toString();
                        themDanhMucCon(DangMatHangActivity.DanhMuc);
                    });
                } else {
                    themDanhMucCon(DangMatHangActivity.DanhMuc);
                }
                break;
            case 2:
                searchView.setVisibility(View.VISIBLE);
                tvBack.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.actionThongTinToDiaChi));
                switch (DangMatHangActivity.loadDiaChi) {
                    case "Tinh":
                        loadTinh();
                        break;
                    case "Quan":
                        loadQuan(DangMatHangActivity.idDiaChi);
                        break;
                    case "Xa":
                        loadXa(DangMatHangActivity.idDiaChi);
                        break;
                }
                break;
        }
    }

    public void themDanhMucCon(String a) {
        tvBack.setOnClickListener(v -> {
            DangMatHangActivity.DanhMuc = "";
            themThongTin(0);

        });
        switch (a) {
            case "Bất Động Sản":
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, DanhMucBatDongSan);
                break;
            case "Xe Cộ":
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, DanhMucXeCo);
                break;
            case "Đồ Điện Tử":
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, DanhMucDoDienTu);
                break;
            case "Sách":
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, DanhMucSach);
                break;
            case "Thời Trang":
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, DanhMucThoiTrang);
                break;
        }

        lvThongTin.setAdapter(adapter);

        lvThongTin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Navigation.findNavController(view).navigate(R.id.actionThongTinToDanhMuc);
                DangMatHangActivity.DanhMucCon = lvThongTin.getItemAtPosition(position).toString();
            }
        });
    }

    private void loadTinh() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String strUrl = "https://vapi.vnappmob.com/api/province/";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, strUrl, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Type listType = new TypeToken<List<Tinh>>() {
                    }.getType();
                    List<Tinh> listTinh = new Gson().fromJson(String.valueOf(response.getJSONArray("results")), listType);

                    diaChiAdapter = new DiaChiAdapter(getContext(), (ma, Ten) -> {

                        DangMatHangActivity.idDiaChi = ma;
                        DangMatHangActivity.ThanhPho = Ten;
                        Navigation.findNavController(getActivity(), R.id.nav_host).navigate(R.id.actionThongTinToDiaChi);

                    });

                    diaChiAdapter.setListTinh(listTinh);
                    searchViewRequest();
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                    rvDiaChi.setLayoutManager(linearLayoutManager);
                    rvDiaChi.setAdapter(diaChiAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void loadQuan(String id) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String strUrl = "https://vapi.vnappmob.com/api/province/district/" + id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, strUrl, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Type listType = new TypeToken<List<Quan>>() {
                    }.getType();
                    List<Quan> listQuan = new Gson().fromJson(String.valueOf(response.getJSONArray("results")), listType);

                    diaChiAdapter = new DiaChiAdapter(getContext(), (ma, Ten) -> {

                        DangMatHangActivity.idDiaChi = ma;
                        DangMatHangActivity.QuanHuyen = Ten;
                        Navigation.findNavController(getActivity(), R.id.nav_host).navigate(R.id.actionThongTinToDiaChi);

                    });
                    diaChiAdapter.setListQuan(listQuan);

                    searchViewRequest();
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                    rvDiaChi.setLayoutManager(linearLayoutManager);
                    rvDiaChi.setAdapter(diaChiAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void loadXa(String id) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String strUrl = "https://vapi.vnappmob.com/api/province/ward/" + id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, strUrl, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Type listType = new TypeToken<List<Xa>>() {
                    }.getType();
                    List<Xa> listXa = new Gson().fromJson(String.valueOf(response.getJSONArray("results")), listType);

                    diaChiAdapter = new DiaChiAdapter(getContext(), (ma, Ten) -> {

                        DangMatHangActivity.idDiaChi = ma;
                        DangMatHangActivity.PhuongXa = Ten;
                        Navigation.findNavController(getActivity(), R.id.nav_host).navigate(R.id.actionThongTinToDiaChi);

                    });
                    diaChiAdapter.setListXa(listXa);

                    searchViewRequest();

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                    rvDiaChi.setLayoutManager(linearLayoutManager);
                    rvDiaChi.setAdapter(diaChiAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void searchViewRequest() {

        SearchManager searchManager = (SearchManager) getContext().getSystemService(getContext().SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                diaChiAdapter.getFilter().filter(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                diaChiAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}

