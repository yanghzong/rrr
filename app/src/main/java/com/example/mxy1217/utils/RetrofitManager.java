package com.example.mxy1217.utils;

import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * date:2018/12/17
 * author:mxy(M)
 * function:
 */

public class RetrofitManager {

    private static final String BASE_URL = "http://www.zhaoapi.cn/";
    private final Retrofit retrofit;

    private static final class SINGLE_INSTANCE {
        private static final RetrofitManager _INSTANCE = new RetrofitManager();
    }

    public static RetrofitManager getInstance() {
        return SINGLE_INSTANCE._INSTANCE;
    }

    private RetrofitManager() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient())
                .build();
    }

    @NonNull
    private OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
    }

    public <T> T create(Class<T> tClass) {
        return retrofit.create(tClass);
    }
}
