package onetreeshopsapp.com.onetreeshops.bean;

import java.util.List;

/**
 * Created by fiona on 2016/10/25.
 */
public class SearchSortResult {

    /**
     * id : 1
     * jsonrpc : 2.0
     * result : {"flag":true,"info":"","res":[{"category_id":0,"product_category":"所有"},{"category_id":22,"product_category":"糕点/面包"},{"category_id":17,"product_category":"水产tt"},{"category_id":18,"product_category":"水果"},{"category_id":23,"product_category":"肉类"},{"category_id":34,"product_category":"蔬菜"},{"category_id":20,"product_category":"调料"},{"category_id":21,"product_category":"饮料"}]}
     */

    private String id;
    private String jsonrpc;
    /**
     * flag : true
     * info :
     * res : [{"category_id":0,"product_category":"所有"},{"category_id":22,"product_category":"糕点/面包"},{"category_id":17,"product_category":"水产tt"},{"category_id":18,"product_category":"水果"},{"category_id":23,"product_category":"肉类"},{"category_id":34,"product_category":"蔬菜"},{"category_id":20,"product_category":"调料"},{"category_id":21,"product_category":"饮料"}]
     */

    private ResultBean result;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private boolean flag;
        private String info;
        /**
         * category_id : 0
         * product_category : 所有
         */

        private List<ResBean> res;

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public List<ResBean> getRes() {
            return res;
        }

        public void setRes(List<ResBean> res) {
            this.res = res;
        }

        public static class ResBean {
            private int category_id;
            private String product_category;
            private int amount;

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public int getCategory_id() {
                return category_id;
            }

            public void setCategory_id(int category_id) {
                this.category_id = category_id;
            }

            public String getProduct_category() {
                return product_category;
            }

            public void setProduct_category(String product_category) {
                this.product_category = product_category;
            }
        }
    }
}
