package onetreeshopsapp.com.onetreeshops.bean;

import java.util.List;

/**
 * Created by fiona on 2016/10/31.
 */

public class CreateShoppingCar {
    List<Shopping> shopping;

    public List<Shopping> getShopping() {
        return shopping;
    }

    public void setShopping(List<Shopping> shopping) {
        this.shopping = shopping;
    }

    public static class  Shopping{
        int pos_session_id;
        List<ProductInfo> lines;

        public int getPos_session_id() {
            return pos_session_id;
        }

        public void setPos_session_id(int pos_session_id) {
            this.pos_session_id = pos_session_id;
        }

        public List<ProductInfo> getLines() {
            return lines;
        }

        public void setLines(List<ProductInfo> lines) {
            this.lines = lines;
        }
    }

}
