package onetreeshopsapp.com.onetreeshops.bean;

/**
 * Created by ERP on 2016/12/29.
 */
public class GetSingglShopInfoResult {
    /**
     * jsonrpc : 2.0
     * id : 1
     * result : {"info":"","res":{"pro_score":2.5,"score":2.5,"service_score":2.5},"flag":true}
     */

    private String jsonrpc;
    private String id;
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
        /**
         * info :
         * res : {"pro_score":2.5,"score":2.5,"service_score":2.5}
         * flag : true
         */

        private String info;
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
            /**
             * pro_score : 2.5
             * score : 2.5
             * service_score : 2.5
             */

            private double pro_score;
            private double score;
            private double service_score;

            public double getPro_score() {
                return pro_score;
            }

            public void setPro_score(double pro_score) {
                this.pro_score = pro_score;
            }

            public double getScore() {
                return score;
            }

            public void setScore(double score) {
                this.score = score;
            }

            public double getService_score() {
                return service_score;
            }

            public void setService_score(double service_score) {
                this.service_score = service_score;
            }
        }
    }
}
