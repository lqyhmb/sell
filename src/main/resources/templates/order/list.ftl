<html>
<head>
    <#include "../common/header.ftl">
</head>
<body>

<div id="wrapper" class="toggled">
    <#-- 左变sidebar -->
    <#include "../common/nav.ftl">
    <#-- 主要内容区域content -->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>订单id</th>
                            <th>姓名</th>
                            <th>手机号</th>
                            <th>地址</th>
                            <th>金额</th>
                            <th>订单状态</th>
                            <th>支付状态</th>
                            <th>创建时间</th>
                            <th colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list orderMasterDtoPage.content as orderMasterDto>
                        <tr>
                            <td>${orderMasterDto.orderId}</td>
                            <td>${orderMasterDto.buyerName}</td>
                            <td>${orderMasterDto.buyerPhone}</td>
                            <td>${orderMasterDto.buyerAddress}</td>
                            <td>${orderMasterDto.orderAmount}</td>
                            <td>${orderMasterDto.getOrderStatusEnum().message}</td>
                            <td>${orderMasterDto.getPayStatusEnum().message}</td>
                            <td>${orderMasterDto.createTime}</td>
                            <td><a href="/sell/seller/order/detail?orderId=${orderMasterDto.orderId}">详情</a></td>
                            <td>
                                <#if orderMasterDto.getOrderStatusEnum().message == "新订单">
                                    <a href="/sell/seller/order/cancel?orderId=${orderMasterDto.orderId}">取消</a>
                                </#if>
                            </td>
                        </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>

            <#-- 分页开始 -->
                <div class="col-md-12 column">
                    <ul class="pagination pull-right">

                    <#if currentPage lte 1>
                        <li class="disabled"><a href="#">上一页</a></li>
                    <#else>
                        <li><a href="/sell/seller/order/list?page=${currentPage-1}&size=${size}">上一页</a></li>
                    </#if>

                    <#list 1..orderMasterDtoPage.getTotalPages() as index>
                        <#if currentPage == index>
                            <li class="disabled"><a href="#">${index}</a></li>
                        <#else>
                            <li><a href="/sell/seller/order/list?page=${index}&size=${size}">${index}</a></li>
                        </#if>
                    </#list>

                    <#if currentPage gte orderMasterDtoPage.getTotalPages()>
                        <li class="disabled"><a href="#">下一页</a></li>
                    <#else>
                        <li><a href="/sell/seller/order/list?page=${currentPage+1}&size=${size}">下一页</a></li>
                    </#if>

                    </ul>
                </div>
            <#-- 分页结束 -->
            </div>
        </div>
    </div>
</div>

</body>
</html>

