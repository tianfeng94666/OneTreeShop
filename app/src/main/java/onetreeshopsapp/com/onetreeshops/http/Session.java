










package onetreeshopsapp.com.onetreeshops.http;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;

import onetreeshopsapp.com.onetreeshops.utils.Const;
import onetreeshopsapp.com.onetreeshops.utils.HttpValue;


public class Session {

	/** 弱引用 */
	// private static Map<String, WeakReference<Bitmap>> imageCache = new
	// HashMap<String, WeakReference<Bitmap>>();
	// public static String Pos_session_id = 343 + "";

	/**
	 * get 获取session
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public String doGet(String url) throws Exception {
		URL localURL = new URL(url);

		URLConnection connection = localURL.openConnection();
		HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
		
//		// 田丰2016-04-20
//		SSLTrustManager sslTrustManager = new SSLTrustManager();
//		HttpsURLConnection httpURLConnection = (HttpsURLConnection) sslTrustManager
//				.connect(url);
		
		httpURLConnection.setConnectTimeout(6000);
		httpURLConnection
				.setRequestProperty("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpURLConnection.setRequestProperty("Accept-Encoding",
				"gzip, deflate, br");
		httpURLConnection.setRequestProperty("Accept-Language",
				"zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
		httpURLConnection.setRequestProperty("Connection", "keep-alive");
		httpURLConnection.setRequestProperty("Cookie", HttpValue.COOKIE);
		// httpURLConnection.setRequestProperty("Host", "192.168.1.27:8069");
		// httpURLConnection.setRequestProperty("Referer",
		// "http://192.168.1.27:8069/web/database/selector");
		httpURLConnection
				.setRequestProperty("User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0");
		httpURLConnection.setInstanceFollowRedirects(false);

		Log.e("get send:", "" + HttpValue.COOKIE);

		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;

//		Log.e("ResponseCode:", "" + httpURLConnection.getResponseCode());
		if (httpURLConnection.getResponseCode() == 200) {
			String key = null;
			String cookieVal = null;
			String sessionId = "";
			for (int i = 1; (key = httpURLConnection.getHeaderFieldKey(i)) != null; i++) {
				if (key.equalsIgnoreCase("set-cookie")) {
					cookieVal = httpURLConnection.getHeaderField(i);
					// this.setCookie(cookieVal);
					cookieVal = cookieVal.substring(0, cookieVal.indexOf(";"));
					sessionId = sessionId + cookieVal + ";";
					Log.e("sessionId:", "" + sessionId);
					HttpValue.SESSION_ID = cookieVal.replace("session_id=","");
					HttpValue.COOKIE = sessionId;
				}
			}
		} else {
			Log.e("ResponseCode:",
					"返回码是--" + httpURLConnection.getResponseCode());
		}

		try {
			reader = new BufferedReader(new InputStreamReader(
					httpURLConnection.getInputStream()));
			while ((tempLine = reader.readLine()) != null) {
				resultBuffer.append(tempLine);
			}

		} finally {
			if (reader != null) {
				reader.close();
			}
		}

		return resultBuffer.toString();
	}

	/**
	 * post 提交表单
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String doPost(String url, Map<String, String> params)
			throws Exception {

		/* Translate parameter map to parameter date string */
		StringBuffer parameterBuffer = new StringBuffer();
		if (params != null) {
			Iterator iterator = params.keySet().iterator();
			String key = null;
			String value = null;
			while (iterator.hasNext()) {
				key = (String) iterator.next();
				if (params.get(key) != null) {
					value = (String) params.get(key);
				} else {
					value = "";
				}

				parameterBuffer.append(key).append("=").append(value);
				if (iterator.hasNext()) {
					parameterBuffer.append("&");
				}
			}
		}

		System.out.println("----------POST parameter : "
				+ parameterBuffer.toString());

		 URL localURL = new URL(url);
		 URLConnection connection = localURL.openConnection();
		 HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

		// 田丰2016-04-20
//		SSLTrustManager sslTrustManager = new SSLTrustManager();
//		HttpsURLConnection httpURLConnection = (HttpsURLConnection) sslTrustManager
//				.connect(url);
		 
		httpURLConnection.setConnectTimeout(6000);
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection
				.setRequestProperty("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpURLConnection.setRequestProperty("Cookie", HttpValue.COOKIE);
		httpURLConnection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		httpURLConnection.setRequestProperty("Content-Length",
				String.valueOf(parameterBuffer.length()));
		httpURLConnection.setInstanceFollowRedirects(false);

		Log.e("post send:", "" + HttpValue.COOKIE);
		OutputStreamWriter outputStreamWriter = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;

		try {
			OutputStream outputStream = httpURLConnection.getOutputStream();
			outputStreamWriter = new OutputStreamWriter(outputStream);
			System.out.println(outputStream.toString());
			outputStreamWriter.write(parameterBuffer.toString());
			outputStreamWriter.flush();

			Log.e("ResponseCode:", "" + httpURLConnection.getResponseCode());

			reader = new BufferedReader(new InputStreamReader(
					httpURLConnection.getInputStream()));
			// System.out.println("2222222222"+httpURLConnection.getOutputStream());
			while ((tempLine = reader.readLine()) != null) {
				resultBuffer.append(tempLine);
			}

		} finally {
			if (outputStreamWriter != null) {
				outputStreamWriter.close();
			}

			if (reader != null) {
				reader.close();
			}
		}

		return resultBuffer.toString();
	}

	public void login(final String username, final String password,
					  final Context context, final Handler handler) {
		try {
			new Thread(new Runnable() {
				@Override
				public void run() {
					String result = null;
					try {
						Log.e("DB_URL:", HttpValue.getHttp()
								+ Const.URL_DB_SELECTOR + HttpValue.DBNAME);
						// 选择数据库
						System.out.println("deget:"+doGet(HttpValue.getHttp()
								+ Const.URL_DB_SELECTOR + HttpValue.DBNAME));
						OpenERPJSONRPC client = new OpenERPJSONRPC();
//
//						// 查询session信息
						JSONObject params = new JSONObject();
						params.put("login", username);
						params.put("password", password);
						result = client.OEJsonRpc(HttpValue.getHttp()
								+ Const.URL_LOGIN, "call", params);
						Log.i("result--------", result);

					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						Message msg = new Message();
						msg.obj = result;
						msg.what = Const.loginCode;
						handler.sendMessage(msg);
					}
				}
			}).start();


		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
