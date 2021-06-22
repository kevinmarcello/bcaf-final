package com.bcaf.bus.controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArrayMap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bcaf.bus.R;
import com.bcaf.bus.model.register.UserRegister;
import com.bcaf.bus.model.user.ResponseUser;
import com.bcaf.bus.model.user.User;
import com.bcaf.bus.network.ApiClient;
import com.bcaf.bus.network.BaseApiService;
import com.bcaf.bus.network.RetrofitInstance;
import com.bcaf.bus.session.MySession;
import com.bcaf.bus.utils.MyUtils;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private String PasswordHolder, EmailHolder, FirstNameHolder,LastNameHolder, MobileNoHolder ;
    private TextInputEditText edFirstname, edLastname, edEmail, edPassword, edMobileNo;
    private Button bRegister;
    private Boolean CheckEditText;
    private MySession session;
    private TextView txtRegister;
    BaseApiService authInterface;
    private BaseApiService baseApiService;
    private User listUser;
    private String xToken = "";

    private MyUtils customUtils;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);

            edFirstname = findViewById(R.id.inp_firstName);
            edLastname = findViewById(R.id.inp_lastName);
            edEmail = findViewById(R.id.inp_email);
            edMobileNo = findViewById(R.id.inp_mobileNumber);
            edPassword = findViewById(R.id.inp_password);
            bRegister = findViewById(R.id.btn_register);
            authInterface = ApiClient.getClient().create(BaseApiService.class);


            bRegister.setOnClickListener(v ->{
                ArrayList<String> Role = new ArrayList<>();
                Role.add("user");
                UserRegister userRegister = new UserRegister(edEmail.getText().toString(),edFirstname.getText().toString(),edLastname.getText().toString(),
                        edMobileNo.getText().toString(),edPassword.getText().toString(),Role);
                Call<User> userCall = authInterface.register(userRegister);
                userCall.enqueue(new Callback<User>() {

                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User responRegister = response.body();
                        Log.d("dedi2", String.valueOf(response.code()));
                        if(responRegister != null){
                            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(i);
                        }else{
                            Toast.makeText(RegisterActivity.this, "Data Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, " Koneksi anda Buruk", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        }
    }