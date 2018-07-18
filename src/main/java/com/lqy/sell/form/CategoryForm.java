package com.lqy.sell.form;

import lombok.Data;

/**
 * 类目form提交表单字段
 * Created by Rodriguez
 * 2018/7/18 9:17
 */
@Data
public class CategoryForm {

    /**
     * 类目id
     */
    private Integer categoryId;

    /**
     * 类目名字
     */
    private String categoryName;

    /**
     * 类目编号
     */
    private Integer categoryType;

}
