package com.example.mxy1217.contact;



import com.example.mxy1217.bean.CartBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * date:2018/12/17
 * author:mxy(M)
 * function:
 */

public interface ICartApi {

    @GET("product/getCarts")
    Observable<CartBean> getCarts(@Query("uid") int uid);
}
