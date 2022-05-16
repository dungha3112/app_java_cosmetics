package com.example.app_cosmetics.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_cosmetics.R;
import com.example.app_cosmetics.models.Item;

import java.text.DecimalFormat;
import java.util.List;

public class XemDonHangChiTietAdapter extends RecyclerView.Adapter<XemDonHangChiTietAdapter.MyViewHolder> {
    Context context;
    List<Item> itemList;

    public XemDonHangChiTietAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_xemdonhang_chitiet, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.txtTenSpChiTiet.setText(item.getTensp());
        holder.txtSoLuongSpChiTiet.setText("Số lượng: "+item.getSoluong());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiaSpChiTiet.setText("Giá: "+ decimalFormat.format(Double.parseDouble(item.getGiasp()))+"₫" );
        Glide.with(context).load(item.getHinhsp()).into(holder.imageChitiet);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageChitiet ;
        TextView txtTenSpChiTiet, txtSoLuongSpChiTiet, txtGiaSpChiTiet;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageChitiet = itemView.findViewById(R.id.item_xemdonhang_Imagechitiet);
            txtTenSpChiTiet = itemView.findViewById(R.id.item_xemdonhang_Tenspchitiet);
            txtSoLuongSpChiTiet = itemView.findViewById(R.id.item_xemdonhang_SLspchitiet);
            txtGiaSpChiTiet = itemView.findViewById(R.id.item_xemdonhang_Giaspchitiet);

        }
    }
}
