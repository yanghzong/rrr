package com.example.mxy1217.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.mxy1217.R;
import com.example.mxy1217.bean.CartBean;
import com.example.mxy1217.widget.MyAddDecreaseView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * date:2018/12/17
 * author:mxy(M)
 * function:
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private Context context;
    private List<CartBean.DataBean.ListBean> list;

    public ProductAdapter(Context context, List<CartBean.DataBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    // 二级条目发生变化的监听
    public interface OnProductClickListener {
        void onProductClick(int position, boolean isChecked);
    }

    private OnProductClickListener onProductClickListener;

    public void setOnProductClickListener(OnProductClickListener onProductClickListener) {
        this.onProductClickListener = onProductClickListener;
    }

    // 加减器发生变化的监听
    public interface OnAddDecreaseProductListener {
        void onChange(int position, int num);
    }

    private OnAddDecreaseProductListener onAddDecreaseProductListener;

    public void setOnAddDecreaseProductListener(OnAddDecreaseProductListener onAddDecreaseProductListener) {
        this.onAddDecreaseProductListener = onAddDecreaseProductListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        // 获取商品(product)对象
        final CartBean.DataBean.ListBean product = list.get(position);

        // 配置显示内容
        String[] images = product.getImages().replace("|", ",").replace("https", "http").split(",");
        Picasso.with(context).load(images[0]).into(holder.imgLogo);
        holder.txtProductName.setText(product.getTitle());
        holder.txtProductPrice.setText("￥" + product.getPrice());
        holder.madAdv.setNum(product.getNum());

        holder.madAdv.setOnAddDecreaseClickListener(new MyAddDecreaseView.OnAddDecreaseClickListener() {
            @Override
            public void onAddClick(int num) {
                product.setNum(num);

                if (onAddDecreaseProductListener != null) {
                    onAddDecreaseProductListener.onChange(position, num);
                }
            }

            @Override
            public void onDecreaseClick(int num) {
                product.setNum(num);

                if (onAddDecreaseProductListener != null) {
                    onAddDecreaseProductListener.onChange(position, num);
                }
            }
        });

        // 商品复选框
        holder.cbProduct.setOnCheckedChangeListener(null);
        holder.cbProduct.setChecked(product.isChecked());
        holder.cbProduct.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                product.setChecked(b);

                if (onProductClickListener != null) {
                    onProductClickListener.onProductClick(position, b);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cb_product)
        CheckBox cbProduct;
        @BindView(R.id.img_logo)
        ImageView imgLogo;
        @BindView(R.id.txt_product_name)
        TextView txtProductName;
        @BindView(R.id.txt_product_price)
        TextView txtProductPrice;
        @BindView(R.id.mad_adv)
        MyAddDecreaseView madAdv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
