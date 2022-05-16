package com.example.app_cosmetics.activitis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.app_cosmetics.R;
import com.example.app_cosmetics.models.GioHang;
import com.example.app_cosmetics.models.SanPham;
import com.example.app_cosmetics.utils.Utils;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;

public class ChiTietActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView txtTenSP , txtGiaSP, txtMoTaSP;
    ImageView hinhAnhSP;
    Button btnThem;
    Spinner spinner;
    SanPham sanPham;
    NotificationBadge badge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);
        AnhXa();
        ActionBar();
        initData();
        initControl();
    }

    private void initControl() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            int solancham =0;
            @Override
            public void onClick(View view) {
                solancham++;
                if (solancham<=10){
                    themGioHang();
                }else {
                    Toast.makeText(getApplicationContext(), "Sản phẩm đã có trong giỏ hàng !", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), GioHangActivity.class));
                    finish();
                }
            }
        });
    }

    private void themGioHang() {
        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
        long gia = Long.parseLong(sanPham.getGiasanpham()) * soluong ;
        boolean flag = false;
        //Sai thi nam o bai 18
        if (Utils.mangGioHang.size() >0){
            for (int i=0; i<Utils.mangGioHang.size() ; i++){
                if (Utils.mangGioHang.get(i).getIdsp() == sanPham.getId()){
                    Utils.mangGioHang.get(i).setSoluong(soluong + Utils.mangGioHang.get(i).getSoluong());
                    Utils.mangGioHang.get(i).setGiasp(gia);
                    flag = true;
                }
            }
            if (flag == false ){
                GioHang gioHang = new GioHang();
                gioHang.setGiasp(gia);
                gioHang.setSoluong(soluong);
                gioHang.setIdsp(sanPham.getId());
                gioHang.setTensp(sanPham.getTensanpham());
                gioHang.setHinhsp(sanPham.getHinhanh());
                Utils.mangGioHang.add(gioHang);
            }

        }else {
            GioHang gioHang = new GioHang();
            gioHang.setGiasp(gia);
            gioHang.setSoluong(soluong);
            gioHang.setIdsp(sanPham.getId());
            gioHang.setTensp(sanPham.getTensanpham());
            gioHang.setHinhsp(sanPham.getHinhanh());
            Utils.mangGioHang.add(gioHang);
        }
        //so luong san pham
        if (Utils.mangGioHang != null){
            int totalItem = 0;
            for (int i=0 ; i<Utils.mangGioHang.size();i++){
                totalItem  += Utils.mangGioHang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalItem));
        }
    }

    private void initData() {
        sanPham = (SanPham) getIntent().getSerializableExtra("chitiet");
        txtTenSP.setText(sanPham.getTensanpham());

        txtMoTaSP.setText(sanPham.getMota());
        Glide.with(getApplicationContext()).load(sanPham.getHinhanh()).into(hinhAnhSP);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtGiaSP.setText("Giá: "+decimalFormat.format(Double.parseDouble(sanPham.getGiasanpham()) )+"₫" );

        Integer [] so = new Integer[]{1};
        ArrayAdapter<Integer> Spinneradapter =
                new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, so);
        spinner.setAdapter(Spinneradapter);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> {
            finish();
        });
    }

    private void AnhXa() {
        toolbar = findViewById(R.id.toolbarChiTietSP);
        txtGiaSP = findViewById(R.id.giaSP_CT);
        txtTenSP = findViewById(R.id.TenSP_CT);
        txtMoTaSP =findViewById(R.id.MoTaSP_CT);
        hinhAnhSP = findViewById(R.id.imageSP);
        spinner = findViewById(R.id.spinner);
        btnThem = findViewById(R.id.btnThemVaoGioHang_CT);
        badge = findViewById(R.id.menu_sl);

        FrameLayout frameLayoutGioHang = findViewById(R.id.frameGioHang);
        frameLayoutGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giohang = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(giohang);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.mangGioHang != null){
            int totalItem = 0;
            for (int i=0 ; i<Utils.mangGioHang.size();i++){
                totalItem  += Utils.mangGioHang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalItem));
        }
    }

}