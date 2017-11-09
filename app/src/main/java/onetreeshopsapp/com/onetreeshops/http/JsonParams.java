package onetreeshopsapp.com.onetreeshops.http;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import onetreeshopsapp.com.onetreeshops.bean.GetTerminalResult;


public class JsonParams {


    /**
     * 获取参数：产品分类
     *
     * @return
     * @throws JSONException
     */
    public static JSONObject getParams_ProductCategory(int uid)
            throws JSONException {

        String modelName = "product.public.category";

        JSONArray fields = new JSONArray();
        fields.put("name");
        fields.put("parent_id");
        fields.put("child_id");
        fields.put("image");
        fields.put("squence");
        fields.put("sequence");

        JSONObject context = new JSONObject();
        context.put("lang", "zh_CN");
        context.put("tz", "Asia/Hong_Kong");
        context.put("uid", uid);
        context.put("sig", "POST");

        JSONObject params = getSearchReadParams(modelName, fields, 0, 0, null,
                null, context);

        return params;
    }

    /**
     * 获取参数：产品
     *
     * @return
     * @throws JSONException
     */
    public static JSONObject getParams_Products(int uid, int pricelist)
            throws JSONException {

        String modelName = "product.product";

        JSONArray fields = new JSONArray();
        fields.put("id");
        fields.put("name");
        fields.put("list_price");
        fields.put("public_categ_id");
        fields.put("uom_id");
        fields.put("price");
        fields.put("taxes_id");
        fields.put("ean13");
        fields.put("default_code");
        fields.put("to_weight");
        fields.put("uos_id");
        fields.put("uos_coeff");
        fields.put("mes_type");
        fields.put("description_sale");
        fields.put("description");
        fields.put("standard_price");
        fields.put("sales_quantity");
        fields.put("short_code");


        JSONArray domain = new JSONArray();

        JSONArray array1 = new JSONArray();
        array1.put("sale_ok");
        array1.put("=");
        array1.put(true);

        JSONArray array2 = new JSONArray();
        array2.put("available_in_pos");
        array2.put("=");
        array2.put(true);

        domain.put(array1);
        domain.put(array2);

        JSONObject context = new JSONObject();
        context.put("lang", "zh_CN");
        context.put("tz", "Asia/Hong_Kong");
        context.put("uid", uid);
        context.put("pricelist", pricelist);

        JSONObject params = getSearchReadParams(modelName, fields, 0, 0,
                domain, null, context);

        return params;
    }


    /**
     * 包装Search_Read 接口的请求参数
     *
     * @param model
     * @param fields
     * @param offset
     * @param limit
     * @param domain
     * @param sort
     * @param context
     * @return
     * @throws JSONException
     */

    public static JSONObject getSearchReadParams(String model,
                                                 JSONArray fields, int offset, int limit, JSONArray domain,
                                                 String sort, JSONObject context) throws JSONException {

        JSONObject params = new JSONObject();
        params.put("model", model);
        if (fields != null) {
            params.put("fields", fields);
        } else {
            params.put("fields", new JSONArray());
        }

        if (offset != 0) {
            params.put("offset", offset);
        } else {
            params.put("offset", 0);
        }

        if (limit != 0) {
            params.put("limit", limit);
        } else {
            params.put("limit", false);
        }

        if (domain == null) {
            domain = new JSONArray();
        }
        params.put("domain", domain);

        if (sort != null) {
            params.put("sort", sort);
        } else {
            params.put("sort", "");
        }

        if (context == null) {
            context = new JSONObject();
        }
        params.put("context", context);

        return params;
    }


    /***
     * 获取session id
     *
     * @param uid
     * @return
     * @throws JSONException
     */
    public static JSONObject setSessionData(int uid) throws JSONException {
        JSONArray fields = new JSONArray();
        fields.put("id");
        fields.put("journal_ids");
        fields.put("name");
        fields.put("user_id");
        fields.put("config_id");
        fields.put("start_at");
        fields.put("stop_at");
        String model = "pos.session";
        int offset = 0;
        int limit = 0;
        String sort = "";
        JSONArray domain = new JSONArray();
        JSONArray innerdomain1 = new JSONArray();
        JSONArray innerdomain2 = new JSONArray();
        innerdomain1.put("state");
        innerdomain1.put("=");
        innerdomain1.put("opened");
        domain.put(innerdomain1);
        innerdomain2.put("user_id");
        innerdomain2.put("=");
        innerdomain2.put(uid);
        domain.put(innerdomain2);
        JSONObject context = new JSONObject();
        context.put("lang", "zh_CN");
        context.put("tz", "Asia/Hong_Kong");
        context.put("uid", uid);
        JSONObject params = JsonParams.getSearchReadParams(model, fields,
                offset, limit, domain, sort, context);
        return params;

    }


