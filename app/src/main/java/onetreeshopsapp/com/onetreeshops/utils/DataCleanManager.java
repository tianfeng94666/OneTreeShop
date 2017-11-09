package onetreeshopsapp.com.onetreeshops.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by ERP on 2016/10/19.
 */
public class DataCleanManager {
    public static void clearAllCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
    }
    private static boolean deleteDir(File dir) {

        if (dir != null && dir.isDirectory()) {

            String[] children = dir.list();

            for (int i = 0; i < children.length; i++) {

                boolean success = deleteDir(new File(dir, children[i]));

                if (!success) {

                    return false;
                    }
                }
            }
        return dir.delete();
        }

}
