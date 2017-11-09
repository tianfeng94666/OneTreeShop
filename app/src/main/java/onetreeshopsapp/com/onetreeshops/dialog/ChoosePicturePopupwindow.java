package onetreeshopsapp.com.onetreeshops.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

import onetreeshopsapp.com.onetreeshops.R;


public class ChoosePicturePopupwindow extends PopupWindow implements OnClickListener, OnTouchListener {

	private View mMenuView;
	private Button scene_camera_btn, scene_photo_btns, cancel_btns;

	@SuppressLint("InflateParams")
	public ChoosePicturePopupwindow(Context context, OnClickListener onClickListener) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.buttom_item, null);
		
		scene_camera_btn = (Button) mMenuView.findViewById(R.id.scene_camera_btn);
		scene_camera_btn.setOnClickListener(onClickListener);
		scene_photo_btns = (Button) mMenuView.findViewById(R.id.scene_photo_btns);
		scene_photo_btns.setOnClickListener(onClickListener);
		cancel_btns = (Button) mMenuView.findViewById(R.id.cancel_btns);
		cancel_btns.setOnClickListener(this);
		
		this.setContentView(mMenuView);
		this.setWidth(LayoutParams.MATCH_PARENT);
		this.setHeight(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		this.setAnimationStyle(R.style.PopupAnimation);
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		this.setBackgroundDrawable(dw);
		mMenuView.setOnTouchListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.cancel_btns:
			dismiss();
			break;
		}
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == MotionEvent.ACTION_UP) {
			dismiss();
		}
		return true;
	}
}
