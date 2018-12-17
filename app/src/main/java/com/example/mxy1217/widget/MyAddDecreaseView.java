package com.example.mxy1217.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.mxy1217.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * date:2018/12/17
 * author:mxy(M)
 * function:
 */

public class MyAddDecreaseView extends LinearLayout {
    @BindView(R.id.btn_decrease)
    Button btnDecrease;
    @BindView(R.id.et_num)
    TextView etNum;
    @BindView(R.id.btn_add)
    Button btnAdd;
    private int num = 1;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        etNum.setText(num + "");
    }

    public interface OnAddDecreaseClickListener {
        void onAddClick(int num);

        void onDecreaseClick(int num);
    }

    private OnAddDecreaseClickListener onAddDecreaseClickListener;

    public void setOnAddDecreaseClickListener(OnAddDecreaseClickListener onAddDecreaseClickListener) {
        this.onAddDecreaseClickListener = onAddDecreaseClickListener;
    }

    public MyAddDecreaseView(Context context) {
        this(context, null);
    }

    public MyAddDecreaseView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyAddDecreaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
    }

    private void initData(Context context) {
        View.inflate(context, R.layout.widget_add_decrease, this);
        ButterKnife.bind(this);
        etNum.setText(num + "");
    }


    @OnClick({R.id.btn_decrease, R.id.btn_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_decrease:
                if (num > 1) {
                    num--;
                }
                etNum.setText(num + "");
                if (onAddDecreaseClickListener != null) {
                    onAddDecreaseClickListener.onDecreaseClick(num);
                }
                break;
            case R.id.btn_add:
                num++;
                etNum.setText(num + "");
                if (onAddDecreaseClickListener != null) {
                    onAddDecreaseClickListener.onAddClick(num);
                }
                break;
        }
    }
}
