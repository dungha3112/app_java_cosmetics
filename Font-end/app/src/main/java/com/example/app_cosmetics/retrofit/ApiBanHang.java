package com.example.app_cosmetics.retrofit;

import com.example.app_cosmetics.models.DonHangModel;
import com.example.app_cosmetics.models.LoaiSpModel;
import com.example.app_cosmetics.models.SanPhamModel;
import com.example.app_cosmetics.models.UserModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiBanHang {
    @GET("getloaisp.php")
    Observable<LoaiSpModel>  getLoaiSp();

    @GET("getsanpham.php")
    Observable<SanPhamModel>  getAllSanPham();
// post
    @POST("chitiet.php")
    @FormUrlEncoded
    Observable<SanPhamModel>  getSanPham(
            @Field("page") int page ,
            @Field("loaisanpham") int loaisanpham
    );
    @POST("dangki.php")
    @FormUrlEncoded
    Observable<UserModel>  dangKi(
            @Field("email") String email ,
            @Field("pass") String pass ,
            @Field("username") String username ,
            @Field("sodienthoai") String sodienthoai ,
            @Field("diachi") String diachi
    );
    @POST("dangnhap.php")
    @FormUrlEncoded
    Observable<UserModel>  dangNhap(
            @Field("email") String email ,
            @Field("pass") String pass
    );
    //thiếu hàm reset mật khẩu

    @POST("donhang.php")
    @FormUrlEncoded
    Observable<UserModel>  taoDonHang(
            @Field("iduser") int iduser ,
            @Field("soluong") int soluong ,
            @Field("diachi") String diachi ,
            @Field("sodienthoai") String sodienthoai ,
            @Field("email") String email ,
            @Field("ghichu") String ghichu ,
            @Field("ngaydat") String ngaydat,
            @Field("tongtien") String tongtien,
            @Field("chitietdonhang") String chitietdonhang
    );
    @POST("xemdonhang.php")
    @FormUrlEncoded
    Observable<DonHangModel>  xemDonHang(
            @Field("iduser") int iduser
    );
    @POST("timkiem.php")
    @FormUrlEncoded
    Observable<SanPhamModel>  timKiem(
            @Field("search") String search
    );
    // cap nhap thong tin tai khoan
    @POST("capnhaptaikhoan.php")
    @FormUrlEncoded
    Observable<SanPhamModel>  capNhapTT(
            @Field("email") String email ,
            @Field("pass") String pass ,
            @Field("username") String username ,
            @Field("sodienthoai") String sodienthoai ,
            @Field("diachi") String diachi
    );

}
