<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link href="https://fonts.googleapis.com/css2?family=DM+Sans:opsz,wght@9..40,200;9..40,300&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap" rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <%--    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/style_2.css">--%>
    <link rel="stylesheet" type="text/css"
          href="<%=application.getContextPath() %>/styles/style_4.css" >
    <title>Site</title>
</head>

<body>

<div class="header">
    <div class="container_1">
        <div class="header__inner">
            <div class="header__text">
                <h1>?</h1>
            </div>
        </div>
    </div>
</div>

<div class="basic">
    <%String user = (String) request.getAttribute("username");%>
    <div class="container_3">
        <div class="basic__inner">
            <div class="block__4">
                <div class="block__text">
                    <h3>You previously logged in as <%=user%> </h3>
                    <h3>Do you want to continue, or log in to another account?</h3>
                </div>
            </div>
        </div>
    </div>
    <div class="container_3">
        <div class="basic__inner">
            <div class="block__5">
                <form action="goToAut" method="GET">
                    <button class="astext">Another account</button>
                </form>
            </div>
            <div class="block__5">
                <form action="goToLog" method="GET">
                    <button class="astext">Log in as <%=user%></button>
                </form>
            </div>
        </div>
    </div>

<%--    <footer>--%>
<%--        <div class="container">--%>
<%--            <div class="footer__inner">--%>
<%--                <div class="footer__text">--%>
<%--                    <h3>tg: zhertvapropagandynolana</h3>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </footer>--%>

</div>

</body>
</html>