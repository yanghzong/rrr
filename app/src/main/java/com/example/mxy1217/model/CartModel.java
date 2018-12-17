package com.example.mxy1217.model;

import com.example.mxy1217.bean.CartBean;
import com.example.mxy1217.contact.ICartApi;
import com.example.mxy1217.utils.RetrofitManager;

import io.reactivex.Observable;

/**
 * date:2018/12/17
 * author:mxy(M)
 * function:
 */

public class CartModel {

    public Observable<CartBean> getCarts(int uid) {
        ICartApi iCartApi = RetrofitManager.getInstance().create(ICartApi.class);
        return iCartApi.getCarts(uid);
    }
}
