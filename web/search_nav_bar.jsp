<%-- START SEARCH BAR --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Start Main Top -->
<div class="main-top">
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-3 user-name-welcome"> 
                <c:set var="role" value="${sessionScope.ROLE}"/>
                <c:choose>
                    <c:when test="${not empty role}">
                        <c:choose>
                            <c:when test="${role eq true}">
                                <p style="display:inline; color: #ffffff; font-family: 'Raleway',sans-serif;text-transform: uppercase;"> Welcome Admin - </p>
                            </c:when>
                            <c:otherwise>
                                <p style="display:inline; color: #ffffff; font-family: 'Raleway',sans-serif;text-transform: uppercase;"> Welcome User - </p>
                            </c:otherwise>
                        </c:choose>
                        <i class="fa fa-user s_color"></i> <font class="user-name">${sessionScope.USERNAME} </font>       
                    </c:when>
                    <c:otherwise>
                        <p style="display:inline; color: #ffffff; font-family: 'Raleway',sans-serif; text-transform: uppercase;"> Welcome guest </p>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="col-sm-5" style="text-align: right">
                <div class="text-slid-box">
                    <div id="offer-box" class="carouselTicker">
                        <ul class="offer-box">
                            <li>
                                <i class="fab fa-opencart"></i> 20% off Entire Purchase Promo code: offT80
                            </li>
                            <li>
                                <i class="fab fa-opencart"></i> 50% - 80% off on Vegetables
                            </li>
                            <li>
                                <i class="fab fa-opencart"></i> Off 10%! Shop Vegetables
                            </li>
                            <li>
                                <i class="fab fa-opencart"></i> Off 50%! Shop Now
                            </li>
                            <li>
                                <i class="fab fa-opencart"></i> Off 10%! Shop Vegetables
                            </li>
                            <li>
                                <i class="fab fa-opencart"></i> 50% - 80% off on Vegetables
                            </li>
                            <li>
                                <i class="fab fa-opencart"></i> 20% off Entire Purchase Promo code: offT30
                            </li>
                            <li>
                                <i class="fab fa-opencart"></i> Off 50%! Shop Now 
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <c:set var="is_hidden" value="style=\"display: none\""/>
            <div class="col-sm-2" style="text-align: right">
                <form action="DispatcherServlet">
                    <button type="submit" name="btnAction" value="Log in" class='glow-on-hover' ${not empty role ? is_hidden : ''}>Log in</button>
                </form>
            </div>
            <div class="col-sm-2">
                <form action="DispatcherServlet">
                    <button type="submit" name="btnAction" value="Log out" class='glow-on-hover' ${empty role ? is_hidden : ''}>Log out</button>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- End Main Top -->

