package org.samtech.naatexamen.model.retrofitmodels.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("pass")
    @Expose
    private String pass;
    @SerializedName("user")
    @Expose
    private String user;

    public Data(String pass, String user) {
        this.pass = pass;
        this.user = user;
    }
}
