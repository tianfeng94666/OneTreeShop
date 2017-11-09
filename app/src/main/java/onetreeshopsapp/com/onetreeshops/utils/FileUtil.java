package onetreeshopsapp.com.onetreeshops.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.UUID;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Environment;
import android.util.Base64;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

public class FileUtil {
	
	private static final int TYPE_IMAGE = 1;
	private static final int TYPE_ADUIO = 2;
	private static final int TYPE_VIDEO = 3;

	private static String getPublicFilePath(int type) {
		String fileDir = null;
		String fileSuffix = null;
		switch (type) {
		case TYPE_ADUIO:
			//fileDir = SmallparkApplication.getInstance().mVoicesDir;
			fileSuffix = ".mp3";
			break;
		case TYPE_VIDEO:
			//fileDir = SmallparkApplication.getInstance().mVideosDir;
			fileSuffix = ".mp4";
			break;
		case TYPE_IMAGE:
			//fileDir = SmallparkApplication.getInstance().mPicturesDir;
			fileSuffix = ".jpg";
			break;
		}
		if (fileDir == null) {
			return null;
		}
		File file = new File(fileDir);
		if (!file.exists()) {
			if (!file.mkdirs()) {
				return null;
			}
		}
		return fileDir + File.separator + UUID.randomUUID().toString().replaceAll("-", "") + fileSuffix;
	}

	/**
	 * {@link #TYPE_ADUIO}<br/>
	 * {@link #TYPE_VIDEO} <br/>
	 * 
	 * @param type
	 * @return
	 */
	private static String getPrivateFilePath(int type, String userId) {
		String fileDir = null;
		String fileSuffix = null;
		switch (type) {
		case TYPE_ADUIO:
			//fileDir = SmallparkApplication.getInstance().mAppDir + File.separator + userId + File.separator + Environment.DIRECTORY_MUSIC;
			fileSuffix = ".mp3";
			break;
		case TYPE_VIDEO:
			//fileDir = SmallparkApplication.getInstance().mAppDir + File.separator + userId + File.separator + Environment.DIRECTORY_MOVIES;
			fileSuffix = ".mp4";
			break;
		}
		if (fileDir == null) {
			return null;
		}
		File file = new File(fileDir);
		if (!file.exists()) {
			if (!file.mkdirs()) {
				return null;
			}
		}
		return fileDir + File.separator + UUID.randomUUID().toString().replaceAll("-", "") + fileSuffix;
	}

	public static String getRandomImageFilePath() {
		return getPublicFilePath(TYPE_IMAGE);
	}

	/*public static String getRandomAudioFilePath() {
		User user = MyApplication.getInstance().mLoginUser;
		if (user != null && !TextUtils.isEmpty(user.getUserId())) {
			return getPrivateFilePath(TYPE_ADUIO, user.getUserId());
		} else {
			return getPublicFilePath(TYPE_ADUIO);
		}
	}

	public static String getRandomAudioAmrFilePath() {
		User user = MyApplication.getInstance().mLoginUser;
		String filePath = null;
		if (user != null && !TextUtils.isEmpty(user.getUserId())) {
			filePath = getPrivateFilePath(TYPE_ADUIO, user.getUserId());
		} else {
			filePath = getPublicFilePath(TYPE_ADUIO);
		}
		if (!TextUtils.isEmpty(filePath)) {
			return filePath.replace(".mp3", ".amr");
		} else {
			return null;
		}
	}

	public static String getRandomVideoFilePath() {
		User user = MyApplication.getInstance().mLoginUser;
		if (user != null && !TextUtils.isEmpty(user.getUserId())) {
			return getPrivateFilePath(TYPE_VIDEO, user.getUserId());
		} else {
			return getPublicFilePath(TYPE_VIDEO);
		}
	}*/

	// ///////////////////////////////////////////////////////////////////////////////////////////////////

	public static void createFileDir(String fileDir) {
		File fd = new File(fileDir);
		if (!fd.exists()) {
			fd.mkdirs();
		}
	}

