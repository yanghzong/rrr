package com.example.mxy1217;

import android.content.Context;
import android.content.Intent;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mxy1217.adapter.ProductAdapter;
import com.example.mxy1217.adapter.ShopperAdapter;
import com.example.mxy1217.base.BaseActivity;
import com.example.mxy1217.bean.CartBean;
import com.example.mxy1217.presenter.CartPresenter;
import com.example.mxy1217.view.ICartView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<CartPresenter> implements ICartView {
    @BindView(R.id.rv_carts)
    RecyclerView rvCarts;
    @BindView(R.id.cb_check_all)
    CheckBox cbCheckAll;
    @BindView(R.id.txt_total_price)
    TextView txtTotalPrice;
    @BindView(R.id.txt_edit)
    TextView txtEdit;
    private List<CartBean.DataBean> list;
    private ShopperAdapter shopperAdapter;

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void getCarts(CartBean data) {
        list.addAll(data.getData());
        shopperAdapter.notifyDataSetChanged();
    }

    @Override
    protected void setListener() {
        super.setListener();
        cbCheckAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isChecked = cbCheckAll.isChecked();
                for (CartBean.DataBean dataBean : list) {
                    dataBean.setChecked(isChecked);
                    List<CartBean.DataBean.ListBean> products = dataBean.getList();
                    for (CartBean.DataBean.ListBean product : products) {
                        product.setChecked(isChecked);
                    }
                }
                calculatePrice();
                shopperAdapter.notifyDataSetChanged();
            }
        });
    }

    private void calculatePrice() {
        float totalPrice = 0;
        for (CartBean.DataBean dataBean : list) {
            List<CartBean.DataBean.ListBean> products = dataBean.getList();
            for (CartBean.DataBean.ListBean product : products) {
                if (product.isChecked()) {
                    totalPrice += product.getPrice() * product.getNum();
                }
            }
        }
        txtTotalPrice.setText("合计：￥" + totalPrice);
    }

    @Override
    public void onCartFailed(Throwable t) {
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void initData() {
        presenter.getCarts();

        list = new ArrayList<>();
        shopperAdapter = new ShopperAdapter(this, list);
        shopperAdapter.setOnShopperClickListener(new ShopperAdapter.OnShopperClickListener() {
            @Override
            public void onShopperClick(int position, boolean isChecked) {
                if (!isChecked) {
                    cbCheckAll.setChecked(false);
                } else {
                    boolean isAllShopperChecked = true;
                    for (CartBean.DataBean dataBean : list) {
                        if (!dataBean.isChecked()) {
                            isAllShopperChecked = false;
                            break;
                        }
                    }
                    cbCheckAll.setChecked(isAllShopperChecked);
                }
                calculatePrice();
            }
        });
        shopperAdapter.setOnAddDecreaseProductListener(new ProductAdapter.OnAddDecreaseProductListener() {
            @Override
            public void onChange(int position, int num) {
                calculatePrice();
            }
        });
        rvCarts.setLayoutManager(new LinearLayoutManager(this));
        rvCarts.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvCarts.setAdapter(shopperAdapter);
    }

    @Override
    protected CartPresenter providePresenter() {
        return new CartPresenter();
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_main;
    }

    @OnClick(R.id.txt_edit)
    public void onViewClicked() {
        startActivity(new Intent(this, SearchActivity.class));
    }
}
