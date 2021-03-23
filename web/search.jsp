<%-- 
    Document   : search
    Created on : Jan 4, 2021, 8:43:38 PM
    Author     : GF65
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Page</title>
    </head>
    <body>
        <h1>Hana Shop</h1>
        <c:set var="isRole" value="${sessionScope.ROLE}"/>
        <c:choose>
            <c:when test="${not empty isRole}">
                <c:set var="role" value="user"/>
                <c:if test="${isRole eq true}">
                    <c:set var="role" value="admin"/>
                </c:if>
                <c:set var="userName" value="${sessionScope.USERNAME}"/>
                <form action="LogoutServlet">
                    Welcome ${role}:
                    <font color="red">${userName}</font> - 
                    <input type="submit" name="btnAction" value="Log out"/>
                </form>
            </c:when>
            <c:otherwise>
                <form action="login.jsp">
                    Welcome guess - <input type="submit" value="Login"/> 
                </form>
            </c:otherwise>
        </c:choose>

        <h3>Search option</h3>
        <form>
            <table border="0">
                <tbody>
                    <tr>
                        <td>Category:</td>
                        <td>
                            <select name="cate" style="width: 200px">
                                <c:set var="listCate" value="${requestScope.LISTCATE}"/>
                                <c:forEach items="${listCate}" var="cate">
                                    <option>${cate}</option>
                                </c:forEach>
                            </select><br/>
                        </td>
                    </tr>
                    <tr>
                        <td>Food Name:</td>
                        <td>
                            <input type="text" value="" style="width: 200px"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Price's range:</td>
                        <td>
                            <select name="priceRange" style="width: 200px">
                                <option>1</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <center>
                                <input type="submit" value="Search" name="btnAction"/>
                            </center>
                        </td>
                        <td> 
                        </td>
                    </tr>
                </tbody>
            </table>

        </form>
    </body>
</html>
