package com.intelligentz.cashpal.handler;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Created by lakshan on 12/26/16.
 */
public class OTPHandler {
    private static LoadingCache<String, String> otpCache;
    static {
        otpCache = CacheBuilder.newBuilder()
                .expireAfterAccess(2, TimeUnit.MINUTES)
                .build(new CacheLoader<String, String>() {
                    public String load(String key) {
                        return "";
                    }
                });
    }

    public static String addNewOTPToCache(String key) {
        String otp = "";
        int randomNum = ThreadLocalRandom.current().nextInt(10000, 100000);
        otp = String.valueOf(randomNum);
        otpCache.put(key, otp);
        return otp;
    }

    public static String getFromOTPCache(String key) {
        try {
            return otpCache.get(key);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
