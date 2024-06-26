package com.example.banhangonline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.banhangonline.Interface.ImageClickListener;
import com.example.banhangonline.R;
import com.example.banhangonline.model.EventBus.TinhTongEvent;
import com.example.banhangonline.model.GioHang;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.MyViewHolder> {
    Context context;
    List<GioHang> gioHangList;

    public GioHangAdapter(Context context, List<GioHang> gioHangList) {
        this.context = context;
        this.gioHangList = gioHangList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GioHang gioHang = gioHangList.get(position);
        holder.item_giohang_tensp.setText(gioHang.getTensp());
        holder.item_giohang_soluong.setText(String.valueOf(gioHang.getSoluong()));
        Glide.with(context).load(gioHang.getHinhsp()).into(holder.item_giohang_image);
        holder.item_giohang_gia.setText(String.format("%,d₫", gioHang.getGiasp())); // Format giá thành tiền tệ
        long gia = gioHang.getSoluong() * gioHang.getGiasp();
        holder.item_giohang_giasp2.setText(String.format("%,d₫", gia));// Format tổng giá thành tiền tệ
        holder.setListener(new ImageClickListener() {
            @Override
            public void onImageClick(View view, int pos, int giatri) {
                if(giatri == 1 ){
                    if(gioHangList.get(pos).getSoluong() >1){
                        int soluongmoi = gioHangList.get(pos).getSoluong()-1;
                        gioHangList.get(pos).setSoluong(soluongmoi);
                    }
                }else if(giatri == 2){
                    if(gioHangList.get(pos).getSoluong() <11    ) {
                        int soluongmoi = gioHangList.get(pos).getSoluong()+1;
                        gioHangList.get(pos).setSoluong(soluongmoi);
                    }
                }
                holder.item_giohang_soluong.setText(gioHangList.get(pos).getSoluong() + " ");
                long gia = gioHang.getSoluong() * gioHang.getGiasp();
                holder.item_giohang_giasp2.setText(String.format("%,d₫", gia));// Format tổng giá thành tiền tệ
                EventBus.getDefault().postSticky(new TinhTongEvent());
            }
        });
    }

    @Override
    public int getItemCount() {
        return gioHangList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView item_giohang_image, imgtru, imgcong;
        TextView item_giohang_tensp, item_giohang_gia,item_giohang_soluong, item_giohang_giasp2;
        ImageClickListener listener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_giohang_image = itemView.findViewById(R.id.item_giohang_image);
            item_giohang_tensp = itemView.findViewById(R.id.item_giohang_tensp);
            item_giohang_gia = itemView.findViewById(R.id.item_giohang_gia);
            item_giohang_soluong = itemView.findViewById(R.id.item_giohang_soluong);
            item_giohang_giasp2 = itemView.findViewById(R.id.item_giohang_giasp2);
            imgtru = itemView.findViewById(R.id.item_giohang_tru);
            imgcong = itemView.findViewById(R.id.item_giohang_cong);

            imgcong.setOnClickListener(this);
            imgtru.setOnClickListener(this);
        }

        public void setListener(ImageClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            if(view == imgtru){
                listener.onImageClick(view,getAdapterPosition(),1);
                //1 trừ
            }else if(view == imgcong){
                //2 cộng
                listener.onImageClick(view,getAdapterPosition(),2);
            }
        }
    }
}
