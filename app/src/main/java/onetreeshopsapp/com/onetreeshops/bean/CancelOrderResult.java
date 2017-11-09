package onetreeshopsapp.com.onetreeshops.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fiona on 2016/10/21.
 */
public class CancelOrderResult implements Serializable{

    /**
     * jsonrpc : 2.0
     * id : 1
     * result : {"info":"","res":["2016102108520949912","2016102108520949913"],"flag":true}
     */

    private String jsonrpc;
    private String id;
    /**
     * info :
     * res : ["2016102108520949912","2016102108520949913"]
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

    public static class ResultBean implements Serializable{
        private String info;
        private boolean flag;
        private List<String> res;

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

        public List<String> getRes() {
            return res;
        }

        public void setRes(List<String> res) {
            this.res = res;
        }
    }
}
