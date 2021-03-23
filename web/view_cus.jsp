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


        

    </head>

    <body>
        <input type="hidden" value="${requestScope.NOTIFY}" id="notify"/>
        <script>
            var notify = document.getElementById("notify").value;
            if (notify.length !== 0) {
                alert(notify);
            }
        </script>
        <%--Start Header and Search Bar  --%>
        <c:import url="search_nav_bar.jsp"/>
        <%--End Header and Search Bar  --%>
        
        <%--Start Header and Search Bar  --%>
        <c:import url="slider.jsp"/>
        <%--End Header and Search Bar  --%>
        
        <!-- Start Products  -->

        <div class="products-box" id="scroll_here">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="title-all text-center">
                            <h1>Product</h1>
                            <p>Enjoy your time</p>
                        </div>
                    </div>
                </div>
                <%-- List Product --%>
                <div class="row special-list">
                    <c:set var="list_food" value="${requestScope.LIST_FOOD}"/>
                    <c:choose>
                        <c:when test="${not empty list_food}">
                            <c:forEach items="${list_food}" var="food">
                                <div class="col-lg-3 col-md-6 special-grid best-seller">
                                    <div class="products-single fix">
                                        <div class="box-img-hover">
                                            <img src="${food.imgData}" class="img-fluid" alt="Image" id="MyPicture" style="width: 300px;height: 350px">
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
                                                            <input type="hidden" name="p_is_scroll_down" value="true"/>
                                                            <input type="hidden" name="p_search_name" value="${param.p_search_name}"/>
                                                            <input type="hidden" name="p_search_cate" value="${param.p_search_cate}"/>
                                                            <input type="hidden" name="p_search_price" value="${param.p_search_price}"/>
                                                            <button class="eye" name="btnAction" value="Add To Cart"><i class="fa fa-cart-plus" ></i></button>
                                                            <input type="hidden" name="p_cur_page" value="${param.p_cur_page}"/>
                                                            <input type="hidden" name="p_pos" value="${param.p_pos}"/>
                                                        </form>
                                                    </li>
                                                </ul>
                                            </div>
                                        </div>
                                        <div class="why-text">
                                            <h4>${food.foodName}</h4>
                                            <h5>${food.price}</h5>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <h2>No food found</h2>
                        </c:otherwise>
                    </c:choose>
                </div>
                <%-- End List Product --%>                        
            </div>
        </div>
        <input type="hidden" value="${param.p_is_scroll_down}" id="scroll"/>
        <!-- End Products  -->


        <script>
            var isClickPage = document.getElementById("scroll").getAttribute("value");
            if (isClickPage === "true") {
                location.href = '#scroll_here';
            }
        </script>
        
        <c:import url="pagingAndFooter.jsp">
            <c:param name="" />
        </c:import>
            

        <a href="#" id="back-to-top" title="Back to top" style="display: none;">&uarr;</a>

        <!-- ALL JS FILES -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
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
    <!-- Start Paging-->
</html>


