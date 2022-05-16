package com.example.app_cosmetics.activitis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.app_cosmetics.R;
import com.example.app_cosmetics.adapters.SanPhamAdapter;
import com.example.app_cosmetics.models.SanPham;
import com.example.app_cosmetics.retrofit.ApiBanHang;
import com.example.app_cosmetics.retrofit.RetrofitClient;
import com.example.app_cosmetics.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SanPhamActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    SanPhamAdapter sanPhamAdapter;
    List<SanPham> sanPhamsList;
    LinearLayoutManager linearLayoutManager;
    Handler handler = new Handler();
    boolean isLoading = false;

    int page = 1;
    int loaisanpham;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        loaisanpham = getIntent().getIntExtra("loaisanpham",0);
        AnhXa();
        ActionBar();
        getData(page);
        addEventLoad();

    }

    private void addEventLoad() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isLoading == false ){
                    if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == sanPhamsList.size()-1){
                        isLoading = true;
                        loadMore();
                    }
                }
            }
        });
    }

    private void loadMore() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                sanPhamsList.add(null);
                sanPhamAdapter.notifyItemInserted(sanPhamsList.size()-1);
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sanPhamsList.remove(sanPhamsList.size()-1);
                sanPhamAdapter.notifyItemRemoved(sanPhamsList.size()-1);
                page = page +1;
                getData(page);
                sanPhamAdapter.notifyDataSetChanged();
                isLoading = true;
            }
        },2000);
    }

    private void getData(int page) {
        compositeDisposable.add(apiBanHang.getSanPham(page,loaisanpham)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                sanPhamModel -> {
                    if (sanPhamModel.isSuccess()){
                        if (sanPhamAdapter == null){
                            sanPhamsList = sanPhamModel.getResult();
                            sanPhamAdapter = new SanPhamAdapter(getApplicationContext(),sanPhamsList);
                            recyclerView.setAdapter(sanPhamAdapter);
                        }else {
                            int vitri = sanPhamsList.size()-1;
                            int soluongadd = sanPhamModel.getResult().size();
                            for (int i=0 ; i<soluongadd; i++){
                                sanPhamsList.add(sanPhamModel.getResult().get(i));
                            }
                            sanPhamAdapter.notifyItemRangeInserted(vitri,soluongadd);
                        }
                    }else{
//                        toastShow("Hết dữ liệu ...");
                  }
                },
                throwable -> {
                    toastShow("Không thế kết nối với Server");
                    isLoading = true;
                }
        ));
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> {
            onBackPressed();
        });
    }
    private void toastShow(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    private void AnhXa() {
        toolbar = findViewById(R.id.toolbarManHinhSanPham);
        recyclerView = findViewById(R.id.recyclerViewSanPham);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        sanPhamsList = new ArrayList<>();

    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}