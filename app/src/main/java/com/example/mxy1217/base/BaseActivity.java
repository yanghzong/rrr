package com.example.mxy1217.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * date:2018/12/17
 * author:mxy(M)
 * function:
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements IView {

    protected P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provideLayoutId());
        ButterKnife.bind(this);
        presenter = providePresenter();
        attachIView();
        initView();
        setListener();
        initData();
    }

    protected abstract void initData();

    protected void setListener() {

    }

    protected void initView() {

    }

    private void attachIView() {
        if (presenter != null) {
            presenter.attach(this);
        }
    }

    protected abstract P providePresenter();

    protected abstract int provideLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detach();
        }
    }
}
