package onetreeshopsapp.com.onetreeshops.bean;

/**
 * Created by Admin on 2016/6/28.
 */
public class GetCaptchaResult {

    private String id;

    private String jsonrpc;

    private Result result;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public String getJsonrpc() {
        return this.jsonrpc;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Result getResult() {
        return this.result;
    }

    public class Result {
        private boolean flag;

        private String info;

        private Res res;

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public boolean getFlag() {
            return this.flag;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getInfo() {
            return this.info;
        }

        public void setRes(Res res) {
            this.res = res;
        }

        public Res getRes() {
            return this.res;
        }

        public class Res {
            private String validatecode;

            public String getValidatecode() {
                return validatecode;
            }

            public void setValidatecode(String validatecode) {
                this.validatecode = validatecode;
            }
        }
    }
}
