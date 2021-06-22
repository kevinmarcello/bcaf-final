package com.bcaf.bus.controller.ui.profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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


public class ProfileFragment extends Fragment {

    private View root;
    private BaseApiService baseApiService;
    private ImageView signOut;
    private MySession mySession;
    private MyUtils customUtils;
    private TextView profileFirstName, profileEmail, profileLastname, profileMobileNo ;
    private Button updateProfile, changePassword;
    private String sEmail, sToken, sFirstName, sLastName, sRoleId, sRoleName, sMobileNo;
    public ProfileFragment() {

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_profile, container, false);
        changePassword = root.findViewById(R.id.changePassword);
        updateProfile = root.findViewById(R.id.updateProfile);

        changePassword.setOnClickListener(v -> changePassword());
        updateProfile.setOnClickListener(v -> updateProfile());
        signOut = root.findViewById(R.id.logOut);
        signOut.setOnClickListener(v -> logout());
        viewData();
        return root;
    }

    private void changePassword(){
        Fragment fragment = null;
        fragment = new ChangePasswordFragment();
        loadFragment(new ChangePasswordFragment());
    }

    private void updateProfile(){
        Fragment fragment = null;
        fragment = new UpdateProfileFragment();
        loadFragment(new UpdateProfileFragment());
    }

    private boolean loadFragment(Fragment fragment){
        if (fragment != null) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_frame, fragment)
                    .commit();
            return true;
        }
        return false;
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
            Log.d("tesss", "1");
            Log.d("profile email",sEmail);

            profileEmail = root.findViewById(R.id.viewEmail);
            profileFirstName= root.findViewById(R.id.viewNamaDepan);
            profileLastname= root.findViewById(R.id.viewNamaBelakang);
            profileMobileNo= root.findViewById(R.id.viewMobileNo);

            profileEmail.setText(sEmail);
            profileFirstName.setText(sFirstName);
            profileLastname.setText(sLastName);
            profileMobileNo.setText(sMobileNo);

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
                        mySession.logoutUser();
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