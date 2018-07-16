<html>
<head>
    <meta charset="UTF-8"/>
    <title>卖家商品列表</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <table class="table table-bordered">
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
                    <th>操作</th>
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
                    <td>${orderMasterDto.orderId}</td>
                    <td>${orderMasterDto.orderId}</td>
                    <td>${orderMasterDto.orderId}</td>
                    <td>${orderMasterDto.orderId}</td>
                </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>

