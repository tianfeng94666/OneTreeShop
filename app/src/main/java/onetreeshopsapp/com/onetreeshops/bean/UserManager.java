package onetreeshopsapp.com.onetreeshops.bean;

/**
 * Created by Admin on 2016/5/23.
 */
public class UserManager {

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
            private String job;

            private String gender;

            private String login;

            private String name;

            private String image;

            public String getJob() {
                return job;
            }

            public void setJob(String job) {
                this.job = job;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public void setLogin(String login) {
                this.login = login;
            }

            public String getLogin() {
                return this.login;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getName() {
                return this.name;
            }


        }
    }
}
