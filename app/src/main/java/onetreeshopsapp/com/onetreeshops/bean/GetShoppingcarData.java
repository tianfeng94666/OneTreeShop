package onetreeshopsapp.com.onetreeshops.bean;

import java.util.List;

/**
 * Created by fiona on 2016/10/31.
 */

public class GetShoppingcarData {
    /**
     * jsonrpc : 2.0
     * id : 1
     * result : {"info":"","res":{"lines":[{"store_name":"顶尖电子秤","amount":1,"product_id":79,"store_id":"3","price":45,"image":"/pos/binary/image?model=product.template&id=79&field=image","uom":"kg","name":"大鸡腿"},{"store_name":"顶尖电子秤","amount":1,"product_id":72,"store_id":"3","price":25,"image":"/pos/binary/image?model=product.template&id=72&field=image","uom":"瓶","name":"原味奶茶"},{"store_name":"顶尖电子秤","amount":1,"product_id":67,"store_id":"3","price":8,"image":"/pos/binary/image?model=product.template&id=67&field=image","uom":"瓶","name":"伊利纯牛奶(250毫升)"},{"store_name":"顶尖电子秤","amount":1,"product_id":83,"store_id":"3","price":10,"image":"/pos/binary/image?model=product.template&id=83&field=image","uom":"Dozen(s)","name":"er"}]},"flag":true}
     */

    private String jsonrpc;
    private String id;
    /**
     * info :
     * res : {"lines":[{"store_name":"顶尖电子秤","amount":1,"product_id":79,"store_id":"3","price":45,"image":"/pos/binary/image?model=product.template&id=79&field=image","uom":"kg","name":"大鸡腿"},{"store_name":"顶尖电子秤","amount":1,"product_id":72,"store_id":"3","price":25,"image":"/pos/binary/image?model=product.template&id=72&field=image","uom":"瓶","name":"原味奶茶"},{"store_name":"顶尖电子秤","amount":1,"product_id":67,"store_id":"3","price":8,"image":"/pos/binary/image?model=product.template&id=67&field=image","uom":"瓶","name":"伊利纯牛奶(250毫升)"},{"store_name":"顶尖电子秤","amount":1,"product_id":83,"store_id":"3","price":10,"image":"/pos/binary/image?model=product.template&id=83&field=image","uom":"Dozen(s)","name":"er"}]}
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
             * store_name : 顶尖电子秤
             * amount : 1.0
             * product_id : 79
             * store_id : 3
             * price : 45.0
             * image : /pos/binary/image?model=product.template&id=79&field=image
             * uom : kg
             * name : 大鸡腿
             */

            private List<ProductInfo> lines;

            public List<ProductInfo> getLines() {
                return lines;
            }

            public void setLines(List<ProductInfo> lines) {
                this.lines = lines;
            }


        }
    }
}
