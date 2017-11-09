package onetreeshopsapp.com.onetreeshops.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;



import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import onetreeshopsapp.com.onetreeshops.R;

/**
 * Created by admin on 2016-05-11.
 */
public class ImageLoad extends AsyncTask<String, String, Bitmap> {

    /** 弱引用 */
    private static Map<String, WeakReference<Bitmap>> imageCache = new HashMap<String, WeakReference<Bitmap>>();

    private String url;
    private ImageView image;

    public ImageLoad(String url, ImageView image) {
        this.url = url;
        this.image = image;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap = null;
        try {
            bitmap = doGetImage(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (null != bitmap) {
            image.setImageBitmap(bitmap);
        } else {
            image.setImageResource(R.mipmap.ic_no_picture);
        }
    }

    public Bitmap doGetImage(String url) throws Exception {
        // 先判断软应用内是否存在这个bitmap
        if (getImageCache(url) == null) {
            URL localURL = new URL(url);

            URLConnection connection = localURL.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

            httpURLConnection.setRequestProperty("Accept",
                    "image/png,image/*;q=0.8,*/*;q=0.5");// ////**********
            httpURLConnection.setRequestProperty("Accept-Encoding",
                    "gzip, deflate");
            httpURLConnection.setRequestProperty("Accept-Language",
                    "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
            httpURLConnection.setRequestProperty("Connection", "keep-alive");
            httpURLConnection.setRequestProperty("Cookie", HttpValue.COOKIE);
            System.out.println("get send:" + HttpValue.COOKIE);
            httpURLConnection
                    .setRequestProperty("User-Agent",
                            "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:43.0) Gecko/20100101 Firefox/43.0");
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setInstanceFollowRedirects(false);
            InputStream inputStream = null;

            inputStream = httpURLConnection.getInputStream();
            if(inputStream == null){
                Log.i("inputStream：	", ""+"null---------------");
            }

//			Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            Bitmap bitmap = getImage(inputStream);
//			Bitmap bitmap = BitmapFactory.decodeStream(new PatchInputStream(inputStream));

            if(bitmap == null){
//				Log.i("bitmap：	", ""+"null---------------");
            }
            bitmap = ImageTools.getInstance().ratio(bitmap, 80f, 80f, 16);

            addImageCache(url, bitmap);
            return bitmap;
        } else {
            return getImageCache(url);
        }
    }

    public Bitmap getImage(InputStream is) throws Exception {
        if (is == null){
            throw new RuntimeException("stream is null");
        } else {
            try {
                byte[] data=readStream(is);
                if(data!=null){
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    return bitmap;
                }else{
                    throw new RuntimeException("data[] is null");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            is.close();
            return null;
        }
    }

    /*
         * 得到图片字节流 数组大小
         * */
    public static byte[] readStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len=inStream.read(buffer)) != -1){
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
    }

//    public class PatchInputStream extends FilterInputStream{
//
//        protected PatchInputStream(InputStream in) {
//            super(in);
//            // TODO Auto-generated constructor stub
//        }
//
//        public long skip(long n)throws IOException{
//            long m=0l;
//            while(m<n){
//                long _m=in.skip(n-m);
//                if(_m==0l){
//                    break;
//                }
//                m+=_m;
//            }
//            return m;
//        }
//
//    }

    /**
     * 加载图片到软引用
     *
     * @param key
     *            软应用的key取值
     * @param bitmap
     *            位图
     */
    private static void addImageCache(String key, Bitmap bitmap) {
        if (bitmap != null) {
            if (getImageCache(key) == null) {
                imageCache.put(key.replaceAll("[^\\w]", ""),
                        new WeakReference<Bitmap>(bitmap));
            }
        }
    }

    /**
     * 获取软引用内的图
     *
     * @param key
     *            软应用key值
     * @return
     */
    private static Bitmap getImageCache(String key) {
//		Log.i("key:", ""+key);
        if (imageCache.get(key.replaceAll("[^\\w]", "")) != null) {
            return imageCache.get(key.replaceAll("[^\\w]", "")).get();
        } else {
            return null;
        }
    }
}

