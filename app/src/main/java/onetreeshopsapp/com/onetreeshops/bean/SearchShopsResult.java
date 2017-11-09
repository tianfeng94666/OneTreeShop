package onetreeshopsapp.com.onetreeshops.bean;

import java.util.List;

/**
 * Created by fiona on 2016/10/25.
 */
public class SearchShopsResult {
    /**
     * jsonrpc : 2.0
     * id : 1
     * result : {"info":"","res":[{"store_name":"测试门店","image":"/pos/binary/image?model=multiple.store&id=1&field=image","product_num":76,"store_id":1},{"store_name":"测试门店2","image":"/pos/binary/image?model=multiple.store&id=5&field=image","product_num":0,"store_id":5}],"flag":true}
     */

    private String jsonrpc;
    private String id;
    /**
     * info :
     * res : [{"store_name":"测试门店","image":"/pos/binary/image?model=multiple.store&id=1&field=image","product_num":76,"store_id":1},{"store_name":"测试门店2","image":"/pos/binary/image?model=multiple.store&id=5&field=image","product_num":0,"store_id":5}]
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

    public static class ResultBean {
        private String info;
        private boolean flag;
        /**
         * store_name : 测试门店
         * image : /pos/binary/image?model=multiple.store&id=1&field=image
         * product_num : 76
         * store_id : 1
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

        public static class ResBean {
            private String store_name;
            private String image;
            private int product_num;
            private int store_id;

            public String getStore_name() {
                return store_name;
            }

            public void setStore_name(String store_name) {
                this.store_name = store_name;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public int getProduct_num() {
                return product_num;
            }

            public void setProduct_num(int product_num) {
                this.product_num = product_num;
            }

            public int getStore_id() {
                return store_id;
            }

            public void setStore_id(int store_id) {
                this.store_id = store_id;
            }
        }
    }
}
