package com.dreamernguyen.AppRaoVatSaFaCo.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.dreamernguyen.AppRaoVatSaFaCo.ApiService;
import com.dreamernguyen.AppRaoVatSaFaCo.LocalDataManager;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.DuLieuTraVe;
import com.dreamernguyen.AppRaoVatSaFaCo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class XacThucActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    PinView pinView;
    String mVerificationId;
    Button btnXacThuc;
    String hoTen,matKhau,SDT,manHinh;
    TextView tvXacThuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xac_thuc);

        pinView = findViewById(R.id.pinView);
        btnXacThuc = findViewById(R.id.btnXacThuc);
        tvXacThuc = findViewById(R.id.tvXacThuc);
        mAuth = FirebaseAuth.getInstance();
        Intent i = getIntent();
        if (i.getExtras() != null){
            manHinh = i.getStringExtra("activity");
            if(manHinh.equals("GianHang")){
                SDT = i.getStringExtra("SDT");

            }
            if(manHinh.equals("DangNhap")){
                SDT = i.getStringExtra("SDT");
                matKhau = i.getStringExtra("matKhau");
            }
            else {
                SDT = i.getStringExtra("SDT");
                hoTen = i.getStringExtra("hoTen");
                matKhau = i.getStringExtra("matKhau");
            }
            if(SDT != null){
                tvXacThuc.setText("Mã xác nhận đã được gửi đến số điện thoại "+SDT);
                guiXacThuc(SDT+"");
            }

        }


        btnXacThuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xacThuc(pinView.getText().toString());
            }
        });
    }
    private void guiXacThuc(String sdt){
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+84"+sdt)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            Log.d("XacThuc", "onVerificationCompleted:" + credential);
            String code = credential.getSmsCode();
            if(code != null){
                pinView.setText(code);
            }


        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.d("XacThuc", "onVerificationFailed "+ e.getMessage());

            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                // Invalid request
            } else if (e instanceof FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
            }

            // Show a message and update the UI
        }

        @Override
        public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Log.d("XacThuc", "onCodeSent:" + verificationId);
            super.onCodeSent(verificationId,token);

            // Save verification ID and resending token so we can use them later
            mVerificationId = verificationId;
        }
    };
    private void xacThuc(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    };
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("XacThuc", "signInWithCredential:success");
                            if(manHinh.equals("DangKy")){
                                DangKy();
                            }
                            if(manHinh.equals("GianHang")){
                                themSoDienThoai();
                            }
                            if(manHinh.equals("DangNhap")){
                                layLaiMatKhau();
                            }
                        } else {
                            Log.d("XacThuc", "signInWithCredential:failure", task.getException());

                        }
                    }
                });
    }

    private void themSoDienThoai() {
        Call<DuLieuTraVe> call = ApiService.apiService.themSoDienThoai(LocalDataManager.getIdNguoiDung(),SDT);
        call.enqueue(new Callback<DuLieuTraVe>() {
            @Override
            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                String thongBao = response.body().getThongBao();
                Toast.makeText(getApplicationContext(), ""+thongBao, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void layLaiMatKhau() {
        Call<DuLieuTraVe> call = ApiService.apiService.quenMatKhau(SDT,matKhau);
        call.enqueue(new Callback<DuLieuTraVe>() {
            @Override
            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                String thongBao = response.body().getThongBao();
                Toast.makeText(getApplicationContext(), ""+thongBao, Toast.LENGTH_SHORT).show();
                finish();

            }

            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void DangKy(){
        Call<DuLieuTraVe> call = ApiService.apiService.dangKy(hoTen,SDT,matKhau);
        call.enqueue(new Callback<DuLieuTraVe>() {
            @Override
            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                String thongBao = response.body().getThongBao();
                if (thongBao.equals("Số điện thoại này đã được đăng ký")){
                    Toast.makeText(getApplicationContext(), ""+thongBao, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), ""+thongBao, Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}