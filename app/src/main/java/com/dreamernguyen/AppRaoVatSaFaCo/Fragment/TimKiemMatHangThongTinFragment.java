package com.dreamernguyen.AppRaoVatSaFaCo.Fragment;

import android.app.SearchManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dreamernguyen.AppRaoVatSaFaCo.Activity.DangMatHangActivity;
import com.dreamernguyen.AppRaoVatSaFaCo.Activity.TimKiemMatHangActivity;
import com.dreamernguyen.AppRaoVatSaFaCo.Adapter.DiaChiAdapter;
import com.dreamernguyen.AppRaoVatSaFaCo.Adapter.ThongTinAdapter;
import com.dreamernguyen.AppRaoVatSaFaCo.DataBase.TimKiemDataBase;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.Quan;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.TimKiem;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.Tinh;
import com.dreamernguyen.AppRaoVatSaFaCo.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TimKiemMatHangThongTinFragment extends Fragment {

    DiaChiAdapter diaChiAdapter;
    ThongTinAdapter thongTinAdapter;
    RecyclerView rv;
    ImageView imgBack;
    TextView tvTieuDe;
    SearchView searchView;

    List<String> DanhMuc = new ArrayList<>();
    List<String> DanhMucBatDongSan = new ArrayList<>();
    List<String> DanhMucXeCo = new ArrayList<>();
    List<String> DanhMucDoDienTu = new ArrayList<>();
    List<String> DanhMucSach = new ArrayList<>();
    List<String> DanhMucThoiTrang = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        DanhMuc.add("T???t c??? danh m???c");
        DanhMuc.addAll(Arrays.asList(getActivity().getResources().getStringArray(R.array.DanhMuc)));
        DanhMucBatDongSan.add("T???t c??? c??c lo???i b???t ?????ng s???n");
        DanhMucBatDongSan.addAll(Arrays.asList(getActivity().getResources().getStringArray(R.array.DanhMucBatDongSan)));
        DanhMucXeCo.add("T???t c??? c??c lo???i xe c???");
        DanhMucXeCo.addAll(Arrays.asList(getActivity().getResources().getStringArray(R.array.DanhMucXeCo)));
        DanhMucDoDienTu.add("T???t c??? c??c lo???i ????? ??i???n t???");
        DanhMucDoDienTu.addAll(Arrays.asList(getActivity().getResources().getStringArray(R.array.DanhMucDoDienTu)));
        DanhMucSach.add("T???t c??? c??c lo???i s??ch");
        DanhMucSach.addAll(Arrays.asList(getActivity().getResources().getStringArray(R.array.DanhMucSach)));
        DanhMucThoiTrang.add("T???t c??? c??c lo???i th???i trang");
        DanhMucThoiTrang.addAll(Arrays.asList(getActivity().getResources().getStringArray(R.array.DanhMucThoiTrang)));
        return inflater.inflate(R.layout.fragment_tim_kiem_thong_tin, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchView=view.findViewById(R.id.sv);
        rv = view.findViewById(R.id.rv);
        imgBack = view.findViewById(R.id.imgBack);
        tvTieuDe = view.findViewById(R.id.tvTieuDe);


        rv.setAdapter(thongTinAdapter);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).popBackStack();
            }
        });

        themThongTin(TimKiemMatHangActivity.viTri);
    }

    private void themThongTin(int viTri) {
        switch (viTri) {
            case 0:
                searchView.setVisibility(View.GONE);
                String danhMuc = TimKiemMatHangActivity.timKiemTemp.getDanhMuc();
                tvTieuDe.setText("H???ng m???c");
                if (danhMuc != null && !danhMuc.isEmpty()) {
                    hienThiDanhMucCon(danhMuc);
                } else {
                    hienThiDanhMucGoc();
                }

                break;

            case 1:
                searchView.setVisibility(View.VISIBLE);

                loadTinh();
                break;

        }

    }

    public void hienThiDanhMucGoc() {
        tvTieuDe.setText("H???ng m???c");
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(v).popBackStack();

            }
        });
        thongTinAdapter = new ThongTinAdapter(getActivity(), new ThongTinAdapter.clickitimTimKiem() {
            @Override
            public void getString(String a) {
                Log.d("TAG", "getString: " + a);

                hienThiDanhMucCon(a);
                if (a.equals("T???t c??? danh m???c")) {
                    TimKiemMatHangActivity.timKiemTemp.setDanhMuc("");
                    Navigation.findNavController(getActivity(), R.id.nav_host).popBackStack();

                }
                ;
                TimKiemDataBase.getInstance(getContext()).timKiemDAO().UpdateTemp(TimKiemMatHangActivity.timKiemTemp);


            }
        });
        thongTinAdapter.setData(DanhMuc);

        rv.setAdapter(thongTinAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        rv.addItemDecoration(itemDecoration);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));


    }

    public void hienThiDanhMucCon(String danhMuc) {
        tvTieuDe.setText("H???ng m???c con");
        if (DanhMucBatDongSan.contains(danhMuc) || danhMuc.equals("B???t ?????ng S???n")) {
            imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hienThiDanhMucGoc();
                }
            });
            thongTinAdapter = new ThongTinAdapter(getActivity(), new ThongTinAdapter.clickitimTimKiem() {
                @Override
                public void getString(String a) {

                    TimKiemMatHangActivity.timKiemTemp.setDanhMuc(a);
                    if (a.equals("T???t c??? c??c lo???i b???t ?????ng s???n")) {
                        TimKiemMatHangActivity.timKiemTemp.setDanhMuc("B???t ?????ng S???n");
                    }

                    TimKiemDataBase.getInstance(getContext()).timKiemDAO().UpdateTemp(TimKiemMatHangActivity.timKiemTemp);

                    Navigation.findNavController(getActivity(), R.id.nav_host).popBackStack();

                }
            });
            thongTinAdapter.setData(DanhMucBatDongSan);

        }
        if (DanhMucXeCo.contains(danhMuc) || danhMuc.equals("Xe C???")) {
            imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    hienThiDanhMucGoc();
                }
            });
            thongTinAdapter = new ThongTinAdapter(getActivity(), new ThongTinAdapter.clickitimTimKiem() {
                @Override
                public void getString(String a) {

                    Log.d("TAG", "getString: " + a);
                    TimKiemMatHangActivity.timKiemTemp.setDanhMuc(a);
                    if (a.equals("T???t c??? c??c lo???i xe c???")) {
                        TimKiemMatHangActivity.timKiemTemp.setDanhMuc("Xe C???");
                    }

                    TimKiemDataBase.getInstance(getContext()).timKiemDAO().UpdateTemp(TimKiemMatHangActivity.timKiemTemp);

                    Navigation.findNavController(getActivity(), R.id.nav_host).popBackStack();

                }
            });
            thongTinAdapter.setData(DanhMucXeCo);

        }
        if (DanhMucDoDienTu.contains(danhMuc) || danhMuc.equals("????? ??i???n T???")) {
            imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    hienThiDanhMucGoc();
                }
            });
            thongTinAdapter = new ThongTinAdapter(getActivity(), new ThongTinAdapter.clickitimTimKiem() {
                @Override
                public void getString(String a) {

                    Log.d("TAG", "getString: " + a);
                    TimKiemMatHangActivity.timKiemTemp.setDanhMuc(a);

                    if (a.equals("T???t c??? c??c lo???i ????? ??i???n t???")) {
                        TimKiemMatHangActivity.timKiemTemp.setDanhMuc("????? ??i???n T???");
                    }
                    TimKiemDataBase.getInstance(getContext()).timKiemDAO().UpdateTemp(TimKiemMatHangActivity.timKiemTemp);
                    Navigation.findNavController(getActivity(), R.id.nav_host).popBackStack();

                }
            });
            thongTinAdapter.setData(DanhMucDoDienTu);

        }
        if (DanhMucSach.contains(danhMuc) || danhMuc.equals("S??ch")) {
            imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    hienThiDanhMucGoc();
                }
            });
            thongTinAdapter = new ThongTinAdapter(getActivity(), new ThongTinAdapter.clickitimTimKiem() {
                @Override
                public void getString(String a) {

                    Log.d("TAG", "getString: " + a);
                    TimKiemMatHangActivity.timKiemTemp.setDanhMuc(a);

                    if (a.equals("T???t c??? c??c lo???i s??ch")) {
                        TimKiemMatHangActivity.timKiemTemp.setDanhMuc("S??ch");

                    }
                    TimKiemDataBase.getInstance(getContext()).timKiemDAO().UpdateTemp(TimKiemMatHangActivity.timKiemTemp);

                    Navigation.findNavController(getActivity(), R.id.nav_host).popBackStack();

                }
            });
            thongTinAdapter.setData(DanhMucSach);

        }
        if (DanhMucThoiTrang.contains(danhMuc) || danhMuc.equals("Th???i Trang")) {
            imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    hienThiDanhMucGoc();
                }
            });
            thongTinAdapter = new ThongTinAdapter(getActivity(), new ThongTinAdapter.clickitimTimKiem() {
                @Override
                public void getString(String a) {

                    Log.d("TAG", "getString: " + a);
                    TimKiemMatHangActivity.timKiemTemp.setDanhMuc(a);

                    if (a.equals("T???t c??? c??c lo???i th???i trang")) {
                        TimKiemMatHangActivity.timKiemTemp.setDanhMuc("Th???i Trang");

                    }
                    TimKiemDataBase.getInstance(getContext()).timKiemDAO().UpdateTemp(TimKiemMatHangActivity.timKiemTemp);

                    Navigation.findNavController(getActivity(), R.id.nav_host).popBackStack();

                }
            });
            thongTinAdapter.setData(DanhMucThoiTrang);

        }

        rv.setAdapter(thongTinAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        rv.addItemDecoration(itemDecoration);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    private void loadTinh() {
        tvTieuDe.setText("T???nh, th??nh ph???");
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String strUrl = "https://vapi.vnappmob.com/api/province/";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, strUrl, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Type listType = new TypeToken<List<Tinh>>() {
                    }.getType();

                    List<Tinh> listTinh = new Gson().fromJson(String.valueOf(response.getJSONArray("results")), listType);

                    Tinh tinh = new Tinh("0", "To??n Qu???c");
                    listTinh.add(0, tinh);
                    diaChiAdapter = new DiaChiAdapter(getContext(), (ma, Ten) -> {


                        TimKiem timKiem = TimKiemMatHangActivity.timKiemTemp;
                        timKiem.setDiaChi(Ten);
                        if (Ten.equals("To??n Qu???c")) {
                            timKiem.setDiaChi("");
                            Navigation.findNavController(getActivity(), R.id.nav_host).navigate(R.id.action_timKiemMatHangThongTinFragment_to_timKiemMatHangKetQuaFragment);

                        }
                        TimKiemDataBase.getInstance(getContext()).timKiemDAO().UpdateTemp(timKiem);

                        loadQuan(ma);
                    });

                    diaChiAdapter.setListTinh(listTinh);

                    searchViewRequest();
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                    rv.setLayoutManager(linearLayoutManager);
                    RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
                    rv.addItemDecoration(itemDecoration);
                    rv.setAdapter(diaChiAdapter);

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
        tvTieuDe.setText("Qu???n, huy???n");
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String strUrl = "https://vapi.vnappmob.com/api/province/district/" + id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, strUrl, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Type listType = new TypeToken<List<Quan>>() {
                    }.getType();
                    List<Quan> listQuan = new Gson().fromJson(String.valueOf(response.getJSONArray("results")), listType);

                    Quan quan = new Quan("0", "T???t C???");
                    listQuan.add(0, quan);
                    diaChiAdapter = new DiaChiAdapter(getContext(), (ma, Ten) -> {
                        TimKiem timKiem = TimKiemMatHangActivity.timKiemTemp;
                        if (Ten.equals("T???t C???")) {

                            Navigation.findNavController(getActivity(), R.id.nav_host).navigate(R.id.action_timKiemMatHangThongTinFragment_to_timKiemMatHangKetQuaFragment);
                        } else {
                            timKiem.setDiaChi(Ten);
                            Navigation.findNavController(getActivity(), R.id.nav_host).navigate(R.id.action_timKiemMatHangThongTinFragment_to_timKiemMatHangKetQuaFragment);

                        }
                        TimKiemDataBase.getInstance(getContext()).timKiemDAO().UpdateTemp(timKiem);
                    });
                    diaChiAdapter.setListQuan(listQuan);

                    searchViewRequest();


                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                    rv.setLayoutManager(linearLayoutManager);
                    RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
                    rv.addItemDecoration(itemDecoration);
                    rv.setAdapter(diaChiAdapter);

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

    public void searchViewRequest(){

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
