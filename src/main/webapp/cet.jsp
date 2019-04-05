<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <base href="<%=basePath%>">
    <title>四六级查询</title>
    <link rel="stylesheet" href="bootstrap-3.3.7/css/bootstrap.min.css">
    <script type="text/javascript" src="bootstrap-3.3.7/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="bootstrap-3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="bootstrap-3.3.7/js/bootstrap-paginator.js"></script>
</head>
<body>
<form class="form-horizontal">
    <div class="form-group">
        <label for="inputUsername" class="col-sm-2 control-label">姓名:</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" id="inputUsername" name="xm">
        </div>
    </div>
    <div class="form-group">
        <label for="inputZkzh" class="col-sm-2 control-label">准考证号:</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" id="inputZkzh" name="zkzh">
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-default">提交</button>
        </div>
    </div>
</form>

</body>
</html>
