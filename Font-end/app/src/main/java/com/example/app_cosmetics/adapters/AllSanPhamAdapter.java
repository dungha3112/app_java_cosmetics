package com.example.app_cosmetics.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_cosmetics.Interfaces.ItemClickListener;
import com.example.app_cosmetics.R;
import com.example.app_cosmetics.activitis.ChiTietActivity;
import com.example.app_cosmetics.models.SanPham;

import java.text.DecimalFormat;
import java.util.List;

public class AllSanPhamAdapter extends RecyclerView.Adapter<AllSanPhamAdapter.MyViewHolder> {
    Context context;
    List<SanPham> array;

    public AllSanPhamAdapter(Context context, List<SanPham> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_sanpham, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SanPham sanPham = array.get(position);
        holder.txtTenSP.setText(sanPham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiaSP.setText("Giá: "+decimalFormat.format(Double.parseDouble(sanPham.getGiasanpham()) )+"₫" );
        Glide.with(context).load(sanPham.getHinhanh()).into(holder.imageViewSP);
        // click vao tung san pham
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {
                if (!isLongClick){
                    //click
                    Intent intent = new Intent(context, ChiTietActivity.class);
                    intent.putExtra("chitiet", sanPham);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return array.size();
    }


    public class MyViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageViewSP ;
        private TextView txtTenSP, txtGiaSP;
        private ItemClickListener itemClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewSP = itemView.findViewById(R.id.imageAllItemSP_image);
            txtTenSP = itemView.findViewById(R.id.txtAllItemSP_TenSP);
            txtGiaSP = itemView.findViewById(R.id.txtAllItemSP_GiaSP);
            itemView.setOnClickListener(this);
        }

        public ItemClickListener getItemClickListener() {
            return itemClickListener;
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), false);
        }
    }
}
