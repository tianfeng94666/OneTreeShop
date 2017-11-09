package onetreeshopsapp.com.onetreeshops.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.base.BaseActivity;
import onetreeshopsapp.com.onetreeshops.bean.ModifyResult;
import onetreeshopsapp.com.onetreeshops.bean.UserManager;
import onetreeshopsapp.com.onetreeshops.dialog.ChoosePicturePopupwindow;
import onetreeshopsapp.com.onetreeshops.dialog.SexDialog;
import onetreeshopsapp.com.onetreeshops.http.JsonRPCAsyncTask;
import onetreeshopsapp.com.onetreeshops.utils.Const;
import onetreeshopsapp.com.onetreeshops.utils.GsonUtil;
import onetreeshopsapp.com.onetreeshops.utils.HttpValue;
import onetreeshopsapp.com.onetreeshops.utils.ImageUtils;
import onetreeshopsapp.com.onetreeshops.utils.IntentUtils;
import onetreeshopsapp.com.onetreeshops.utils.NetWorkUtils;
import onetreeshopsapp.com.onetreeshops.utils.SharedPreferencesUtils;
import onetreeshopsapp.com.onetreeshops.utils.ToastUtils;
import onetreeshopsapp.com.onetreeshops.utils.UIProgressUtil;
import onetreeshopsapp.com.onetreeshops.utils.view.CircleImageView;

/**
 * Created by ERP on 2016/10/9.
 */
