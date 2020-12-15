<%-- 
    Document   : CheckGrade
    Created on : Dec 15, 2020, 8:26:01 PM
    Author     : jianqing
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html style='height: 100%;'>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="/WEB-INF/jspf/headtags.jspf" %>
        <title>LuckyBaud Grade</title>
    </head>
    <body style='height: 100%;'>
        <iframe style="width: 100%; height: 100%;" frameborder="0" src="https://anime.luckycanvas.cn/LuckyMain.html?${assignment.getAssignmentNameEncoded()}∧${assignment.getDateCommonFormat()}∧${assignment.getGradeEarned()}∧${assignment.getMaxGrade()}∧1000∧1000∧0∧idk|∨">
            Your browser does not support iFrame!
        </iframe>
    </body>
</html>
