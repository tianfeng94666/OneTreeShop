package onetreeshopsapp.com.onetreeshops.bean;

import java.io.Serializable;

/**
 * Created by fiona on 2016/10/21.
 */
public class CreateOrderResult implements Serializable{

    /**
     * jsonrpc : 2.0
     * id : 1
     * result : {"info":"","res":{"payment_way_number":"2017010903160951233"},"flag":true}
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
         * info :
         * res : {"payment_way_number":"2017010903160951233"}
         * flag : true
         */

        private String info;
        private ResBean res;
        private boolean flag;

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public ResBean getRes() {
            return res;
        }

        public void setRes(ResBean res) {
            this.res = res;
        }

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public static class ResBean {
            /**
             * payment_way_number : 2017010903160951233
             */

            private String payment_way_number;

            public String getPayment_way_number() {
                return payment_way_number;
            }

            public void setPayment_way_number(String payment_way_number) {
                this.payment_way_number = payment_way_number;
            }
        }
    }
}
