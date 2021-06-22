package com.bcaf.bus.controller.ui.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bcaf.bus.R;
import com.bcaf.bus.controller.SplashActivity;
import com.bcaf.bus.network.BaseApiService;
import com.bcaf.bus.network.RetrofitInstance;
import com.bcaf.bus.session.MySession;
import com.bcaf.bus.utils.MyUtils;

import java.util.HashMap;

public class HomeFragment extends Fragment {

    private View root;
    private BaseApiService baseApiService;
    private MySession mySession;
    private MyUtils customUtils;
    private TextView homeName, homeEmail;
    private MySession session;
    private String sEmail, sToken, sFirstName, sLastName, sRoleId, sRoleName, sMobileNo;
    private ImageView signOut;
    public HomeFragment() {

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_home, container, false);
        viewData();
        session = new MySession(root.getContext());
        signOut = root.findViewById(R.id.logOut);

        signOut.setOnClickListener(v -> logout());
        return root;
    }

    private void viewData() {

        //Utils
        customUtils = new MyUtils(root.getContext());

        mySession = new MySession(root.getContext());
        mySession.checkLogin();

        if(mySession.isLoggedIn()) {
            HashMap<String, String> sUser = mySession.getUserDetails();
            sToken = sUser.get(MySession.KEY_TOKEN);
            sFirstName = sUser.get(MySession.KEY_FIRST_NAME);
            sLastName = sUser.get(MySession.KEY_LAST_NAME);
            sRoleId = sUser.get(MySession.KEY_ROLE_ID);
            sRoleName = sUser.get(MySession.KEY_ROLE_NAME);
            sEmail = sUser.get(MySession.KEY_EMAIL);
            sMobileNo = sUser.get(MySession.KEY_MOBILE_NUMBER);

//            homeEmail = root.findViewById(R.id.homeEmail);
//            homeName= root.findViewById(R.id.homeNama);
//            homeEmail.setText(sEmail);
//            homeName.setText(sFirstName);

        }

        baseApiService = RetrofitInstance.getRetrofitInstance(sToken).create(BaseApiService.class);

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