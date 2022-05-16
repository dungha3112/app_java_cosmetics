package com.example.app_cosmetics.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_cosmetics.R;
import com.example.app_cosmetics.models.DonHang;

import java.text.DecimalFormat;
import java.util.List;

public class XemDonHangAdapter extends RecyclerView.Adapter<XemDonHangAdapter.MyViewHolder> {
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    Context context;
    List<DonHang> array;

    public XemDonHangAdapter(Context context, List<DonHang> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_xemdonhang, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DonHang donHang = array.get(position);
        holder.txtIddonhang.setText("Đơn hàng: "+donHang.getId());
        DecimalFormat  decimalFormat = new DecimalFormat("###,###,###");
        holder.txtTongSoLuongDonHang.setText("Tổng số lượng: "+donHang.getSoluong());
        holder.txtTongTienDonHang.setText("Tổng số tiền: "+ decimalFormat.format(Double.parseDouble(donHang.getTongtien()))+"₫" );
        holder.txtngayDatHang.setText("Ngày đặt hàng: "+donHang.getNgaydat());
        LinearLayoutManager layoutManager = new LinearLayoutManager(
          holder.recyclerViewDonHang_ChiTiet.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(donHang.getItem().size());
        // adapter chitiet
        XemDonHangChiTietAdapter chiTietAdapter = new XemDonHangChiTietAdapter(context,donHang.getItem());
        holder.recyclerViewDonHang_ChiTiet.setLayoutManager(layoutManager);
        holder.recyclerViewDonHang_ChiTiet.setAdapter(chiTietAdapter);
        holder.recyclerViewDonHang_ChiTiet.setRecycledViewPool(viewPool);

    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtIddonhang , txtTongSoLuongDonHang, txtTongTienDonHang, txtngayDatHang;
        RecyclerView recyclerViewDonHang_ChiTiet;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIddonhang = itemView.findViewById(R.id.iddonhang);
            txtTongSoLuongDonHang = itemView.findViewById(R.id.txtTongSoLuongDonHang);
            txtngayDatHang = itemView.findViewById(R.id.txtngayDatHang);
            txtTongTienDonHang = itemView.findViewById(R.id.txtTongTienDonHang);
            recyclerViewDonHang_ChiTiet = itemView.findViewById(R.id.recyclerViewDonHang_ChiTiet);
        }
    }
}
