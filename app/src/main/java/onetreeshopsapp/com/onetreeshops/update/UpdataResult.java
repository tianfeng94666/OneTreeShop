package onetreeshopsapp.com.onetreeshops.update;

/**
 * Created by admin on 2016-05-13.
 */
public class UpdataResult {

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
            private String version;
            private String apkurl;
            private String file_name;
            private String size;
            private  boolean important;

            public boolean isImportant() {
                return important;
            }

            public void setImportant(boolean important) {
                this.important = important;
            }

            public String getSize() {
                return size;
            }

            public void setSize(String size) {
                this.size = size;
            }

            public String getFile_name() {
                return file_name;
            }

            public void setFile_name(String file_name) {
                this.file_name = file_name;
            }

            public String getVersion() {
                return version;
            }

            public void setVersion(String version) {
                this.version = version;
            }

            public String getApkurl() {
                return apkurl;
            }

            public void setApkurl(String apkurl) {
                this.apkurl = apkurl;
            }
        }
    }
}
