<html>
<head>
<#include "../common/header.ftl">
</head>
<body>

<div id="wrapper" class="toggled">
<#-- 左变sidebar -->
<#include "../common/nav.ftl">

    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
            <#-- 订单信息 -->
                <div class="col-md-4 column">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>订单id</th>
                            <th>订单总金额</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${orderMasterDto.orderId}</td>
                            <td>${orderMasterDto.orderAmount}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            <#-- 订单详细信息 -->
                <div class="col-md-12 column">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>商品id</th>
                            <th>商品名称</th>
                            <th>价格</th>
                            <th>数量</th>
                            <th>总额</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list orderMasterDto.orderDetailList as orderDetail>
                        <tr>
                            <td>${orderDetail.productId}</td>
                            <td>${orderDetail.productName}</td>
                            <td>${orderDetail.productPrice}</td>
                            <td>${orderDetail.productQuantity}</td>
                            <td>${orderDetail.productPrice * orderDetail.productQuantity}</td>
                        </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>

            <#-- 操作 -->
                <div class="col-md-4 column">
                <#if orderMasterDto.getOrderStatusEnum().message == "新订单">
                    <a href="/sell/seller/order/finish?orderId=${orderMasterDto.orderId}" type="button"
                       class="btn btn-default btn-primary">完结订单</a>
                    <a href="/sell/seller/order/cancel?orderId=${orderMasterDto.orderId}" type="button"
                       class="btn btn-default btn-danger">取消订单</a>
                </#if>
                </div>
            </div>
        </div>
    </div>

</div>

</body>
</html>
