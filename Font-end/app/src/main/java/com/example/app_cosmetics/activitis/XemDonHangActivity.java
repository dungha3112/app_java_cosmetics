package com.example.app_cosmetics.activitis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.widget.Toolbar;

import com.example.app_cosmetics.R;
import com.example.app_cosmetics.adapters.XemDonHangAdapter;
import com.example.app_cosmetics.retrofit.ApiBanHang;
import com.example.app_cosmetics.retrofit.RetrofitClient;
import com.example.app_cosmetics.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class XemDonHangActivity extends AppCompatActivity {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    RecyclerView recylerViewXemDonHang;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_don_hang);
        AnhXa();
        inintControl();
        getDonHang();

    }

    private void getDonHang() {
        compositeDisposable.add(apiBanHang.xemDonHang(Utils.user_current.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        donHangModel -> {
                            XemDonHangAdapter adapter = new XemDonHangAdapter(getApplicationContext(), donHangModel.getResult());
                            recylerViewXemDonHang.setAdapter(adapter);

                        },
                        throwable -> {
                            Log.d("LoiXemDonHang", throwable.getMessage());
                        }
                ));
    }

    private void inintControl() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> {
            onBackPressed();
        });
    }

    private void AnhXa() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        recylerViewXemDonHang = findViewById(R.id.recylerViewXemDonHang);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recylerViewXemDonHang.setLayoutManager(layoutManager);

        toolbar = findViewById(R.id.toolbarXemDonHang);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}