    /**
     * 获取用户管理信息
     */
    public static JSONObject getUser_Manager() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("sig", "GET");
        return params;
    }

    /**
     * 修改用户管理信息
     */
    public static JSONObject getUser_Modify(String id, String name, String address, String contacts, String fax, String website, String receipt)
            throws JSONException {
        JSONObject params = new JSONObject();
        params.put("sig", "PATCH");
        params.put("id", id);
        params.put("name", name);
        params.put("address", address);
        params.put("contacts", contacts);
        params.put("fax", fax);
        params.put("website", website);
        params.put("receipt", receipt);
        return params;
    }


    /**
     * 删除产品分类
     */
    public static JSONObject getDelete(int id, String name)
            throws JSONException {
        JSONObject params = new JSONObject();
        params.put("sig", "DELETE");
        params.put("id", id);
//        params.put("parent_id",null);
//        params.put("sequence",)
        params.put("name", name);
        return params;
    }

    /**
     * 结算账单信息
     */
    public static JSONObject getClearing(String start, String stop, String page)
            throws JSONException {
        JSONObject params = new JSONObject();
        params.put("start", start);
        params.put("stop", stop);
        params.put("page", page);
        return params;
    }

    /**
     * 新建产品分类
     */
    public static JSONObject getNew_Product(String name, int parent_id, int sequence)
            throws JSONException {
        JSONObject params = new JSONObject();
        params.put("sig", "POST");
        params.put("name", name);
        if(parent_id==-100){
            params.put("parent_id", "");
        }else{
            params.put("parent_id", parent_id);
        }
        params.put("sequence", sequence);
        return params;
    }

    /**
     * 单位
     */
    public static JSONObject getParams_Uom(int uid)
            throws JSONException {

        String modelName = "product.uom";
        JSONArray fields = new JSONArray();
        fields.put("id");
        fields.put("name");

        JSONObject context = new JSONObject();
        context.put("lang", "zh_CN");
        context.put("tz", "Asia/Hong_Kong");
        context.put("uid", uid);

        JSONObject params = getSearchReadParams(modelName, fields, 0, 0, null,
                null, context);

        return params;
    }

    /**
     * 新建我的产品
     */
    public static JSONObject createNewProduct(String name, String public_categ_id, int uom_id, float product_cost, float product_sale, boolean to_weight)
            throws JSONException {
        JSONObject params = new JSONObject();
        params.put("sig", "POST");
        params.put("name", name);
        params.put("public_categ_id", public_categ_id);
        params.put("uom_id", uom_id);
        params.put("lst_price", product_sale);
        params.put("standard_price", product_cost);
        params.put("to_weight", to_weight);
//        params.put(/*tempFile.getPath().replace("/", "")*/"pic", tempFile);
        return params;
    }
    /**
     * 修改我的产品
     */
    public static JSONObject patchProduct(String name,int productId, String public_categ_id, int uom_id, float lst_price, float price, boolean to_weight)
            throws JSONException {
        JSONObject params = new JSONObject();
        params.put("sig", "PATCH");
        params.put("id",productId);
        params.put("name", name);
        params.put("public_categ_id", public_categ_id);
        params.put("uom_id", uom_id);
        params.put("lst_price", lst_price);
        params.put("standard_price", price);
        params.put("to_weight", to_weight);
//        params.put(/*tempFile.getPath().replace("/", "")*/"pic", tempFile);
        return params;
    }
    /**
     * 修改我的分类
     */
    public static JSONObject getParams_Patch(int id, String name,  int sequence)
            throws JSONException {
        JSONObject params = new JSONObject();
        params.put("sig", "PATCH");
        params.put("id", id);
        params.put("name", name);
        params.put("sequence", sequence);
        return params;
    }

    /**
     * 获取终端号
     */
    public static JSONObject get_Terminal()
            throws JSONException {
        JSONObject params = new JSONObject();
        return params;
    }

    /**
     * 获取销售汇总
     */
    public static JSONObject get_Summary(List<GetTerminalResult.Result.Res> terminals, String start, String stop, ArrayList<String> list)
            throws JSONException {

        JSONObject params = new JSONObject();
        JSONArray args = new JSONArray(list);
        params.put("terminals", args);
        params.put("start", start);
        params.put("stop", stop);
        params.put("terminals", args);
        return params;
    }
}
