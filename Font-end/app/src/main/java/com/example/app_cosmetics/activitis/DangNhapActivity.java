package com.example.app_cosmetics.activitis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DangNhapActivity extends AppCompatActivity {
    TextView txtDangKi , txtQuenMatKhau;
    EditText email, pass;
    Button btnDangNhap;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        initView();
        initControl();
    }
    private void initControl() {
        txtDangKi.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), DangKiActivity.class));
        });
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_email  = email.getText().toString().trim();
                String str_pass   = pass.getText().toString().trim();
                if (TextUtils.isEmpty(str_email)){
                    showToast("Bạn chưa nhập email");
                    email.requestFocus();
                }else if (!Patterns.EMAIL_ADDRESS.matcher(str_email).matches()){
                    showToast("email chưa đúng định dạng !");
                    email.requestFocus();
                }else if (TextUtils.isEmpty(str_pass)){
                    showToast("Bạn chưa nhập password");
                    pass.requestFocus();
                }else {
                    // luu tk , mk
                    Paper.book().write("email", str_email);
                    Paper.book().write("pass", str_pass);

                    compositeDisposable.add(apiBanHang.dangNhap(str_email, str_pass)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                userModel -> {
                                    if (userModel.isSuccess()){
                                        Utils.user_current = userModel.getResult();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }else {
                                        showToast("Thông tin đăng nhập chưa đúng");
                                    }
                                },
                                throwable -> {
                                    showToast(throwable.getMessage());
                                    Log.d("LoiDangNhap", throwable.getMessage());
                                }
                        ));
                }
            }
        });
        txtQuenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ResetPassActivity.class));
            }
        });

    }

    private void initView() {
        //lưu tài khoản , mk
        Paper.init(this);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

        txtDangKi = findViewById(R.id.txtDangKi);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        //check
        if (Paper.book().read("email") !=null && Paper.book().read("pass") !=null ){
            email.setText(Paper.book().read("email"));
            pass.setText(Paper.book().read("pass"));
        }
        //foget pass
        txtQuenMatKhau = findViewById(R.id.txtQuenMatKhau);
    }

    private void  showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.user_current.getEmail() !=null && Utils.user_current.getPass() !=null ){
            email.setText(Utils.user_current.getEmail());
            pass.setText(Utils.user_current.getPass());
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}