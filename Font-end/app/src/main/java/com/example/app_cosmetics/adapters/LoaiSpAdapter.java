package com.example.app_cosmetics.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.app_cosmetics.R;
import com.example.app_cosmetics.models.LoaiSp;

import java.util.List;

public class LoaiSpAdapter extends BaseAdapter {
    Context context;
    List<LoaiSp> array;

    public LoaiSpAdapter(Context context, List<LoaiSp> array ) {
        this.array = array;
        this.context = context;
    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public  class  ViewHolder{
        TextView txtTenSp;
        ImageView imageSp;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view ==null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_loaisanpham,null    );
            viewHolder.txtTenSp = view.findViewById(R.id.txtItemLoaiSP_TenSP);
            viewHolder.imageSp  = view.findViewById(R.id.imageItemLoaiSP_image);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.txtTenSp.setText(array.get(i).getTenloaisanpham());
        Glide.with(context).load(array.get(i).getHinhanh()).into(viewHolder.imageSp);
        return view;
    }
}
