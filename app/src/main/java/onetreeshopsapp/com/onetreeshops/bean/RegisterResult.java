package onetreeshopsapp.com.onetreeshops.bean;

/**
 * Created by Admin on 2016/6/17.
 */
public class RegisterResult {

    /**
     * jsonrpc : 2.0
     * id : 1
     * result : {"info":"signup success!","res":true}
     */

    private String jsonrpc;
    private String id;
    /**
     * info : signup success!
     * res : true
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

    public static class ResultBean {
        private String info;
        private boolean res;

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public boolean isRes() {
            return res;
        }

        public void setRes(boolean res) {
            this.res = res;
        }
    }
}
