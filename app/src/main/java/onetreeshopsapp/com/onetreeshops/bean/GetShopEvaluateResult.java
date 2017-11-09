package onetreeshopsapp.com.onetreeshops.bean;

import java.util.List;

/**
 * Created by ERP on 2016/12/26.
 */
public class GetShopEvaluateResult {
    /**
     * jsonrpc : 2.0
     * id : 1
     * result : {"info":"","res":{"data":[{"user_image":"/pos/binary/image?model=res.users&id=5&field=image&pic_change=0","feedback":" ","grade":"good","pro_score":3,"content":"吃饭发发发古古怪怪","create_time":"2016-12-23 16:55:30","service_score":4,"user_name":"15888888888"},{"user_image":"/pos/binary/image?model=res.users&id=10&field=image&pic_change=2","feedback":" ","grade":"bad","pro_score":2,"content":"这么久才送，黄花菜都凉了！","create_time":"2016-12-26 11:31:03","service_score":1,"user_name":"15666666666"}],"good_num":1,"all_num":2,"common_num":0,"bad_num":1},"flag":true}
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
         * res : {"data":[{"user_image":"/pos/binary/image?model=res.users&id=5&field=image&pic_change=0","feedback":" ","grade":"good","pro_score":3,"content":"吃饭发发发古古怪怪","create_time":"2016-12-23 16:55:30","service_score":4,"user_name":"15888888888"},{"user_image":"/pos/binary/image?model=res.users&id=10&field=image&pic_change=2","feedback":" ","grade":"bad","pro_score":2,"content":"这么久才送，黄花菜都凉了！","create_time":"2016-12-26 11:31:03","service_score":1,"user_name":"15666666666"}],"good_num":1,"all_num":2,"common_num":0,"bad_num":1}
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
             * data : [{"user_image":"/pos/binary/image?model=res.users&id=5&field=image&pic_change=0","feedback":" ","grade":"good","pro_score":3,"content":"吃饭发发发古古怪怪","create_time":"2016-12-23 16:55:30","service_score":4,"user_name":"15888888888"},{"user_image":"/pos/binary/image?model=res.users&id=10&field=image&pic_change=2","feedback":" ","grade":"bad","pro_score":2,"content":"这么久才送，黄花菜都凉了！","create_time":"2016-12-26 11:31:03","service_score":1,"user_name":"15666666666"}]
             * good_num : 1
             * all_num : 2
             * common_num : 0
             * bad_num : 1
             */

            private int good_num;
            private int all_num;
            private int common_num;
            private int bad_num;
            private List<DataBean> data;

            public int getGood_num() {
                return good_num;
            }

            public void setGood_num(int good_num) {
                this.good_num = good_num;
            }

            public int getAll_num() {
                return all_num;
            }

            public void setAll_num(int all_num) {
                this.all_num = all_num;
            }

            public int getCommon_num() {
                return common_num;
            }

            public void setCommon_num(int common_num) {
                this.common_num = common_num;
            }

            public int getBad_num() {
                return bad_num;
            }

            public void setBad_num(int bad_num) {
                this.bad_num = bad_num;
            }

            public List<DataBean> getData() {
                return data;
            }

            public void setData(List<DataBean> data) {
                this.data = data;
            }

            public static class DataBean {
                /**
                 * user_image : /pos/binary/image?model=res.users&id=5&field=image&pic_change=0
                 * feedback :
                 * grade : good
                 * pro_score : 3
                 * content : 吃饭发发发古古怪怪
                 * create_time : 2016-12-23 16:55:30
                 * service_score : 4
                 * user_name : 15888888888
                 */

                private String user_image;
                private String feedback;
                private String grade;
                private int pro_score;
                private String content;
                private String create_time;
                private int service_score;
                private String user_name;

                public String getUser_image() {
                    return user_image;
                }

                public void setUser_image(String user_image) {
                    this.user_image = user_image;
                }

                public String getFeedback() {
                    return feedback;
                }

                public void setFeedback(String feedback) {
                    this.feedback = feedback;
                }

                public String getGrade() {
                    return grade;
                }

                public void setGrade(String grade) {
                    this.grade = grade;
                }

                public int getPro_score() {
                    return pro_score;
                }

                public void setPro_score(int pro_score) {
                    this.pro_score = pro_score;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public String getCreate_time() {
                    return create_time;
                }

                public void setCreate_time(String create_time) {
                    this.create_time = create_time;
                }

                public int getService_score() {
                    return service_score;
                }

                public void setService_score(int service_score) {
                    this.service_score = service_score;
                }

                public String getUser_name() {
                    return user_name;
                }

                public void setUser_name(String user_name) {
                    this.user_name = user_name;
                }
            }
        }
    }
}
