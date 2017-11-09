package onetreeshopsapp.com.onetreeshops.bean;

/**
 * Created by Admin on 2016/6/22.
 */
public class NewResult {

    /**
     * jsonrpc : 2.0
     * id : 1
     * result : {"info":"","res":{"id":177},"flag":true}
     */

    private String jsonrpc;
    private String id;
    /**
     * info :
     * res : {"id":177}
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
        /**
         * id : 177
         */

        private ResBean res;
        private boolean flag;

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public ResBean getRes() {
            return res;
        }

        public void setRes(ResBean res) {
            this.res = res;
        }

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public static class ResBean {
            private int id;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }
    }
}
