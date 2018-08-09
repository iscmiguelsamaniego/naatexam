package org.samtech.naatexamen.model.retrofitmodels.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("agente")
    @Expose
    private String agente;
    @SerializedName("error")
    @Expose
    private Object error;
    @SerializedName("id_user")
    @Expose
    private String idUser;
    @SerializedName("token")
    @Expose
    private String token;

    public String getAgente() {
        return agente;
    }

    public Object getError() {
        return error;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getToken() {
        return token;
    }

}
