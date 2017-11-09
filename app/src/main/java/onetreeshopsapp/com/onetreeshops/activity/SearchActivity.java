package onetreeshopsapp.com.onetreeshops.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.base.BaseActivity;
import onetreeshopsapp.com.onetreeshops.base.MyListView;
import onetreeshopsapp.com.onetreeshops.sqlite.RecordSQLiteOpenHelper;

/**
 * Created by fiona on 2016/10/21.
 */
public class SearchActivity extends BaseActivity {

    @BindView(R.id.image_pull)
    ImageView imagePull;
    @BindView(R.id.et_dish_search)
    EditText etDishSearch;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.listView)
    MyListView listView;
    @BindView(R.id.tv_clear)
    TextView tvClear;
    TextView tvPopuwindowProduct;
    TextView tvPopuwindowShop;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.iv_title_left)
    ImageView ivTitleLeft;
    private RecordSQLiteOpenHelper helper = new RecordSQLiteOpenHelper(this);
    ;
    private SQLiteDatabase db;
    private BaseAdapter adapter;
    private PopupWindow pop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        getData();
        // 初始化控件
        initView();
    }

    private void getData() {
        etDishSearch.setText(getIntent().getStringExtra("searchKey"));
    }

    /**
     * 插入数据
     */
    private void insertData(String tempName) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into records(name) values('" + tempName + "')");
        db.close();
    }

    /**
     * 模糊查询数据
     */
    private void queryData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name like '%" + tempName + "%' order by id desc ", null);
        // 创建adapter适配器对象
        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String[]{"name"},
                new int[]{android.R.id.text1}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        // 设置适配器
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /**
     * 检查数据库中是否已经有该条记录
     */
    private boolean hasData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});
        //判断是否有下一个
        return cursor.moveToNext();
    }

    /**
     * 清空数据
     */
    private void deleteData() {
        db = helper.getWritableDatabase();
        db.execSQL("delete from records");
        db.close();
    }

    private void initView() {
        etDishSearch = (EditText) findViewById(R.id.et_dish_search);
        tvTip = (TextView) findViewById(R.id.tv_tip);
        listView = (MyListView) findViewById(R.id.listView);
        tvClear = (TextView) findViewById(R.id.tv_clear);
        // 清空搜索历史
        tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
                queryData("");
            }
        });

        // 搜索框的键盘搜索键点击回调
        etDishSearch.setOnKeyListener(new View.OnKeyListener() {// 输入完后按键盘上的搜索键

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {// 修改回车键功能
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                            getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    // 按完搜索键后将当前查询的关键字保存起来,如果该关键字已经存在就不执行保存
                    boolean hasData = hasData(etDishSearch.getText().toString().trim());
                    if (!hasData) {
                        insertData(etDishSearch.getText().toString().trim());
                        queryData("");
                    }
                    // TODO 根据输入的内容模糊查询商品，并跳转到另一个界面，由你自己去实现
                    respondIntent();
                }
                return false;
            }
        });

        // 搜索框的文本变化实时监听
        etDishSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() == 0) {
                    tvTip.setText("搜索历史");
                } else {
                    tvTip.setText("搜索结果");
                }
                String tempName = etDishSearch.getText().toString();
                // 根据tempName去模糊查询数据库中有没有数据
                queryData(tempName);

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                String name = textView.getText().toString();
                etDishSearch.setText(name);
//                Toast.makeText(SearchActivity.this, name, Toast.LENGTH_SHORT).show();
                // TODO 获取到item上面的文字，根据该关键字跳转到另一个页面查询，由你自己去实现
                respondIntent();
            }
        });


        // 第一次进入查询所有的历史记录
        queryData("");
        // 调整EditText左边的搜索按钮的大小
//        Drawable drawable = getResources().getDrawable(R.mipmap.find);
//        drawable.setBounds(0, 0, 60, 60);// 第一0是距左边距离，第二0是距上边距离，60分别是长宽
//        etDishSearch.setCompoundDrawables(drawable, null, null, null);// 只放左边


        imagePull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popwindow();
                pop.showAsDropDown(v, 10, 10);
            }
        });
        ivTitleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void respondIntent() {
        Intent intent;
        if (tvType.getText().equals("宝贝")) {
            intent = new Intent(this, SearchproductResultActivity.class);
            intent.putExtra("key", etDishSearch.getText().toString());
            intent.putExtra("sig", "product");
        } else {
            intent = new Intent(this, SearchShopResultActivity.class);
            intent.putExtra("key", etDishSearch.getText().toString());
            intent.putExtra("sig", "store");
        }
        startActivity(intent);

        finish();
    }

    private void popwindow() {
        pop = new PopupWindow(this);
        View view = this.getLayoutInflater().inflate(R.layout.popuwindow_choose_type, null);

        tvPopuwindowProduct = (TextView) view.findViewById(R.id.tv_popuwindow_product);
        tvPopuwindowShop = (TextView) view.findViewById(R.id.tv_popuwindow_shop);
        pop.setWidth(240);
        pop.setHeight(200);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);
        tvPopuwindowProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvType.setText("宝贝");
                pop.dismiss();
            }
        });
        tvPopuwindowShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvType.setText("门店");
                pop.dismiss();
            }
        });
    }
}
