package com.bcaf.bus.controller.ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.collection.ArrayMap;
import androidx.fragment.app.Fragment;

import com.bcaf.bus.R;
import com.bcaf.bus.model.user.User;
import com.bcaf.bus.network.BaseApiService;
import com.bcaf.bus.network.RetrofitInstance;
import com.bcaf.bus.session.MySession;
import com.bcaf.bus.utils.MyUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileFragment extends Fragment {
    private View root;
    private Button bSubmit;
    private MySession session;
    private String EmailHolder, MobileNoHolder, FirstNameHolder, LastNameHolder;
    private EditText FirstName,LastName,Email,MobileNo;
    private BaseApiService baseApiService;
    private String xToken = "";
    private MyUtils customUtils;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_update_profile, container, false);
        session= new MySession(getActivity());
        HashMap<String, String> sUsernya = session.getUserDetails();
        String sToken = sUsernya.get(MySession.KEY_TOKEN);

        FirstName = root.findViewById(R.id.updateFirstName);
        LastName = root.findViewById(R.id.updateLastName);
        MobileNo = root.findViewById(R.id.updateMobileNo);
        bSubmit = root.findViewById(R.id.btn_UpdateProfile);



        String sEmail = sUsernya.get(MySession.KEY_EMAIL);
        String sFirstName = sUsernya.get(MySession.KEY_FIRST_NAME);
        String sLastName = sUsernya.get(MySession.KEY_LAST_NAME);
        String sMobileNumber = sUsernya.get(MySession.KEY_MOBILE_NUMBER);

        FirstName.setText(sFirstName);
        LastName.setText(sLastName);
        MobileNo.setText(sMobileNumber);


        baseApiService = RetrofitInstance.getRetrofitInstance(""+sToken).create(BaseApiService.class);
        bSubmit = root.findViewById(R.id.btn_UpdateProfile);
        bSubmit.setOnClickListener(view -> updateProfile());

        return root;
    }

    private void updateProfile(){

        MobileNoHolder = MobileNo.getText().toString();
        FirstNameHolder = FirstName.getText().toString();
        LastNameHolder = LastName.getText().toString();

        Log.wtf("Holder",MobileNoHolder + FirstNameHolder + LastNameHolder);

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("firstName", FirstNameHolder);
        jsonParams.put("lastName", LastNameHolder);
        jsonParams.put("mobileNumber",MobileNoHolder );

        String error = jsonParams.toString();
//        String error2 = jsonParams.get("lastName").toString();
//        String error3 = jsonParams.get("mobileNumber").toString();

        Log.wtf("firstname = ",  error);
//        Log.wtf("lastname = ",  error2);
//        Log.wtf("mob = ",  error3);

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),(
                        new JSONObject(jsonParams)).toString());

        Call<User> userUpdate = baseApiService.updateUser(body);
        userUpdate.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.body()!=null){
                    String xfirstName  = response.body().getFirstName();
                    String xlastName  = response.body().getLastName();
                    String xmobileNumber  = response.body().getMobileNumber();
                    session.userUpdateSession(xfirstName, xlastName, xmobileNumber);

                    Toast.makeText(getActivity(),"Profile sudah terganti", Toast.LENGTH_SHORT).show();
                    Fragment fragment = null;
                    fragment = new ProfileFragment();
                    loadFragment(new ProfileFragment());
                }

                else{
                    Log.wtf("error input 1 = ", response.message() + response.body() + response.raw());
                    Log.d("Json =  ",body.toString());
                    Toast.makeText(getActivity(),"Error" + response.message(), Toast.LENGTH_SHORT).show();
                    Log.wtf("error input = ", response.message() + response.body());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
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
}