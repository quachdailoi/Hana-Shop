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
        <div class="wrapper">
            <form class="login" method="POST" action="DispatcherServlet">
                <p class="title">Create New User</p>
                <input type="text" placeholder="UserID" autofocus name="p_user_id" value="${sessionScope.USERID}" readonly/>
                <i class="fa fa-user"></i>
                <input type="text" placeholder="Full name" name="p_user_name"/>
                <i class="fa fa-key"></i>
                <c:set value="${LIST_CATE}" var="listCate"/>
                <div class="container">
                    <div class="row">
                        <div class="dropdown dropdown-dark" style="width: 200px; height: 30px">        
                            <select class="custom-select" id="gender2" name="cbbxCate">
                                <c:forEach items="${requestScope.LIST_CATE}" var="cate">
                                    <option value="${cate.id}">${cate.name}</option>
                                </c:forEach>                               
                            </select>   
                        </div>
                    </div>
                </div>
                <button name="btnAction" value="Create New Account">
                    <i class="spinner"></i>
                    <span class="state">Create New Account</span>
                </button>
            </form>
            <form class="login" method="POST" action="DispatcherServlet">
                <button name="btnAction" value="Log in">
                    <i class="spinner"></i>
                    <span class="state">Login</span>
                </button>    
            </form>
        </div>
    </body>
</html>
