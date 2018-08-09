package org.samtech.naatexamen.retrofit;

import org.samtech.naatexamen.model.retrofitmodels.request.LoginRequest;
import org.samtech.naatexamen.model.retrofitmodels.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiDefinition {

    @Headers({"SO: Android", "Version: 2.5.2"})
    @POST("/AgenteMovil.svc/agMov/login")
    Call<LoginResponse> requestLogin(@Body LoginRequest body);

}
