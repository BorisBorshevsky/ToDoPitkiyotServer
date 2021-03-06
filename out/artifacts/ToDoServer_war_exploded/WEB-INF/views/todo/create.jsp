<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/header.jspf"%>

<div class="container">
    <div class="row">
        <div class="span3">
            <%@ include file="../common/sidebar.jspf"%>
        </div>
        <div class="span9">
            <div class="well">
                <div class="page-header">
                    <h1>Create a new todo</h1>
                </div>

                <form id="createTodoForm" action="/todos/new.do" method="post" class="form-horizontal">

                    <fieldset>

                        <div class="control-group">
                            <label class="control-label" for="title">Title:</label>
                            <div class="controls">
                                <input type="text" id="title" name="title" required="required" autofocus="autofocus" />
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label" for="dueDate">Due date:</label>
                            <div class="controls">
                                <input type="text" id="dueDate" name="dueDate" data-format="yyyy-MM-dd" value="${requestScope.today}" required="required" />
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label" for="priority">Priority:</label>
                            <div class="controls">
                                <select id="priority" name="priority">
                                  <option value="LOW">Low</option>
                                  <option value="MEDIUM">Medium</option>
                                  <option value="HIGH">High</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-actions">
                            <button type="submit" class="btn btn-primary"> <i class="icon-ok icon-white"></i> Create</button>
                            <button type="button" class="btn" onclick="history.go(-1)"><i class="icon-remove"></i> Cancel</button>
                        </div>

                    </fieldset>

                    <script>
                        $('#dueDate').datepicker({
                            format : 'dd/mm/yyyy'
                        });
                    </script>

                </form>

            </div>
        </div>
    </div>
</div>

<%--end content--%>
<%@ include file="../common/footer.jspf"%>
