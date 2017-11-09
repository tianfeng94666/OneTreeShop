package onetreeshopsapp.com.onetreeshops.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fiona on 2016/10/18.
 */
public class Orders implements Serializable{
    /**
     * address : 深圳南山区……
     * amount_total : 105
     * consignee : 张三
     * lines : [{"price_unit":30,"product_id":1,"qty":5,"store_id":"888","stroes_name":"一棵树"}]
     * order_amount : 100
     * pay_state : 已付款
     * pay_way : 会员
     * pos_session_id : session_id
     * store_id : 888
     * stroes_name : 一棵树
     * telephone : 13588888888
     * transport_cost : 5
     */

    private List<OrdersAppBean> orders_app;

    public List<OrdersAppBean> getOrders_app() {
        return orders_app;
    }

    public void setOrders_app(List<OrdersAppBean> orders_app) {
        this.orders_app = orders_app;
    }

    public static class OrdersAppBean implements Serializable{
        private int amount_total;
        private int order_amount;
        private String pay_state;
        private String pay_way;
        private String pos_session_id;
        private String store_id;
        private String stroes_name;
        private String telephone;
        private int transport_cost;
        private int address_id;
        public int getAddress_id() {
            return address_id;
        }
        public void setAddress_id(int address_id) {
            this.address_id = address_id;
        }
        /**
         * price_unit : 30
         * product_id : 1
         * qty : 5.0
         * store_id : 888
         * stroes_name : 一棵树
         */

        private List<OrderProduct> lines;



        public int getAmount_total() {
            return amount_total;
        }

        public void setAmount_total(int amount_total) {
            this.amount_total = amount_total;
        }


        public int getOrder_amount() {
            return order_amount;
        }

        public void setOrder_amount(int order_amount) {
            this.order_amount = order_amount;
        }

        public String getPay_state() {
            return pay_state;
        }

        public void setPay_state(String pay_state) {
            this.pay_state = pay_state;
        }

        public String getPay_way() {
            return pay_way;
        }

        public void setPay_way(String pay_way) {
            this.pay_way = pay_way;
        }

        public String getPos_session_id() {
            return pos_session_id;
        }

        public void setPos_session_id(String pos_session_id) {
            this.pos_session_id = pos_session_id;
        }

        public String getStore_id() {
            return store_id;
        }

        public void setStore_id(String store_id) {
            this.store_id = store_id;
        }

        public String getStroes_name() {
            return stroes_name;
        }

        public void setStroes_name(String stroes_name) {
            this.stroes_name = stroes_name;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public int getTransport_cost() {
            return transport_cost;
        }

        public void setTransport_cost(int transport_cost) {
            this.transport_cost = transport_cost;
        }

        public List<OrderProduct> getLines() {
            return lines;
        }

        public void setLines(List<OrderProduct> lines) {
            this.lines = lines;
        }


    }
}
