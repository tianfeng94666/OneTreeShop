package onetreeshopsapp.com.onetreeshops.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fiona on 2016/10/9.
 */
public class GetShopsDataResult implements Serializable {

    /**
     * jsonrpc : 2.0
     * id : 1
     * result : {"info":"","res":[{"store_name":"测试门店","distance_to_store":102,"store_id":1,"order_num":59,"store_public":false,"store_pic":false},{"store_name":"封云森的商店","distance_to_store":false,"store_id":2,"order_num":0,"store_public":false,"store_pic":false},{"store_name":"顶尖电子秤","distance_to_store":8488,"store_id":3,"order_num":19,"store_public":false,"store_pic":false},{"store_name":"测试门店2","distance_to_store":false,"store_id":5,"order_num":0,"store_public":false,"store_pic":false}],"flag":true}
     */

    private String jsonrpc;
    private String id;
    /**
     * info :
     * res : [{"store_name":"测试门店","distance_to_store":102,"store_id":1,"order_num":59,"store_public":false,"store_pic":false},{"store_name":"封云森的商店","distance_to_store":false,"store_id":2,"order_num":0,"store_public":false,"store_pic":false},{"store_name":"顶尖电子秤","distance_to_store":8488,"store_id":3,"order_num":19,"store_public":false,"store_pic":false},{"store_name":"测试门店2","distance_to_store":false,"store_id":5,"order_num":0,"store_public":false,"store_pic":false}]
     * flag : true
     */

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

    public static class ResultBean implements Serializable{
        private String info;
        private boolean flag;
        /**
         * store_name : 测试门店
         * distance_to_store : 102
         * store_id : 1
         * order_num : 59
         * store_public : false
         * store_pic : false
         */

        private List<ResBean> res;

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public List<ResBean> getRes() {
            return res;
        }

        public void setRes(List<ResBean> res) {
            this.res = res;
        }

        public static class ResBean implements Serializable{
            private String store_name;
            private String store_pic;
            private int distance_to_store;
            private int store_id;
            private int order_num;
            private String store_public;
            private float score;
            private float pro_score;
            private float service_score;

            public float getPro_score() {
                return pro_score;
            }

            public void setPro_score(float pro_score) {
                this.pro_score = pro_score;
            }

            public float getService_score() {
                return service_score;
            }

            public void setService_score(float service_score) {
                this.service_score = service_score;
            }

            public float getScore() {
                return score;
            }

            public void setScore(float score) {
                this.score = score;
            }

            public String getStore_pic() {
                return store_pic;
            }

            public void setStore_pic(String store_pic) {
                this.store_pic = store_pic;
            }

            public String getStore_name() {
                return store_name;
            }

            public void setStore_name(String store_name) {
                this.store_name = store_name;
            }

            public int getDistance_to_store() {
                return distance_to_store;
            }

            public void setDistance_to_store(int distance_to_store) {
                this.distance_to_store = distance_to_store;
            }

            public int getStore_id() {
                return store_id;
            }

            public void setStore_id(int store_id) {
                this.store_id = store_id;
            }

            public int getOrder_num() {
                return order_num;
            }

            public void setOrder_num(int order_num) {
                this.order_num = order_num;
            }

            public String getStore_public() {
                return store_public;
            }

            public void setStore_public(String store_public) {
                this.store_public = store_public;
            }
        }
    }
}
