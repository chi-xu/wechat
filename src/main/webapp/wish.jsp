<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: John Dawson
  Date: 2019/3/28
  Time: 14:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <base href="<%=basePath%>">
    <script type="text/javascript" src="bootstrap-3.3.7/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="bootstrap-3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="bootstrap-3.3.7/js/bootstrap-paginator.js"></script>
    <title>心愿墙</title>
</head>
<body>
<script>
    window.onload = function () {
        var showSta=0;
        document.getElementById("show").onclick = function () {
            if(showSta == 0){
                document.getElementById("wish").style.display = "block";
                showSta = 1;
            }else {
                document.getElementById("wish").style.display = "none";
                showSta = 0;
            }


        }
    }

    function go(currPage){
        $('#currPage').val(currPage)
        document.frm.submit();
    }
</script>
<input type="button" id="show" value="发布我的信息">
<div id='wish' >
    <form action="wish/addWish" method="post" >
        <input type="text" name="username" placeholder="您的昵称" ><br>
        <%--<input style="width: 180px" type="date" name="publishtime"><br>--%>
        <input type="hidden" name="publishtime" size="60px" value="<%=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) %>"/>
        <textarea name="content" style="width: 200px;height: 200px;" placeholder="您的心愿"></textarea>
        <input type="submit" style="width: 150px;height: 25px;display: block;margin: 5px auto;"value="提交信息"/>

    </form>
</div>

<form action="/wish/queryWish" method="post" name="frm">
    <input type="hidden" id="currPage" name="currPage" />
</form>

<c:forEach items="${page.data}" var="t" varStatus = "status">
    <div id="lost" style="width: 400px;height: 150px;background-color: #2b542c">
        <span>${t.username}</span><br>
        <span><fmt:formatDate value="${t.publishtime}" pattern="yyyy-MM-dd HH:mm:ss"/></span><br>
        <span>${t.content}</span><br>
    </div>
</c:forEach>


<div>
    共${page.totalPage}页 第${page.currPage}页
    <a href="javascript:go(1)">首页</a>
    <c:if test="${page.currPage>1 }">
    <a href="javascript:go(${page.currPage-1})">
        </c:if>
        上一页
        <c:if test="${page.currPage>1 }">
    </a>
    </c:if>
    <c:if test="${page.currPage<page.totalPage }">
    <a href="javascript:go(${page.currPage+1})">
        </c:if>
        下一页
        <c:if test="${page.currPage>=page.totalPage }">
    </a>
    </c:if>
    <a href="javascript:go(${page.totalPage})">末页</a>
</div>
</body>
</html>
