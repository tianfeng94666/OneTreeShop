package onetreeshopsapp.com.onetreeshops.bean;

import java.util.List;

/**
 * Created by Admin on 2016/6/28.
 */
public class GetTerminalResult {

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

        private List<Res> res;

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

        public void setRes(List<Res> res) {
            this.res = res;
        }

        public List<Res> getRes() {
            return this.res;
        }

        public class Res {
            private String name;

            private String terminal_no;

            public void setName(String name) {
                this.name = name;
            }

            public String getName() {
                return this.name;
            }

            public void setTerminal_no(String terminal_no) {
                this.terminal_no = terminal_no;
            }

            public String getTerminal_no() {
                return this.terminal_no;
            }
        }
    }
}
