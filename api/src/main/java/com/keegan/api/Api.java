package com.keegan.api;

import com.keegan.model.CouponEntity;

import java.util.List;

/**
 * Api接口
 *
 * @author Keegan小钢
 * @date 15/6/21
 * @version 1.0
 */
public interface Api {
    //URL参数中method的值，method则根据不同接口定义不同常量。
    // 发送验证码
    public static final String SEND_SMS_CODE = "service.sendSmsCode4Register";
    // 注册
    public  static final String REGISTER = "customer.registerByPhone";
    // 登录
    public static final String LOGIN = "customer.loginByApp";
    // 券列表
    public static final String LIST_COUPON = "issue.listNewCoupon";

    /**
     * 发送验证码
     *
     * @param phoneNum 手机号码
     * @return 成功时返回：{ "code": 0, "msg":"success" }
     */
    public ApiResponse<Void> sendSmsCode4Register(String phoneNum);

    /**
     * 注册
     *
     * @param phoneNum 手机号码
     * @param code     验证码
     * @param password MD5加密的密码
     * @return 成功时返回：{ "code": 0, "msg":"success" }
     */
    public ApiResponse<Void> registerByPhone(String phoneNum, String code, String password);

    /**
     * 登录
     *
     * @param loginName 登录名（手机号）
     * @param password  MD5加密的密码
     * @param imei      手机IMEI串号
     * @param loginOS   Android为1
     * @return 成功时返回：{ "code": 0, "msg":"success" }
     */
    public ApiResponse<Void> loginByApp(String loginName, String password, String imei, int loginOS);

    /**
     * 券列表
     *
     * @param currentPage 当前页数
     * @param pageSize    每页显示数量
     * @return 成功时返回：{ "code": 0, "msg":"success", "objList":[...] }
     */
    public ApiResponse<List<CouponEntity>> listNewCoupon(int currentPage, int pageSize);
}
