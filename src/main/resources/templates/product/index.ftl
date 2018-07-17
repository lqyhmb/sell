<html>
<head>
<#include "../common/header.ftl">
</head>

<body>
<div id="wrapper" class="toggled">

<#--边栏sidebar-->
<#include "../common/nav.ftl">

<#--主要内容content-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <form role="form" method="post" action="/sell/seller/product/save">

                        <div class="form-group">
                            <label>名称</label>
                            <input name="productName" value="${(productInfo.productName)!""}" type="text"
                                   class="form-control"/>
                        </div>

                        <div class="form-group">
                            <label>价格</label>
                            <input name="productPrice" value="${(productInfo.productPrice)!""}" type="number"
                                   class="form-control"/>
                        </div>

                        <div class="form-group">
                            <label>库存</label>
                            <input name="productStock" value="${(productInfo.productStock)!""}" type="number"
                                   class="form-control"/>
                        </div>

                        <div class="form-group">
                            <label>描述</label>
                            <input name="productDescription" value="${(productInfo.productDescription)!""}" type="text"
                                   class="form-control"/>
                        </div>

                        <div class="form-group">
                            <label>图片</label>
                            <img width="150" height="150" src="${(productInfo.productIcon)!""}" alt=""/>
                            <input name="productIcon" class="form-control" type="text" value="${(productInfo.productIcon)!""}"/>
                        </div>

                        <div class="form-group">
                            <label>类目</label>
                            <select name="categoryType" class="form-control">
                            <#list productCategoryList as category>
                                <option value="${category.categoryType}"
                                        <#if (productInfo.categoryType)?? && productInfo.categoryType == category.categoryType>selected</#if>>
                                ${category.categoryName}</option>
                            </#list>
                            </select>
                        </div>
                        <input name="productId" value="${(productInfo.productId)!""}" type="text" hidden/>
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>