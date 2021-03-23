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

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
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
        <%--Start Header and Search Bar  --%>
        <c:import url="search_nav_bar.jsp"/>
        <%--End Header and Search Bar  --%>

        <!-- Start History Table  -->

        <div style="float: contour; padding-bottom: 15px; padding-left: 120px; border-radius: 20px">
            <form style="padding: 10px; background-color: rgba(0, 189, 173, 1); border-radius: 20px" action="DispatcherServlet">
                <font style="padding-left: 5px; font-weight: bold; color: #761c19">Name</font> <input type="text" name="p_his_name" value="${param.p_his_name}" style="height: 30px; border-radius: 20px"/>

                <font style="padding-left: 5px; font-weight: bold; color: #761c19">Order Date</font> <input type="date" name="p_his_date" value="${param.p_his_date}" style="height: 30px; border-radius: 20px"/>

                <button style="border-radius: 10px" type="submit" value="History" name="btnAction"><i class="fa fa-search" style="height: 13px"></i></button>
            </form> 
        </div>

        <div class="container">
            <c:set var="listOrder" value="${requestScope.LIST_ORDER}"/>
            <c:choose>
                <c:when test="${not empty listOrder}">
                    <div class="accordion" id="accordionExample">
                        <c:forEach items="${listOrder}" var="order" varStatus="counter">
                            <div class="card">
                                <div class="card-header" id="headingOne">
                                    <h5 class="mb-0">
                                        <button class="btn btn-link" type="button" data-toggle="collapse" data-target="#${counter.count}" aria-expanded="true" aria-controls="collapseOne">
                                            #${counter.count}. ${order.orderId} - ${order.orderDate} - ${order.status} - ${order.address}
                                        </button>
                                    </h5>
                                </div>
                                <div id="${counter.count}" class="collapse" aria-labelledby="headingOne" data-parent="#accordionExample" >
                                    <div class="card-body">
                                        <div class="cart-box-main">
                                            <div class="container">
                                                <div class="tableFixHead table-main table-responsive" >
                                                    <form action="DispatcherServlet" method="POST" id="MyForm">
                                                        <table  class="table"  id="MyTable">
                                                            <thead>
                                                                <tr>
                                                                    <th>
                                                            <center>Image</center>
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
                                                            </tr>
                                                            </thead>
                                                            <tbody>
                                                                <c:set var="mapFood" value="${requestScope.MAP_FOOD}"/>
                                                                <c:set var="listFood" value="${mapFood[order.orderId]}"/>
                                                                <c:forEach items="${listFood}" var="food" varStatus="counter">
                                                                    <tr>
                                                                        <td class="thumbnail-img">
                                                                            <a href="GotoDetailServlet?p_food_id=${food.foodId}">
                                                                                <img class="img-fluid" src="${food.image}" alt="" style="width: 100px; height: 100px"/>
                                                                            </a>
                                                                        </td>
                                                                        <td class="name-pr">
                                                                            <a href="GotoDetailServlet?p_food_id=${food.foodId}">
                                                                                ${food.name}
                                                                            </a>
                                                                        </td>
                                                                        <td class="price-pr">
                                                                            <p>${food.price}</p>
                                                                        </td>
                                                                        <td class="quantity-box"><input readonly name="p_food_quantity" type="number" value="${food.price}" min="1" step="1" class="c-input-text qty text"></td>
                                                                        <td class="total-pr">
                                                                            <p>${food.quantity}</p>
                                                                        </td>
                                                                    </tr>
                                                                </c:forEach>
                                                            </tbody>
                                                        </table>
                                                        <!--  hidden field for Delete action-->
                                                        <input name="p_food_id_D" value="" type="hidden" id="p_food_id_D"/>
                                                        <!--  hidden field for submit form and send action -->
                                                        <input type="submit" id="MyButton" name="btnAction" value="Update Cart" style="display: none"/>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:when>
                <c:otherwise>
                    NOT FOUND ANY SHOPPING HISTORY
                </c:otherwise>
            </c:choose>
        </div>


        <!-- End Table  -->



        <!-- Start Footer  -->
        <footer>
            <center><div class="footer-main">
                <div class="container">

                    <div class="col-lg-4 col-md-12 col-sm-12">
                        <div class="footer-widget">
                            <h4>About Freshshop</h4>
                            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p> 
                            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. </p> 							
                        </div>
                    </div>
                </div>
            </div>
        </div></center>
    </footer>

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




    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
<!-- Start Paging-->
</html>


