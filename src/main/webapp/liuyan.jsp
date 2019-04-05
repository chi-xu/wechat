<%--
  Created by IntelliJ IDEA.
  User: John Dawson
  Date: 2019/3/21
  Time: 15:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>留言板</title>

</head>
<body>
<div align="center"><h2>留下您的意见或建议！</h2></div>
<div><h5>注: * 号为必填项</h5> </div>

<form onsubmit=document.getElementById("url").value=window.location.host; name="myform" method="post" target="_blank"
      enctype="multipart/form-data" action="http://login.liuyanbao.net/index.php?action=savecontent"
      accept-charset="GB2312" onsubmit="if(isIE)document.charset='GB2312'">
    <input type="hidden" name="fid" value="16655"/>
    <input type="hidden" name="guishu" value="18638"/>
    <input type="hidden" name="url" value="" id="url"/>
   <table width="100%">
        <tr>
            <td></td>
        </tr>
        <tr>
            <td width="20%">姓名：</td>
            <td width="80%"><input type="text" name="content[0]" value=""/><font color="red"> <b>*</b> </font>&nbsp</td>
        </tr>
        <tr>
            <td width="20%">手机号：</td>
            <td width="80%"><input type="text" name="content[1]" value=""/><font color="red"> <b>*</b> </font>&nbsp</td>
        </tr>
        <tr>
            <td width="20%">邮箱：</td>
            <td width="80%"><input type="text" name="content[2]" value=""/><font color="red"> <b>*</b> </font>&nbsp</td>
        </tr>
        <tr>
            <td width="20%">留言板：</td>
            <td width="80%"><textarea name="content[3]" cols="50" rows="10"></textarea><font color="red"> <b>*</b>
            </font>&nbsp
            </td>
        </tr>
        <tr>
            <td width="20%"></td>
            <td width="80%"><input type="submit" name="submitcontent" value="提交"></td>
        </tr>
    </table>

</form>
</div>
</body>
</html>
