package com.example.app_cosmetics.utils;

import com.example.app_cosmetics.models.GioHang;
import com.example.app_cosmetics.models.User;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public  static  final String BASE_URL = "http://192.168.1.2:8080/AppCosmetics/";
    public  static List<GioHang> mangGioHang;
    public  static List<GioHang> mangMuaHang = new ArrayList<>();
    public  static User user_current = new User();
}
