package com.dreamernguyen.AppRaoVatSaFaCo.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    TextView tvBack;

    List<String> DanhMuc = new ArrayList<>();
    List<String> DanhMucBatDongSan = new ArrayList<>();
    List<String> DanhMucXeCo = new ArrayList<>();
    List<String> DanhMucDoDienTu = new ArrayList<>();
    List<String> DanhMucSach = new ArrayList<>();
    List<String> DanhMucThoiTrang = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        DanhMuc.add("Tất cả danh mục");
        DanhMuc.addAll(Arrays.asList(getActivity().getResources().getStringArray(R.array.DanhMuc)));
        DanhMucBatDongSan.add("Tất cả các loại bất động sản");
        DanhMucBatDongSan.addAll(Arrays.asList(getActivity().getResources().getStringArray(R.array.DanhMucBatDongSan)));
        DanhMucXeCo.add("Tất cả các loại xe cộ");
        DanhMucXeCo.addAll(Arrays.asList(getActivity().getResources().getStringArray(R.array.DanhMucXeCo)));
        DanhMucDoDienTu.add("Tất cả các loại đồ điện tử");
        DanhMucDoDienTu.addAll(Arrays.asList(getActivity().getResources().getStringArray(R.array.DanhMucDoDienTu)));
        DanhMucSach.add("Tất cả các loại sách");
        DanhMucSach.addAll(Arrays.asList(getActivity().getResources().getStringArray(R.array.DanhMucSach)));
        DanhMucThoiTrang.add("Tất cả các loại thời trang");
        DanhMucThoiTrang.addAll(Arrays.asList(getActivity().getResources().getStringArray(R.array.DanhMucThoiTrang)));
        return inflater.inflate(R.layout.fragment_tim_kiem_thong_tin, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv = view.findViewById(R.id.rv);
        tvBack = view.findViewById(R.id.tvBack);


        rv.setAdapter(thongTinAdapter);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        tvBack.setOnClickListener(new View.OnClickListener() {
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
                Log.d("TAG", "themThongTin: " + "danhMuc");

                String danhMuc = TimKiemMatHangActivity.timKiemTemp.getDanhMuc();

                if (danhMuc != null && !danhMuc.isEmpty()) {
                    hienThiDanhMucCon(danhMuc);
                } else {
                    hienThiDanhMucGoc();
                }

                break;

            case 1:
                loadTinh();
                break;

        }

    }

    public void hienThiDanhMucGoc() {

        tvBack.setOnClickListener(new View.OnClickListener() {
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
                if (a.equals("Tất cả danh mục")) {
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

        if (DanhMucBatDongSan.contains(danhMuc) || danhMuc.equals("Bất Động Sản")) {
            tvBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hienThiDanhMucGoc();
                }
            });
            thongTinAdapter = new ThongTinAdapter(getActivity(), new ThongTinAdapter.clickitimTimKiem() {
                @Override
                public void getString(String a) {

                    TimKiemMatHangActivity.timKiemTemp.setDanhMuc(a);
                    if (a.equals("Tất cả các loại bất động sản")) {
                        TimKiemMatHangActivity.timKiemTemp.setDanhMuc("Bất Động Sản");
                    }

                    TimKiemDataBase.getInstance(getContext()).timKiemDAO().UpdateTemp(TimKiemMatHangActivity.timKiemTemp);

                    Navigation.findNavController(getActivity(), R.id.nav_host).popBackStack();

                }
            });
            thongTinAdapter.setData(DanhMucBatDongSan);

        }
        if (DanhMucXeCo.contains(danhMuc) || danhMuc.equals("Xe Cộ")) {
            tvBack.setOnClickListener(new View.OnClickListener() {
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
                    if (a.equals("Tất cả các loại xe cộ")) {
                        TimKiemMatHangActivity.timKiemTemp.setDanhMuc("Xe Cộ");
                    }

                    TimKiemDataBase.getInstance(getContext()).timKiemDAO().UpdateTemp(TimKiemMatHangActivity.timKiemTemp);

                    Navigation.findNavController(getActivity(), R.id.nav_host).popBackStack();

                }
            });
            thongTinAdapter.setData(DanhMucXeCo);

        }
        if (DanhMucDoDienTu.contains(danhMuc) || danhMuc.equals("Đồ Điện Tử")) {
            tvBack.setOnClickListener(new View.OnClickListener() {
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

                    if (a.equals("Tất cả các loại đồ điện tử")) {
                        TimKiemMatHangActivity.timKiemTemp.setDanhMuc("Đồ Điện Tử");
                    }
                    TimKiemDataBase.getInstance(getContext()).timKiemDAO().UpdateTemp(TimKiemMatHangActivity.timKiemTemp);
                    Navigation.findNavController(getActivity(), R.id.nav_host).popBackStack();

                }
            });
            thongTinAdapter.setData(DanhMucDoDienTu);

        }
        if (DanhMucSach.contains(danhMuc) || danhMuc.equals("Sách")) {
            tvBack.setOnClickListener(new View.OnClickListener() {
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

                    if (a.equals("Tất cả các loại sách")) {
                        TimKiemMatHangActivity.timKiemTemp.setDanhMuc("Sách");

                    }
                    TimKiemDataBase.getInstance(getContext()).timKiemDAO().UpdateTemp(TimKiemMatHangActivity.timKiemTemp);

                    Navigation.findNavController(getActivity(), R.id.nav_host).popBackStack();

                }
            });
            thongTinAdapter.setData(DanhMucSach);

        }
        if (DanhMucThoiTrang.contains(danhMuc) || danhMuc.equals("Thời Trang")) {
            tvBack.setOnClickListener(new View.OnClickListener() {
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

                    if (a.equals("Tất cả các loại thời trang")) {
                        TimKiemMatHangActivity.timKiemTemp.setDanhMuc("Thời Trang");

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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String strUrl = "https://vapi.vnappmob.com/api/province/";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, strUrl, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Type listType = new TypeToken<List<Tinh>>() {
                    }.getType();

                    List<Tinh> listTinh = new Gson().fromJson(String.valueOf(response.getJSONArray("results")), listType);

                    Tinh tinh = new Tinh("0", "Toàn Quốc");
                    listTinh.add(0, tinh);
                    diaChiAdapter = new DiaChiAdapter(getContext(), (ma, Ten) -> {


                        TimKiem timKiem = TimKiemMatHangActivity.timKiemTemp;
                        timKiem.setDiaChi(Ten);
                        if (Ten.equals("Toàn Quốc")) {
                            timKiem.setDiaChi("");
                            Navigation.findNavController(getActivity(), R.id.nav_host).navigate(R.id.action_timKiemMatHangThongTinFragment_to_timKiemMatHangKetQuaFragment);

                        }
                        TimKiemDataBase.getInstance(getContext()).timKiemDAO().UpdateTemp(timKiem);

                        loadQuan(ma);
                    });

                    diaChiAdapter.setListTinh(listTinh);
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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String strUrl = "https://vapi.vnappmob.com/api/province/district/" + id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, strUrl, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Type listType = new TypeToken<List<Quan>>() {
                    }.getType();
                    List<Quan> listQuan = new Gson().fromJson(String.valueOf(response.getJSONArray("results")), listType);

                    Quan quan = new Quan("0", "Tất Cả");
                    listQuan.add(0, quan);
                    diaChiAdapter = new DiaChiAdapter(getContext(), (ma, Ten) -> {
                        TimKiem timKiem = TimKiemMatHangActivity.timKiemTemp;
                        if (Ten.equals("Tất Cả")) {

                            Navigation.findNavController(getActivity(), R.id.nav_host).navigate(R.id.action_timKiemMatHangThongTinFragment_to_timKiemMatHangKetQuaFragment);
                        } else {
                            timKiem.setDiaChi(Ten);
                            Navigation.findNavController(getActivity(), R.id.nav_host).navigate(R.id.action_timKiemMatHangThongTinFragment_to_timKiemMatHangKetQuaFragment);

                        }
                        TimKiemDataBase.getInstance(getContext()).timKiemDAO().UpdateTemp(timKiem);
                    });
                    diaChiAdapter.setListQuan(listQuan);
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


}
