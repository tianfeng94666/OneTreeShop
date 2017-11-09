package onetreeshopsapp.com.onetreeshops.base;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by admin on 2016-05-11.
 */
public class ProductVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int id;
    private String name; // 商品名
    private String list_price; // 单价
    private String price;
    private Object public_categ_id; // 分类ID及名称, 使用时转换成String[]
    private Object[] taxes_id;
    private String ean13;
    private String default_code;
    private boolean to_weight;
    private Object[] uom_id; // 单位ID及名称
    private String uos_id;
    private String uos_coeff;
    private String mes_type;
    private String description_sale;
    private String short_code;
    private String description;
    private String standard_price;
    private String sales_quantity;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getList_price() {
        return list_price;
    }

    public void setList_price(String list_price) {
        this.list_price = list_price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Object getPublic_categ_id() {
        return public_categ_id;
    }

    public void setPublic_categ_id(Object public_categ_id) {
        this.public_categ_id = public_categ_id;
    }

    public Object[] getTaxes_id() {
        return taxes_id;
    }

    public void setTaxes_id(Object[] taxes_id) {
        this.taxes_id = taxes_id;
    }

    public String getEan13() {
        return ean13;
    }

    public void setEan13(String ean13) {
        this.ean13 = ean13;
    }

    public String getDefault_code() {
        return default_code;
    }

    public void setDefault_code(String default_code) {
        this.default_code = default_code;
    }

    public boolean getTo_weight() {
        return to_weight;
    }

    public void setTo_weight(boolean to_weight) {
        this.to_weight = to_weight;
    }

    public Object[] getUom_id() {
        return uom_id;
    }

    public void setUom_id(Object[] uom_id) {
        this.uom_id = uom_id;
    }

    public String getUos_id() {
        return uos_id;
    }

    public void setUos_id(String uos_id) {
        this.uos_id = uos_id;
    }

    public String getUos_coeff() {
        return uos_coeff;
    }

    public void setUos_coeff(String uos_coeff) {
        this.uos_coeff = uos_coeff;
    }

    public String getMes_type() {
        return mes_type;
    }

    public void setMes_type(String mes_type) {
        this.mes_type = mes_type;
    }

    public String getDescription_sale() {
        return description_sale;
    }

    public void setDescription_sale(String description_sale) {
        this.description_sale = description_sale;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStandard_price() {
        return standard_price;
    }

    public void setStandard_price(String standard_price) {
        this.standard_price = standard_price;
    }

    public String getSales_quantity() {
        return sales_quantity;
    }
    public String getShort_code() {
        return short_code;
    }

    public void setShort_code(String short_code) {
        this.short_code = short_code;
    }

    public void setSales_quantity(String sales_quantity) {
        this.sales_quantity = sales_quantity;
    }

    public static class EntityProductVO implements Serializable {
        private static final long serialVersionUID = 1L;

        private String id;// 请求ID

        private String jsonrpc;// jsonrpc版本

        private Result result;// 返回对象

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

        public Result getResult() {
            return result;
        }

        public void setResult(Result result) {
            this.result = result;
        }

    }

    public class Result implements Serializable {
        private static final long serialVersionUID = 1L;

        private ArrayList<ProductVO> records;

        public ArrayList<ProductVO> getRecords() {
            return records;
        }

        public void setRecords(ArrayList<ProductVO> records) {
            this.records = records;
        }

    }
}
