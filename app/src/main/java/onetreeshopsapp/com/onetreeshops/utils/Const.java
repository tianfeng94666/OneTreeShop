package onetreeshopsapp.com.onetreeshops.utils;

/**
 * @author MY
 */
public interface Const {
    /*--------------------------app相关-------------------------------------*/
    /** 软件更新- 检查APK版本号是否有更新 */
    public static String UPDATE_SELECT = "/pos/installation_package_detection";
    public static final int UPDATE_SELECT_CODE = 0x0011;
    /** 软件更新- 下载APK */
    public static String UPDATE_DOWNLOAD = "/market/app_download";
    public String SP_FILENAME = "app-release.apk"; // 下载文件名

    public String SP_USERNAME = "USERNAME"; // 用户名

    // 登陆
    public static String URL_LOGIN = "/app/login";

    public static final int loginCode = 1;


    // 选择数据库
    public static String URL_DB_SELECTOR = "/web?db=";

    /**
     * 获取所有门店信息
     */
    public static String SHOPS_DATA = "/market/store";

    /**
     * 获取单独某个门店信息
     */
    public static String SINGLE_SHOPS_DATA = "/pos/get_store_score";

    /**
     * 获取门店的产品
     */
    public static String SHOPS_PRODUCT = "/market/product";
    /** 注册界面 - 请求验证码 */
    public static String REG_CAPTCHA ="/market/validatecode";
    public static final int CAPTCHA_CODE = 0x0005;
    /** 注册界面 - 注册 */
    public static String REGISTER ="/app/signup";
    public static final int REGISTER_CODE = 0x0006;
    /** 修改密码 */
    public static String USER_PWD_MODIFY ="/market/change_pwd";
    public static final int USER_PWD_MODIFY_CODE = 0x0009;
    /** 绑定手机号 */
    public static String BINDING_PHONE ="/market/change_login";
    public static final int BINDING_PHONE_CODE = 0x0010;
    /** 查询用户信息 */
    public static String USER_INFO ="/market/search_member";
    public static final int USER_INFO_CODE = 0x0007;
    /** 修改用户信息 */
    public static String USER_INFO_MODIFY ="/market/change_member";
    public static final int USER_INFO_MODIFY_CODE = 0x0008;
    /** 修改用户头像 */
    public static String USER_IMAGE ="/market/set_member_pic";
    public static final int USER_IMAGE_CODE = 0x0016;
    /**
     * 查询我的收获地址
     * *
     */
    public static String MYADDRES_URL = "/market/address";
    public static final int GETMYADDRES_CODE = 0x0001;

    /**
     * 新加我的收获地址
     * *
     */
    public static final int ADDMYADDRES_CODE = 0x0002;

    /**
     * 删除我的收获地址
     * *
     */
    public static final int DELECTMYADDRES_CODE = 0x0003;

    /**
     * 新加我的收获地址
     * *
     */
    public static final int EDITMYADDRES_CODE = 0x0004;
    /**
     * 交班记录
     */
    public static String SHIFT_RECORD = "/pos/handover_log";
    /** 查询订单 */
    public static String SEARCH_ALLORDERS ="/pos/get_order_information";
    public static final int SEARCH_ALLORDERS_CODE = 0x0012;
    /** 取消订单 */
    public static String CANCEL_ORDERS ="/pos/cancel_app_order";
    public static final int CANCEL_ORDERS_CODE = 0x0013;
    /** 删除订单 */
    public static String DELECT_ORDERS ="/pos/delete_app_order";
    public static final int DELECT_ORDERS_CODE = 0x0014;
    /** 意见反馈 */
    public static String SUGGEST_URL ="/pos/app_feedback_information";
    public static final int  SUGGEST_CODE = 0x0015;
    /** 评价 */
    public static String EVALUATE_URL ="/market/store_score";
    public static final int  EVALUATE_CODE = 0x0016;

    /*
     * 提交订单
     */
    public static String COMMIT_ORDER = "/pos/create_order_information";
    /**
     * 搜索产品或门店
     */
    public static String  SEARCH_CODE = "/market/search_pro_sto";
    /**
     * 搜索分类
     */
    public static String  SEARCH_SORT_CODE = "/market/product_category";
    /**
     * 微信支付
     */
    public static String  WE_PAY = "/shop_wechat/payment/app/order";

    /**
     * 创建购物车
     */
    public static String  CREATE_SHOPPINGCAR = "/pos/create_shopping_cart";
    /**
     * 获取购物车中数据
     */
    public static String  GET_SHOPPINGCAR = "/pos/get_shopping_cart";

    /**
     * 删除购物车中数据
     */
    public static String  DELETE_SHOPPINGCAR_PRODUCT = "/pos/delete_shopping_cart";
    /**
     * 门店中产品搜索
     */
    public static String SHOP_PRODUCT_SEARCH ="/marker/search_in_store";
    /**
     * 查询客户对商家评价
     */
    public static String SHOP_EVALUATE ="/pos/store_estimate_back";
    /**
     * 退单
     */
    public static String ORDER_REFUND ="/shop_wechat/payment/app/refund";
}
