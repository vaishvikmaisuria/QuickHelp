package com.example.profile.Retrofit;

import com.example.profile.Classes.MedicalForm;

import org.json.JSONObject;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import java.util.List;

public interface INodeJs {


    @POST("user/login")
    @FormUrlEncoded
    Observable<String> loginUser(@Field("username") String username,
                                 @Field("password") String password);
    @POST("user/register")
    @FormUrlEncoded
    Observable<String> registerUser(@Field("email") String email,
                                    @Field("username") String username,
                                   // @Field("memStatus") String memStatus,
                                    @Field("firstName") String firstName,
                                    @Field("lastName") String lastName,
                                    @Field("password") String password);

    @POST("user/updateInfo")
    @FormUrlEncoded
    Call<String> updateMed(@Body String m);
}
