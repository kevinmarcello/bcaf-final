package com.bcaf.bus.controller.ui.bus;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bcaf.bus.R;
import com.bcaf.bus.adapter.bus.BusListAdapter;
import com.bcaf.bus.controller.SplashActivity;
import com.bcaf.bus.model.bus.Bus;
import com.bcaf.bus.network.ApiClient;
import com.bcaf.bus.network.BaseApiService;
import com.bcaf.bus.session.MySession;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusFragment extends Fragment {

    private RecyclerView rvListAgency;
    private TextView tInternetHilang;
    private BaseApiService baseApiService;
    private RecyclerView.Adapter mBusAdapter;
    private RecyclerView.LayoutManager mLayoutManagerAgency;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageView signOut;
    SharedPreferences.Editor editor;
    SharedPreferences pref;
    private View root;

    private MySession session;
    Context _context;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_bus, container, false);
        viewData();
        session = new MySession(root.getContext());
        signOut = root.findViewById(R.id.logOut);

        signOut.setOnClickListener(v -> logout());
        return root;
    }

    private void viewData() {

        rvListAgency = root.findViewById(R.id.listBus);
        swipeRefreshLayout = root.findViewById(R.id.swipeRefresh);

        baseApiService = ApiClient.getClient().create(BaseApiService.class);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            viewBus();
        });
        viewBus();
    }

    private void viewBus() {
        Call<List<Bus>> callUserList = baseApiService.getBus();
        callUserList.enqueue(new Callback<List<Bus>>() {
            @Override
            public void onResponse(Call<List<Bus>> call, Response<List<Bus>> response) {
                try {
                    if (response.body() != null){
                       generateBus(response.body());
                    }else {
                        Toast.makeText(root.getContext(), "data bus kosong", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Log.wtf("Error : ",e.getMessage());
                }
                swipeRefreshLayout.setRefreshing(false);
            }
            @Override
            public void onFailure(Call<List<Bus>> call, Throwable t) {
                t.printStackTrace();
                Log.wtf("Failure : ",t.getMessage());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    private void generateBus(List<Bus> busList){
        mLayoutManagerAgency = new LinearLayoutManager(root.getContext());
        rvListAgency.setLayoutManager(mLayoutManagerAgency);

        mBusAdapter = new BusListAdapter(busList, root.getContext());
        rvListAgency.setAdapter(mBusAdapter);
    }

    private void logout(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Logout");
        builder.setMessage("Apakah anda yakin mau logout?");
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        session.logoutUser();
                        dialog.dismiss();
                        Intent i = new Intent(getActivity(), SplashActivity.class);
                        startActivity(i);
                    }
                });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }




}