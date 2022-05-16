package com.example.app_cosmetics.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_cosmetics.Interfaces.IImageClickListenner;
import com.example.app_cosmetics.R;
import com.example.app_cosmetics.activitis.GioHangActivity;
import com.example.app_cosmetics.models.EventBus.TinhTongTienEvent;
import com.example.app_cosmetics.models.GioHang;
import com.example.app_cosmetics.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.MyViewHolder> {
    Context context;
    List<GioHang> array;
    long thanhtien;

    public GioHangAdapter(Context context, List<GioHang> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang , parent, false);
        return new MyViewHolder(view);
    }
    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GioHang gioHang = array.get(position);
        holder.item_giohang_Tensp.setText(gioHang.getTensp());
        holder.item_giohang_SL.setText(gioHang.getSoluong()+"");
        Glide.with(context).load(gioHang.getHinhsp()).into(holder.item_giohang_hinhanh);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.item_giohang_Giasp.setText("Giá: "+decimalFormat.format(gioHang.getGiasp() )+"₫" );
        thanhtien = gioHang.getSoluong() * gioHang.getGiasp();
        holder.item_giohang_thanhtien.setText("Tổng: "+decimalFormat.format(thanhtien)+"₫");

        holder.layoutItem_sanpham.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (holder.checkBox.isChecked() == true){
                    holder.checkBox.setChecked(false);
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                    builder.setTitle("Thông báo !!");
                    builder.setMessage("Bạn có muốn xóa không ?");
                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (holder.checkBox.isChecked()){
                                Utils.mangGioHang.remove(position);
                                notifyDataSetChanged();
                                EventBus.getDefault().postSticky(new TinhTongTienEvent());
                            }else {
                                Utils.mangGioHang.remove(position);
                                notifyDataSetChanged();
                                EventBus.getDefault().postSticky(new TinhTongTienEvent());
                            }
                        }
                    });
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            holder.checkBox.setChecked(true);
                            dialogInterface.cancel();
                        }
                    });
                    builder.show();
                }else {
                    holder.checkBox.setChecked(false);
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                    builder.setTitle("Thông báo !!");
                    builder.setMessage("Bạn có muốn xóa không ?");
                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (holder.checkBox.isChecked()){
                                Utils.mangGioHang.remove(position);
                                notifyDataSetChanged();
                                EventBus.getDefault().postSticky(new TinhTongTienEvent());
                            }else {
                                Utils.mangGioHang.remove(position);
                                notifyDataSetChanged();
                                EventBus.getDefault().postSticky(new TinhTongTienEvent());
                            }
                        }
                    });
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            holder.checkBox.setChecked(true);
                            dialogInterface.cancel();
                        }
                    });
                    builder.show();
                }
                return false;
            }
        });

        // checkbox gio hang
