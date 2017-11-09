package onetreeshopsapp.com.onetreeshops.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fiona on 2016/10/9.
 */
public class GetProductDateResult implements Serializable {

    /**
     * id : 1
     * jsonrpc : 2.0
     * result : {"flag":true,"info":"","res":[{"image":"/pos/binary/image?model=product.template&id=62&field=image","name":"123","price":8.98,"uom":47},{"image":"/pos/binary/image?model=product.template&id=99&field=image","name":"123456","price":7.99,"uom":49},{"image":"/pos/binary/image?model=product.template&id=63&field=image","name":"124和共和国","price":55.09,"uom":49},{"image":"/pos/binary/image?model=product.template&id=64&field=image","name":"125面包","price":35,"uom":49},{"image":"/pos/binary/image?model=product.template&id=60&field=image","name":"23ghgfdddd","price":34,"uom":44},{"image":"/pos/binary/image?model=product.template&id=103&field=image","name":"333ghhggfdfxxx反对的","price":30,"uom":48},{"image":"/pos/binary/image?model=product.template&id=98&field=image","name":"a规划机会v从宝钢股份","price":4.09,"uom":46},{"image":"/pos/binary/image?model=product.template&id=87&field=image","name":"dff","price":56,"uom":45},{"image":"/pos/binary/image?model=product.template&id=93&field=image","name":"ert","price":56,"uom":21},{"image":"/pos/binary/image?model=product.template&id=94&field=image","name":"haha","price":24,"uom":44},{"image":"/pos/binary/image?model=product.template&id=91&field=image","name":"hxt","price":56,"uom":44},{"image":"/pos/binary/image?model=product.template&id=89&field=image","name":"jc","price":56,"uom":21},{"image":"/pos/binary/image?model=product.template&id=105&field=image","name":"kkkkkghgfdddd","price":90.09,"uom":48},{"image":"/pos/binary/image?model=product.template&id=61&field=image","name":"rt","price":45,"uom":22},{"image":"/pos/binary/image?model=product.template&id=100&field=image","name":"tfff","price":6,"uom":43},{"image":"/pos/binary/image?model=product.template&id=86&field=image","name":"tianfeng2","price":24,"uom":20},{"image":"/pos/binary/image?model=product.template&id=84&field=image","name":"tianshi1","price":34,"uom":22},{"image":"/pos/binary/image?model=product.template&id=95&field=image","name":"ttt面包","price":56.98,"uom":46},{"image":"/pos/binary/image?model=product.template&id=90&field=image","name":"tty","price":45,"uom":42},{"image":"/pos/binary/image?model=product.template&id=85&field=image","name":"tyty","price":13,"uom":20},{"image":"/pos/binary/image?model=product.template&id=92&field=image","name":"xia","price":67,"uom":22},{"image":"/pos/binary/image?model=product.template&id=88&field=image","name":"xx","price":12.7,"uom":45},{"image":"/pos/binary/image?model=product.template&id=104&field=image","name":"一毛钱好贵啊sghgddff","price":0.1,"uom":48},{"image":"/pos/binary/image?model=product.template&id=49&field=image","name":"乌鸡","price":56,"uom":3},{"image":"/pos/binary/image?model=product.template&id=58&field=image","name":"仓鱼","price":70,"uom":3},{"image":"/pos/binary/image?model=product.template&id=42&field=image","name":"八角","price":8,"uom":24},{"image":"/pos/binary/image?model=product.template&id=23&field=image","name":"原味奶茶fghnndgg","price":25,"uom":20},{"image":"/pos/binary/image?model=product.template&id=35&field=image","name":"原味芝士蛋糕","price":19,"uom":46},{"image":"/pos/binary/image?model=product.template&id=45&field=image","name":"厨邦酱油(500毫升)","price":15.89,"uom":20},{"image":"/pos/binary/image?model=product.template&id=34&field=image","name":"双层芝士面包dggffdyy","price":8,"uom":46},{"image":"/pos/binary/image?model=product.template&id=16&field=image","name":"土豆","price":12,"uom":20},{"image":"/pos/binary/image?model=product.template&id=50&field=image","name":"多宝鱼","price":55,"uom":3},{"image":"/pos/binary/image?model=product.template&id=102&field=image","name":"奶油蛋糕1","price":15.06,"uom":46},{"image":"/pos/binary/image?model=product.template&id=59&field=image","name":"小小鱼","price":54,"uom":21},{"image":"/pos/binary/image?model=product.template&id=48&field=image","name":"小龙虾","price":70,"uom":3},{"image":"/pos/binary/image?model=product.template&id=20&field=image","name":"巧克力蛋糕好吧","price":90,"uom":3},{"image":"/pos/binary/image?model=product.template&id=44&field=image","name":"扁豆","price":6,"uom":3},{"image":"/pos/binary/image?model=product.template&id=21&field=image","name":"提拉米苏蛋糕","price":80,"uom":3},{"image":"/pos/binary/image?model=product.template&id=32&field=image","name":"晨光牛奶(200毫升)ggffffffg","price":9.99,"uom":20},{"image":"/pos/binary/image?model=product.template&id=36&field=image","name":"杂粮面包","price":15,"uom":47},{"image":"/pos/binary/image?model=product.template&id=27&field=image","name":"梨子","price":70,"uom":22},{"image":"/pos/binary/image?model=product.template&id=37&field=image","name":"榴莲","price":45,"uom":3},{"image":"/pos/binary/image?model=product.template&id=53&field=image","name":"橙汁gcdddd","price":8,"uom":20},{"image":"/pos/binary/image?model=product.template&id=97&field=image","name":"汁鸡王","price":8,"uom":21},{"image":"/pos/binary/image?model=product.template&id=31&field=image","name":"火龙果","price":15,"uom":21},{"image":"/pos/binary/image?model=product.template&id=26&field=image","name":"炸豆腐","price":15,"uom":24},{"image":"/pos/binary/image?model=product.template&id=7&field=image","name":"炸鸡块","price":34,"uom":3},{"image":"/pos/binary/image?model=product.template&id=13&field=image","name":"牛肉","price":35,"uom":3},{"image":"/pos/binary/image?model=product.template&id=107&field=image","name":"猪肉","price":5,"uom":48},{"image":"/pos/binary/image?model=product.template&id=57&field=image","name":"猪肝","price":10,"uom":3},{"image":"/pos/binary/image?model=product.template&id=38&field=image","name":"猪蹄","price":55,"uom":3},{"image":"/pos/binary/image?model=product.template&id=41&field=image","name":"王老吉","price":55.99,"uom":22},{"image":"/pos/binary/image?model=product.template&id=33&field=image","name":"甜甜圈","price":5,"uom":46},{"image":"/pos/binary/image?model=product.template&id=15&field=image","name":"盐","price":10,"uom":21},{"image":"/pos/binary/image?model=product.template&id=29&field=image","name":"红辣椒","price":5,"uom":3},{"image":"/pos/binary/image?model=product.template&id=12&field=image","name":"羊肉","price":30,"uom":3},{"image":"/pos/binary/image?model=product.template&id=28&field=image","name":"芥菜","price":5,"uom":3},{"image":"/pos/binary/image?model=product.template&id=10&field=image","name":"花椒","price":56,"uom":21},{"image":"/pos/binary/image?model=product.template&id=17&field=image","name":"苹果","price":60,"uom":3},{"image":"/pos/binary/image?model=product.template&id=24&field=image","name":"草鱼","price":24,"uom":3},{"image":"/pos/binary/image?model=product.template&id=30&field=image","name":"莴笋","price":8,"uom":3},{"image":"/pos/binary/image?model=product.template&id=43&field=image","name":"蒙牛纯牛奶(250毫升)","price":12,"uom":20},{"image":"/pos/binary/image?model=product.template&id=56&field=image","name":"蒙牛酸牛奶(350毫升)","price":8.09,"uom":20},{"image":"/pos/binary/image?model=product.template&id=96&field=image","name":"虾米","price":56,"uom":21},{"image":"/pos/binary/image?model=product.template&id=22&field=image","name":"蛋挞","price":25,"uom":46},{"image":"/pos/binary/image?model=product.template&id=47&field=image","name":"螃蟹","price":65,"uom":3},{"image":"/pos/binary/image?model=product.template&id=25&field=image","name":"西瓜汁dhjjjffggbv","price":20,"uom":20},{"image":"/pos/binary/image?model=product.template&id=65&field=image","name":"西蓝花","price":5,"uom":3},{"image":"/pos/binary/image?model=product.template&id=46&field=image","name":"长豆角","price":5,"uom":3},{"image":"/pos/binary/image?model=product.template&id=19&field=image","name":"青瓜","price":4,"uom":3},{"image":"/pos/binary/image?model=product.template&id=52&field=image","name":"香蕉","price":8,"uom":3},{"image":"/pos/binary/image?model=product.template&id=40&field=image","name":"骨头","price":25,"uom":3},{"image":"/pos/binary/image?model=product.template&id=51&field=image","name":"鲜鱿","price":35,"uom":3},{"image":"/pos/binary/image?model=product.template&id=54&field=image","name":"鲫鱼","price":35,"uom":3},{"image":"/pos/binary/image?model=product.template&id=55&field=image","name":"鸭肉","price":60,"uom":3},{"image":"/pos/binary/image?model=product.template&id=39&field=image","name":"龙虾","price":80,"uom":3}]}
     */

