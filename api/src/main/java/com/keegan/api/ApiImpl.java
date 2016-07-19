package com.keegan.api;

import com.google.gson.reflect.TypeToken;
import com.keegan.api.net.HttpEngine;
import com.keegan.api.utils.EncryptUtil;
import com.keegan.model.CouponEntity;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/8.
 */
public class ApiImpl implements Api {
    private static final String APP_KEY = "ANDROID_KCOUPON";
    //HttpEngine获取数据时出错，如超时，返回此作为event和msg的ApiResponse实例
    private static final String TIME_OUT_EVENT = "CONNECT_TIME_OUT";
    private static final String TIME_OUT_EVENT_MSG = "连接服务器失败";

    private HttpEngine httpEngine;

    public ApiImpl() { httpEngine = HttpEngine.getInstance(); }

    @Override
    public ApiResponse<Void> sendSmsCode4Register(String phoneNum) {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("appKey", APP_KEY);
        paramsMap.put("method", SEND_SMS_CODE);
        paramsMap.put("phoneNum", phoneNum);

        Type type = new TypeToken<ApiResponse<Void>>(){}.getType();
        try {
            return httpEngine.postHandle(paramsMap, type);
        } catch(IOException e) {
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }
    }

    @Override
    public ApiResponse<Void> registerByPhone(String phoneNum, String code, String password) {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("appKey", APP_KEY);
        paramsMap.put("method", REGISTER);
        paramsMap.put("phoneNum", phoneNum);
        paramsMap.put("code", code);
        paramsMap.put("password", EncryptUtil.makeMD5(password));

        Type type = new TypeToken<ApiResponse<List<CouponEntity>>>(){}.getType();
        try {
            return httpEngine.postHandle(paramsMap, type);
        } catch(IOException e) {
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }
    }

    @Override
    public ApiResponse<Void> loginByApp(String loginName, String password, String imei, int loginOS) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("appKey", APP_KEY);
        paramMap.put("method", LOGIN);
        paramMap.put("loginName", loginName);
        paramMap.put("password", EncryptUtil.makeMD5(password));
        paramMap.put("imei", imei);
        paramMap.put("loginOS", String.valueOf(loginOS));

        Type type = new TypeToken<ApiResponse<List<CouponEntity>>>(){}.getType();
        try {
            return httpEngine.postHandle(paramMap, type);
        } catch (IOException e) {
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }
    }

    @Override
    public ApiResponse<List<CouponEntity>> listNewCoupon(int currentPage, int pageSize) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("appKey", APP_KEY);
        paramMap.put("method", LIST_COUPON);
        paramMap.put("currentPage", String.valueOf(currentPage));
        paramMap.put("pageSize", String.valueOf(pageSize));

        Type type = new TypeToken<ApiResponse<List<CouponEntity>>>(){}.getType();
        try {
            return httpEngine.postHandle(paramMap, type);
        } catch (IOException e) {
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }
    }
}
