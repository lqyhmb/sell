package com.lqy.sell.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 用户信息
 * Created by Rodriguez
 * 2018/7/18 9:46
 */
@Data
@Entity
public class SellerInfo {

    @Id
    private String sellerId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 微信openid
     */
    private String openid;

}