    private String id;
    private String jsonrpc;
    /**
     * flag : true
     * info :
     * res : [{"image":"/pos/binary/image?model=product.template&id=62&field=image","name":"123","price":8.98,"uom":47},{"image":"/pos/binary/image?model=product.template&id=99&field=image","name":"123456","price":7.99,"uom":49},{"image":"/pos/binary/image?model=product.template&id=63&field=image","name":"124和共和国","price":55.09,"uom":49},{"image":"/pos/binary/image?model=product.template&id=64&field=image","name":"125面包","price":35,"uom":49},{"image":"/pos/binary/image?model=product.template&id=60&field=image","name":"23ghgfdddd","price":34,"uom":44},{"image":"/pos/binary/image?model=product.template&id=103&field=image","name":"333ghhggfdfxxx反对的","price":30,"uom":48},{"image":"/pos/binary/image?model=product.template&id=98&field=image","name":"a规划机会v从宝钢股份","price":4.09,"uom":46},{"image":"/pos/binary/image?model=product.template&id=87&field=image","name":"dff","price":56,"uom":45},{"image":"/pos/binary/image?model=product.template&id=93&field=image","name":"ert","price":56,"uom":21},{"image":"/pos/binary/image?model=product.template&id=94&field=image","name":"haha","price":24,"uom":44},{"image":"/pos/binary/image?model=product.template&id=91&field=image","name":"hxt","price":56,"uom":44},{"image":"/pos/binary/image?model=product.template&id=89&field=image","name":"jc","price":56,"uom":21},{"image":"/pos/binary/image?model=product.template&id=105&field=image","name":"kkkkkghgfdddd","price":90.09,"uom":48},{"image":"/pos/binary/image?model=product.template&id=61&field=image","name":"rt","price":45,"uom":22},{"image":"/pos/binary/image?model=product.template&id=100&field=image","name":"tfff","price":6,"uom":43},{"image":"/pos/binary/image?model=product.template&id=86&field=image","name":"tianfeng2","price":24,"uom":20},{"image":"/pos/binary/image?model=product.template&id=84&field=image","name":"tianshi1","price":34,"uom":22},{"image":"/pos/binary/image?model=product.template&id=95&field=image","name":"ttt面包","price":56.98,"uom":46},{"image":"/pos/binary/image?model=product.template&id=90&field=image","name":"tty","price":45,"uom":42},{"image":"/pos/binary/image?model=product.template&id=85&field=image","name":"tyty","price":13,"uom":20},{"image":"/pos/binary/image?model=product.template&id=92&field=image","name":"xia","price":67,"uom":22},{"image":"/pos/binary/image?model=product.template&id=88&field=image","name":"xx","price":12.7,"uom":45},{"image":"/pos/binary/image?model=product.template&id=104&field=image","name":"一毛钱好贵啊sghgddff","price":0.1,"uom":48},{"image":"/pos/binary/image?model=product.template&id=49&field=image","name":"乌鸡","price":56,"uom":3},{"image":"/pos/binary/image?model=product.template&id=58&field=image","name":"仓鱼","price":70,"uom":3},{"image":"/pos/binary/image?model=product.template&id=42&field=image","name":"八角","price":8,"uom":24},{"image":"/pos/binary/image?model=product.template&id=23&field=image","name":"原味奶茶fghnndgg","price":25,"uom":20},{"image":"/pos/binary/image?model=product.template&id=35&field=image","name":"原味芝士蛋糕","price":19,"uom":46},{"image":"/pos/binary/image?model=product.template&id=45&field=image","name":"厨邦酱油(500毫升)","price":15.89,"uom":20},{"image":"/pos/binary/image?model=product.template&id=34&field=image","name":"双层芝士面包dggffdyy","price":8,"uom":46},{"image":"/pos/binary/image?model=product.template&id=16&field=image","name":"土豆","price":12,"uom":20},{"image":"/pos/binary/image?model=product.template&id=50&field=image","name":"多宝鱼","price":55,"uom":3},{"image":"/pos/binary/image?model=product.template&id=102&field=image","name":"奶油蛋糕1","price":15.06,"uom":46},{"image":"/pos/binary/image?model=product.template&id=59&field=image","name":"小小鱼","price":54,"uom":21},{"image":"/pos/binary/image?model=product.template&id=48&field=image","name":"小龙虾","price":70,"uom":3},{"image":"/pos/binary/image?model=product.template&id=20&field=image","name":"巧克力蛋糕好吧","price":90,"uom":3},{"image":"/pos/binary/image?model=product.template&id=44&field=image","name":"扁豆","price":6,"uom":3},{"image":"/pos/binary/image?model=product.template&id=21&field=image","name":"提拉米苏蛋糕","price":80,"uom":3},{"image":"/pos/binary/image?model=product.template&id=32&field=image","name":"晨光牛奶(200毫升)ggffffffg","price":9.99,"uom":20},{"image":"/pos/binary/image?model=product.template&id=36&field=image","name":"杂粮面包","price":15,"uom":47},{"image":"/pos/binary/image?model=product.template&id=27&field=image","name":"梨子","price":70,"uom":22},{"image":"/pos/binary/image?model=product.template&id=37&field=image","name":"榴莲","price":45,"uom":3},{"image":"/pos/binary/image?model=product.template&id=53&field=image","name":"橙汁gcdddd","price":8,"uom":20},{"image":"/pos/binary/image?model=product.template&id=97&field=image","name":"汁鸡王","price":8,"uom":21},{"image":"/pos/binary/image?model=product.template&id=31&field=image","name":"火龙果","price":15,"uom":21},{"image":"/pos/binary/image?model=product.template&id=26&field=image","name":"炸豆腐","price":15,"uom":24},{"image":"/pos/binary/image?model=product.template&id=7&field=image","name":"炸鸡块","price":34,"uom":3},{"image":"/pos/binary/image?model=product.template&id=13&field=image","name":"牛肉","price":35,"uom":3},{"image":"/pos/binary/image?model=product.template&id=107&field=image","name":"猪肉","price":5,"uom":48},{"image":"/pos/binary/image?model=product.template&id=57&field=image","name":"猪肝","price":10,"uom":3},{"image":"/pos/binary/image?model=product.template&id=38&field=image","name":"猪蹄","price":55,"uom":3},{"image":"/pos/binary/image?model=product.template&id=41&field=image","name":"王老吉","price":55.99,"uom":22},{"image":"/pos/binary/image?model=product.template&id=33&field=image","name":"甜甜圈","price":5,"uom":46},{"image":"/pos/binary/image?model=product.template&id=15&field=image","name":"盐","price":10,"uom":21},{"image":"/pos/binary/image?model=product.template&id=29&field=image","name":"红辣椒","price":5,"uom":3},{"image":"/pos/binary/image?model=product.template&id=12&field=image","name":"羊肉","price":30,"uom":3},{"image":"/pos/binary/image?model=product.template&id=28&field=image","name":"芥菜","price":5,"uom":3},{"image":"/pos/binary/image?model=product.template&id=10&field=image","name":"花椒","price":56,"uom":21},{"image":"/pos/binary/image?model=product.template&id=17&field=image","name":"苹果","price":60,"uom":3},{"image":"/pos/binary/image?model=product.template&id=24&field=image","name":"草鱼","price":24,"uom":3},{"image":"/pos/binary/image?model=product.template&id=30&field=image","name":"莴笋","price":8,"uom":3},{"image":"/pos/binary/image?model=product.template&id=43&field=image","name":"蒙牛纯牛奶(250毫升)","price":12,"uom":20},{"image":"/pos/binary/image?model=product.template&id=56&field=image","name":"蒙牛酸牛奶(350毫升)","price":8.09,"uom":20},{"image":"/pos/binary/image?model=product.template&id=96&field=image","name":"虾米","price":56,"uom":21},{"image":"/pos/binary/image?model=product.template&id=22&field=image","name":"蛋挞","price":25,"uom":46},{"image":"/pos/binary/image?model=product.template&id=47&field=image","name":"螃蟹","price":65,"uom":3},{"image":"/pos/binary/image?model=product.template&id=25&field=image","name":"西瓜汁dhjjjffggbv","price":20,"uom":20},{"image":"/pos/binary/image?model=product.template&id=65&field=image","name":"西蓝花","price":5,"uom":3},{"image":"/pos/binary/image?model=product.template&id=46&field=image","name":"长豆角","price":5,"uom":3},{"image":"/pos/binary/image?model=product.template&id=19&field=image","name":"青瓜","price":4,"uom":3},{"image":"/pos/binary/image?model=product.template&id=52&field=image","name":"香蕉","price":8,"uom":3},{"image":"/pos/binary/image?model=product.template&id=40&field=image","name":"骨头","price":25,"uom":3},{"image":"/pos/binary/image?model=product.template&id=51&field=image","name":"鲜鱿","price":35,"uom":3},{"image":"/pos/binary/image?model=product.template&id=54&field=image","name":"鲫鱼","price":35,"uom":3},{"image":"/pos/binary/image?model=product.template&id=55&field=image","name":"鸭肉","price":60,"uom":3},{"image":"/pos/binary/image?model=product.template&id=39&field=image","name":"龙虾","price":80,"uom":3}]
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

    public static class ResultBean implements Serializable {
        private boolean flag;
        private String info;
        /**
         * image : /pos/binary/image?model=product.template&id=62&field=image
         * name : 123
         * price : 8.98
         * uom : 47
         */

