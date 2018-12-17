package com.example.mxy1217.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;


import com.example.mxy1217.R;
import com.example.mxy1217.bean.CartBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * date:2018/12/17
 * author:mxy(M)
 * function:
 */

public class ShopperAdapter extends RecyclerView.Adapter<ShopperAdapter.ViewHolder> {

    private Context context;
    private List<CartBean.DataBean> list;

    public ShopperAdapter(Context context, List<CartBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    // 一级列表发生变化的监听
    public interface OnShopperClickListener {
        void onShopperClick(int position, boolean isChecked);
    }

    private OnShopperClickListener onShopperClickListener;

    public void setOnShopperClickListener(OnShopperClickListener onShopperClickListener) {
        this.onShopperClickListener = onShopperClickListener;
    }

    // 二级列表的加减器监听
    public ProductAdapter.OnAddDecreaseProductListener onAddDecreaseProductListener;

    public void setOnAddDecreaseProductListener(ProductAdapter.OnAddDecreaseProductListener onAddDecreaseProductListener) {
        this.onAddDecreaseProductListener = onAddDecreaseProductListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shopper, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final CartBean.DataBean shopper = list.get(position);
        holder.txtShopperName.setText(shopper.getSellerName());

        // 产品的列表
        List<CartBean.DataBean.ListBean> list = this.list.get(position).getList();
        final ProductAdapter productAdapter = new ProductAdapter(context, list);
        holder.rvProduct.setLayoutManager(new LinearLayoutManager(context));
        // 给二级列表添加一个加减器监听
        if (onAddDecreaseProductListener != null) {
            productAdapter.setOnAddDecreaseProductListener(onAddDecreaseProductListener);
        }
        // 二级条目的复选事件
        productAdapter.setOnProductClickListener(new ProductAdapter.OnProductClickListener() {
            @Override
            public void onProductClick(int position, boolean isChecked) {
                if (!isChecked) {
                    shopper.setChecked(false);
                    if (onShopperClickListener != null) {
                        onShopperClickListener.onShopperClick(position, false);
                    }
                } else {
                    boolean isAllProductChecked = true;
                    for (CartBean.DataBean.ListBean listBean : shopper.getList()) {
                        if (!listBean.isChecked()) {
                            isAllProductChecked = false;
                            break;
                        }
                    }
                    shopper.setChecked(isAllProductChecked);
                    if (onShopperClickListener != null) {
                        onShopperClickListener.onShopperClick(position, true);
                    }
                }
                notifyDataSetChanged();
                onAddDecreaseProductListener.onChange(0, 0);
            }
        });
        holder.rvProduct.setAdapter(productAdapter);

        holder.cbShopper.setOnCheckedChangeListener(null);
        holder.cbShopper.setChecked(shopper.isChecked());
        holder.cbShopper.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                shopper.setChecked(b);

                List<CartBean.DataBean.ListBean> productList = shopper.getList();
                for (CartBean.DataBean.ListBean listBean : productList) {
                    listBean.setChecked(b);
                }
                productAdapter.notifyDataSetChanged();

                if (onShopperClickListener != null) {
                    onShopperClickListener.onShopperClick(position, b);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cb_shopper)
        CheckBox cbShopper;
        @BindView(R.id.txt_shopper_name)
        TextView txtShopperName;
        @BindView(R.id.rv_product)
        RecyclerView rvProduct;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
