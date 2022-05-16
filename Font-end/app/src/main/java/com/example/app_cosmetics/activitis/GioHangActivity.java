package com.example.app_cosmetics.activitis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_cosmetics.R;
import com.example.app_cosmetics.adapters.GioHangAdapter;
import com.example.app_cosmetics.models.EventBus.TinhTongTienEvent;
import com.example.app_cosmetics.models.GioHang;
import com.example.app_cosmetics.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.List;

public class GioHangActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView txtGioHangTrong , txtTongTien;
    Button btnMuaHang, btnTiepTucMuaHang;
    RecyclerView recyclerView;
    GioHangAdapter gioHangAdapter;
    long tongtien =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        AnhXa();
        inintControl();
        tinhTongTien();
        if (Utils.mangGioHang.size() ==0 ){
            txtGioHangTrong.setVisibility(View.VISIBLE);
        }
    }

    public void tinhTongTien() {
        tongtien =0;
        for (int i=0; i<Utils.mangMuaHang.size(); i++){
            tongtien += (Utils.mangMuaHang.get(i).getGiasp()) *(Utils.mangMuaHang.get(i).getSoluong());
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtTongTien.setText(decimalFormat.format(tongtien )+"₫");
    }

    private void inintControl() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> {
            onBackPressed();
        });
        // list gio hang
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        if (Utils.mangGioHang.size() ==0 ){
            txtGioHangTrong.setVisibility(View.VISIBLE);
        }else {
            gioHangAdapter = new GioHangAdapter(getApplicationContext(), Utils.mangGioHang);
            recyclerView.setAdapter(gioHangAdapter);
        }
        // mua hang
        btnMuaHang.setOnClickListener(view -> {
            if (Utils.mangGioHang.size()== 0){
                Toast.makeText(getApplicationContext(), "Giỏ hàng trống, nên không thể mua hàng", Toast.LENGTH_SHORT).show();
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                txtTongTien.setText(decimalFormat.format(tongtien )+"₫");
                finish();
            } else if (Utils.mangMuaHang.size()==0){
                Toast.makeText(getApplicationContext(), "Bạn nên chọn sản phẩm để mua", Toast.LENGTH_SHORT).show();
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                txtTongTien.setText(decimalFormat.format(tongtien )+"₫");
            } else {
                Intent intent = new Intent(getApplicationContext(),ThanhToanActivity.class );
                intent.putExtra("tongtien",tongtien);
                Utils.mangGioHang.clear();
                startActivity(intent);
            }

        });
        // tiep tuc mua hang
        btnTiepTucMuaHang.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        });

    }

    private void AnhXa() {
        toolbar = findViewById(R.id.toolbarGioHang);
        txtGioHangTrong = findViewById(R.id.txtGioHangTrong);
        txtTongTien = findViewById(R.id.txtTongTien);
        btnMuaHang = findViewById(R.id.btnMuaHang);
        btnTiepTucMuaHang = findViewById(R.id.btnTiepTucMuaHang);
        recyclerView = findViewById(R.id.recyclerViewGioHang);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
    @Subscribe(sticky = true , threadMode = ThreadMode.MAIN)
    public  void eventTinhTongTien(TinhTongTienEvent event ){
        if (event !=null){
            tinhTongTien();
        }
    }
}