package onetreeshopsapp.com.onetreeshops.bean;

import java.io.Serializable;

/**
 * Created by ERP on 2016/10/24.
 */
public class OrdersProductBean2 implements Serializable {
    private String name;//商品名
    private String image;//商品图片
    private double price_subtotal;//总价
    private int qty;//数量
    private double price_unit;//商品单价
    private String uom;//单位

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice_subtotal() {
        return price_subtotal;
    }

    public void setPrice_subtotal(double price_subtotal) {
        this.price_subtotal = price_subtotal;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice_unit() {
        return price_unit;
    }

    public void setPrice_unit(double price_unit) {
        this.price_unit = price_unit;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }
}
