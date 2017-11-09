package onetreeshopsapp.com.onetreeshops.bean;

/**
 * Created by fiona on 2016/10/10.
 */
public class EventProduct {
    String  eventname ;
    ProductInfo data;

    public EventProduct(String eventname,ProductInfo data){
        this.eventname =eventname;
        this.data =data;
    }

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public ProductInfo getData() {
        return data;
    }

    public void setData(ProductInfo data) {
        this.data = data;
    }
}
