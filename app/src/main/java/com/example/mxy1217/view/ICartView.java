package com.example.mxy1217.view;


import com.example.mxy1217.base.IView;
import com.example.mxy1217.bean.CartBean;

/**
 * date:2018/12/17
 * author:mxy(M)
 * function:
 */

public interface ICartView extends IView {

    void getCarts(CartBean data);

    void onCartFailed(Throwable t);
}
