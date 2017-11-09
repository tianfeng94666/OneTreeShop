package onetreeshopsapp.com.onetreeshops.bean;

/**
 * Created by fiona on 2016/5/31.
 */
public class MemberResult {


    /**
     * id : 1
     * jsonrpc : 2.0
     * result : {"flag":true,"info":"","res":{"level":"普通会员","m_account":"2433610379","member_id":8,"money":21052.32,"name":"田丰","percent":85,"points":0}}
     */

    private String id;
    private String jsonrpc;
    /**
     * flag : true
     * info :
     * res : {"level":"普通会员","m_account":"2433610379","member_id":8,"money":21052.32,"name":"田丰","percent":85,"points":0}
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
         * level : 普通会员
         * m_account : 2433610379
         * member_id : 8
         * money : 21052.32
         * name : 田丰
         * percent : 85
         * points : 0
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
            private String level;
            private String m_account;
            private int member_id;
            private double money;
            private String name;
            private int percent;
            private int points;

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public String getM_account() {
                return m_account;
            }

            public void setM_account(String m_account) {
                this.m_account = m_account;
            }

            public int getMember_id() {
                return member_id;
            }

            public void setMember_id(int member_id) {
                this.member_id = member_id;
            }

            public double getMoney() {
                return money;
            }

            public void setMoney(double money) {
                this.money = money;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getPercent() {
                return percent;
            }

            public void setPercent(int percent) {
                this.percent = percent;
            }

            public int getPoints() {
                return points;
            }

            public void setPoints(int points) {
                this.points = points;
            }
        }
    }
}