        private List<ProductInfo> res;

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

        public List<ProductInfo> getRes() {
            return res;
        }

        public void setRes(List<ProductInfo> res) {
            this.res = res;
        }

        public static class ResBean implements Serializable {
            private String image;
            private String name;
            private double price;
            private String uom;
            private int amount;
            private int store_id;
            private String store_name;
            private int product_id;
            private boolean isSelect;

            public boolean isSelect() {
                return isSelect;
            }

            public void setSelect(boolean select) {
                isSelect = select;
            }

            public int getProduct_id() {
                return product_id;
            }

            public void setProduct_id(int product_id) {
                this.product_id = product_id;
            }

            public int getStore_id() {
                return store_id;
            }

            public void setStore_id(int store_id) {
                this.store_id = store_id;
            }

            public String getStore_name() {
                return store_name;
            }

            public void setStore_name(String store_name) {
                this.store_name = store_name;
            }

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getUom() {
                return uom;
            }

            public void setUom(String uom) {
                this.uom = uom;
            }
        }

        /*public static class ResBean {
            private String image;
            private String name;
            private double price;
            private String uom;
            private int amount;
            private int store_id;
            private String store_name;
            private int product_id;
            private boolean isSelect;

            public boolean isSelect() {
                return isSelect;
            }

            public void setSelect(boolean select) {
                isSelect = select;
            }

            public int getProduct_id() {
                return product_id;
            }

            public void setProduct_id(int product_id) {
                this.product_id = product_id;
            }

            public int getStore_id() {
                return store_id;
            }

            public void setStore_id(int store_id) {
                this.store_id = store_id;
            }

            public String getStore_name() {
                return store_name;
            }

            public void setStore_name(String store_name) {
                this.store_name = store_name;
            }

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getUom() {
                return uom;
            }

            public void setUom(String uom) {
                this.uom = uom;
            }
        }
*/
    }

}
