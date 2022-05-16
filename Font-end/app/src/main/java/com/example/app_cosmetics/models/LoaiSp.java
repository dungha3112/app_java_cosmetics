package com.example.app_cosmetics.models;

public class LoaiSp {
    int id;
    String tenloaisanpham;
    String hinhanh;

    public LoaiSp(int id, String tenloaisanpham, String hinhanh) {
        this.id = id;
        this.tenloaisanpham = tenloaisanpham;
        this.hinhanh = hinhanh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenloaisanpham() {
        return tenloaisanpham;
    }

    public void setTenloaisanpham(String tenloaisanpham) {
        this.tenloaisanpham = tenloaisanpham;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }
}
