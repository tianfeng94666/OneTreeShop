package onetreeshopsapp.com.onetreeshops.application;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;


import org.xutils.x;

import onetreeshopsapp.com.onetreeshops.service.LocationService;
import onetreeshopsapp.com.onetreeshops.utils.CrashHandler;

/**
 * Created by admin on 2016-05-04.
 */
public class LYJApplication extends Application {
    private boolean isHaveInternet = false;
    private static LYJApplication lyjApplication;
    private boolean isDownload = false;
    private boolean isInDownload = false;
    public LocationService locationService;
    public Vibrator mVibrator;
    public boolean isInDownload() {
        return isInDownload;
    }

    public void setInDownload(boolean inDownload) {
        isInDownload = inDownload;
    }

    public boolean isDownload() {
        return isDownload;
    }

    public void setDownload(boolean download) {
        isDownload = download;
    }

    public boolean isHaveInternet() {
        return isHaveInternet;
    }

    public void setHaveInternet(boolean haveInternet) {
        isHaveInternet = haveInternet;
    }

    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);//Xutils初始化
        lyjApplication = this;
        //异常捕捉
        /*CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());*/
        /***
         * 初始化定位sdk，建议在Application中创建
         */
        locationService = new LocationService(getApplicationContext());
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
    }
    public static LYJApplication getLyjApplication() {
        return lyjApplication;
    }

    public void setLyjApplication(LYJApplication lyjApplication) {
        this.lyjApplication = lyjApplication;
    }

}
