<%@ include file="common/header.jspf" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">
    <div class="row well">
        <div class="span10 offset2">
            <div class="row">

                <div class="span10">
                    <h3>Welcome to Pitkiyot - Your ToDoList Manager</h3>

                    <c:if test="${sessionScope.user == null}">
                        <p>
                            <a class="btn btn-primary btn-large" href="/login"> Sign in </a> or <a class="btn btn-primary btn-large" href="/register"> Sign up </a>
                        </p>
                    </c:if>

                    <c:if test="${sessionScope.user != null}">
                        <p>
                            <a class="btn btn-primary btn-large" href="/todos"> Go to my Home page </a>
                        </p>
                    </c:if>



                </div>

            </div>

        </div>

    </div>
</div>

<%@ include file="common/footer.jspf" %>
