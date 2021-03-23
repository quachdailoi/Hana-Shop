<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>Login Page</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="./css/login.css">
        <link rel="stylesheet" type="text/css" href="./css/custom.css">
        <script type="text/javascript" src="./js/login.js"></script>
        <script src="https://kit.fontawesome.com/a076d05399.js"></script>

    </head>
    <body>
        <input type="hidden" value="${requestScope.NOTIFY}" id="notify"/>
        <script>
            var notify = document.getElementById("notify").value;
            if(notify.length !== 0) {
                alert(notify);
            }
        </script>
        <div class="wrapper">
            <form class="login" method="POST" action="DispatcherServlet">
                <c:if test="${not empty requestScope.LAST_RESOURCE}">
                    <input type="hidden" name="p_last_resource" value="${requestScope.LAST_RESOURCE}"/>
                </c:if>
                <p class="title">Log in</p>
                <input type="text" placeholder="Username" autofocus name="p_user_id"/>
                <i class="fa fa-user"></i>
                <input type="password" placeholder="Password" name="p_user_password" />
                <i class="fa fa-key"></i>
                <div class="g-sign-in-button" onclick="location.href='https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:8080/HanaShop_SE140970/login-google&response_type=code&client_id=360035477712-5ngm2u0t6o210hltlsp651q9fbempbhg.apps.googleusercontent.com&approval_prompt=force';">
                    <div class="content-wrapper">
                        <div class='logo-wrapper'>
                            <img src='https://developers.google.com/identity/images/g-logo.png'>
                        </div>
                        <span class='text-container'>
                            <span>Login with Google</span>
                        </span>
                    </div>
                </div>
                <button name="btnAction" value="Real Login">
                    <i class="spinner"></i>
                    <span class="state">Log in</span>
                </button>
            </form>
            <footer class="shopnow"><h3 ><a href="SearchServlet">Shopping now</a></h3><i class="fa fa-shopping-cart" style="font-size:26px"></i></footer>
        </div>
    </body>
</html>
