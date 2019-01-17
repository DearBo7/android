package com.dan.httpasyn.config;

/**
 * Created by Dan on 2019/1/17 11:43
 */
public class HttpConfig {

    //服务器api
    public static String REST_URL = "http://119.37.194.4:5555";

    //api
    public static String REST_API = "/xtp-api";

    /**
     * 登录验证  参数:userName,pwd
     */
    public static String loginUrl = REST_URL + REST_API + "/user/loginUser";

}
