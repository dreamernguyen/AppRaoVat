package com.dreamernguyen.AppRaoVatSaFaCo.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.api.exceptions.ApiException;
import com.dreamernguyen.AppRaoVatSaFaCo.ApiService;
import com.dreamernguyen.AppRaoVatSaFaCo.LocalDataManager;
import com.dreamernguyen.AppRaoVatSaFaCo.MainActivity;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.DuLieuTraVe;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.NguoiDung;
import com.dreamernguyen.AppRaoVatSaFaCo.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangNhapActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final int RC_SIGN_IN = 1;
    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    GoogleApiClient mApiClient;
    SignInButton signInButton;
    TextView btnDangKy, tvQuenMatKhau;
    TextInputLayout layoutSDT, layoutMatKhau;
    TextInputEditText edSDT, edMatKhau;
    MaterialButton btnDangNhap;
    MaterialCheckBox checkBoxDangNhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();

        layoutSDT = findViewById(R.id.layoutSDT);
        layoutMatKhau = findViewById(R.id.layoutMatKhau);
        edSDT = findViewById(R.id.edSDT);
        edMatKhau = findViewById(R.id.edMatKhau);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        btnDangKy = findViewById(R.id.tvDangKy);
        tvQuenMatKhau = findViewById(R.id.tvQuenmatkhau);
        signInButton = findViewById(R.id.sign_in_button);
        checkBoxDangNhap = findViewById(R.id.checkbox);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DangNhapActivity.this, DangKyActivity.class);
                startActivity(i);
            }
        });
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DangNhap();
            }
        });
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        edSDT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layoutSDT.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edMatKhau.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layoutMatKhau.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvQuenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edSDT.getText().toString().trim().isEmpty()) {
                    layoutSDT.setError("Nhập số điện thoại để lấy lại mật khẩu !");
                }
                if (edMatKhau.getText().toString().trim().isEmpty()) {
                    layoutMatKhau.setError("Nhập mật khẩu mới !");
                }
                if(!edSDT.getText().toString().trim().isEmpty() && !edMatKhau.getText().toString().trim().isEmpty() && edSDT.getText().toString().length() == 10) {
                    Intent i = new Intent(getApplicationContext(), XacThucActivity.class);
                    i.putExtra("activity", "DangNhap");
                    i.putExtra("SDT", edSDT.getText().toString());
                    i.putExtra("matKhau", edMatKhau.getText().toString());
                    startActivity(i);
                }
            }
        });
        if(LocalDataManager.getLuuDangNhap()!= null && LocalDataManager.getLuuDangNhap() == true){
            checkBoxDangNhap.setChecked(true);
        }
        checkBoxDangNhap.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LocalDataManager.setLuuDangNhap(isChecked);
            }
        });
        if(LocalDataManager.getLuuDangNhap()){
            edSDT.setText(LocalDataManager.getSDT());
            edMatKhau.setText(LocalDataManager.getMatKhau());
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    public void DangNhap() {
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(edSDT.getApplicationWindowToken(), 0);
        manager.hideSoftInputFromWindow(edMatKhau.getApplicationWindowToken(), 0);
        validateForm();
        if (layoutSDT.getError() == null && layoutMatKhau.getError() == null) {
            String mMatKhau = edMatKhau.getText().toString();
            String mSdt = edSDT.getText().toString();

            if(LocalDataManager.getLuuDangNhap()){
                LocalDataManager.setSDT(edSDT.getText().toString());
                LocalDataManager.setMatKhau(edMatKhau.getText().toString());
            }else {
                LocalDataManager.setSDT(null);
                LocalDataManager.setMatKhau(null);
            }

            NguoiDung nguoiDung = new NguoiDung(mSdt, mMatKhau);
            Call<DuLieuTraVe> call = ApiService.apiService.dangNhap(nguoiDung);
            call.enqueue(new Callback<DuLieuTraVe>() {
                @Override
                public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                    String thongBao = response.body().getThongBao();
                    NguoiDung nguoiDung = response.body().getNguoiDung();
                    if (thongBao.equals("Tài khoản không tồn tại !")) {
                        layoutSDT.setError(thongBao);
                    }
                    if (thongBao.equals("Sai mật khẩu !")) {
                        layoutMatKhau.setError(thongBao);
                    } else {
                        Toast.makeText(DangNhapActivity.this, thongBao, Toast.LENGTH_SHORT).show();
                    }
                    if(nguoiDung != null){
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        LocalDataManager.setIdNguoiDung(nguoiDung.getId());
                        LocalDataManager.setNguoiDung(nguoiDung);
                        startActivity(intent);
                        finish();
                    }

                }

                @Override
                public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                    Toast.makeText(DangNhapActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("dangNhap", "onFailure: " + t.getMessage());
                }
            });
        } else {
            Toast.makeText(this, "Vui lòng kiểm tra lại thông tin đăng nhập !", Toast.LENGTH_SHORT).show();
        }

    }

    public void validateForm() {
        if (edSDT.getText().toString().trim().isEmpty()) {
            layoutSDT.setError("Số điện thoại không được để trống !");
        }
        if (edMatKhau.getText().toString().trim().isEmpty()) {
            layoutMatKhau.setError("Mật khẩu không được để trống !");
        }
        if (edSDT.getText().toString().length() != 10) {
            layoutSDT.setError("Vui lòng nhập số điện thoại có 10 số !");
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == 0) {
                return;
            } else {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    Log.d("DangNhapGG", "firebaseAuthWithGoogle:" + account.getId());
                    firebaseAuthWithGoogle(account.getIdToken());
                } catch (ApiException e) {
                    Log.d("DangNhapGG", "Google sign in failed " + e.getMessage().toString());
                }
            }
        }


    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("DangNhapGG", "signInWithCredential:success");
                            Log.d("DangNhapGG", "onComplete: " + mAuth.getCurrentUser().getEmail());
                            Call<DuLieuTraVe> call = ApiService.apiService.dangNhapGG(mAuth.getCurrentUser().getEmail(),mAuth.getCurrentUser().getPhotoUrl().toString(),mAuth.getCurrentUser().getDisplayName());
                            call.enqueue(new Callback<DuLieuTraVe>() {
                                @Override
                                public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                                    if (response.body().getThongBao().equals("Email này chưa được liên kết !")) {
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        LocalDataManager.setIdNguoiDung(response.body().getNguoiDung().getId());
                                        LocalDataManager.setNguoiDung(response.body().getNguoiDung());
                                        startActivity(intent);
                                        finish();
                                        Toast.makeText(DangNhapActivity.this, response.body().getThongBao(), Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        LocalDataManager.setIdNguoiDung(response.body().getNguoiDung().getId());
                                        LocalDataManager.setNguoiDung(response.body().getNguoiDung());
                                        startActivity(intent);
                                        finish();
                                        Toast.makeText(DangNhapActivity.this, response.body().getThongBao(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                                    Log.d("dangNhapGG", "onFailure: " + t.getMessage());
                                }
                            });

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d("DangNhapGG", "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Kết nối lỗi", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        if(LocalDataManager.getLuuDangNhap()){
            LocalDataManager.setSDT(edSDT.getText().toString());
            LocalDataManager.setMatKhau(edMatKhau.getText().toString());
        }else {
            LocalDataManager.setSDT(null);
            LocalDataManager.setMatKhau(null);
        }
        if(LocalDataManager.getLuuDangNhap()!= null && LocalDataManager.getLuuDangNhap() == true){
            checkBoxDangNhap.setChecked(true);
        }
        super.onResume();

    }
}