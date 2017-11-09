package onetreeshopsapp.com.onetreeshops.utils.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import onetreeshopsapp.com.onetreeshops.R;

/**
 * Created by ERP on 2016/12/23.
 */
public class ArrowTextView extends TextView{
    public ArrowTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ini(context, attrs);
    }
    private void ini(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ArrowTextView);
        radius=typedArray.getDimension(R.styleable.ArrowTextView_radius, 0);
        arrowWidth=typedArray.getDimension(R.styleable.ArrowTextView_arrowWidth, 0);
        arrowInHeight=typedArray.getDimension(R.styleable.ArrowTextView_arrowInHeight, 0);
        color=typedArray.getColor(R.styleable.ArrowTextView_bg, Color.RED);
    }
    public ArrowTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        ini(context, attrs);
    }
    public ArrowTextView(Context context) {
        super(context);
    }
    private float radius;
    private float arrowWidth;
/**
 * 三角形箭头在此高度居中......
 */
    private float arrowInHeight;
    private int color;
    /**
     * @param arrowWidth  三角形箭头的宽度.......
     */
    public void setArrowWidth(float arrowWidth){
        this.arrowWidth=arrowWidth;
        invalidate();

    }
    /**
     * @param arrowInHeight   三角形箭头在此高度居中......
     */
    public void setArrowInHeight(float arrowInHeight){
        this.arrowInHeight=arrowInHeight;
        invalidate();
    }
    /**
     * @param radius  矩形四角圆角的半径..........
     */
    public void setRadius(float radius){
        this.radius=radius;
        invalidate();

    }
    /**
     * @param color   箭头矩形的背景色.........
     */
    public void setBgColor(int color){
        this.color=color;
        invalidate();

    }
    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint=new Paint();
        paint.setColor(color==0?Color.RED:color);
        paint.setAntiAlias(true);
        if(radius==0){
            radius= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
        }
        if(arrowWidth==0){
            arrowWidth=TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        }
//带圆角的矩形(左边减去三角形的宽度...........)
        int left = (int) (getPaddingLeft()-arrowWidth);
        int height=getHeight();
        canvas.drawRoundRect(new RectF(left, 0, getWidth(), height), radius, radius, paint);
        if(arrowInHeight==0){
            arrowInHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
        }
        height = (int) (height>arrowInHeight?arrowInHeight:height);
//画三角形
        Path path=new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        float yMiddle = height/2;
        float yTop=yMiddle-(arrowWidth/2);
        float yBottom=yMiddle+(arrowWidth/2);
        path.moveTo(0, yMiddle);
        path.lineTo(left, yTop);
        path.lineTo(left, yBottom);
        path.lineTo(0, yMiddle);
        path.close();
        canvas.drawPath(path, paint);
// canvas.restore();
// canvas.translate(left, 0);
        super.onDraw(canvas);

    }





}
