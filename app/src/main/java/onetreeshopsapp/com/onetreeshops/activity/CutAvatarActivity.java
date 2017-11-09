package onetreeshopsapp.com.onetreeshops.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.utils.view.CutAvatarView;

public class CutAvatarActivity extends Activity {
	
	public static Bitmap bitmap;

	
	private CutAvatarView mCutAvatarView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cut_avatar);
		Intent intent = getIntent();

		mCutAvatarView = (CutAvatarView) findViewById(R.id.cut_avatar_view);
		Bitmap bitmap = intent.getParcelableExtra("bitmap");
		mCutAvatarView.setImageBitmap(bitmap);

		findViewById(R.id.btn_cut).setOnClickListener(doCut());
	}
	
	
	private View.OnClickListener doCut() {
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (bitmap != null && bitmap.isRecycled()) {
					bitmap.recycle();
				}
				bitmap = mCutAvatarView.clip(true);
				setResult(RESULT_OK);
				finish();
			}
		};
	}
	
}
