package com.lqy.sell.service.impl;

import com.lqy.sell.dataobject.SellerInfo;
import com.lqy.sell.repository.SellerInfoRepository;
import com.lqy.sell.service.SellerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Rodriguez
 * 2018/7/18 9:53
 */
@Service
public class SellerInfoServiceImpl implements SellerInfoService {

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Override
    public SellerInfo findByOpenid(String openid) {
        return sellerInfoRepository.findByOpenid(openid);
    }

}