	/**
	 * 
	 * @param fullName
	 */
	public static void delFile(String fullName) {
		File file = new File(fullName);
		if (file.exists()) {
			if (file.isFile()) {
				try {
					file.delete();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 删除文件夹里面的所有文件
	 * 
	 * @param path
	 *            String 文件夹路径 如 /sdcard/data/
	 */
	public static void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			System.out.println(path + tempList[i]);
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]); // 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]); // 再删除空文件夹
			}
		}
	}

	/**
	 * 删除文件夹
	 * 
	 * @param //filePathAndName
	 *            String 文件夹路径及名称 如/sdcard/data/
	 * @param //fileContent
	 *            String
	 * @return boolean
*/	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			System.out.println("删除文件夹操作出错");
			e.printStackTrace();
		}
	}
	
	

	/**
	 * 判断是否有存储卡
	 */
	public static boolean checkStorage() {
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 清除应用程序的缓存（主要是webview的缓存）
	 */
	public static void clearWebViewCache(File file) {

		if (file != null && file.exists() && file.isDirectory()) {
			for (File item : file.listFiles()) {
				if (item.isDirectory()) {
					clearWebViewCache(item); // 递归删除所有子项
				} else {
					item.delete();
				}
			}
			file.delete();
		}
	}
	
	

	public static void saveObjToSp(Object obj, SharedPreferences sp, String name) {
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

	public static Object readObjFromSp(SharedPreferences sp, String name) {
		Object obj = null;

		String productBase64 = sp.getString(name, "");

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

	/**
	 * 创建文件夹
	 */
	public static void creatFiles() {
		try {
			// 判断SD卡是否存在
			if (checkStorage()) {
				// 绝对路径
				String fileDir = Environment.getExternalStorageDirectory()
						.getAbsolutePath() + File.separator + "smallpark";
				// 文件操作
				File file = new File(fileDir);
				// 判断
				if (!file.exists()) {
					file.mkdirs();
				}
			} else {
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 写入私有文件
	 * 
	 * @param context
	 *            上下文
	 * @param fileName
	 *            文件名
	 * @param info
	 *            文件内容
	 */
	public static void writeFile(Context context, String fileName, String info) {
		try {
			FileOutputStream fout = context.openFileOutput(fileName,
					Context.MODE_PRIVATE);
			byte[] bytes = info.getBytes();
			fout.write(bytes);
			fout.close();
		} catch (Exception err) {

		}
	}

	/**
	 * 读取私有文件内容
	 * 
	 * @param context
	 *            上下文
	 * @param fileName
	 *            文件名
	 * @return
	 */
	public static String readFile(Context context, String fileName) {
		try {
			FileInputStream fin = context.openFileInput(fileName);
			int length = fin.available();// 获取文件长度
			byte[] bytes = new byte[length];
			fin.read(bytes);
			//return EncodingUtils.getString(bytes, "ENCODING");
			return null;
		} catch (Exception err) {
			return "";
		}
	}

	/**
	 * 优化bitmap
	 * 
	 * @param context
	 *            上下文
	 * @param pathName
	 *            路径名称
	 * @param iv
	 *            图片控件
	 * @return 图片
	 */
	public static Bitmap compressBitmapBySize(Context context, String pathName,
			ImageView iv) {
		// 返回的图片
		Bitmap bitmap = null;
		try {
			// 1.计算出来屏幕的宽高.
			int windowWidth = iv.getHeight();
			int windowHeight = iv.getWidth();
			// 2.计算出来图片的宽高.
			Options opts = new Options();
			// 设置 不去真正的解析位图 不把他加载到内存 只是获取这个图片的宽高信息
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(pathName, opts);

			int bitmapHeight = opts.outHeight;
			int bitmapWidth = opts.outWidth;

			// 判断是否需要优化bitmap
			if (bitmapHeight > windowHeight || bitmapWidth > windowWidth) {
				int scaleX = bitmapWidth / windowWidth;
				int scaleY = bitmapHeight / windowHeight;
				if (scaleX > scaleY) {
					// 按照水平方向的比例缩放
					opts.inSampleSize = scaleX;
				} else {
					// 按照竖直方向的比例缩放
					opts.inSampleSize = scaleY;
				}
			} else {
				// 如果图片比手机屏幕小 不去缩放了.
				opts.inSampleSize = 1;
			}
			// 让位图工厂真正的去解析图片
			opts.inJustDecodeBounds = false;

			bitmap = BitmapFactory.decodeFile(pathName, opts);

		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(context, "图片解析错误", Toast.LENGTH_SHORT).show();
		}
		return bitmap;
	}

	/**
	 * 优化bitmap
	 * 
	 * @param //context
	 *            上下文
	 * @param //resID
	 *            资源id
	 * @return 图片
	 */
	public static Bitmap compressBitmapBySize(byte[] data, WindowManager wm) {
		// 返回的图片
		Bitmap bitmap = null;
		try {
			// 1.计算出来屏幕的宽高.
			int windowWidth = wm.getDefaultDisplay().getWidth();
			int windowHeight = wm.getDefaultDisplay().getHeight();
			// 2.计算出来图片的宽高.
			Options opts = new Options();
			// 设置 不去真正的解析位图 不把他加载到内存 只是获取这个图片的宽高信息
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeByteArray(data, 0, data.length, opts);

			int bitmapHeight = opts.outHeight;
			int bitmapWidth = opts.outWidth;

			// 判断是否需要优化bitmap
			if (bitmapHeight > windowHeight || bitmapWidth > windowWidth) {
				int scaleX = bitmapWidth / windowWidth;
				int scaleY = bitmapHeight / windowHeight;
				if (scaleX > scaleY) {
					// 按照水平方向的比例缩放
					opts.inSampleSize = scaleX;
				} else {
					// 按照竖直方向的比例缩放
					opts.inSampleSize = scaleY;
				}
			} else {
				// 如果图片比手机屏幕小 不去缩放了.
				opts.inSampleSize = 1;
			}
			// 让位图工厂真正的去解析图片
			opts.inJustDecodeBounds = false;

			bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, opts);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * 优化bitmap
	 * 
	 * @param context
	 *            上下文
	 * @param resID
	 *            资源id
	 * @return 图片
	 */
	public static Bitmap compressBitmapTest(Context context, int resID,
			WindowManager wm) {
		// 返回的图片
		Bitmap bitmap = null;
		try {
			InputStream is = context.getResources().openRawResource(resID);
			// 1.计算出来屏幕的宽高.
			int windowWidth = wm.getDefaultDisplay().getWidth();
			int windowHeight = wm.getDefaultDisplay().getHeight();
			// 2.计算出来图片的宽高.
			Options opts = new Options();
			// 设置 不去真正的解析位图 不把他加载到内存 只是获取这个图片的宽高信息
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(is, null, opts);
			int bitmapHeight = opts.outHeight;
			int bitmapWidth = opts.outWidth;
			// 判断是否需要优化bitmap
			if (bitmapHeight > windowHeight || bitmapWidth > windowWidth) {
				int scaleX = bitmapWidth / windowWidth;
				int scaleY = bitmapHeight / windowHeight;
				if (scaleX > scaleY) {
					// 按照水平方向的比例缩放
					opts.inSampleSize = scaleX;
				} else {
					// 按照竖直方向的比例缩放
					opts.inSampleSize = scaleY;
				}
			} else {
				// 如果图片比手机屏幕小 不去缩放了.
				opts.inSampleSize = 1;
			}
			// 让位图工厂真正的去解析图片
			opts.inJustDecodeBounds = false;
			// 注意: 流的操作
			bitmap = BitmapFactory.decodeStream(is, null, opts);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * 优化bitmap
	 * 
	 * @param context
	 *            上下文
	 * @param resID
	 *            资源id
	 * @param windowWidth
	 *            屏幕的宽(可直接调用getScreenWidth())
	 * @param windowHeight
	 *            屏幕的高(可直接调用getScreenHeight())
	 * @return 图片
	 */
	public static Bitmap compressBitmapTest(Context context, int resID,
			int windowWidth, int windowHeight) {
		// 返回的图片
		Bitmap bitmap = null;
		try {
			InputStream is = context.getResources().openRawResource(resID);
			// 计算出来图片的宽高.
			Options opts = new Options();
			// 设置 不去真正的解析位图 不把他加载到内存 只是获取这个图片的宽高信息
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(is, null, opts);
			int bitmapHeight = opts.outHeight;
			int bitmapWidth = opts.outWidth;
			// 判断是否需要优化bitmap
			if (bitmapHeight > windowHeight || bitmapWidth > windowWidth) {
				int scaleX = bitmapWidth / windowWidth;
				int scaleY = bitmapHeight / windowHeight;
				if (scaleX > scaleY) {
					// 按照水平方向的比例缩放
					opts.inSampleSize = scaleX;
				} else {
					// 按照竖直方向的比例缩放
					opts.inSampleSize = scaleY;
				}
			} else {
				// 如果图片比手机屏幕小 不去缩放了.
				opts.inSampleSize = 1;
			}
			// 让位图工厂真正的去解析图片
			opts.inJustDecodeBounds = false;
			// 注意: 流的操作
			bitmap = BitmapFactory.decodeStream(is, null, opts);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}
	public static Bitmap compressBitmapTest(Context context, String pathName,
			int windowWidth, int windowHeight) {
		// 返回的图片
		Bitmap bitmap = null;
		try {
			InputStream is = new FileInputStream(new File(pathName));
			// 计算出来图片的宽高.
			Options opts = new Options();
			// 设置 不去真正的解析位图 不把他加载到内存 只是获取这个图片的宽高信息
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(is, null, opts);
			int bitmapHeight = opts.outHeight;
			int bitmapWidth = opts.outWidth;
			// 判断是否需要优化bitmap
			if (bitmapHeight > windowHeight || bitmapWidth > windowWidth) {
				int scaleX = bitmapWidth / windowWidth;
				int scaleY = bitmapHeight / windowHeight;
				if (scaleX > scaleY) {
					// 按照水平方向的比例缩放
					opts.inSampleSize = scaleX;
				} else {
					// 按照竖直方向的比例缩放
					opts.inSampleSize = scaleY;
				}
			} else {
				// 如果图片比手机屏幕小 不去缩放了.
				opts.inSampleSize = 1;
			}
			// 让位图工厂真正的去解析图片
			opts.inJustDecodeBounds = false;
			// 注意: 流的操作
			bitmap = BitmapFactory.decodeStream(is, null, opts);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	/***
	 * 按质量压缩图片
	 * 
	 * @param //image
	 * @return
	 */
	public static File compressImage(String pathName) {
		File file = null;
		try {
			Bitmap image = BitmapFactory.decodeFile(pathName);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中

			int options = 100;
			while (baos.toByteArray().length / 1024.0 / 1024.0 > 2) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
				baos.reset();// 重置baos即清空baos
				image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
				options -= 10;// 每次都减少10
			}
			ByteArrayInputStream isBm = new ByteArrayInputStream(
					baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
			Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片

			FileOutputStream fos = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
		} catch (FileNotFoundException e) {
		}
		return file;
	}

}
