package onetreeshopsapp.com.onetreeshops.bean;

import java.io.Serializable;

/**
 * Created by fiona on 2016/11/18.
 */

public class OrderProduct implements Serializable{
    /**
     * amount : 2
     * image : /pos/binary/image?model=product.product&id=72&field=image&pic_change=0
     * name : 原味奶茶
     * price : 25
     * product_id : 72
     * uom : 瓶
     */

    private int amount;
    private String image;
    private String name;
    private double price;
    private int product_id;
    private String uom;
    private String store_name;

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }
}
