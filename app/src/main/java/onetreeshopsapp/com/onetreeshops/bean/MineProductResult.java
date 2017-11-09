package onetreeshopsapp.com.onetreeshops.bean;

import java.util.List;

/**
 * Created by Admin on 2016/6/23.
 */
public class MineProductResult {

    /**
     * jsonrpc : 2.0
     * id : 1
     * result : {"info":"","res":[{"uom_id":3,"public_categ_id":"","list_price":34,"product_id":7,"ean13":false,"short_code":"ere","standard_price":39,"product_category":null,"default_code":false,"to_weight":true,"description_sale":false,"product_name":"ere","product_oum":"kg","description":false},{"uom_id":5,"public_categ_id":"","list_price":45,"product_id":6,"ean13":false,"short_code":"rr","standard_price":0,"product_category":null,"default_code":false,"to_weight":true,"description_sale":false,"product_name":"rr","product_oum":"Hour(s)","description":false},{"uom_id":5,"public_categ_id":"","list_price":1,"product_id":9,"ean13":false,"short_code":"rt","standard_price":0,"product_category":null,"default_code":false,"to_weight":true,"description_sale":false,"product_name":"rt","product_oum":"Hour(s)","description":false},{"uom_id":5,"public_categ_id":"","list_price":56,"product_id":14,"ean13":false,"short_code":"tfc","standard_price":34,"product_category":null,"default_code":false,"to_weight":true,"description_sale":false,"product_name":"tfc","product_oum":"Hour(s)","description":false},{"uom_id":21,"public_categ_id":"","list_price":1,"product_id":10,"ean13":false,"short_code":"hj","standard_price":34,"product_category":null,"default_code":false,"to_weight":false,"description_sale":false,"product_name":"花椒","product_oum":"袋","description":false},{"uom_id":21,"public_categ_id":"","list_price":12,"product_id":4,"ean13":false,"short_code":"jzw","standard_price":0,"product_category":null,"default_code":false,"to_weight":true,"description_sale":false,"product_name":"机智王","product_oum":"袋","description":false},{"uom_id":1,"public_categ_id":"","list_price":78,"product_id":11,"ean13":false,"short_code":"jc","standard_price":45,"product_category":null,"default_code":false,"to_weight":true,"description_sale":false,"product_name":"鸡翅","product_oum":"Unit(s)","description":false},{"uom_id":1,"public_categ_id":"","list_price":54,"product_id":8,"ean13":false,"short_code":"nd","standard_price":0,"product_category":null,"default_code":false,"to_weight":false,"description_sale":false,"product_name":"牛肚","product_oum":"Unit(s)","description":false},{"uom_id":3,"public_categ_id":"","list_price":35,"product_id":13,"ean13":false,"short_code":"nr","standard_price":25,"product_category":null,"default_code":false,"to_weight":true,"description_sale":false,"product_name":"牛肉","product_oum":"kg","description":false},{"uom_id":1,"public_categ_id":"","list_price":45,"product_id":16,"ean13":false,"short_code":"td","standard_price":12,"product_category":null,"default_code":false,"to_weight":false,"description_sale":false,"product_name":"土豆","product_oum":"Unit(s)","description":false},{"uom_id":5,"public_categ_id":"","list_price":34,"product_id":5,"ean13":false,"short_code":"xcm","standard_price":0,"product_category":null,"default_code":false,"to_weight":true,"description_sale":false,"product_name":"小草莓","product_oum":"Hour(s)","description":false},{"uom_id":21,"public_categ_id":"","list_price":10,"product_id":15,"ean13":false,"short_code":"y","standard_price":5,"product_category":null,"default_code":false,"to_weight":false,"description_sale":false,"product_name":"盐","product_oum":"袋","description":false},{"uom_id":3,"public_categ_id":"","list_price":30,"product_id":12,"ean13":false,"short_code":"yr","standard_price":20,"product_category":null,"default_code":false,"to_weight":true,"description_sale":false,"product_name":"羊肉","product_oum":"kg","description":false}],"flag":true}
     */

