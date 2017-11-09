package onetreeshopsapp.com.onetreeshops.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;


public class GsonUtil {
	public static <T> T jsonToBean(String result, Class<T> clazz) {
		Gson gson = new Gson();
		T t = null;
		try {
			t = gson.fromJson(result, clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return t;
	}

	public static <T> T jsonToBean(String result, Type type) {
		Gson gson = new Gson();
		T t = gson.fromJson(result, type);
		return t;
	}

	public static <T> String beanToJson(T t) {
		Gson gson = new Gson();
		return gson.toJson(t);
	}

	public static boolean isError(String result) {
		boolean flag =false;
		if (result != null) {
			JSONObject object = null;
			try {
				object = new JSONObject(result);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			if (object.has("error")) {
				flag =true;
				/*return true;*/
			} else {
				/*return false;*/
				flag = false;
			}
		}
		return flag;
	}
}
