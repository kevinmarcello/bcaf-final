package com.bcaf.bus.adapter.agency;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bcaf.bus.R;
import com.bcaf.bus.model.agency.Agency;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AgencyListAdapter extends  RecyclerView.Adapter<AgencyListAdapter.MyViewHolder> {

    List<Agency> agencyList;
    private Context context;

    public AgencyListAdapter(List<Agency> agencyList, Context a) {
        this.agencyList = agencyList;
        this.context = a;
    }

    @Override
    public @NotNull
    AgencyListAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_agency, parent, false);
        return new AgencyListAdapter.MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AgencyListAdapter.MyViewHolder holder, int position) {
        holder.code.setText("Agency Code : "+agencyList.get(position).getCode());
        holder.name.setText("Agency Name : "+agencyList.get(position).getName());
        holder.detail.setText("Agency Detail : "+agencyList.get(position).getDetail());

    }

    @Override
    public int getItemCount() {
        return agencyList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView detail,name,code;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            detail = itemView.findViewById(R.id.agencyDetail);
            code = itemView.findViewById(R.id.agencyCode);
            name = itemView.findViewById(R.id.agencyName);
        }
    }
}
