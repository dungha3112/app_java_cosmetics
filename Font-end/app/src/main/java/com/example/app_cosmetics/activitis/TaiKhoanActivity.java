package com.example.app_cosmetics.activitis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_cosmetics.R;
import com.example.app_cosmetics.models.User;
import com.example.app_cosmetics.retrofit.ApiBanHang;
import com.example.app_cosmetics.retrofit.RetrofitClient;
import com.example.app_cosmetics.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TaiKhoanActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText email , pass , repas, diachi , sodienthoai , userName;
    Button btnCapNhap;
    ApiBanHang apiBanHang;
    TextInputLayout linerepass , linepass;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tai_khoan);
        AnhXa();
        infor();
        ActionBar();
    }

    private void infor() {

    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> {
            onBackPressed();
        });
        btnCapNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capNhapTT();
            }
        });
        linepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linerepass.setVisibility(View.VISIBLE);
                repas.setVisibility(View.VISIBLE);
            }
        });
        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linerepass.setVisibility(View.VISIBLE);
                repas.setVisibility(View.VISIBLE);
            }
        });
    }

    private void capNhapTT() {
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
                compositeDisposable.add(apiBanHang.capNhapTT(str_email, str_pass, str_username, str_mobile, str_diachi)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                userModel -> {
                                    if (userModel.isSuccess()){
                                        showToast("Cập nhập thành công");
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    }else {
                                        showToast("Cập nhập thất bại");
                                        Log.d("LoiCapNhapTT", userModel.getMessage());
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
        toolbar = findViewById(R.id.toolbarThongTinTaiKhoan);
        email  = findViewById(R.id.inputemail);
        pass = findViewById(R.id.inputpassword);
        repas = findViewById(R.id.inputRepassword);
        diachi = findViewById(R.id.inputDiaChi);
        sodienthoai = findViewById(R.id.inputSDT);
        userName = findViewById(R.id.inputUserName);
        btnCapNhap = findViewById(R.id.btnCapNhap);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        linerepass = findViewById(R.id.linerepass);
        linepass = findViewById(R.id.linepass);

//        if (Paper.book().read("email") !=null && Paper.book().read("pass") !=null
//                && Paper.book().read("diachi") !=null && Paper.book().read("sodienthoai") !=null
//                && Paper.book().read("userName") !=null){
//            email.setText(Paper.book().read("email"));
//            pass.setText(Paper.book().read("pass"));
//            diachi.setText(Paper.book().read("diachi"));
//            sodienthoai.setText(Paper.book().read("sodienthoai"));
//            userName.setText(Paper.book().read("userName"));
//        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.user_current.getEmail() !=null && Utils.user_current.getPass() !=null
                && Utils.user_current.getDiachi() !=null && Utils.user_current.getMobile() !=null
                && Utils.user_current.getUsername() !=null){
            email.setText(Utils.user_current.getEmail());
            pass.setText(Utils.user_current.getPass());
            diachi.setText(Utils.user_current.getDiachi());
            sodienthoai.setText(Utils.user_current.getMobile());
            userName.setText(Utils.user_current.getUsername());
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

}