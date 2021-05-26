package com.dreamernguyen.AppRaoVatSaFaCo.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.dreamernguyen.AppRaoVatSaFaCo.Activity.DangMatHangActivity;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.MatHang;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.Quan;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.Tinh;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.Xa;
import com.dreamernguyen.AppRaoVatSaFaCo.R;

import java.util.ArrayList;
import java.util.List;

public class DiaChiAdapter extends RecyclerView.Adapter<DiaChiAdapter.DiaChiViewHolder> implements Filterable {
    private Context context;
    private List<Tinh> listTinh;
    private List<Tinh> listTinhCu;
    private List<Quan> listQuan;
    private List<Quan> listQuanCu;
    private List<Xa> listXa;
    private List<Xa> listXaCu;
    onClickThongTin onClickThongTin;

    public DiaChiAdapter(Context context) {
        this.context = context;
    }

    @Override
    public Filter getFilter() {

        if (listTinh != null) {
            return FilterTinh;
        }
        if (listQuan != null) {
            return FilterQuan;
        }
        if (listXa!=null){
            return FilterXa;
        }
        return null;
    }
    public Filter FilterTinh =new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String a = constraint.toString();
            Log.d("TAG", "performFiltering: "+a);
            if (a.isEmpty()){
                listTinh=listTinhCu;
            }
            else {
                List<Tinh> list1 = new ArrayList<>();

                for (Tinh tinh : listTinhCu){
                    if (tinh.getTenTinh().toLowerCase().contains(a.toLowerCase())){
                        list1.add(tinh);

                    }
                }
                listTinh= list1;
                Log.d("TAG", "performFiltering: list "+list1);
            }
            FilterResults filterResults= new FilterResults();
            filterResults.values=listTinh;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            listTinh = (List<Tinh>) results.values;
            notifyDataSetChanged();
        }
    };
    public Filter FilterQuan = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String a = constraint.toString();
            Log.d("TAG", "performFiltering: "+a);
            if (a.isEmpty()){
                listQuan=listQuanCu;
            }
            else {
                List<Quan> list1 = new ArrayList<>();

                for (Quan quan : listQuanCu){
                    if (quan.getTenQuan().toLowerCase().contains(a.toLowerCase())){
                        list1.add(quan);

                    }
                }
                listQuan= list1;
                Log.d("TAG", "performFiltering: list "+list1);
            }
            FilterResults filterResults= new FilterResults();
            filterResults.values=listQuan;
            return filterResults;        }


        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {


            listQuan = (List<Quan>) results.values;
            notifyDataSetChanged();
        }
    };
    private Filter FilterXa = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String a = constraint.toString();
            Log.d("TAG", "performFiltering: "+a);
            if (a.isEmpty()){
                listXa=listXaCu;
            }
            else {
                List<Xa> list1 = new ArrayList<>();

                for (Xa xa : listXaCu){
                    if (xa.getTenXa().toLowerCase().contains(a.toLowerCase())){
                        list1.add(xa);

                    }
                }
                listXa= list1;
                Log.d("TAG", "performFiltering: list "+list1);
            }
            FilterResults filterResults= new FilterResults();
            filterResults.values=listXa;
            return filterResults;                }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {


            listXa = (List<Xa>) results.values;
            notifyDataSetChanged();
        }
    };

    public interface onClickThongTin {

        void getString(String ma, String Ten);

    }

    public DiaChiAdapter(Context context, DiaChiAdapter.onClickThongTin onClickThongTin) {
        this.context = context;
        this.onClickThongTin = onClickThongTin;
    }

    public void setListTinh(List<Tinh> list) {
        this.listTinh = list;
        this.listTinhCu=list;
        notifyDataSetChanged();
    }

    public void setListQuan(List<Quan> list) {
        this.listQuan = list;
        this.listQuanCu=list;
        notifyDataSetChanged();
    }

    public void setListXa(List<Xa> list) {
        this.listXa = list;
        this.listXaCu=list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public DiaChiAdapter.DiaChiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dia_chi, parent, false);
        return new DiaChiAdapter.DiaChiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaChiAdapter.DiaChiViewHolder holder, int position) {
        if (listTinh != null && !listTinh.isEmpty()) {
            Tinh tinh = listTinh.get(position);
            holder.tvTenDiaChi.setText(tinh.getTenTinh());
            holder.tvTenDiaChi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickThongTin.getString(tinh.getMaTinh(), tinh.getTenTinh());
                }
            });


        }
        if (listQuan != null && !listQuan.isEmpty()) {
            Quan quan = listQuan.get(position);
            holder.tvTenDiaChi.setText(quan.getTenQuan());
            holder.tvTenDiaChi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickThongTin.getString(quan.getMaQuan(), quan.getTenQuan());

                }
            });

        }
        if (listXa != null && !listXa.isEmpty()) {
            Xa xa = listXa.get(position);
            holder.tvTenDiaChi.setText(xa.getTenXa());
            holder.tvTenDiaChi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickThongTin.getString(xa.getMaXa(), xa.getTenXa());
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        if (listTinh != null) {
            return listTinh.size();
        }
        if (listQuan != null) {
            return listQuan.size();
        }
        if (listXa != null) {
            return listXa.size();
        }
        return 0;
    }

    public class DiaChiViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenDiaChi;

        public DiaChiViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenDiaChi = itemView.findViewById(R.id.tvTenDiaChi);
        }
    }
}
