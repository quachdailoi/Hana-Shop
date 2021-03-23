
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
        <%-- START PRODUCT DETAILS --%>

        <div class="shop-detail-box-main">
            <div class="container">
                <form action="DispatcherServlet" method="POST" onsubmit="return validation()">
                    <input type="hidden" name="p_cur_page" value="${requestScope.CUR_PAGE}"/>
                    <input type="hidden" name="p_search_name" value="${search_name}"/>
                    <input type="hidden" name="p_search_cate" value="${search_cate}"/>
                    <input type="hidden" name="p_search_price" value="${search_price}"/>
                    <input type="hidden" name="p_is_scroll_down" value="true"/>
                    <c:set var="food" value="${requestScope.FOOD}"/>
                    <input type="hidden" name="p_old_food_name" value="${food.foodName}"/>
                    <input type="hidden" name="p_old_data_image" value="${food.imgData}"/>
                    <input type="hidden" name="p_old_food_description" value="${food.description}"/>
                    <input type="hidden" name="p_old_food_price" value="${food.price}"/>
                    <input type="hidden" name="p_old_food_quantity" value="${food.quantity}"/>
                    <input type="hidden" name="p_old_create_date" value="${food.createDate}"/>
                    <input type="hidden" name="p_old_cate_id" value="${food.cateId}"/>
                    <input type="hidden" name="p_old_food_status" value="${food.status}"/>
                    <div class="row">
                        <div class="col-xl-5 col-lg-5 col-md-6">
                            <div id="carousel-example-1" class="single-product-slider carousel slide" data-ride="carousel">
                                <div class="carousel-inner" role="listbox">
                                    <div class="carousel-item active"> <img class="d-block w-100" src="${food.imgData}" id="image" style="width: 350px; height: 450px" onerror="alert('Not image available!')"> </div>
                                </div>
                                <div class="file-input" style="float: left; width: 100% ;padding: 20px">
                                    <center>
                                        <input type="file" id="file" class="file" onchange="(document.getElementById('image').src = window.URL.createObjectURL(this.files[0])); console.log(this.value); encodeImageFileAsURL(this)"/>
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
                                    <span style="display: inline-block; padding: 20px">
                                        <label class="control-label">Food ID</label>
                                        <input style="text-align: center" class="form-control" value="${food.foodId}" name="p_food_id" type="text" style="width: 150px" readonly/>
                                    </span>
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
                                    <center><input type="text" class="form-control" value="${food.foodName}" name="p_food_name" style="width: 500px" required maxlength="20"/></center>
                                </div>
                                <div>
                                    <span style="display: inline-block; padding: 20px">
                                        <label class="control-label">Price</label>
                                        <input id="priceF" style="text-align: center" class="form-control" value="${food.price}" name="p_food_price" min="0" step="any" type="number" style="width: 150px" required/>
                                    </span>
                                    <span style="display: inline-block;padding: 20px">
                                        <label class="control-label">Updated Quantity</label>
                                        <input id="quantityF" style="text-align: center" class="form-control" value="${food.quantity}" name="p_food_quantity" min="0" type="number" style="width: 150px" required/>
                                    </span>
                                </div>
                                <div>
                                    <div style="float: left; width: 70%">
                                        <h4>Short Description:</h4>
                                        <textarea class="form-control" rows="5" cols="50" name="p_food_description" maxlength="300" required>${food.description}</textarea>
                                    </div>
                                    <div style="float: left;width: 30%">
                                        <h4>Status</h4>
                                        <div class="slideThree">  
                                            <input type="checkbox" value="checked" id="slideThree" name="p_food_status" ${food.status ? 'checked' : ''} />
                                            <label for="slideThree"></label>
                                        </div>
                                    </div>
                                </div>
                                <br/>
                                <div class="price-box-bar">
                                    <div class="button-group filter-button-group">
                                        <button type="submit" value="Update" name="btnAction" data-filter="*" class="hover-action" >Update</button>
                                        
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>  
                                            
            <script>
                function validation() {

                    if (!confirm("Are you sure to update food?")) {
                        return false;
                    } else {
                        var n = document.getElementById("priceF").value;
                        if (isNaN(n)) {
                            document.getElementById("priceF").style.borderColor = "red";
                            alert("Price is a Numeric Value");
                            return false;
                        } else if (n < 0) {
                            document.getElementById("priceF").style.borderColor = "red";
                            alert("Price is a Positive Number");
                            return false;
                        }

                        n = document.getElementById("quantityF").value;

                        if (isNaN(n) || !Number.isInteger()(n)) {
                            document.getElementById("quantityF").style.borderColor = "red";
                            alert("Quantity is an Integer Numeric Value");
                            return false;
                        } else if (n < 0) {
                            document.getElementById("quantityF").style.borderColor = "red";
                            alert("Quantity is an Integer Positive Number");
                            return false;
                        }

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

            </script>                    
        </div>
        <%-- END PRODUCT DETAILS --%>

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