package onetreeshopsapp.com.onetreeshops.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Admin on 2016/6/17.
 */
public class MyAddresRecord implements Serializable {

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

    public class Result implements Serializable{
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

        public class Res implements Serializable{
            private String name;
            private String telephone;
            private String address;
            private int addr_id;
            private boolean default_addr;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTelephone() {
                return telephone;
            }

            public void setTelephone(String telephone) {
                this.telephone = telephone;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getAddr_id() {
                return addr_id;
            }

            public void setAddr_id(int addr_id) {
                this.addr_id = addr_id;
            }

            public boolean isDefault_addr() {
                return default_addr;
            }

            public void setDefault_addr(boolean default_addr) {
                this.default_addr = default_addr;
            }
        }
    }
}
