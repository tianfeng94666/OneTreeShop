package onetreeshopsapp.com.onetreeshops.utils;

import android.content.Context;
import android.content.Intent;

/**
*
*@author tianfeng
*create at 2016-05-19
*/
public class IntentUtils {
    private  static IntentUtils intentUtils;

    public static  IntentUtils getIntentUtils(){
        if(intentUtils == null)
            intentUtils = new IntentUtils();
        return intentUtils;
    }

    public  void intent(Context context,Class t){
        Intent intent = new Intent();
        intent.setClass(context,t);
        context.startActivity(intent);

    }
    public  void intent(Context context,Class t,String name){
        Intent intent = new Intent();
        intent.setClass(context,t);
        intent.putExtra("username",name);
        context.startActivity(intent);

    }
}
