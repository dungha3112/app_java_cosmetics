package com.example.app_cosmetics.activitis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_cosmetics.R;
import com.example.app_cosmetics.retrofit.ApiBanHang;
import com.example.app_cosmetics.retrofit.RetrofitClient;
import com.example.app_cosmetics.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DangKiActivity extends AppCompatActivity {
    EditText  email , pass , repas, diachi , sodienthoai , userName;
    TextView  txtDangNhap;
    Button btnDangNhap;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki);
        AnhXa();
        initBar();
    }

    private void initBar() {
        txtDangNhap.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), DangNhapActivity.class));
        });
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dangKi();
            }
        });
    }

    private void dangKi() {
        String str_username  = userName.getText().toString().trim();
        String str_email  = email.getText().toString().trim();
        String str_pass   = pass.getText().toString().trim();
        String str_repas  = repas.getText().toString().trim();
        String str_mobile = sodienthoai.getText().toString().trim();
        String str_diachi = diachi.getText().toString().trim();
        if (TextUtils.isEmpty(str_username)){
            showToast("Bạn chưa nhập tên người dùng");
            userName.requestFocus();
        }else if (TextUtils.isEmpty(str_email)){
            showToast("Bạn chưa nhập email");
            email.requestFocus();
        }else if (!Patterns.EMAIL_ADDRESS.matcher(str_email).matches()){
            showToast("email chưa đúng định dạng !");
            email.requestFocus();
        }else if (TextUtils.isEmpty(str_pass)){
            showToast("Bạn chưa nhập password");
            pass.requestFocus();
        }else if (TextUtils.isEmpty(str_repas)){
            showToast("Bạn chưa nhập Repassword");
            repas.requestFocus();
        }else if (TextUtils.isEmpty(str_mobile)){
            showToast("Bạn chưa nhập Số điện thoại");
            sodienthoai.requestFocus();
        }else if (str_mobile.length()!=10){
            showToast("Số điện thoại chưa đúng định dạng !");
            sodienthoai.requestFocus();
        }else if (TextUtils.isEmpty(str_diachi)){
            showToast("Bạn chưa nhập Địa chỉ");
            diachi.requestFocus();
        }else {
            if (str_pass.equals(str_repas)){
                //post data
                compositeDisposable.add(apiBanHang.dangKi(str_email, str_pass, str_username, str_mobile, str_diachi  )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            userModel -> {
                                if (userModel.isSuccess()){
                                    Utils.user_current.setEmail(str_email);
                                    Utils.user_current.setPass(str_pass);
                                    Intent intent = new Intent(getApplicationContext(), DangNhapActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else {
                                    showToast(userModel.getMessage());
                                }
                            },
                            throwable -> {
                                showToast(throwable.getMessage());
                            }
                    ));
            }else {
                showToast("Pass chưa khớp !");
                repas.requestFocus();
            }
        }
    }
    private void  showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void AnhXa() {
        txtDangNhap = findViewById(R.id.txtDangNhap);
        email  = findViewById(R.id.inputemail);
        pass = findViewById(R.id.inputpassword);
        repas = findViewById(R.id.inputRepassword);
        btnDangNhap = findViewById(R.id.btnDangKi);
        diachi = findViewById(R.id.inputDiaChi);
        sodienthoai = findViewById(R.id.inputSDT);
        userName = findViewById(R.id.inputUserName);

        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}