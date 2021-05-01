package com.dreamernguyen.AppRaoVatSaFaCo.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dreamernguyen.AppRaoVatSaFaCo.Activity.TimKiemMatHangActivity;
import com.dreamernguyen.AppRaoVatSaFaCo.Adapter.MatHangAdapter;
import com.dreamernguyen.AppRaoVatSaFaCo.ApiService;
import com.dreamernguyen.AppRaoVatSaFaCo.DataBase.TimKiemDataBase;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.DuLieuTraVe;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.TimKiem;
import com.dreamernguyen.AppRaoVatSaFaCo.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TimKiemMatHangKetQuaFragment extends Fragment {


    EditText edTieuDe;
    TextView tvDiaChi, tvHangMuc;
    Spinner tvSapXep;
    RecyclerView rv;

    MatHangAdapter matHangAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tim_kiem_ket_qua, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edTieuDe = view.findViewById(R.id.edTieuDe);
        tvDiaChi = view.findViewById(R.id.tvDiaChi);
        tvHangMuc = view.findViewById(R.id.tvHangMuc);
        tvSapXep = view.findViewById(R.id.tvSapXep);
        rv = view.findViewById(R.id.rv);


        if (TimKiemMatHangActivity.timKiemTemp.getDanhMuc().isEmpty()) {
            tvHangMuc.setText("Tất cả danh mục");
        } else {
            tvHangMuc.setText(TimKiemMatHangActivity.timKiemTemp.getDanhMuc());
        }

        if (TimKiemMatHangActivity.timKiemTemp.getDiaChi().isEmpty()) {
            tvDiaChi.setText("Toàn Quốc");
        } else {
            tvDiaChi.setText(TimKiemMatHangActivity.timKiemTemp.getDiaChi());
        }

        matHangAdapter = new MatHangAdapter(getContext());
        rv.setAdapter(matHangAdapter);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        nhanNut();
    }

    public void nhanNut() {

        tvHangMuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(v).navigate(R.id.action_timKiemMatHangKetQuaFragment_to_timKiemMatHangThongTinFragment);

                TimKiemMatHangActivity.viTri = 0;
            }
        });
        tvDiaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(v).navigate(R.id.action_timKiemMatHangKetQuaFragment_to_timKiemMatHangThongTinFragment);
                TimKiemMatHangActivity.viTri = 1;
            }
        });

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.SapXep, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tvSapXep.setAdapter(arrayAdapter);
        tvSapXep.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            TimKiem timKiem = TimKiemMatHangActivity.timKiemTemp;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        timKiem.setSapXepGiaBan("1");
                        timKiem.setSapXepThoiGian("");
                        TimKiemDataBase.getInstance(getContext()).timKiemDAO().UpdateTemp(timKiem);
                        break;
                    case 1:
                        timKiem.setSapXepGiaBan("-1");
                        timKiem.setSapXepThoiGian("");
                        TimKiemDataBase.getInstance(getContext()).timKiemDAO().UpdateTemp(timKiem);

                        break;
                    case 2:
                        timKiem.setSapXepGiaBan("");
                        timKiem.setSapXepThoiGian("1");
                        TimKiemDataBase.getInstance(getContext()).timKiemDAO().UpdateTemp(timKiem);

                        break;
                    case 3:
                        timKiem.setSapXepGiaBan("");
                        timKiem.setSapXepThoiGian("-1");
                        TimKiemDataBase.getInstance(getContext()).timKiemDAO().UpdateTemp(timKiem);

                        break;

                }
                loadThongTin();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void loadThongTin() {
        TimKiem timKiem = TimKiemMatHangActivity.timKiemTemp;
        Log.d("TAG", "loadThongTinTimKiemDangTao: " + "tieude " + timKiem.getTieuDe() + " danhMuc " + timKiem.getDanhMuc() + " diachi " + timKiem.getDiaChi() + " sapxepgia " + timKiem.getSapXepGiaBan() + " sapxepthoigian " + timKiem.getSapXepThoiGian());
        Call<DuLieuTraVe> call = ApiService.apiService.timKiemMatHang(timKiem.getTieuDe(), timKiem.getDanhMuc(), timKiem.getDiaChi(), timKiem.getSapXepThoiGian(), timKiem.getSapXepGiaBan());
        call.enqueue(new Callback<DuLieuTraVe>() {
            @Override
            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                matHangAdapter.setData(response.body().getDanhSachMatHang());
                rv.setAdapter(matHangAdapter);
                rv.setLayoutManager(new LinearLayoutManager(getContext()));

            }

            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {

            }
        });
    }


}
