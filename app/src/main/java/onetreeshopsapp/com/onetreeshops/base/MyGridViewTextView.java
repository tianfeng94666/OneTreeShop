package onetreeshopsapp.com.onetreeshops.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by fiona on 2016/9/12.
 */
public class MyGridViewTextView extends TextView
{

    public MyGridViewTextView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public MyGridViewTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public boolean isFocused()
    {
        return true;
    }

}
