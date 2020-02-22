<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<html>
    <head>
        <title>Error Page</title>
    </head>

    <body>
        An error has occurred! Please, contact administrator.
        <p>Message: ${exception.message}</p>
    </body>
</html>
