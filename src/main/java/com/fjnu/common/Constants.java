package com.fjnu.common;

/**
 * Created by LCC on 2018/4/3.
 */
public final class Constants {

    //-------------------------- 客户端版本部分 ----------------------------
    /** 客户端版本 */
    public static final String CLIENT_VERSION = "v2.0.9";
    /** 是否是管理者模式（暂时区分管理员客户端和使用者客户端参数） */
    public static final boolean IS_MANAGER_MODEL = true;
//    public static final boolean IS_MANAGER_MODEL = false;

    //--------------------------  http 请求部分 -----------------------------
    /** 服务器默认IP地址 */
    public static final String SERVER_DEFAULT_IP = "127.0.0.1:8080";
    /** http连接超时时间 */
    public static final int CONNECT_TIMEOUT = 10;
    /** http读超时时间 */
    public static final int READ_TIMEOUT = 20;
    /** http写超时时间 */
    public static final int WRITE_TIMEOUT = 20;

}
