
package com.dreamernguyen.AppRaoVatSaFaCo.Activity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.util.Log;

        import androidx.appcompat.app.AppCompatActivity;

        import com.dreamernguyen.AppRaoVatSaFaCo.DataBase.TimKiemDataBase;
        import com.dreamernguyen.AppRaoVatSaFaCo.Models.TimKiem;
        import com.dreamernguyen.AppRaoVatSaFaCo.R;


public class TimKiemMatHangActivity extends AppCompatActivity {

    public static int viTri,idThanhPho,idQuan =0;

    public static TimKiem timKiemTemp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem_mat_hang);

        Intent intent = getIntent();
        String HangMuc = intent.getStringExtra("hangMuc");

        if (HangMuc != null && !HangMuc.isEmpty()) {
            createTemp("", HangMuc, "", "", "1", "DangTao");
        } else {
            createTemp("", "", "", "", "1", "DangTao");
        }
    }

    public void createTemp(String tieuDe, String danhMuc, String diaChi, String sapXepThoiGian, String sapXepGiaBan, String trangThai) {

        timKiemTemp = new TimKiem(tieuDe, danhMuc, sapXepThoiGian, diaChi, sapXepGiaBan, trangThai);
        TimKiemDataBase.getInstance(getApplicationContext()).timKiemDAO().createTemp(timKiemTemp);
        timKiemTemp = TimKiemDataBase.getInstance(getApplicationContext()).timKiemDAO().findTemp("DangTao");
        Log.d("TAG", "createTemp: da tao " + timKiemTemp.getTrangThai()+" hang muc" +timKiemTemp.getDanhMuc()+" gia ban"+timKiemTemp.getSapXepGiaBan()+" thoi gian "+timKiemTemp.getSapXepThoiGian());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}