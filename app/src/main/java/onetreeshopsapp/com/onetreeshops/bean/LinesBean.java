package onetreeshopsapp.com.onetreeshops.bean;

import java.io.Serializable;

/**
 * Created by admin on 2016-05-24.
 */
public class LinesBean implements Serializable {
    private double price_unit;
    private int product_id;
    private double qty;
    private double discount;
    private String name;
    private String standard_price;

    public String getStandard_price() {
        return standard_price;
    }

    public void setStandard_price(String standard_price) {
        this.standard_price = standard_price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice_unit() {
        return price_unit;
    }

    public void setPrice_unit(double price_unit) {
        this.price_unit = price_unit;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }
}