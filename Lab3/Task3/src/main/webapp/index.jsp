<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page errorPage="error.jsp"%>
<html>
    <head>
        <title>$Title$</title>
    </head>
    <body>
        <form action="${pageContext.request.contextPath}" method="post">
            <input name="first" value=${first}><input name="second" value=${second}><br>
            <button type="submit" name="button" value="sum">+</button>
            <button type="submit" name="button" value="minus">-</button>
            <button type="submit" name="button" value="mult">*</button>
            <button type="submit" name="button" value="div">/</button>
            <p>результат: <span id="result">${result}</span></p>
        </form>
    </body>
</html>