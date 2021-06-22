package com.bcaf.bus.adapter.bus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bcaf.bus.R;
import com.bcaf.bus.model.bus.Agency;
import com.bcaf.bus.model.bus.Bus;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BusListAdapter extends RecyclerView.Adapter<BusListAdapter.MyViewHolder>{

    List<Agency> agencyList;
    List<Bus> busList;
    private Context context;

    public BusListAdapter(List<Bus> busList, Context a) {
//        this.agencyList = agencyList;
        this.context = a;
        this.busList = busList;
    }

    @Override
    public @NotNull MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_bus, parent, false);
        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        holder.code.setText("Bus Code : " + busList.get(position).getCode());
        holder.seat.setText("Seat Capacity : "+String.valueOf(busList.get(position).getCapacity()));

        String detailAgency = busList.get(position).getAgency().getDetail();
        holder.detail.setText("Detail : "+detailAgency);

        String namaAgency = busList.get(position).getAgency().getName();
        holder.agency.setText("Agency Name = " + namaAgency);

    }

    @Override
    public int getItemCount() {
        return busList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView detail,seat,code,agency;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            detail = itemView.findViewById(R.id.detailAgencyBus);
            code = itemView.findViewById(R.id.codeBus);
            seat = itemView.findViewById(R.id.seatBus);
            agency = itemView.findViewById(R.id.agencyBus);
        }
    }
}
