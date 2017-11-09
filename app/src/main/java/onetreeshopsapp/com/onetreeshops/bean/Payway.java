package onetreeshopsapp.com.onetreeshops.bean;

import java.io.Serializable;

/**
 * Created by admin on 2016-05-24.
 */
public class Payway implements Serializable {


    /**
     * jsonrpc : 2.0
     * id : 1
     * result : {"info":"","res":{"TVIP":{"journal_id":11,"statement_id":19},"TZFB":{"journal_id":12,"statement_id":21},"TBNK":{"journal_id":10,"statement_id":17},"TCSH":{"journal_id":8,"statement_id":18},"TWWX":{"journal_id":13,"statement_id":20}},"flag":true}
     */

    private String jsonrpc;
    private String id;
    /**
     * info :
     * res : {"TVIP":{"journal_id":11,"statement_id":19},"TZFB":{"journal_id":12,"statement_id":21},"TBNK":{"journal_id":10,"statement_id":17},"TCSH":{"journal_id":8,"statement_id":18},"TWWX":{"journal_id":13,"statement_id":20}}
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

    public static class ResultBean  implements Serializable{
        private String info;
        /**
         * TVIP : {"journal_id":11,"statement_id":19}
         * TZFB : {"journal_id":12,"statement_id":21}
         * TBNK : {"journal_id":10,"statement_id":17}
         * TCSH : {"journal_id":8,"statement_id":18}
         * TWWX : {"journal_id":13,"statement_id":20}
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

        public static class ResBean implements Serializable{
            /**
             * journal_id : 11
             * statement_id : 19
             */

            private TVIPBean TVIP;
            /**
             * journal_id : 12
             * statement_id : 21
             */

            private TZFBBean TZFB;
            /**
             * journal_id : 10
             * statement_id : 17
             */

            private TBNKBean TBNK;
            /**
             * journal_id : 8
             * statement_id : 18
             */

            private TCSHBean TCSH;
            /**
             * journal_id : 13
             * statement_id : 20
             */

            private TWWXBean TWWX;

            public TVIPBean getTVIP() {
                return TVIP;
            }

            public void setTVIP(TVIPBean TVIP) {
                this.TVIP = TVIP;
            }

            public TZFBBean getTZFB() {
                return TZFB;
            }

            public void setTZFB(TZFBBean TZFB) {
                this.TZFB = TZFB;
            }

            public TBNKBean getTBNK() {
                return TBNK;
            }

            public void setTBNK(TBNKBean TBNK) {
                this.TBNK = TBNK;
            }

            public TCSHBean getTCSH() {
                return TCSH;
            }

            public void setTCSH(TCSHBean TCSH) {
                this.TCSH = TCSH;
            }

            public TWWXBean getTWWX() {
                return TWWX;
            }

            public void setTWWX(TWWXBean TWWX) {
                this.TWWX = TWWX;
            }

            public static class TVIPBean  implements Serializable{
                private int journal_id;
                private int statement_id;

                public int getJournal_id() {
                    return journal_id;
                }

                public void setJournal_id(int journal_id) {
                    this.journal_id = journal_id;
                }

                public int getStatement_id() {
                    return statement_id;
                }

                public void setStatement_id(int statement_id) {
                    this.statement_id = statement_id;
                }
            }

            public static class TZFBBean  implements Serializable{
                private int journal_id;
                private int statement_id;

                public int getJournal_id() {
                    return journal_id;
                }

                public void setJournal_id(int journal_id) {
                    this.journal_id = journal_id;
                }

                public int getStatement_id() {
                    return statement_id;
                }

                public void setStatement_id(int statement_id) {
                    this.statement_id = statement_id;
                }
            }

            public static class TBNKBean  implements Serializable{
                private int journal_id;
                private int statement_id;

                public int getJournal_id() {
                    return journal_id;
                }

                public void setJournal_id(int journal_id) {
                    this.journal_id = journal_id;
                }

                public int getStatement_id() {
                    return statement_id;
                }

                public void setStatement_id(int statement_id) {
                    this.statement_id = statement_id;
                }
            }

            public static class TCSHBean implements Serializable{
                private int journal_id;
                private int statement_id;

                public int getJournal_id() {
                    return journal_id;
                }

                public void setJournal_id(int journal_id) {
                    this.journal_id = journal_id;
                }

                public int getStatement_id() {
                    return statement_id;
                }

                public void setStatement_id(int statement_id) {
                    this.statement_id = statement_id;
                }
            }

            public static class TWWXBean implements Serializable{
                private int journal_id;
                private int statement_id;

                public int getJournal_id() {
                    return journal_id;
                }

                public void setJournal_id(int journal_id) {
                    this.journal_id = journal_id;
                }

                public int getStatement_id() {
                    return statement_id;
                }

                public void setStatement_id(int statement_id) {
                    this.statement_id = statement_id;
                }
            }
        }
    }
}
