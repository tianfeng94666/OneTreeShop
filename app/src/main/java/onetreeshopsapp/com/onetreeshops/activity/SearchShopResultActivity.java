package onetreeshopsapp.com.onetreeshops.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.adapter.SearchShopsResultAdapter;
import onetreeshopsapp.com.onetreeshops.base.BaseActivity;
import onetreeshopsapp.com.onetreeshops.bean.SearchShopsResult;
import onetreeshopsapp.com.onetreeshops.http.JsonRPCAsyncTask;
import onetreeshopsapp.com.onetreeshops.utils.Const;
import onetreeshopsapp.com.onetreeshops.utils.GsonUtil;
import onetreeshopsapp.com.onetreeshops.utils.HttpValue;
import onetreeshopsapp.com.onetreeshops.utils.ToastUtils;

/**
 * Created by fiona on 2016/10/22.
 */
public class SearchShopResultActivity extends BaseActivity {
    @BindView(R.id.iv_title_left)
    ImageView ivTitleLeft;
    @BindView(R.id.et_dish_search)
    EditText etDishSearch;
    @BindView(R.id.lv_shops)
    ListView lvShops;
    @BindView(R.id.tv_hint)
    TextView tvHint;
    private String key;
    private String sig;
    private int GET_SEARCH_RESULT = 1;
    private SearchShopsResultAdapter searchShopsResultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_shops_result);
        ButterKnife.bind(this);

        getData();
        seach();
        init();
    }

    private void init() {
        etDishSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    //do something;
                    key = etDishSearch.getText().toString();
                    seach();
                    return true;
                }
                return false;
            }
        });

        lvShops.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchShopResultActivity.this, ShopActivity.class);
                intent.putExtra("stote_id", resBeen.get(position).getStore_id() + "");
                startActivity(intent);
            }
        });
    }


    private void seach() {
        try {
            JSONObject params = new JSONObject();
            params.put("sig", sig);
            params.put("key", key);
            new JsonRPCAsyncTask(this, mHandler,
                    GET_SEARCH_RESULT, HttpValue.getHttp() + Const.SEARCH_CODE, "call",
                    params).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getData() {
        key = getIntent().getStringExtra("key");
        sig = getIntent().getStringExtra("sig");
    }

    private List<SearchShopsResult.ResultBean.ResBean> resBeen;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            //门店信息

            if (msg.what == GET_SEARCH_RESULT) {
                String result = (String) msg.obj;

                if (result != null && result.contains("error")) {
                    ToastUtils.show(SearchShopResultActivity.this, "获取门店错误");
                    return;
                }
                SearchShopsResult searchShopsResult = GsonUtil.jsonToBean(result, SearchShopsResult.class);
                if (searchShopsResult != null && searchShopsResult.getResult().isFlag()) {
                    Log.v("搜索结果", result);
                    lvShops.setVisibility(View.VISIBLE);
                    tvHint.setVisibility(View.GONE);
                    resBeen = searchShopsResult.getResult().getRes();
                    searchShopsResultAdapter = new SearchShopsResultAdapter(SearchShopResultActivity.this, resBeen);
                    lvShops.setAdapter(searchShopsResultAdapter);
                } else {

                    lvShops.setVisibility(View.GONE);
                    tvHint.setVisibility(View.VISIBLE);
                    tvHint.setText("没有搜索到相关门店！");
                }

            }


        }
    };


    @OnClick({R.id.iv_title_left, R.id.et_dish_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                finish();
                break;
            case R.id.et_dish_search:
                break;

        }
    }
}
