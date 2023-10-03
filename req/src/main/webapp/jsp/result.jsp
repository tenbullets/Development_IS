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
          href="<%=application.getContextPath() %>/styles/style_2.css" >
    <title>Site</title>
</head>

<body>

    <div class="header">
        <div class="container_1">
            <div class="header__inner">
                <div class="header__text">
                    <% String status = (String) request.getAttribute("status");%>
                    <h1><%=status%></h1>
                </div>
            </div>
        </div>
    </div>

    <div class="basic">
        <div class="container_2">
            <div class="basic__inner">
                <div class="block__2">
                    <%String res = (String) request.getAttribute("resultOfAut");%>
                    <h4 class="block__text"><%=res%></h4>
                </div>
            </div>
        </div>

        <footer>
            <div class="container">
                <div class="footer__inner">
                    <div class="footer__text">
                        <h3>tg: zhertvapropagandynolana</h3>
                    </div>
                </div>
            </div>
        </footer>
    </div>

</body>
</html>