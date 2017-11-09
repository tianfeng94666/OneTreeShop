package onetreeshopsapp.com.onetreeshops.bean;

import java.util.List;

/**
 * Created by fiona on 2016/10/25.
 */
public class SortProductResult {
    /**
     * jsonrpc : 2.0
     * id : 1
     * result : {"info":"","res":[{"store_name":"顶尖电子秤","product_id":83,"store_id":3,"price":10,"image":"/pos/binary/image?model=product.template&id=83&field=image","uom":"Dozen(s)","name":"er"},{"store_name":"顶尖电子秤","product_id":81,"store_id":3,"price":188,"image":"/pos/binary/image?model=product.template&id=81&field=image","uom":"袋","name":"田氏鸡翅"}],"flag":true}
     */

    private String jsonrpc;
    private String id;
    /**
     * info :
     * res : [{"store_name":"顶尖电子秤","product_id":83,"store_id":3,"price":10,"image":"/pos/binary/image?model=product.template&id=83&field=image","uom":"Dozen(s)","name":"er"},{"store_name":"顶尖电子秤","product_id":81,"store_id":3,"price":188,"image":"/pos/binary/image?model=product.template&id=81&field=image","uom":"袋","name":"田氏鸡翅"}]
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
         * store_name : 顶尖电子秤
         * product_id : 83
         * store_id : 3
         * price : 10.0
         * image : /pos/binary/image?model=product.template&id=83&field=image
         * uom : Dozen(s)
         * name : er
         */

        private List<ProductInfo> res;

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

        public List<ProductInfo> getRes() {
            return res;
        }

        public void setRes(List<ProductInfo> res) {
            this.res = res;
        }


    }
}
