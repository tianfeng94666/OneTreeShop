package onetreeshopsapp.com.onetreeshops.http;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;


import org.json.JSONObject;

import onetreeshopsapp.com.onetreeshops.utils.IntentUtils;
import onetreeshopsapp.com.onetreeshops.utils.LogUtils;
import onetreeshopsapp.com.onetreeshops.utils.NetWorkUtils;
import onetreeshopsapp.com.onetreeshops.utils.ToastUtils;


public class JsonRPCAsyncTask extends AsyncTask<String, String, String> implements Thread.UncaughtExceptionHandler{

	private OpenERPJSONRPC client;
	private Context context; // 上下文
	private Handler handler; // 通信handler
	private int requestCode; // 请求码， 区分请求

	private String url; // 接口路径
	private String method; // 方式
	private JSONObject params; // 参数

	public JsonRPCAsyncTask(Context context, Handler handler, int requestCode,
							String url, String method, JSONObject params) {
		super();
		this.context = context;
		this.handler = handler;
		this.requestCode = requestCode;
		this.url = url;
		this.params = params;
		this.method = method;
		client = new OpenERPJSONRPC();
	}

	@Override
	protected String doInBackground(String... args) {
		String result = null;
		LogUtils.e("Json_url", url);
		LogUtils.e("参数", params.toString());
		if(NetWorkUtils.getNetConnecState(context)){
			result = null;
			try {
				result = client.OEJsonRpc(url, method, params);
				LogUtils.i("request.body--返回结果：", "" + result);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}else {
			((Activity)context).runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(context,"网络连接有问题！",Toast.LENGTH_SHORT).show();
				}
			});

		}
		return result;
	}

	@Override
	protected void onPostExecute(String result) {
		Message msg = new Message();
		msg.what = requestCode;
		msg.obj = result;
		handler.sendMessage(msg);
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		System.out.println("threadId ="+thread.getId()+",ex="+ex.toString());
	}
}
