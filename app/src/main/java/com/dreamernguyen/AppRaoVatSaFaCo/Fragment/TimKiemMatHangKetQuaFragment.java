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
import com.dreamernguyen.AppRaoVatSaFaCo.Adapter.MatHangAdapter2;
import com.dreamernguyen.AppRaoVatSaFaCo.ApiService;
import com.dreamernguyen.AppRaoVatSaFaCo.DataBase.TimKiemDataBase;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.DuLieuTraVe;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.TimKiem;
import com.dreamernguyen.AppRaoVatSaFaCo.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TimKiemMatHangKetQuaFragment extends Fragment {


    EditText edTieuDe;
    TextView tvDiaChi,tvTrong;
    Spinner tvSapXep;
    RecyclerView rv;
    MaterialButton btnDangMuc;

    TextInputEditText edTimKiem;
    MatHangAdapter2 matHangAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tim_kiem_ket_qua, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edTimKiem=view.findViewById(R.id.edTimKiem);
        edTieuDe = view.findViewById(R.id.edTieuDe);
        tvDiaChi = view.findViewById(R.id.tvDiaChi);
        btnDangMuc = view.findViewById(R.id.btnDanhMuc);
        tvSapXep = view.findViewById(R.id.spnSapXep);
        tvTrong = view.findViewById(R.id.tvTrong);
        rv = view.findViewById(R.id.rv);

        edTimKiem.setText(TimKiemMatHangActivity.timKiemTemp.getTieuDe());

        if (TimKiemMatHangActivity.timKiemTemp.getDanhMuc().isEmpty()) {
            btnDangMuc.setText("T???t c???");
        } else {
            btnDangMuc.setText(TimKiemMatHangActivity.timKiemTemp.getDanhMuc());
        }

        if (TimKiemMatHangActivity.timKiemTemp.getDiaChi().isEmpty()) {
            tvDiaChi.setText("To??n Qu???c");
        } else {
            tvDiaChi.setText(TimKiemMatHangActivity.timKiemTemp.getDiaChi());
        }

        matHangAdapter = new MatHangAdapter2(getContext());
        rv.setAdapter(matHangAdapter);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));


        nhanNut();
    }

    public void nhanNut() {

        btnDangMuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("a", "onClick: ");
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

        edTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_timKiemMatHangKetQuaFragment_to_timKiemMatHangNhanhFragment);
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
                        timKiem.setSapXepGiaBan("");
                        timKiem.setSapXepThoiGian("-1");
                        TimKiemDataBase.getInstance(getContext()).timKiemDAO().UpdateTemp(timKiem);

                          break;
                    case 1:
                        timKiem.setSapXepGiaBan("");
                        timKiem.setSapXepThoiGian("1");
                        TimKiemDataBase.getInstance(getContext()).timKiemDAO().UpdateTemp(timKiem);
                        break;
                    case 2:
                        timKiem.setSapXepGiaBan("1");
                        timKiem.setSapXepThoiGian("");
                        TimKiemDataBase.getInstance(getContext()).timKiemDAO().UpdateTemp(timKiem);

                        break;
                    case 3:

                        timKiem.setSapXepGiaBan("-1");
                        timKiem.setSapXepThoiGian("");
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


                if(response.body().getDanhSachMatHang().size() >0){
                    rv.setVisibility(View.VISIBLE);
                    matHangAdapter.setData(response.body().getDanhSachMatHang());
                    rv.setAdapter(matHangAdapter);
                    rv.setLayoutManager(new LinearLayoutManager(getContext()));
                }else {
                    rv.setVisibility(View.GONE);
                    tvTrong.setVisibility(View.VISIBLE);
                    tvTrong.setText("Kh??ng c?? m???t h??ng n??o ph?? h???p t??m ki???m c???a b???n !");
                }


            }

            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {

            }
        });
    }


}
