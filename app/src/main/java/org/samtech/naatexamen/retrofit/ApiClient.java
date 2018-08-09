package org.samtech.naatexamen.retrofit;

import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.samtech.naatexamen.utils.Keys.JSON_FACTORY;

public class ApiClient {
    private static Retrofit retrofit = null;

    public static Retrofit api(String paramBaseURL, int converterType) {
        if (retrofit == null) {
            Converter.Factory factory = null;

            switch (converterType) {

                case JSON_FACTORY:
                    factory = GsonConverterFactory
                            .create(new GsonBuilder()
                                    .setLenient()
                                    .create());
                    break;
            }

            assert factory != null;

            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(paramBaseURL)
                    .client(okHttpClient)
                    .addConverterFactory(factory)
                    .build();
        }
        return retrofit;
    }
}
