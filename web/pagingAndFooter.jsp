<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Start Paging-->
        <c:set var="max_page" value="${requestScope.MAX_PAGE}"/>
        <c:set var="cur_page" value="${(empty requestScope.CUR_PAGE) || (requestScope.CUR_PAGE le 0) ? 1 : requestScope.CUR_PAGE }"/>

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


        <!-- Start copyright  -->
        <div class="footer-copyright">
            <p class="footer-company">All Rights Reserved. &copy; 2018 <a href="#">ThewayShop</a> Design By :
                <a href="https://html.design/">html design</a></p>
        </div>
        <!-- End copyright  -->