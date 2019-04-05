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
    <title>成绩查询</title>
    <link rel="stylesheet" href="bootstrap-3.3.7/css/bootstrap.min.css">
    <script type="text/javascript" src="bootstrap-3.3.7/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="bootstrap-3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="bootstrap-3.3.7/js/bootstrap-paginator.js"></script>
</head>

<body>

<div class="panel panel-info">
    <div class="panel-heading">成绩查询</div>
    <div class="panel-body">
        <form  class="navbar-form navbar-left" action="user/scoreList" method="post">
            <input type="hidden" value="${score[0].userId}" name="userId">
            <div class="form-group">

                <div class="form-group">

                        学年：<select name="year" id="year">
                            <c:forEach items="${year}" var="y">
                                <option value="${y.year}" >${y.year}</option>
                            </c:forEach>
                        </select>
                        学期：<select name="term" id="term">
                            <option value="1" selected >1</option>
                            <option value="2" >2</option>
                    </select>

                </div>

                <button class="btn btn-success">查询</button>

            </div>

        </form>
    </div>
</div>

<table class="table table-bordered table-striped table-hover">
    <tr class="label-success" >

        <th style="width:40%">课程名称</th>
        <th style="width:30%">学分</th>

        <th style="width:30%">成绩</th>
    </tr>
    <c:forEach items="${score}" var="s" varStatus = "status">
        <tr>
            <td>${s.course.courseName }</td>
            <td>${s.credit }</td>
            <td>${s.score }</td>
        </tr>
    </c:forEach>

</table>
</body>


</html>
