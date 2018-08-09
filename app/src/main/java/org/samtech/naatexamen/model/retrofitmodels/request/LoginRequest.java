package org.samtech.naatexamen.model.retrofitmodels.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    @SerializedName("data")
    @Expose
    private Data data;

    public LoginRequest(Data data) {
        this.data = data;
    }
}