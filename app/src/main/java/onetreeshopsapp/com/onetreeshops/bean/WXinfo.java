package onetreeshopsapp.com.onetreeshops.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 田丰 on 2016/12/1.
 */

public class WXinfo {


    /**
     * jsonrpc : 2.0
     * id : 1
     * result : {"return_data":{"package":"Sign=WXPay","timestamp":1482220166,"sign":"1E4AAD01AB564CEF0B27FC7A891CA531","partnerid":"1404806302","appid":"wxf5c5898d9588568b","prepayid":"wx20161220154926c25d9f5f530533445760","noncestr":"9aftq4y4h5lbgobst9uljrqju5lnyrdu"},"return_code":"SUCCESS","return_msg":""}
     */

    private String jsonrpc;
    private String id;
    private ResultBean result;

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * return_data : {"package":"Sign=WXPay","timestamp":1482220166,"sign":"1E4AAD01AB564CEF0B27FC7A891CA531","partnerid":"1404806302","appid":"wxf5c5898d9588568b","prepayid":"wx20161220154926c25d9f5f530533445760","noncestr":"9aftq4y4h5lbgobst9uljrqju5lnyrdu"}
         * return_code : SUCCESS
         * return_msg :
         */

        private ReturnDataBean return_data;
        private String return_code;
        private String return_msg;

        public ReturnDataBean getReturn_data() {
            return return_data;
        }

        public void setReturn_data(ReturnDataBean return_data) {
            this.return_data = return_data;
        }

        public String getReturn_code() {
            return return_code;
        }

        public void setReturn_code(String return_code) {
            this.return_code = return_code;
        }

        public String getReturn_msg() {
            return return_msg;
        }

        public void setReturn_msg(String return_msg) {
            this.return_msg = return_msg;
        }

        public static class ReturnDataBean {
            /**
             * package : Sign=WXPay
             * timestamp : 1482220166
             * sign : 1E4AAD01AB564CEF0B27FC7A891CA531
             * partnerid : 1404806302
             * appid : wxf5c5898d9588568b
             * prepayid : wx20161220154926c25d9f5f530533445760
             * noncestr : 9aftq4y4h5lbgobst9uljrqju5lnyrdu
             */

            @SerializedName("package")
            private String packageX;
            private int timestamp;
            private String sign;
            private String partnerid;
            private String appid;
            private String prepayid;
            private String noncestr;

            public String getPackageX() {
                return packageX;
            }

            public void setPackageX(String packageX) {
                this.packageX = packageX;
            }

            public int getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(int timestamp) {
                this.timestamp = timestamp;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public String getPartnerid() {
                return partnerid;
            }

            public void setPartnerid(String partnerid) {
                this.partnerid = partnerid;
            }

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getPrepayid() {
                return prepayid;
            }

            public void setPrepayid(String prepayid) {
                this.prepayid = prepayid;
            }

            public String getNoncestr() {
                return noncestr;
            }

            public void setNoncestr(String noncestr) {
                this.noncestr = noncestr;
            }
        }
    }
}
