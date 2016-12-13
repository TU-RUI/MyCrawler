package com.okhttp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class CookieManager implements CookieJar {
    private final Map<String, List<Cookie>> cookieStore = new ConcurrentHashMap<>();

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        // TODO Auto-generated method stub
        cookieStore.put(url.host(), cookies);
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        // TODO Auto-generated method stub
        List<Cookie> cookies = cookieStore.get(url.host());
        return cookies != null ? cookies : new ArrayList<Cookie>();
    }

    private static CookieManager cm = null;

    private CookieManager() {
    }

    public static CookieManager getInstance() {
        if (cm == null) {
            synchronized (CookieManager.class) {
                if (cm == null) {
                    cm = new CookieManager();
                }
            }
        }
        return cm;
    }

}
