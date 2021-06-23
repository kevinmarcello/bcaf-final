package com.bcaf.bus.controller.ui.profile;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.collection.ArrayMap;
import androidx.fragment.app.Fragment;

import com.bcaf.bus.R;
import com.bcaf.bus.model.user.User;
import com.bcaf.bus.network.BaseApiService;
import com.bcaf.bus.network.RetrofitInstance;
import com.bcaf.bus.session.MySession;
import com.bcaf.bus.utils.MyUtils;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordFragment extends Fragment {
    private View root;
    private Button bSubmit;
    private MySession session;
    private String Password1Holder, Password2Holder;
    private TextInputEditText password1,password2;
    private BaseApiService baseApiService;
    private String xToken = "";
    private MyUtils customUtils;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_change_password, container, false);
        session= new MySession(getActivity());
        HashMap<String, String> sUsernya = session.getUserDetails();
        String sToken = sUsernya.get(MySession.KEY_TOKEN);

        password1 = root.findViewById(R.id.password_1);
        password2 = root.findViewById(R.id.password_2);

        session = new MySession(root.getContext());


        Log.wtf("myTOken",sToken);
        baseApiService = RetrofitInstance.getRetrofitInstance(""+sToken).create(BaseApiService.class);
        bSubmit = root.findViewById(R.id.btn_changePass);
        bSubmit.setOnClickListener(view -> checkInputPass());
        return root;
    }

    public void checkInputPass(){
        Password1Holder = password1.getText().toString();
        Password2Holder = password2.getText().toString();

        if(TextUtils.isEmpty(Password1Holder)||TextUtils.isEmpty(Password2Holder)){
            Toast.makeText(getActivity(),"Password & Re-type tidak boleh kosong", Toast.LENGTH_SHORT).show();
        }

        else if(!Objects.equals(Password1Holder,Password2Holder)){
            Toast.makeText(getActivity(),"Password & Re-Type tidak sama!", Toast.LENGTH_SHORT).show();
        }
        else{
            changePass();
        }
    }

    public void changePass(){
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("password", password1.getText().toString());

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),(
                        new JSONObject(jsonParams)).toString());

        Call<User> userCall = baseApiService.changePassword(body);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.body()!=null){
                    Toast.makeText(getActivity(),"Password sudah terganti", Toast.LENGTH_SHORT).show();
                    Fragment fragment = null;
                    fragment = new ChangePasswordFragment();
                    loadFragment(new ChangePasswordFragment());
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