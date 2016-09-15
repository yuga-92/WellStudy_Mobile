package com.bootcamp.wellstudy.api;

import android.util.Base64;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class PicassoOkHttpClient {
    private static PicassoOkHttpClient instance = null;
    private OkHttpClient client;

    private PicassoOkHttpClient(final String username, final String password) {
        client = new okhttp3.OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    String credentials = username + ":" + password;
                    final String basic =
                            "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

                    @Override
                    public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                        Request newRequest = chain.request().newBuilder()
                                .addHeader("Authorization", basic)
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .build();
    }

    public static PicassoOkHttpClient getInstance(String username, String password) {
        if (instance == null) {
            instance = new PicassoOkHttpClient(username, password);
        }
        return instance;
    }

    public static void setInstanceNull() {
        instance = null;
    }

    public OkHttpClient getService() {
        return client;
    }


}
