package onetreeshopsapp.com.onetreeshops.bean;

/**
 * Created by fiona on 2016/10/31.
 */

public class CreateShoppingcarResult {
    /**
     * jsonrpc : 2.0
     * id : 1
     * result : {"info":"","flag":true}
     */

    private String jsonrpc;
    private String id;
    /**
     * info :
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

    public static class ResultBean {
        private String info;
        private boolean flag;

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
    }
}
