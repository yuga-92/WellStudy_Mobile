package com.bootcamp.wellstudy.api;

import android.util.Base64;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.bootcamp.wellstudy.Constants.API_BASE_URL;

public class ServiceGenerator {

    private static ServiceGenerator instance = null;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());
    private WellStudyClient apiModule;

    private ServiceGenerator(String username, String password) {
        if (username != null && password != null) {
            String credentials = username + ":" + password;
            final String basic =
                    "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();

                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", basic)
                            .header("Accept", "application/json")
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }
        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        apiModule = retrofit.create(WellStudyClient.class);
    }

    public static ServiceGenerator getInstance(String username, String password) {
        if (instance == null) {
            instance = new ServiceGenerator(username, password);
        }
        return instance;
    }

    public static void setInstanceNull() {
        instance = null;
    }


    public static ServiceGenerator getInstance() {
        if (instance == null) {
            instance = new ServiceGenerator(null, null);
        }
        return instance;
    }

    public WellStudyClient getService() {
        return apiModule;
    }

}