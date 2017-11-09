package onetreeshopsapp.com.onetreeshops.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

/**
 * 配置文件操作类
 * 
 * @author chenshuai
 * @update 2014-10-23 上午10:06:04
 * @version V1.0
 */
public class SharedPreferencesUtils {
	/** 配置文件名称 */
	private static final String SHAREDPREGERENCESNAME = "cn.com.system_manager_preferences";
	/** 单例对象 */
	private static SharedPreferencesUtils intance = null;
	/** 配置文件对象 */
	private SharedPreferences sharedPreferences = null;

	/**
	 * 获取单例对象
	 * 
	 * @return
	 */
	public static SharedPreferencesUtils getIntance() {
		if (intance == null) {
			intance = new SharedPreferencesUtils();
		}
		return intance;
	}

	private SharedPreferencesUtils() {
	}

	/**
	 * 获取配置文件对象
	 * 
	 * @param context
	 *            上下文
	 * @return
	 */
	public SharedPreferences getSharedPreferences(Context context) {
		try {
			if (sharedPreferences == null) {
				sharedPreferences = context.getSharedPreferences(
						SHAREDPREGERENCESNAME, Context.MODE_PRIVATE);
			}
		} catch (Exception e) {
		}
		return sharedPreferences;
	}

	/**
	 * 保存对象到SharedPreferences
	 * 
	 * @param sp
	 *            SharedPreferences对象
	 * @param name
	 *            SharedPreferences内的KV的K值
	 * @param obj
	 *            对象
	 */
	public static void saveObjToSp(SharedPreferences sp, String name, Object obj) {
		// 创建字节输出流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			// 创建对象输出流，并封装字节流
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			// 将对象写入字节流
			oos.writeObject(obj);
			// 将字节流编码成base64的字符窜
			String obj_Base64 = new String(Base64.encode(baos.toByteArray(), 0));

			Editor editor = sp.edit();
			editor.putString(name, obj_Base64);

			editor.commit();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从SharedPreferences读取对象
	 * 
	 * @param sp
	 *            SharedPreferences对象
	 * @param name
	 *            SharedPreferences内的KV的K值
	 * @return 对象
	 */
	public static Object readObjFromSp(SharedPreferences sp, String name) {
		Object obj = null;

		String productBase64 = sp.getString(name, "");

		if ("".equals(productBase64)) {
			return "";
		}

		// 读取字节
		byte[] base64 = Base64.decode(productBase64.getBytes(), 0);

		// 封装到字节流
		ByteArrayInputStream bais = new ByteArrayInputStream(base64);
		try {
			// 再次封装
			ObjectInputStream bis = new ObjectInputStream(bais);
			// 读取对象
			try {
				obj = bis.readObject();

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}

	public static void deleteObjFromSp(SharedPreferences sp, String name) {
		try {
			Editor editor = sp.edit();
			// editor.clear();
			editor.remove(name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}