    private String jsonrpc;
    private String id;
    /**
     * info :
     * res : [{"uom_id":3,"public_categ_id":"","list_price":34,"product_id":7,"ean13":false,"short_code":"ere","standard_price":39,"product_category":null,"default_code":false,"to_weight":true,"description_sale":false,"product_name":"ere","product_oum":"kg","description":false},{"uom_id":5,"public_categ_id":"","list_price":45,"product_id":6,"ean13":false,"short_code":"rr","standard_price":0,"product_category":null,"default_code":false,"to_weight":true,"description_sale":false,"product_name":"rr","product_oum":"Hour(s)","description":false},{"uom_id":5,"public_categ_id":"","list_price":1,"product_id":9,"ean13":false,"short_code":"rt","standard_price":0,"product_category":null,"default_code":false,"to_weight":true,"description_sale":false,"product_name":"rt","product_oum":"Hour(s)","description":false},{"uom_id":5,"public_categ_id":"","list_price":56,"product_id":14,"ean13":false,"short_code":"tfc","standard_price":34,"product_category":null,"default_code":false,"to_weight":true,"description_sale":false,"product_name":"tfc","product_oum":"Hour(s)","description":false},{"uom_id":21,"public_categ_id":"","list_price":1,"product_id":10,"ean13":false,"short_code":"hj","standard_price":34,"product_category":null,"default_code":false,"to_weight":false,"description_sale":false,"product_name":"花椒","product_oum":"袋","description":false},{"uom_id":21,"public_categ_id":"","list_price":12,"product_id":4,"ean13":false,"short_code":"jzw","standard_price":0,"product_category":null,"default_code":false,"to_weight":true,"description_sale":false,"product_name":"机智王","product_oum":"袋","description":false},{"uom_id":1,"public_categ_id":"","list_price":78,"product_id":11,"ean13":false,"short_code":"jc","standard_price":45,"product_category":null,"default_code":false,"to_weight":true,"description_sale":false,"product_name":"鸡翅","product_oum":"Unit(s)","description":false},{"uom_id":1,"public_categ_id":"","list_price":54,"product_id":8,"ean13":false,"short_code":"nd","standard_price":0,"product_category":null,"default_code":false,"to_weight":false,"description_sale":false,"product_name":"牛肚","product_oum":"Unit(s)","description":false},{"uom_id":3,"public_categ_id":"","list_price":35,"product_id":13,"ean13":false,"short_code":"nr","standard_price":25,"product_category":null,"default_code":false,"to_weight":true,"description_sale":false,"product_name":"牛肉","product_oum":"kg","description":false},{"uom_id":1,"public_categ_id":"","list_price":45,"product_id":16,"ean13":false,"short_code":"td","standard_price":12,"product_category":null,"default_code":false,"to_weight":false,"description_sale":false,"product_name":"土豆","product_oum":"Unit(s)","description":false},{"uom_id":5,"public_categ_id":"","list_price":34,"product_id":5,"ean13":false,"short_code":"xcm","standard_price":0,"product_category":null,"default_code":false,"to_weight":true,"description_sale":false,"product_name":"小草莓","product_oum":"Hour(s)","description":false},{"uom_id":21,"public_categ_id":"","list_price":10,"product_id":15,"ean13":false,"short_code":"y","standard_price":5,"product_category":null,"default_code":false,"to_weight":false,"description_sale":false,"product_name":"盐","product_oum":"袋","description":false},{"uom_id":3,"public_categ_id":"","list_price":30,"product_id":12,"ean13":false,"short_code":"yr","standard_price":20,"product_category":null,"default_code":false,"to_weight":true,"description_sale":false,"product_name":"羊肉","product_oum":"kg","description":false}]
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
         * uom_id : 3
         * public_categ_id :
         * list_price : 34.0
         * product_id : 7
         * ean13 : false
         * short_code : ere
         * standard_price : 39.0
         * product_category : null
         * default_code : false
         * to_weight : true
         * description_sale : false
         * product_name : ere
         * product_oum : kg
         * description : false
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
            private int uom_id;
            private String public_categ_id;
            private double list_price;
            private int product_id;
            private boolean ean13;
            private String short_code;
            private double standard_price;
            private Object product_category;
            private boolean default_code;
            private boolean to_weight;
            private boolean description_sale;
            private String product_name;
            private String product_oum;
            private boolean description;
            private String pic_change;
            public String getPic_change() {
                return pic_change;
            }

            public void setPic_change(String pic_change) {
                this.pic_change = pic_change;
            }

            public int getUom_id() {
                return uom_id;
            }

            public void setUom_id(int uom_id) {
                this.uom_id = uom_id;
            }

            public String getPublic_categ_id() {
                return public_categ_id;
            }

            public void setPublic_categ_id(String public_categ_id) {
                this.public_categ_id = public_categ_id;
            }

            public double getList_price() {
                return list_price;
            }

            public void setList_price(double list_price) {
                this.list_price = list_price;
            }

            public int getProduct_id() {
                return product_id;
            }

            public void setProduct_id(int product_id) {
                this.product_id = product_id;
            }

            public boolean isEan13() {
                return ean13;
            }

            public void setEan13(boolean ean13) {
                this.ean13 = ean13;
            }

            public String getShort_code() {
                return short_code;
            }

            public void setShort_code(String short_code) {
                this.short_code = short_code;
            }

            public double getStandard_price() {
                return standard_price;
            }

            public void setStandard_price(double standard_price) {
                this.standard_price = standard_price;
            }

            public Object getProduct_category() {
                return product_category;
            }

            public void setProduct_category(Object product_category) {
                this.product_category = product_category;
            }

            public boolean isDefault_code() {
                return default_code;
            }

            public void setDefault_code(boolean default_code) {
                this.default_code = default_code;
            }

            public boolean isTo_weight() {
                return to_weight;
            }

            public void setTo_weight(boolean to_weight) {
                this.to_weight = to_weight;
            }

            public boolean isDescription_sale() {
                return description_sale;
            }

            public void setDescription_sale(boolean description_sale) {
                this.description_sale = description_sale;
            }

            public String getProduct_name() {
                return product_name;
            }

            public void setProduct_name(String product_name) {
                this.product_name = product_name;
            }

            public String getProduct_oum() {
                return product_oum;
            }

            public void setProduct_oum(String product_oum) {
                this.product_oum = product_oum;
            }

            public boolean isDescription() {
                return description;
            }

            public void setDescription(boolean description) {
                this.description = description;
            }
        }
    }
}
