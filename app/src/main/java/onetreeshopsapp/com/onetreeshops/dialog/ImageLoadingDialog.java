package onetreeshopsapp.com.onetreeshops.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import onetreeshopsapp.com.onetreeshops.R;

public class ImageLoadingDialog extends Dialog {

	public ImageLoadingDialog(Context context) {
		super(context, R.style.myDialogTheme);
	}

	private ImageLoadingDialog(Context context, int theme) {
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_imageloading);
	}

}
