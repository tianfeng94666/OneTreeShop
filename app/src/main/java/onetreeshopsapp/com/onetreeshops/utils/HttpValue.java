package onetreeshopsapp.com.onetreeshops.utils;

/**
 * Created by fiona on 2016/9/19.
 */
public class HttpValue {
    public static  int SOCKET_PORT = 8069;
    public static  String SOCKET_IP = "172.16.64.19" ;
    // 配置信息(不变的)
//	public static String DBNAME = "market"; // 服务器数据库
    public static String DBNAME = "newmarket"; // 服务器数据库
    // 登陆保存到的session
    public static String COOKIE = ""; // openERP的会话session=SESSION_ID
    public static String SESSION_ID = "";//openERP的会话session
    public static int SP_USERID = 0; // 用户名
    public static int PRICELIST = 0;
    /**
     * 设备唯一标识码
     */
    public static String terminal ="" ;
    /**
     * ip
     */
    public static String IP = "";

//	public static String IP = "http://192.168.1.253:8069";
    /**
     * 拼接地址
     *
     * @return
     */
    public static String getHttp() {
        return "http://"+IP;
    }

    /**
     * 结束activity的code
     */
    public static int ACTIVITY_FINISH = 77;

    /**
     * 商户信息
     */
    public static String SHOP_NAME = "";
    public static String SHOP_VALUE_ID = "";
    public static String SHOP_ADDRESS = "";
    public static String SHOP_PHONE_NUMBER = "";
    public static String SHOP_WEB = "";
    public static String SHOP_EMAIL = "";
    public static String SHOP_WELCOM = "";
    public static String CASHIER_NAEM= "";
    public static String SHOP_ID= "";
    /**
     * 对应支付方式的编码,
     * 银联支付 TBNK  会员支付 TVIP
     * 现金支付 TCSH   微信支付 TWWX
     * 支付宝支付 TZFB
     */
    public static int TBNK ;
    public static int TCSH ;
    public static int TVIP ;
    public static int TWWX ;
    public static int TZFB ;
    public static String UPDATE_DOWNLOAD;
}

