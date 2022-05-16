package com.example.app_cosmetics.activitis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.app_cosmetics.R;

public class ThongTinActivity extends AppCompatActivity {
    Toolbar toolbarThongTin;
    //ImageView image_giohang,imageSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin);
        AnhXa();
        ActionBar();

    }

    private void ActionBar() {
        setSupportActionBar(toolbarThongTin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarThongTin.setNavigationOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        });
//        image_giohang.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent giohang = new Intent(getApplicationContext(), GioHangActivity.class);
//                startActivity(giohang);
//                finish();
//            }
//        });
//        imageSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent giohang = new Intent(getApplicationContext(), SearchActivity.class);
//                startActivity(giohang);
//                finish();
//            }
//        });
    }

    private void AnhXa() {
        toolbarThongTin = findViewById(R.id.toolbarThongTin);
//        image_giohang = findViewById(R.id.image_giohang);
//        imageSearch = findViewById(R.id.imageSearch);
    }
}