package com.bcaf.bus.network;


import com.bcaf.bus.model.agency.Agency;
import com.bcaf.bus.model.bus.Bus;
import com.bcaf.bus.model.register.UserRegister;
import com.bcaf.bus.model.user.ResponseUser;
import com.bcaf.bus.model.user.User;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface BaseApiService {

    @POST("auth/login")
    Call<ResponseUser> getLogin(@Body RequestBody params);

    @POST("auth/register")
    Call<User> register(@Body UserRegister userRegister);

    @POST("user/changePassword")
    Call<User> changePassword(@Body RequestBody requestBody);

    @GET("bus")
    Call<List<Bus>> getBus();

    @GET("agency")
    Call<List<Agency>> getAgency();


}