public class MyEditActivity extends BaseActivity implements View.OnClickListener{
    private ImageButton mine_edit_back_imgbtn;
    private LinearLayout ll_set_mytelphone,ll_set_myimage;
    private  String imageurl;
    private CircleImageView mine_image;
    private TextView mine_name,mine_sex,mine_job,mine_modify_pwd,mine_telphone,tv_set_myimage;
    private ChoosePicturePopupwindow popupwindow;
    private static byte[] imageAsBytes;
    private static String encoded;
    private final String ACTION_NAME = "SET_SEX";
    private SharedPreferences sp;
    private String mCurrentPhotoPath;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private  File image;
    private String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_edit);
        sp = getSharedPreferences("SP",Context.MODE_PRIVATE);
        initview();

    }

    @Override
    protected void onResume() {
        super.onResume();
        UIProgressUtil.showProgress(MyEditActivity.this,"数据查询中...",true);
        getuserinfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }

    private void initview() {
        mine_edit_back_imgbtn = (ImageButton) findViewById(R.id.mine_edit_back_imgbtn);
        mine_edit_back_imgbtn.setOnClickListener(this);
        ll_set_myimage = (LinearLayout) findViewById(R.id.ll_set_myimage);
        ll_set_myimage.setOnClickListener(this);
        ll_set_mytelphone = (LinearLayout) findViewById(R.id.ll_set_mytelphone);
        ll_set_mytelphone.setOnClickListener(this);
        mine_image = (CircleImageView) findViewById(R.id.mine_image);
        mine_name = (TextView) findViewById(R.id.mine_name);
        /*if (mine_image.getDrawable() != null) {
            //getInfo(MyEditActivity.this);
            Glide.with(this)
                    .load(HttpValue.getHttp() + "/pos/binary/image?model=res.users&id=" + HttpValue.SP_USERID + "&field=image")
                    .into(mine_image);
        }*/
        mine_image.setOnClickListener(this);
        mine_name.setOnClickListener(this);
        mine_sex = (TextView) findViewById(R.id.mine_sex);
        mine_sex.setOnClickListener(this);
        mine_job = (TextView) findViewById(R.id.mine_job);
        mine_job.setOnClickListener(this);
        mine_telphone = (TextView) findViewById(R.id.mine_telphone);

        mine_modify_pwd = (TextView) findViewById(R.id.mine_modify_pwd);
        mine_modify_pwd.setOnClickListener(this);
        popupwindow = new ChoosePicturePopupwindow(MyEditActivity.this,this);
        registerBoradcastReceiver();

    }
    private void setMy_img(String url){
        Log.v("url",HttpValue.getHttp()+url);
        imageurl = HttpValue.getHttp()+url;
        Glide.with(this)
                .load(imageurl)
                .error(R.mipmap.icon)
                .into(mine_image);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.mine_edit_back_imgbtn:
                //IntentUtils.getIntentUtils().intent(MyEditActivity.this, IndexActivity.class);
                finish();
                break;
            case R.id.ll_set_myimage:
                popupwindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.ll_set_mytelphone:
                IntentUtils.getIntentUtils().intent(MyEditActivity.this,BindingPhoneActivity.class);
                break;
            case R.id.mine_name:
                String type = "modifyname";
                startactivity(type,mine_name.getText().toString().trim());
                break;
            case R.id.mine_sex:
                SexDialog sexDialog = new SexDialog(MyEditActivity.this);
                sexDialog.show();
                break;
            case R.id.mine_job:
                String type1 = "modifyjob";
                startactivity(type1,mine_job.getText().toString().trim());
                break;
            case R.id.mine_modify_pwd:
                Intent intent2 = new Intent(MyEditActivity.this,ModifyPasswordActivity.class);
                intent2.putExtra("type","修改账户密码");
                startActivity(intent2);
                finish();
                break;
            case R.id.scene_camera_btn:
                //startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), 1);
                dispatchTakePictureIntent();
                popupwindow.dismiss();
                break;
            case R.id.scene_photo_btns:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 2);
                popupwindow.dismiss();
                break;

        }
    }
    private void getuserinfo() {
        if (!NetWorkUtils.getNetConnecState(this)) {
            Toast.makeText(MyEditActivity.this, "请检查网络是否连接！", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            JSONObject params = new JSONObject();
            params.put("user_id",HttpValue.SP_USERID);
            new JsonRPCAsyncTask(MyEditActivity.this, mHandler,
                    Const.USER_INFO_CODE, HttpValue.getHttp() + Const.USER_INFO, "call",
                    params).execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void edituserinfo(String sex) {
        if (!NetWorkUtils.getNetConnecState(this)) {
            Toast.makeText(MyEditActivity.this, "请检查网络是否连接！", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            JSONObject params = new JSONObject();
            params.put("user_id",HttpValue.SP_USERID);
            params.put("gender",sex);
            new JsonRPCAsyncTask(MyEditActivity.this, mHandler,
                    Const.USER_INFO_MODIFY_CODE, HttpValue.getHttp() + Const.USER_INFO_MODIFY, "call",
                    params).execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            String result = (String) msg.obj;
            UIProgressUtil.cancelProgress();
            if (result==null){
                ToastUtils.show(MyEditActivity.this,"亲，服务器出问题啦，请稍后再试！");
                return;
            }
            if (msg.what == Const.USER_INFO_CODE) {
                System.out.println("查询用户返回数据为：" + result);
                if (GsonUtil.isError(result)) {
                    ToastUtils.show(MyEditActivity.this, "查询我的信息出错");
                    return;
                }
                UserManager userManager = GsonUtil.jsonToBean(result,UserManager.class);
                if (userManager.getResult().getRes()!=null&&userManager.getResult().getFlag()){
                    setuserinfo(userManager);
                    setMy_img(userManager.getResult().getRes().getImage());
                }
            }else if (msg.what == Const.USER_INFO_MODIFY_CODE ){
                System.out.println("修改返回数据为：" + result);
                if (GsonUtil.isError(result)) {
                    ToastUtils.show(MyEditActivity.this, "修改出错");
                    return;
                }
                ModifyResult modifyResult = GsonUtil.jsonToBean(result, ModifyResult.class);
                if (modifyResult.getResult().getFlag()) {
                    ToastUtils.show(MyEditActivity.this,"修改成功！");
                }else {
                    ToastUtils.show(MyEditActivity.this,"修改失败！");
                }
            }
        }
    };

    private void setuserinfo(UserManager userInfo) {
        mine_name.setText(userInfo.getResult().getRes().getName());
        String sex = userInfo.getResult().getRes().getGender();
        if (sex.equals("female")){
            mine_sex.setText("女");
        }else if(sex.equals("male")){
            mine_sex.setText("男");
        }else {
            mine_sex.setText("性别");
        }
        mine_job.setText("false"==userInfo.getResult().getRes().getJob()?"职业" : userInfo.getResult().getRes().getJob());
        // 设置保存的登陆名
        String phone = (null == SharedPreferencesUtils.readObjFromSp(sp,
                Const.SP_USERNAME) ? "" : ""
                + SharedPreferencesUtils.readObjFromSp(sp, Const.SP_USERNAME));
        mine_telphone.setText(phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));

    }

    private void startactivity(String type,String date){
        Intent intent = new Intent(MyEditActivity.this,ModifyInfoActivity.class);
        intent.putExtra("type",type);
        intent.putExtra("data",date);
        startActivity(intent);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println(data);
        /*if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            setPic();
        }*/
        if (resultCode == -1) {
            if (data != null) {
                switch (requestCode) {
                    case REQUEST_TAKE_PHOTO:
                        /*Bundle extras = data.getExtras();
                        Bitmap bm = (Bitmap) extras.get("data");
                        // 调用本地存储图片
                        saveInfo(MyEditActivity.this, bm);
                        mine_image.setImageBitmap(bm);*/
                        setPic();
                        postImg();
                        break;
                    case 2:
                        Uri uri = data.getData();
                 ContentResolver cr = getContentResolver();
                        try {
                            Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                            Bitmap newBitmap = ImageUtils.scaleImg(bitmap, 200, 200);
                            // 调用本地存储图片
                            //saveInfo(MyEditActivity.this, newBitmap);
                            saveCropPic(newBitmap);
                            mine_image.setImageBitmap(newBitmap);

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * 把图片转换为字节流存储到本地
     */
    /*public static boolean saveInfo(Context context, Bitmap bm) {
        SharedPreferences sp = context.getSharedPreferences("photo", Context.MODE_PRIVATE);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        SharedPreferences.Editor edit;
        try {
            bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();
            encoded = Base64.encodeToString(b, Base64.DEFAULT);
            edit = sp.edit();
            edit.putString("btm", encoded);
            edit.commit();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }*/
    /**
     * 获取本地图片
     */
    /*public  byte[] getInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences("photo", Context.MODE_PRIVATE);
        String picture = (String) sp.getString("btm", "");
        imageAsBytes = Base64.decode(picture.getBytes(), Base64.DEFAULT);
        if (imageAsBytes.length == 0) {
            mine_image.setImageResource(R.mipmap.mine_icon_person);
        } else {
            mine_image.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
             myimagebitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
        }
        return imageAsBytes;
    }*/
    //广播接收
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String sex = intent.getExtras().getString("sex");
            if(action.equals(ACTION_NAME)){
               if (sex.equals("female")){
                   mine_sex.setText("女");
               }else if(sex.equals("male")){
                   mine_sex.setText("男");
               }
                edituserinfo(sex);
            }
        }
    };

    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(ACTION_NAME);
        registerReceiver(mBroadcastReceiver, myIntentFilter);
    }
    //从相册中选择，保存所选择的图片以便上传服务器
    private void saveCropPic(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileOutputStream fis = null;
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        try {
            fis = new FileOutputStream(createImageFile());
            fis.write(baos.toByteArray());
            fis.flush();
            postImg();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != baos) {
                    baos.close();
                }
                if (null != fis) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //拍照
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }
    //新建图片文件
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String storageDir = Environment.getExternalStorageDirectory() + "/OneTreeShop";
        File dir = new File(storageDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
         path = storageDir + "/" + timeStamp + ".jpg";
        System.out.println("zhaopian  path<<"+path);
        image = new File(path);
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.i("upload", "photo path = " + mCurrentPhotoPath);
        return image;
    }

    //拍照后设置头像
    private void setPic() {
         int targetW = mine_image.getWidth();
         int targetH = mine_image.getHeight();
        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath,bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath,bmOptions);
        mine_image.setImageBitmap(bitmap);
    }
    /**
     * 上传头像图片到服务器
     */
    public void postImg() {
        RequestParams params = new RequestParams(HttpValue.getHttp() + Const.USER_IMAGE);
        params.addBodyParameter("user_id", HttpValue.SP_USERID + "");
        params.addBodyParameter("pic", image);
        params.addHeader("Cookie", HttpValue.SESSION_ID);
        System.out.println("图片Cookie为：" + HttpValue.COOKIE);
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                if (result.equals("True")) {
                    //ToastUtils.show(MyEditActivity.this, "头像设置成功！");
                    System.out.println("图片上传成功！");

                } else {
                    //ToastUtils.show(MyEditActivity.this, "头像设置失败！");
                    System.out.println("图片上传失败！");
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                System.out.println("图片上传erro！");
            }

            @Override
            public void onCancelled(CancelledException e) {

            }
            @Override
            public void onFinished() {
                System.out.println("图片上传over！");
            }
        });
    }

}
