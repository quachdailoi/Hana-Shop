<%-- START SEARCH BAR--%>
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

        <%-- Start All Title Box --%>
        <div class="all-title-box">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <h2>Food Detail</h2>
                        <ul class="breadcrumb">
                            <li class="breadcrumb-item"><a href="SearchServlet">Shop</a></li>
                            <li class="breadcrumb-item active"> Food Detail </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <%-- End All Title Box --%>

        <%-- BEGIN Details Panel--%>

        <div class="shop-detail-box-main">
            <form action="DispatcherServlet" method="POST">
                <div class="container">
                    <div class="row">
                        <div class="col-xl-5 col-lg-5 col-md-6">
                            <div id="carousel-example-1" class="single-product-slider carousel slide" data-ride="carousel">
                                <div class="carousel-inner" role="listbox">
                                    <div class="carousel-item active"> <img class="d-block w-100" src="${food.imgData}" id="image" style="width: 350px; height: 450px" onerror="alert('Not image available!')"> </div>
                                </div>
                                <div class="file-input" style="float: left; width: 100% ;padding: 20px">
                                    <center>
                                        <input type="file" id="file" class="file" onchange="(document.getElementById('image').src = window.URL.createObjectURL(this.files[0])); encodeImageFileAsURL(this)"/>
                                        <input type="hidden" id="imgData" name="p_data_image" value="${food.imgData}"/>
                                        <label for="file">
                                            Select file
                                            <p class="file-name"></p>
                                        </label>
                                    </center>
                                </div>
                            </div>
                        </div>
                        <div class="col-xl-7 col-lg-7 col-md-6">
                            <div class="single-product-details">
                                <div>
                                    <span style="display: inline-block;padding: 20px">
                                        <label class="control-label">Cate ID</label>
                                        <select style="text-align: center" class="form-control" name="p_cate_id">
                                            <c:forEach items="${requestScope.LIST_CATE}" var="cate">
                                                <option value="${cate.id}" ${food.cateId eq cate.id ? 'selected' : ''}>
                                                    ${cate.name}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </span>
                                </div>
                                <div class="form-group quantity-box">
                                    <label class="control-label">Name</label>
                                    <center><input type="text" class="form-control" value="${food.foodName}" name="p_food_name" style="width: 500px" required minlength="2" maxlength="20"/></center>
                                </div>
                                <div>
                                    <span style="display: inline-block; padding: 20px">
                                        <label class="control-label">Price</label>
                                        <input id="price" style="text-align: center" class="form-control" value="${food.price}" name="p_food_price" min="0" type="number" style="width: 150px" required/>
                                        <font style="color: red" id="notifyPrice"></font>
                                    </span>
                                    <span style="display: inline-block;padding: 20px">
                                        <label class="control-label">Quantity</label>
                                        <input id="quantity" style="text-align: center" class="form-control" value="${food.quantity}" name="p_food_quantity" min="0" type="number" style="width: 150px" required/>
                                        <font style="color: red" id="notifyQuantity"></font>
                                    </span>
                                </div>
                                <div>
                                    <div style="float: left; width: 70%">
                                        <h4>Short Description:</h4>
                                        <textarea class="form-control" rows="5" cols="50" name="p_food_description" maxlength="300" required>${food.description}</textarea>
                                    </div>
                                </div>
                                <br/>
                                <br/>
                                <br/>
                                <div class="price-box-bar">
                                    <div class="button-group filter-button-group">
                                        <button type="button" data-filter="*" class="hover-action" onclick="confirmCreate()">Create</button>
                                        <input value="Create" name="btnAction" type="submit" style="display: none" id="btnAction"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </form>                    
            <script>
                function quantityValidation() {
                    var n = document.getElementById("quantity").value;

                    if (isNaN(n)) {
                        document.getElementById(
                                "notifyQuantity").style.borderColor = "red";
                        document.getElementById(
                                "notifyQuantity").title = "Please enter Numeric value";
                        return false;
                    } else if (n < 0) {
                        document.getElementById(
                                "notifyQuantity").style.borderColor = "red";
                        document.getElementById(
                                "notifyQuantity").title = "Please enter Positive number";
                        return false;
                    } else {
                        return true;
                    }
                }
            </script>                    

            <script>
                function encodeImageFileAsURL(element) {
                    var file = element.files[0];
                    var reader = new FileReader();
                    reader.onloadend = function () {
                        if (reader.result.length !== 0 || reader.result) {
                            document.getElementById("imgData").value = reader.result;
                        }
                    };
                    reader.readAsDataURL(file);
                }

                function confirmCreate() {
                    if (!confirm("Are you sure to create this food?")) {
                        return false;
                    }
                    document.getElementById("btnAction").click();
                }
            </script>                    
        </div>
        <%-- End Details Panel--%>     

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