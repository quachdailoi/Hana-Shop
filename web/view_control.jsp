<!-- Start Search Bar-->
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

        <%--Start Header and Search Bar  --%>
        <c:import url="slider.jsp"/>
        <%--End Header and Search Bar  --%>

        <!-- Start Products  -->

        <div class="products-box" id="scroll_here">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="title-all text-center">
                            <h1>Action Control</h1>
                            <p>Tick on product to delete a such of products!</p>
                        </div>
                    </div>
                </div>
                <%-- Form for delete foods action --%>

                <form action="DispatcherServlet" method="POST" id="MyForm">
                    <input type="submit" name="btnAction" value="" id="btnAction" style="display: none"/>
                    <input type="hidden" name="p_cur_page" value="${requestScope.CUR_PAGE}"/>
                    <input type="hidden" name="p_is_scroll_down" value="true"/>
                    <input type="hidden" name="p_search_name" value="${param.p_search_name}"/>
                    <input type="hidden" name="p_search_cate" value="${param.p_search_cate}"/>
                    <input type="hidden" name="p_search_price" value="${param.p_search_price}"/>
                    <%-- Button Action--%>
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="special-menu text-center">
                                <div class="button-group filter-button-group">
                                    <button type="button" id="btnUpdate" data-filter="*" class="hover-action" onclick="confirmDelete()">Delete All</button>
                                    <button type="button" id="btnCreateNewFood" data-filter=".top-featured" class="hover-action" onclick="clickCreateNewFood()">Create New Food</button>
                                    <!-- Input tag replace button tag Delete above-->
                                </div>
                            </div>
                        </div>
                    </div>
                    <%-- End Button Action--%>
                    <div class="row special-list">
                        <c:set var="list_food" value="${requestScope.LIST_FOOD}"/>
                        <c:choose>
                            <c:when test="${not empty list_food}">
                                <c:forEach items="${list_food}" var="food">
                                    <div class="col-lg-3 col-md-6 special-grid best-seller">
                                        <div class="products-single fix">
                                            <div class="box-img-hover">
                                                <c:if test="${role eq true}">
                                                    <div class="type-lb">
                                                        <p class="sale">${food.status eq true ? 'Enable' : 'Disable'}</p>
                                                    </div>
                                                </c:if>
                                                <img src="${food.imgData}" class="img-fluid" alt="Image" id="MyPicture" style="width: 300px;height: 350px">
                                                <div class="mask-icon">
                                                    <ul>
                                                        <li>
                                                            <%-- click button then set value for a hidden input tag before submit--%>            
                                                            <button type="button" class="eye" onclick="clickDetails('${food.foodId}')" id="btnDetails"><i class="fas fa-eye"></i></button>
                                                        </li> 
                                                    </ul>
                                                </div>
                                            </div>
                                            <div class="why-text">
                                                <h4>${food.foodName}</h4>
                                                <h5>${food.price}</h5>
                                                <input type="checkbox" name="cbx_food_id" value="${food.foodId}" ${food.status eq true ? '' : 'Disabled'}/>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                                <%-- Input tag for only food_id when click details button, use js--%>            
                                <input type="hidden" name="p_food_id" value="" id="foodId"/>
                            </c:when>
                            <c:otherwise>
                                <h2>No food found</h2>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </form>
                <%-- End Form for delete foods action --%>                            
            </div>
        </div>
        <input type="hidden" value="${param.p_is_scroll_down}" id="scroll"/>
        <!-- End Products  -->


        <script>
            var isClickPage = document.getElementById("scroll").getAttribute("value");
            if (isClickPage === "true") {
                location.href = '#scroll_here';
            }
            function confirmDelete() {
                if (!confirm("Are you sure to Delete food(s)?")) {
                    return false;
                }
                //click btn Delete and submit form.
                document.getElementById("btnAction").value = 'Delete';
                document.getElementById("btnAction").click();
            }
            function clickDetails(foodId) {
                document.getElementById("foodId").value = foodId;
                document.getElementById("btnAction").value = 'Details';
                document.getElementById("btnAction").click();
            }
            function clickCreateNewFood() {
                document.getElementById("btnAction").value = 'Create New Food';
                document.getElementById("btnAction").click();
            }
        </script>

        <!-- Start Paging-->
        <c:set var="max_page" value="${requestScope.MAX_PAGE}"/>
        <c:set var="cur_page" value="${(empty param.p_cur_page) || (param.p_cur_page le 0) ? 1 : param.p_cur_page }"/>

        <c:set var="movable" value="${max_page lt 4 ? 'display: none;' : ''}"/>
        <c:set var="pre_able" value="${cur_page eq 1 ? 'disabled' : ''}"/>
        <c:set var="pre_able1" value="${cur_page eq 1 ? 'background-color: gray' : ''}"/>
        <c:set var="next_able" value="${cur_page eq max_page ? 'disabled' : ''}"/>
        <c:set var="next_able1" value="${cur_page eq max_page ? 'background-color: gray' : ''}"/>

        <c:set var="pos" value="${empty param.p_pos ? 'first' : param.p_pos}"/>
        <input type="hidden" id="max_page" value="${max_page}" style="display: none"/>
        <!-- Start Pagination -->
        <form action="DispatcherServlet" method="POST" >
            <input type="hidden" name="btnAction" value="Search"/>
            <input type="hidden" name="p_search_name" value="${param.p_search_name}"/>
            <input type="hidden" name="p_search_cate" value="${param.p_search_cate}"/>
            <input type="hidden" name="p_search_price" value="${param.p_search_price}"/>
            <input type="hidden" name="p_is_scroll_down" value="true"/>
            <input type="hidden" name="p_cur_page" value="${cur_page}" id="p_cur_page"/>
            <input type="hidden" name="p_pos" value="${pos}" id="p_pos"/>
            <button id="MySubmitButton" style="display: none"></button>
        </form>
        <div class="container">
            <div class="pagination p12">
                <span>
                    <%--         temp btn--%>
                    <button ${pre_able} style="${movable} ${pre_able1}"  type="button" onclick="changePage(${cur_page - 1}, 'pre')">Previous</button>


                    <c:choose>
                        <c:when test="${pos eq 'first'}">
                            <c:set var="begin" value="${cur_page}"/>
                            <c:set var="end" value="${cur_page+2}"/>
                        </c:when>
                        <c:when test="${pos eq 'second'}">
                            <c:set var="begin" value="${cur_page-1}" />
                            <c:set var="end" value="${cur_page+1}"/>
                        </c:when>
                        <c:when test="${pos eq 'third'}">
                            <c:set var="begin" value="${cur_page-2}" />
                            <c:set var="end" value="${cur_page}"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="begin" value="${1}" />
                            <c:set var="end" value="${1}"/>
                        </c:otherwise>
                    </c:choose>


                    <c:forEach begin="${begin gt 1 ? begin : 1}" end="${end gt max_page ? max_page : end}" var="page" varStatus="counter">
                        <c:choose>
                            <c:when test="${counter.count eq 1}">
                                <c:set var="i_pos" value="first" />
                            </c:when>
                            <c:when test="${counter.count eq 2}">
                                <c:set var="i_pos" value="second" />
                            </c:when>
                            <c:when test="${counter.count eq 3}">
                                <c:set var="i_pos" value="third" />
                            </c:when>
                        </c:choose>
                        <!--                temp btn-->
                        <button type="button" class='${page eq cur_page ? 'is-active' : ''}' onclick="changePage(${page}, '${i_pos}')">${page}</button>
                    </c:forEach>

                    <%--           link button--%>

                    <button ${next_able} style="${movable} ${next_able1}"  type="button" onclick="changePage(${cur_page + 1}, 'next')">Next</button>

                </span>
            </div>
        </div>



        <!-- Start copyright  -->
        <div class="footer-copyright">
            <p class="footer-company">All Rights Reserved. &copy; 2018 <a href="#">ThewayShop</a> Design By :
                <a href="https://html.design/">html design</a></p>
        </div>
        <!-- End copyright  -->

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
        <script>

                        function changePage(page, i_pos) {
                            var maxPage = document.getElementById('max_page').value;
                            var curPos = document.getElementById('p_pos').value;

                            if (page <= 0) {
                                page = 1;
                            } else if (page > maxPage) {
                                page = maxPage;
                            }

                            document.getElementById('p_cur_page').value = page;

                            if (i_pos.length !== 0 || !i_pos) {
                                if (i_pos === 'pre') {
                                    if (curPos === 'third') {
                                        curPos = 'second';
                                    } else {
                                        curPos = 'first';
                                    }
                                } else if (i_pos === 'next') {
                                    if (curPos === 'first') {
                                        curPos = 'second';
                                    } else {
                                        curPos = 'third';
                                    }
                                } else {
                                    curPos = i_pos;
                                }
                            } else {
                                curPos = 'first';
                            }

                            document.getElementById('p_pos').value = curPos;
                            document.getElementById('MySubmitButton').click();
                        }
        </script>
    </body>
    <!-- Start Paging-->
</html>


