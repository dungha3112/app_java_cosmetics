package com.example.app_cosmetics.activitis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.app_cosmetics.R;
import com.example.app_cosmetics.adapters.AllSanPhamAdapter;
import com.example.app_cosmetics.adapters.LoaiSpAdapter;
import com.example.app_cosmetics.adapters.SanPhamAdapter;
import com.example.app_cosmetics.models.LoaiSp;
import com.example.app_cosmetics.models.SanPham;
import com.example.app_cosmetics.retrofit.ApiBanHang;
import com.example.app_cosmetics.retrofit.RetrofitClient;
import com.example.app_cosmetics.utils.Utils;
import com.google.android.material.navigation.NavigationView;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewFlipper viewFlipper;
    private RecyclerView recyclerViewManHinhChinh;
    private NavigationView navigationView;
    private ListView listViewManHinhChinh;
    private DrawerLayout drawerLayout;
    private LoaiSpAdapter loaiSpAdapter;
    private List<LoaiSp> mangLoaiSp;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    List<SanPham> mangSanPham;
    AllSanPhamAdapter allsanPhamAdapter;
    //gio hang
    NotificationBadge badge;
    FrameLayout frameGioHang;
    ImageView imageSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

        AnhXa();
        ActionBar();
        // kiem tra mang
        if (isConnect(this)){
            ActionViewFlipper();
            getLoaiSp();
            getSanPham();
            getVentClick();
        }else {
            toastShow("Không có Internet");
        }

    }

    private void getVentClick() {
        listViewManHinhChinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0: startActivity(new Intent(getApplicationContext(),MainActivity.class ));
                        break;
                    case 1: Intent taikhoan = new Intent(getApplicationContext(), TaiKhoanActivity.class);
                        startActivity(taikhoan);
                        break;
                    case 2: Intent taytrang = new Intent(getApplicationContext(), SanPhamActivity.class);
                        taytrang.putExtra("loaisanpham",1);
                        startActivity(taytrang);
                        break;
                    case 3: Intent kemduong = new Intent(getApplicationContext(), SanPhamActivity.class);
                        kemduong.putExtra("loaisanpham",2);
                        startActivity(kemduong);
                        break;
                    case 4: Intent suaruamat = new Intent(getApplicationContext(), SanPhamActivity.class);
                        suaruamat.putExtra("loaisanpham",3);
                        startActivity(suaruamat);
                        break;
                    case 5: Intent kemchongnang = new Intent(getApplicationContext(), SanPhamActivity.class);
                        kemchongnang.putExtra("loaisanpham",4);
                        startActivity(kemchongnang);
                        break;
                    case 6: startActivity(new Intent(getApplicationContext(),ThongTinActivity.class ));
                        break;
                    case 7: startActivity(new Intent(getApplicationContext(),LienHeActivity.class ));
                        break;
                    case 8:  Intent xemdonhang = new Intent(getApplicationContext(), XemDonHangActivity.class);
                        startActivity(xemdonhang);
                        break;
                    case 9:  Intent dangxuat = new Intent(getApplicationContext(), DangNhapActivity.class);
                            startActivity(dangxuat);
                            finish();
                        break;


                }
            }
        });
    }


    private void toastShow(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    private void AnhXa() {
        toolbar = findViewById(R.id.toolbarManHinhChinh);
        viewFlipper = findViewById(R.id.viewLipper);
        recyclerViewManHinhChinh = findViewById(R.id.recyclerViewManHinhChinh);
        navigationView = findViewById(R.id.navigationview);
        listViewManHinhChinh = findViewById(R.id.listViewManHinhChinh);
        drawerLayout = findViewById(R.id.drawerlayout);
        // khoi tao list
        mangLoaiSp = new ArrayList<>();
        mangSanPham = new ArrayList<>();

        badge = findViewById(R.id.menu_sl);
        if (Utils.mangGioHang ==null){
            Utils.mangGioHang = new ArrayList<>();
        }else {
            if (Utils.mangGioHang != null){
                int totalItem = 0;
                for (int i=0 ; i<Utils.mangGioHang.size();i++){
                    totalItem  += Utils.mangGioHang.get(i).getSoluong();
                }
                badge.setText(String.valueOf(totalItem));
            }
        }
        frameGioHang = findViewById(R.id.frameGioHang);
        frameGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giohang = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(giohang);
            }
        });
        // search
        imageSearch = findViewById(R.id.imageSearch);

    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(view -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });
        imageSearch.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), SearchActivity.class));
        });
    }

    private void ActionViewFlipper() {
        List<String> mangQuangCao = new ArrayList<>();
        mangQuangCao.add("https://media.hasaki.vn/hsk/1642829650pc-gia.jpg");
        mangQuangCao.add("https://media.hasaki.vn/hsk/1642477134846x250spa.jpg");
        mangQuangCao.add("https://media.hasaki.vn/hsk/1642478659home---45.jpg");
        mangQuangCao.add("https://media.hasaki.vn/hsk/1642071288home---44.jpg");
        mangQuangCao.add("https://media.hasaki.vn/hsk/1642583109846x250.jpg");
        for (int i=0; i<mangQuangCao.size() ; i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangQuangCao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        //set time
        viewFlipper.setFlipInterval(7000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);
    }
    // check internet
    private boolean isConnect(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi !=null && wifi.isConnected()) || (mobile != null && mobile.isConnected()) ){
            return true;
        }else {
            return false;
        }
    }
    // lay loai san pham
    private void getLoaiSp() {
        compositeDisposable.add(apiBanHang.getLoaiSp()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        loaiSpModel -> {
                            if (loaiSpModel.isSuccess()){
                                mangLoaiSp = loaiSpModel.getResult();
                                mangLoaiSp.add(1,new LoaiSp(0,"Tài Khoản","https://image.shutterstock.com/image-vector/information-sign-icon-vector-logo-260nw-1415768726.jpg"));
                                //khoi tao adapter
                                loaiSpAdapter = new LoaiSpAdapter(getApplicationContext(),mangLoaiSp);
                                listViewManHinhChinh.setAdapter(loaiSpAdapter);
                            }
                        }
                ));
    }
    // lay san pham
    private void getSanPham() {
        compositeDisposable.add(apiBanHang.getAllSanPham()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                sanPhamModel -> {
                    if (sanPhamModel.isSuccess()){
                        mangSanPham = sanPhamModel.getResult();
                        allsanPhamAdapter = new AllSanPhamAdapter(getApplicationContext(), mangSanPham);
                        recyclerViewManHinhChinh.setAdapter(allsanPhamAdapter);
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2 );
                        recyclerViewManHinhChinh.setLayoutManager(layoutManager);
                        recyclerViewManHinhChinh.setHasFixedSize(true);

                    }
                },
                throwable -> {
                    toastShow("Không thế kết nối với Server");
                }
        ));
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
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