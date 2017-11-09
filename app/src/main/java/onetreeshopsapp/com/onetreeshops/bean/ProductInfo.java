package onetreeshopsapp.com.onetreeshops.bean;

import java.io.Serializable;

/**
 * Created by fiona on 2016/10/25.
 */
public class ProductInfo implements Serializable {

    private String image;
    private String name;
    private double price;
    private String uom;
    private int amount;
    private int store_id;
    private String store_name;
    private int product_id;
    private boolean isSelect;
    private int category_id;
    private double saled_num;

    public double getSaled_num() {
        return saled_num;
    }

    public void setSaled_num(double saled_num) {
        this.saled_num = saled_num;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

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

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

}