//        if (holder.checkBox.isChecked()){
//            Utils.mangMuaHang.add(gioHang);
//            EventBus.getDefault().postSticky(new TinhTongTienEvent());
//        }else {
//            for (int i=0; i<Utils.mangMuaHang.size(); i++){
//                if (Utils.mangMuaHang.get(i).getIdsp() == gioHang.getIdsp()){
//                    Utils.mangMuaHang.remove(i);
//                    EventBus.getDefault().postSticky(new TinhTongTienEvent());
//                }
//            }
//            EventBus.getDefault().postSticky(new TinhTongTienEvent());
//        }

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                 if (b){
                     Utils.mangMuaHang.add(gioHang);
                     EventBus.getDefault().postSticky(new TinhTongTienEvent());
                 }else {
                    for (int i=0; i<Utils.mangMuaHang.size(); i++){
                        if (Utils.mangMuaHang.get(i).getIdsp() == gioHang.getIdsp()){
                            Utils.mangMuaHang.remove(i);
                            EventBus.getDefault().postSticky(new TinhTongTienEvent());
                        }
                    }
                 }
            }
        });
        // tang giam so luong
        holder.setiImageClickListenner(new IImageClickListenner() {
            @Override
            public void onImageClick(View view, int pos, int value) {
                Log.d("TAG","onImageClick: "+pos + "....."+value);
                int soluongmoi =  array.get(pos).getSoluong();
                if (value ==1){
                    if (array.get(pos).getSoluong() >1){
                        array.get(pos).setSoluong(soluongmoi -1);
                        holder.item_giohang_Giasp.setText("Giá: "+decimalFormat.format(gioHang.getGiasp() )+"₫" );
                        holder.item_giohang_SL.setText(array.get(pos).getSoluong()+"");
                        thanhtien = array.get(pos).getSoluong() * array.get(pos).getGiasp();
                        holder.item_giohang_thanhtien.setText("Tổng: "+decimalFormat.format(thanhtien)+"₫");
                        //set lai tinh tong tien
                        EventBus.getDefault().postSticky(new TinhTongTienEvent());
                    }else if(array.get(pos).getSoluong() ==1){
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                        builder.setTitle("Thông báo !!");
                        builder.setMessage("Bạn có muốn xóa không ?");
                        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (holder.checkBox.isChecked()){
                                    Utils.mangGioHang.remove(pos);
                                    notifyDataSetChanged();
                                    EventBus.getDefault().postSticky(new TinhTongTienEvent());
                                }else {
                                    Utils.mangGioHang.remove(pos);
                                    notifyDataSetChanged();
                                    EventBus.getDefault().postSticky(new TinhTongTienEvent());
                                }
                            }
                        });
                        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        builder.show();
                    }
                }else if (value ==2){
                    if (array.get(pos).getSoluong() <10){
                        array.get(pos).setSoluong(soluongmoi+1);
                    }
                    holder.item_giohang_SL.setText(array.get(pos).getSoluong()+"");
                    thanhtien = array.get(pos).getSoluong() * array.get(pos).getGiasp();
                    holder.item_giohang_thanhtien.setText("Tổng: "+decimalFormat.format(thanhtien)+"₫");
                    //set lai tinh tong tien
                    EventBus.getDefault().postSticky(new TinhTongTienEvent());
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return array.size() ;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView item_giohang_hinhanh , item_giohang_Tru, item_giohang_Cong ;
        TextView item_giohang_Tensp, item_giohang_Giasp ,item_giohang_SL ,item_giohang_thanhtien;
        IImageClickListenner iImageClickListenner;
        LinearLayout layoutItem_sanpham;
        CheckBox checkBox ;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_giohang_hinhanh = itemView.findViewById(R.id.item_giohang_hinhanh);
            item_giohang_Tru = itemView.findViewById(R.id.item_giohang_Tru);
            item_giohang_Cong = itemView.findViewById(R.id.item_giohang_Cong);
            item_giohang_Tensp = itemView.findViewById(R.id.item_giohang_Tensp);
            item_giohang_Giasp = itemView.findViewById(R.id.item_giohang_Giasp);
            item_giohang_SL = itemView.findViewById(R.id.item_giohang_SL);
            item_giohang_thanhtien = itemView.findViewById(R.id.item_giohang_thanhtien);
            checkBox = itemView.findViewById(R.id.item_giohang_checkbox);

            //
            layoutItem_sanpham = itemView.findViewById(R.id.layoutItem_sanpham);
            // event click
            item_giohang_Cong.setOnClickListener(this);
            item_giohang_Tru.setOnClickListener(this);

        }
        // tang giam so luong
        public void setiImageClickListenner(IImageClickListenner iImageClickListenner) {
            this.iImageClickListenner = iImageClickListenner;
        }
        @Override
        public void onClick(View view) {
            if (view == item_giohang_Tru){
                iImageClickListenner.onImageClick(view,getAdapterPosition(), 1);
            }else if (view == item_giohang_Cong){
                iImageClickListenner.onImageClick(view,getAdapterPosition(), 2);
            }
        }
    }
}
