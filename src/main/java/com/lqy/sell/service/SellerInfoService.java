package com.lqy.sell.service;

import com.lqy.sell.dataobject.SellerInfo;

/**
 * Created by Rodriguez
 * 2018/7/18 9:52
 */
public interface SellerInfoService {

    SellerInfo findByOpenid(String openid);

}
