package onetreeshopsapp.com.onetreeshops.http;

import android.util.Log;

import com.github.kevinsawicki.http.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import onetreeshopsapp.com.onetreeshops.utils.HttpValue;
import onetreeshopsapp.com.onetreeshops.utils.LogUtils;


public class OpenERPJSONRPC {
    private int _rid = 1;

    /**
     * 结果
     * JSONRPC 访问open服务器
     *
     * @param url    路径
     * @param method 方式：call
     * @param params 参数：json格式
     * @return
     * @throws JSONException
     */
    public HttpRequest JsonRpc(String url, String method, JSONObject params)
            throws JSONException {

        HttpRequest request = HttpRequest.post(url);
//        //针对单项证书给予忽略（注意，双向证书需要导入证书文件）
//        request.trustAllCerts();
//        //信任所有地址
//        request.trustAllHosts();

        LogUtils.e("Cookie", "" + HttpValue.COOKIE);
        request.header("Cookie", HttpValue.COOKIE);

        JSONObject postData = new JSONObject();
        postData.put("json-rpc", "2.0");
        postData.put("method", method);
        postData.put("params", params);
        postData.put("id", "" + this._rid);
        LogUtils.d("id:" + this._rid);

        this._rid++;
        request.contentType("application/json");
        request.acceptJson();
        try {
            request.send(postData.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        int statusCode = 0;
        try {
            statusCode = request.code();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (statusCode != 200) {
            LogUtils.e("statusCode:", "" + statusCode);
        } else {
            LogUtils.d("statusCode:", "" + statusCode);
        }
        return request;
    }

    /**
     * 代理请求，得到响应结果
     *
     * @param url
     * @param method
     * @param params
     * @return JSON格式的数据
     * @throws JSONException
     */
    public String OEJsonRpc(String url, String method, JSONObject params) {

        //LogUtils.e("url：", ""+url);
        LogUtils.e("参数：", "" + params.toString());

        HttpRequest request = null;
        try {
            request = this.JsonRpc(url, method, params);
            LogUtils.e("requesthead",request.headers().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //get data
        String result = null;
        try {
            result = request.body();
            Log.e("request.body", result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


}
