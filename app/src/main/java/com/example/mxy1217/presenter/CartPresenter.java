package com.example.mxy1217.presenter;



import com.example.mxy1217.base.BasePresenter;
import com.example.mxy1217.bean.CartBean;
import com.example.mxy1217.model.CartModel;
import com.example.mxy1217.view.ICartView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * date:2018/12/17
 * author:mxy(M)
 * function:
 */

public class CartPresenter extends BasePresenter<ICartView> {

    private CartModel model;

    @Override
    protected void provideModel() {
        model = new CartModel();
    }

    public void getCarts() {
        model.getCarts(71)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CartBean>() {
                    @Override
                    public void accept(CartBean cartBean) throws Exception {
                        if (cartBean != null && "0".equals(cartBean.getCode())) {
                            if (iView != null) {
                                iView.getCarts(cartBean);
                                return;
                            }
                            if (iView != null) {
                                iView.onCartFailed(new Throwable("服务未响应"));
                            }
                        } else {
                            if (iView != null) {
                                iView.onCartFailed(new Throwable(cartBean.getMsg()));
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (iView != null) {
                            iView.onCartFailed(new Throwable("网络异常"));
                        }
                    }
                });
    }
}
