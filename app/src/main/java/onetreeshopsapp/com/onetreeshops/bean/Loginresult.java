package onetreeshopsapp.com.onetreeshops.bean;

/**
 * Created by admin on 2016-05-13.
 */
public class Loginresult {

    /**
     * id : 1
     * jsonrpc : 2.0
     * result : {"flag":true,"info":"Login Success","res":{"session_id":1}}
     */

    private String id;
    private String jsonrpc;
    /**
     * flag : true
     * info : Login Success
     * res : {"session_id":1}
     */

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

    public static class ResultBean {
        private boolean flag;
        private String info;
        /**
         * session_id : 1
         */

        private ResBean res;

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

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

        public static class ResBean {
            private int user_id;

            public int getuser_id() {
                return user_id;
            }

            public void setSession_id(int user_id) {
                this.user_id = user_id;
            }
        }
    }
}
