<%-- START SEARCH BAR --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
    <!-- Basic -->

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">

        <!-- Mobile Metas -->
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Site Metas -->
        <title>Hana Shop</title>
        <meta name="keywords" content="">
        <meta name="description" content="">
        <meta name="author" content="">

        <!-- Site Icons -->
        <link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon">
        <link rel="apple-touch-icon" href="images/apple-touch-icon.png">

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <!-- Site CSS -->
        <link rel="stylesheet" href="css/style.css">
        <!-- Responsive CSS -->
        <link rel="stylesheet" href="css/responsive.css">
        <!-- Custom CSS -->
        <link rel="stylesheet" href="css/custom.css">


        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
          <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->

    </head>

    <body>
        <input type="hidden" value="${requestScope.NOTIFY}" id="notify"/>
        <script>
            var notify = document.getElementById("notify").value;
            if (notify.length !== 0) {
                alert(notify);
            }
        </script>
        <%-- End Search Bar  --%>
        <c:import url="search_nav_bar.jsp"/>
        <%-- End Search Bar  --%>

        <!-- Start All Title Box -->
        <div class="all-title-box">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <h2>Cart</h2>
                        <ul class="breadcrumb">
                            <li class="breadcrumb-item"><a href="SearchServlet">Shop</a></li>
                            <li class="breadcrumb-item active">Cart</li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <!-- End All Title Box -->

        <!--        script for fix header table-->
        <!--        <script>
                    var $th = $('.tableFixHead').find('thead th');
                    $('.tableFixHead').on('scroll', function () {
                        $th.css('transform', 'translateY(' + this.scrollTop + 'px)');
                    });
                </script>
                <script src="https://code.jquery.com/jquery-3.1.0.js"></script>-->
        <!-- Start Cart  -->
        <div class="cart-box-main">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="tableFixHead table-main table-responsive" >
                            <c:choose>
                                <c:when test="${empty sessionScope.CART.items}">
                                    <font style="font-style: initial; font-size: 20px; font-weight: bold; color: green">
                                    No Item In Your Cart! 
                                    </font>
                                </c:when>
                                <c:otherwise>
                                    <form action="DispatcherServlet" method="POST" id="MyForm">
                                        <table  class="table"  id="MyTable">
                                            <thead>
                                                <tr>
                                                    <th>
                                            <center>Images</center>
                                            </th>
                                            <th>
                                            <center>Product Name</center>
                                            </th>
                                            <th>
                                            <center>Price</center>
                                            </th>
                                            <th>
                                            <center>Quantity</center>
                                            </th>
                                            <th>
                                            <center>Total</center>
                                            </th>
                                            <th>
                                            <center>Remove</center>
                                            </th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                                <c:set var="total" value="0"/>
                                                <c:set var="validCart" value="${requestScope.VALID_CART}"/>
                                                <c:set var="isCheck" value="${not empty validCart ? true : false}"/>
                                                <c:forEach items="${sessionScope.CART.items}" var="item"  varStatus='counter'>

                                                    <c:set var="food_detail" value="${sessionScope.CART_DETAIL[item.key]}"/>
                                                    <c:set var="subTotal" value="${item.value * food_detail.price}"/>
                                                    <tr>
                                                        <td class="thumbnail-img">
                                                            <a href="GotoDetailServlet?p_food_id=${item.key}">
                                                                <img class="img-fluid" src="${food_detail.imgData}" alt="" style="width: 200px; height: 200px"/>
                                                            </a>
                                                        </td>
                                                        <td class="name-pr">
                                                            <a href="GotoDetailServlet?p_food_id=${item.key}">
                                                                ${food_detail.foodName}
                                                            </a>
                                                        </td>
                                                        <td class="price-pr">
                                                            <p>${food_detail.price}</p>
                                                        </td>
                                                        <c:if test="${isCheck}">
                                                            <c:set var="forBorder" value="style='border-color: red'"/>
                                                            <c:set var="forTitle" value="title='available quantity: ${validCart[item.key]}'"/>
                                                        </c:if>
                                                        <td class="quantity-box"><input ${forBorder} ${forTitle} name="p_food_quantity" type="number" value="${item.value}" min="1" step="1" class="c-input-text qty text"></td>
                                                        <td class="total-pr">
                                                            <p>${subTotal}</p>
                                                        </td>
                                                        <td class="remove-pr">
                                                            <button type="button" onclick="confirmRemove('${item.key}')">Remove</button>
                                                            <!-- hidden field for update action (in loop)-->
                                                            <input type="hidden" name="p_food_id_U" value="${item.key}"/>
                                                        </td>
                                                    </tr>
                                                    <c:set var="total" value="${total + subTotal}"/>
                                                </c:forEach>
                                            <script>
                                                function confirmRemove(p_food_id_D) {
                                                    if (!confirm('Are you sure for removing this food out of your cart!')) {
                                                        return false;
                                                    } else {
                                                        document.getElementById("MyButton").value = "Remove";
                                                        document.getElementById("p_food_id_D").value = p_food_id_D;
                                                        document.getElementById("MyButton").click();
                                                    }
                                                }
                                            </script>
                                            </tbody>
                                        </table>
                                        <!--  hidden field for Delete action-->
                                        <input name="p_food_id_D" value="" type="hidden" id="p_food_id_D"/>
                                        <!--  hidden field for submit form and send action -->
                                        <input type="submit" id="MyButton" name="btnAction" value="Update Cart" style="display: none"/>
                                    </form>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
                <br/>
                <div class="row my-5">
                    <div class="col-lg-6 col-sm-6">

                    </div>
                    <div class="col-lg-6 col-sm-6">
                        <div class="update-box">
                            <input type="submit" value="Update Cart" onclick="clickUpdate()"/>
                        </div>

                    </div>
                    <script>
                        function clickUpdate() {
                            document.getElementById("MyButton").click();
                        }
                    </script>
                </div>
                <br/>
                <div class="row my-5">
                    <div class="col-lg-8 col-sm-12"></div>
                    <div class="col-lg-4 col-sm-12">
                        <div class="order-box">
                            <div class="d-flex gr-total">
                                <h5>Grand Total</h5>
                                <div class="ml-auto h5"> ${total} </div>
                            </div>
                            <hr> </div>
                    </div>
                    <div class="col-12 d-flex shopping-box"><a href="DispatcherServlet?btnAction=Checkout" class="ml-auto btn hvr-hover">Checkout</a> </div>

                </div>

            </div>
        </div>
        <!-- End Cart -->

        <%-- START SUGGEST FOOD LIST--%>

        <!-- Start Shop Detail  -->

        <div class="shop-detail-box-main">
            <div class="container">

                <div class="row my-5">
                    <div class="col-lg-12">
                        <div class="title-all text-center">
                            <h1>Suggest Products</h1>
                            <p>Enjoy your life with our shop.</p>
                        </div>
                        <div class="featured-products-box owl-carousel owl-theme">

                            <c:set var="suggestFood" value="${sessionScope.SUGGEST_LIST}"/>
                            <c:forEach items="${suggestFood}" var="food">
                                <div class="item">
                                    <div class="products-single fix">
                                        <div class="box-img-hover">
                                            <img src="${food.imgData}" class="img-fluid" alt="Image" style="width: 300px;height: 350px">
                                            <div class="mask-icon">
                                                <ul>
                                                    <li>
                                                        <form action="DispatcherServlet" method="POST">
                                                            <%-- params: food_id, search_name, cate_id, price_range--%>
                                                            <button class="eye" name="btnAction" value="Details"><i class="fas fa-eye" ></i></button>
                                                            <input type="hidden" name="p_food_id" value="${food.foodId}"/>
                                                            <input type="hidden" name="p_search_name" value="${param.p_search_name}"/>
                                                            <input type="hidden" name="p_search_cate" value="${param.p_search_cate}"/>
                                                            <input type="hidden" name="p_search_price" value="${param.p_search_price}"/>
                                                        </form>
                                                    </li>
                                                    <li>
                                                        <form action="DispatcherServlet" method="post">
                                                            <input type="hidden" name="p_food_id" value="${food.foodId}"/>
                                                            <input type="hidden" name="p_search_name" value="${param.p_search_name}"/>
                                                            <input type="hidden" name="p_search_cate" value="${param.p_search_cate}"/>
                                                            <input type="hidden" name="p_search_price" value="${param.p_search_price}"/>
                                                            <button class="eye" name="btnAction" value="Add To Cart"><i class="fa fa-cart-plus" ></i></button>
                                                            <input type="hidden" name="p_from" value="view_cart.jsp"/>
                                                        </form>
                                                    </li>
                                                </ul>
                                            </div>
                                        </div>
                                        <div class="why-text">
                                            <h4>${food.foodName}</h4>
                                            <h5> ${food.price}</h5>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>    
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <%-- START SUGGEST FOOD LIST--%>

        <%-- START FOOTER --%>

        <!-- Start Footer  -->
        <footer>
            <div class="footer-main">
                <div class="container">
                    <div class="row">
                        <div class="col-lg-4 col-md-12 col-sm-12">
                            <div class="footer-link-contact">
                                <h4>Business Time</h4>
                                <ul>
                                    <li><p>Monday - Friday: 08.00am to 05.00pm</p></li> <li><p>Saturday: 10.00am to 08.00pm</p></li> <li><p>Sunday: <span>Closed</span></p></li>
                                </ul>
                            </div>
                        </div>
                        <div class="col-lg-4 col-md-12 col-sm-12">
                            <div class="footer-widget">
                                <h4>About Freshshop</h4>
                                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p> 
                                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. </p> 							
                            </div>
                        </div>
                        <div class="col-lg-4 col-md-12 col-sm-12">
                            <div class="footer-link-contact">
                                <h4>Contact Us</h4>
                                <ul>
                                    <li>
                                        <p><i class="fas fa-map-marker-alt"></i>Address: Michael I. Days 3756 <br>Preston Street Wichita,<br> KS 67213 </p>
                                    </li>
                                    <li>
                                        <p><i class="fas fa-phone-square"></i>Phone: <a href="tel:+1-888705770">+1-888 705 770</a></p>
                                    </li>
                                    <li>
                                        <p><i class="fas fa-envelope"></i>Email: <a href="mailto:contactinfo@gmail.com">contactinfo@gmail.com</a></p>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <hr/>
                    </div>
                </div>
            </div>
        </footer>
        <!-- End Footer  -->

        <!-- Start copyright  -->
        <div class="footer-copyright">
            <p class="footer-company">All Rights Reserved. &copy; 2018 <a href="#">ThewayShop</a> Design By :
                <a href="https://html.design/">html design</a></p>
        </div>
        <!-- End copyright  -->

        <a href="#" id="back-to-top" title="Back to top" style="display: none;">&uarr;</a>

        <!-- ALL JS FILES -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
        <script src="js/jquery-3.2.1.min.js"></script>
        <script src="js/popper.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <!-- ALL PLUGINS -->
        <script src="js/jquery.superslides.min.js"></script>
        <script src="js/bootstrap-select.js"></script>
        <script src="js/inewsticker.js"></script>
        <script src="js/bootsnav.js."></script>
        <script src="js/images-loded.min.js"></script>
        <script src="js/isotope.min.js"></script>
        <script src="js/owl.carousel.min.js"></script>
        <script src="js/baguetteBox.min.js"></script>
        <script src="js/form-validator.min.js"></script>
        <script src="js/contact-form-script.js"></script>
        <script src="js/custom.js"></script>
    </body>

</html>

<%-- END FOOTER --%>