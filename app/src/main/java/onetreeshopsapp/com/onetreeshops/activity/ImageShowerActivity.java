package onetreeshopsapp.com.onetreeshops.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.dialog.ImageLoadingDialog;
import onetreeshopsapp.com.onetreeshops.utils.HttpValue;
import onetreeshopsapp.com.onetreeshops.utils.UIProgressUtil;

public class ImageShowerActivity extends Activity {
	private ImageView imageView;
	private ImageButton myimage_back_imgbtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imageshower);
		UIProgressUtil.showProgress(this,"加载中...");
		imageView = (ImageView) findViewById(R.id.iv_show_myimage);
		myimage_back_imgbtn = (ImageButton) findViewById(R.id.myimage_back_imgbtn);
		myimage_back_imgbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		Intent intent = getIntent();
	    String imageurl =  intent.getStringExtra("imageurl");
		Picasso
				.with(this)
				.load(imageurl)
				.fit()
				.error(R.mipmap.icon)
				.into(imageView, new Callback() {
					@Override
					public void onSuccess() {
						UIProgressUtil.cancelProgress();
						System.out.println("1111111");
					}
					@Override
					public void onError() {
						UIProgressUtil.cancelProgress();
						System.out.println("22222222");
					}
				});
		/*final ImageLoadingDialog dialog = new ImageLoadingDialog(this);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				dialog.dismiss();
			}
		}, 1000 * 2);*/
	}

	/*@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		finish();
		return true;
	}*/



}
