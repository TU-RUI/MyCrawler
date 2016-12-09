package com.okhttp;

import okhttp3.OkHttpClient;

public class OkHttpClientManager {
    private static OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
    
    private OkHttpClientManager(){};
    
    public static OkHttpClient.Builder getBuilder(){
        return httpClientBuilder;
    }
}
