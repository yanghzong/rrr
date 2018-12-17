package com.example.mxy1217;

import android.content.Context;
import android.content.Intent;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mxy1217.base.BaseActivity;
import com.example.mxy1217.base.BasePresenter;
import com.example.mxy1217.widget.FlowLayout;

import butterknife.BindView;

import butterknife.OnClick;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.fl_content)
    FlowLayout flContent;
    @BindView(R.id.et_search_content)
    EditText etSearchContent;
    @BindView(R.id.btn_search)
    Button btnSearch;

    @Override
    protected void initData() {

    }

    @Override
    protected BasePresenter providePresenter() {
        return null;
    }

    @Override
    protected void setListener() {
        super.setListener();
        btnSearch.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                startActivity(new Intent(SearchActivity.this, WebActivity.class));
                return true;
            }
        });
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @OnClick(R.id.btn_search)
    public void onViewClicked() {
        String content = etSearchContent.getText().toString();
        if (!TextUtils.isEmpty(content)) {
            TextView txt = new TextView(SearchActivity.this);
            txt.setText(content);
            txt.setBackgroundResource(R.drawable.bg_gray);
            ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(20, 20, 20, 20);
            txt.setLayoutParams(params);
            txt.setPadding(10, 10, 10, 10);
            flContent.addView(txt);
        } else {
            Toast.makeText(this, "搜索内容不能为空", Toast.LENGTH_SHORT).show();
        }
    }
}
