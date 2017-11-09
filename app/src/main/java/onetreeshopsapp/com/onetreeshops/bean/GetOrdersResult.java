package onetreeshopsapp.com.onetreeshops.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ERP on 2016/10/24.
 */
public class GetOrdersResult implements Serializable{
    private String id;
    private String jsonrpc;
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

    public static class ResultBean implements Serializable{
        private boolean flag;
        private String info;
        private int total;
        private List<ResBean> res;

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
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

        public static class ResBean implements Serializable{
            private int store_id;
            private String store_name;
            private String pay_state;
            private List<OrdersProductBean> lines;
            private double amount_total;
            private double transport_cost;
            private double order_amount;
            private String pos_reference;
            private String create_date;
            private String consignee;
            private String telephone;
            private String address;
            private boolean isselect;
            private int remainder_time;

            public int getStore_id() {
                return store_id;
            }

            public void setStore_id(int store_id) {
                this.store_id = store_id;
            }

            public boolean isselect() {
                return isselect;
            }

            public void setIsselect(boolean isselect) {
                this.isselect = isselect;
            }

            public List<OrdersProductBean> getLines() {
                return lines;
            }

            public void setLines(List<OrdersProductBean> lines) {
                this.lines = lines;
            }

            public String getConsignee() {
                return consignee;
            }

            public void setConsignee(String consignee) {
                this.consignee = consignee;
            }

            public String getTelephone() {
                return telephone;
            }

            public void setTelephone(String telephone) {
                this.telephone = telephone;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getStore_name() {
                return store_name;
            }

            public void setStore_name(String store_name) {
                this.store_name = store_name;
            }

            public String getPay_state() {
                return pay_state;
            }

            public void setPay_state(String pay_state) {
                this.pay_state = pay_state;
            }

            public double getAmount_total() {
                return amount_total;
            }

            public void setAmount_total(double amount_total) {
                this.amount_total = amount_total;
            }

            public double getTransport_cost() {
                return transport_cost;
            }

            public void setTransport_cost(double transport_cost) {
                this.transport_cost = transport_cost;
            }

            public double getOrder_amount() {
                return order_amount;
            }

            public void setOrder_amount(double order_amount) {
                this.order_amount = order_amount;
            }

            public String getPos_reference() {
                return pos_reference;
            }

            public void setPos_reference(String pos_reference) {
                this.pos_reference = pos_reference;
            }

            public String getCreate_date() {
                return create_date;
            }

            public void setCreate_date(String create_date) {
                this.create_date = create_date;
            }
            public static class OrdersProductBean implements Serializable {
                private String name;//商品名
                private String image;//商品图片
                private double price_subtotal;//总价
                private int amount;//数量
                private double price;//商品单价
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

                public int getAmount() {
                    return amount;
                }

                public void setAmount(int amount) {
                    this.amount = amount;
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

            public int getRemainder_time() {
                return remainder_time;
            }

            public void setRemainder_time(int remainder_time) {
                this.remainder_time = remainder_time;
            }
        }


    }



}