<!-- Start Main Top -->
<header class="main-header">
    <!-- Start Navigation -->
    <nav class="navbar navbar-expand-lg navbar-light bg-light navbar-default bootsnav">
        <div class="" style="width: 1533px; padding: 30px; background-color: rgba(77, 91, 90, 0.33); float: top">
            <!-- Start Header Navigation -->
            <div class="navbar-header">
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar-menu" aria-controls="navbars-rs-food" aria-expanded="false" aria-label="Toggle navigation">
                    <i class="fa fa-bars"></i>
                </button>
                <a class="navbar-brand" href="SearchServlet"><img src="images/logo.png" class="logo" alt=""></a>
            </div>
            <!-- End Header Navigation -->

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="navbar-menu">
                <ul class="nav navbar-nav ml-auto" data-in="fadeInDown" data-out="fadeOutUp">
                    <li class="nav-item active"><a class="nav-link" href="SearchServlet">Home</a></li>

                    <c:set var="role" value="${sessionScope.ROLE}"/>
                    <c:if test="${not role eq true}">
                        <li class="side-menu">
                            <a href="DispatcherServlet?btnAction=Cart">
                                <i class="fa fa-shopping-bag"></i>
                                <!-- get quantity in cart-->
                                <c:forEach items="${sessionScope.CART.items}" var="food">
                                    <c:set var="total" value="${total + food.value}"/>
                                </c:forEach>
                                <span class="badge">${total}</span>
                                <p>My Cart</p>
                            </a>
                        </li>
                        <li class="side-menu">
                            <a class="nav-link" href="DispatcherServlet?btnAction=History">Shopping History</a>
                        </li>
                    </c:if>
                </ul>
            </div>
            <!-- /.navbar-collapse -->

            <!-- Start Atribute Navigation -->
            <div style="float: right; padding-bottom: 15px; padding-left: 120px; border-radius: 20px">
                <form style="padding: 10px; background-color: rgba(0, 189, 173, 1); border-radius: 20px" action="DispatcherServlet">
                    <input name="p_cur_page" value="1" type="hidden"/>
                    <!-- para for scroll down: NAME="p_is_click_page" / VALUE="true" -->
                    <input type="hidden" value="true" name="p_is_scroll_down"/>
                    <input type="hidden" name="p_cur_page" value="${requestScope.CUR_PAGE}"/>
                    <c:set var="search_name" value="${param.p_search_name}"/>
                    <%-- para for name searching: name="p_search_name" / VALUE="${not empty search_name ? search_name : ''}" --%>
                    <font style="padding-left: 5px; font-weight: bold; color: #761c19">Name</font> <input type="text" name="p_search_name" value="${param.p_search_name}" style="height: 30px; border-radius: 20px"/>
                    <c:set var="search_cate" value="${param.p_search_cate}"/>
                    <font style="font-weight: bold; color: #761c19">Category</font> <select name="p_search_cate" style="height: 30px; float: top; border-style: solid">
                        <option value="none" selected style="">----None----</option>
                        <c:set var="list_cate" value="${requestScope.LIST_CATE}"/>
                        <c:if test="${empty list_cate}">
                            <c:set var="list_cate" value="${sessionScope.LIST_CATE}"/>
                        </c:if>
                        <c:forEach items="${list_cate}" var="cate">
                            <option ${cate.id eq search_cate ? 'selected' : ''} value="${cate.id}">${cate.name}</option>
                        </c:forEach>
                    </select>
                    <c:set var="search_price" value="${param.p_search_price}"/>    
                    <font style="font-weight: bold; color: #761c19">Price's range</font> <select name="p_search_price" style="height: 30px; float: top; border-style: solid">
                        <option style="border-radius: 20px" value="none" selected>----None----</option>
                        <option value="0,100000" ${search_price eq '0,100000' ? 'selected' : ''}>0 ---> 100 000</option>
                        <option value="101000,300000" ${search_price eq '101000,300000' ? 'selected' : ''}>101 000 ---> 300 000</option>
                        <option value="301000,500000" ${search_price eq '301000,500000' ? 'selected' : ''}>301 000 ---> 500 000</option>
                        <option value="701000,1000000" ${search_price eq '701000,1000000' ? 'selected' : ''}>701 000 ---> 1000 000</option>
                        <option value="1001000,1300000" ${search_price eq '1001000,1300000' ? 'selected' : ''}>1 001 000 ---> 1 300 000</option>
                        <option value="1301000,1500000" ${search_price eq '1301000,1500000' ? 'selected' : ''}>1 301 000 ---> 1 500 000</option>
                        <option value="1500000" ${search_price eq '1500000' ? 'selected' : ''}> > 1 500 000 </option>
                    </select>
                    <button style="border-radius: 10px" type="submit" value="Search" name="btnAction"><i class="fa fa-search" style="height: 13px"></i></button>
                </form> 
            </div>
            <!-- End Atribute Navigation -->
        </div>

    </nav>
    <!-- End Navigation -->
</header>
<!-- End Main Top -->

<%-- END SEARCH BAR --%>