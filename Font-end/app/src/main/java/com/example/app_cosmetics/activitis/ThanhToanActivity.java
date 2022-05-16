package com.example.app_cosmetics.activitis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_cosmetics.R;
import com.example.app_cosmetics.retrofit.ApiBanHang;
import com.example.app_cosmetics.retrofit.RetrofitClient;
import com.example.app_cosmetics.utils.Utils;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThanhToanActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView txtTongTien, txtSoDienThoai, txtEmail;
    EditText edtDiaChi , edtGhiChu;
    Button btnDatHang;
    ApiBanHang apiBanHang;
    long tongtien;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    int totalItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        AnhXa();
        inintControl();
        countItem();

    }

    private void countItem() {
         totalItem = 0;
        for (int i=0 ; i<Utils.mangMuaHang.size();i++){
            totalItem  += Utils.mangMuaHang.get(i).getSoluong();
        }
    }

    private void inintControl() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> {
            onBackPressed();
        });
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongtien = getIntent().getLongExtra("tongtien", 0);
        txtTongTien.setText(decimalFormat.format(tongtien)+"₫");
        txtEmail.setText(Utils.user_current.getEmail());
        txtSoDienThoai.setText(Utils.user_current.getMobile());
        btnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_diachi = edtDiaChi.getText().toString().trim();
                String str_ghichu = edtGhiChu.getText().toString().trim();
                if (TextUtils.isEmpty(str_diachi)){
                    toastShow("Bạn chưa nhập địa chỉ");
                    edtDiaChi.requestFocus();
                }else if (TextUtils.isEmpty(str_ghichu)){
                    toastShow("Bạn nên nhập ghi chú để có phục vụ tốt nhất");
                    edtGhiChu.requestFocus();
                }else  {
                    //post data
                    Log.d("Test", new Gson().toJson(Utils.mangMuaHang));
                    String str_email = Utils.user_current.getEmail();
                    String str_sodienthoai = Utils.user_current.getMobile();
                    int iduser = Utils.user_current.getId();
                    Date date = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("E-dd-M-yyyy hh:mm:ss");
                    String strDate = formatter.format(date);

                    compositeDisposable
                    .add(apiBanHang.taoDonHang(iduser,totalItem,str_diachi,str_sodienthoai, str_email,str_ghichu, strDate
                            , String.valueOf(tongtien),new Gson().toJson(Utils.mangMuaHang))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                                userModel -> {
                                    Toast.makeText(getApplicationContext(), "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                                    Utils.mangMuaHang.clear();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    finish();
                                },
                                throwable -> {
                                    Log.d("LoiThanhToan", throwable.getMessage());
                                    Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                        ));
                }
            }
        });
    }
    private void toastShow(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    private void AnhXa() {
        toolbar = findViewById(R.id.toolbarThanhToan);
        txtTongTien = findViewById(R.id.txtTongTien);
        txtSoDienThoai = findViewById(R.id.txtSoDienThoai);
        txtEmail = findViewById(R.id.txtEmail);
        edtDiaChi = findViewById(R.id.inputDiaChi);
        edtGhiChu = findViewById(R.id.inputGhiChu);
        btnDatHang = findViewById(R.id.btnDatHang);

        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.user_current.getEmail() !=null && Utils.user_current.getPass() !=null
                && Utils.user_current.getDiachi() !=null && Utils.user_current.getMobile() !=null
                && Utils.user_current.getUsername() !=null){
            txtEmail.setText(Utils.user_current.getEmail());
            txtSoDienThoai.setText(Utils.user_current.getMobile());

        }
    }
    @Override
    protected void onRestart() {
        compositeDisposable.clear();
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}