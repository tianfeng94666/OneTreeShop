package onetreeshopsapp.com.onetreeshops.update;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import java.io.File;

import onetreeshopsapp.com.onetreeshops.utils.FileUtil;
import onetreeshopsapp.com.onetreeshops.utils.ToastUtils;

/**
 * Created by ERP on 2016/11/19.
 */
public class InitApkBroadCastReceiver extends BroadcastReceiver {
    /* 下载包安装路径 */
    private static  String savePath = null;
    /* 下载包文件名 */
    private static  String saveFileName = null;
    private Context mContext;
    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        if (Intent.ACTION_PACKAGE_ADDED.equals(intent.getAction())) {
            //ToastUtils.show(mContext,"监听到系统广播添加！");
        }
        if (Intent.ACTION_PACKAGE_REMOVED.equals(intent.getAction())) {
            //ToastUtils.show(mContext,"监听到系统广播移除");
        }
        if (Intent.ACTION_PACKAGE_REPLACED.equals(intent.getAction())) {
            ToastUtils.show(mContext,"监听到系统广播替换");
            //delSmallparkApk();
        }
        if (Intent.ACTION_PACKAGE_INSTALL.equals(intent.getAction())) {
            //ToastUtils.show(mContext,"监听到系统广播安装");
        }
    }
    /**
     * 删除已经更新的apk
     */
    /**
     * 删除还未安装的apk
     */
    public  void delSmallparkApk() {
        savePath = Environment.getExternalStorageDirectory() + File.separator +"OneTreeShopDownload" + File.separator;
        saveFileName = "onetreeshop.apk";
        if (FileUtil.checkStorage()) {
            File apkfile = new File(savePath+saveFileName);
            if (!apkfile.exists()) {
                ToastUtils.show(mContext,"安装包不存在！");
            }
           else if (apkfile.exists()) {
                apkfile.delete();
                System.gc();
                if (apkfile.delete()){
                    ToastUtils.show(mContext,"安装包已删除！");
                }else {
                    ToastUtils.show(mContext,"安装包删除失败！");
                }
            }
        }
    }

}
