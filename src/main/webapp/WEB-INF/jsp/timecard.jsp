<%--
  Created by IntelliJ IDEA.
  User: PrivateAcc
  Date: 2017-10-21
  Time: 2:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>SFU</title>

    <!-- Bootstrap core CSS -->
    <link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="resources/css/simple-sidebar.css" rel="stylesheet">

    <link rel="stylesheet" href="resources/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="resources/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="resources/css/form-elements.css">
    <link rel="stylesheet" href="resources/css/style.css">

    <!-- jQuery Resources -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://momentjs.com/downloads/moment.min.js"></script>
    <script src="http://code.jquery.com/ui/1.11.1/jquery-ui.min.js"></script>
    <script src= 'resources/js/timecard.js'></script>
</head>

<style>
    #side-container{
    }

    #side-contact{
        position: absolute;
        bottom: 0;
        color: #ffffff;
    }

</style>

<body>

<div id="wrapper" class="toggled">

    <jsp:include page="partfiles/sidebar.jsp"/>


    <!-- Page Content -->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <i class="fa fa-bars fa-2x sidebar-brand" id="menu-toggle"></i>
            <div class="col-sm-12 text">
                <div class="description" style="margin-bottom: 2em;">
                    <center>
                        <img src="resources/img/logo_made/logo_2.png" class="img-responsive" style="height:100px;width:500px">

                    </center>
                </div>
                <div class="panel panel-primary">
                    <div class="panel-heading">Time Card</div>
                    <div class="panel-body">

                        <form:form modelAttribute="timeCard" method="post">
                            <div class="row form-group">
                                <div class="col-md-4">
                                    <label><form:radiobutton path="campus" name="campus" id="vancouver" value="VANCOUVER"/> Vancouver </label>
                                </div>
                                <div class="col-md-4">
                                    <label><form:radiobutton path="campus" name="campus" id="burnaby" value="BURNABY"/> Burnaby </label>
                                </div>
                                <div class="col-md-4">
                                    <label><form:radiobutton path="campus" name="campus" id="surrey" value="SURREY"/> Surrey </label>
                                </div>
                                <div class="col-md-6 ">
                                    <label > Date : </label>
                                    <form:input path="date" class="form-control" type="date" id="date" value=""/>
                                </div>
                                <div class="col-md-6" style="margin-bottom: 1em;">
                                    <label > Location : </label>
                                    <form:input path="location" class="form-control" type="input" id="location" value=""/>
                                </div>
                                <div class="col-md-12">
                                    <div class="panel panel-info">
                                        <div class="panel-heading">Action</div>
                                        <div class="panel-body">
                                            <div class="form-group">
                                                <table class="table table-bordered" id="action">
                                                    <tbody>
                                                    <tr>
                                                        <th>Item</th>
                                                        <th>#</th>
                                                        <th>Total</th>
                                                    </tr>
                                                    <tr>
                                                        <td style="padding-top: 1.5%;">Smoking Prevention</td>
                                                        <td>
                                                            <button type="button" name='increaseTotal' class="btn" style="background-color: blue;"><span class="glyphicon glyphicon-plus"></span></button>
                                                            <button type="button" name='decreaseTotal' class="btn"><span class="glyphicon glyphicon-minus"></span></button>
                                                        </td>
                                                        <td><form:input path="SPTotal" type="input" class="form-control" id="SPTotal" value="0" /></td>
                                                    </tr>
                                                    <tr>
                                                        <td style="padding-top: 1.5%;">Theft Prevention</td>
                                                        <td>
                                                            <button type="button" name='increaseTotal' class="btn" style="background-color: blue;"><span class="glyphicon glyphicon-plus"></span></button>
                                                            <button type="button" name='decreaseTotal' class="btn"><span class="glyphicon glyphicon-minus"></span></button>
                                                        </td>
                                                        <td><form:input path="TPTotal" type="input" class="form-control" id="TPTotal" value="0"/></td>
                                                    </tr>
                                                    <tr>
                                                        <td style="padding-top: 1.5%;">Public Contact</td>
                                                        <td>
                                                            <button type="button" name='increaseTotal' class="btn" style="background-color: blue;"><span class="glyphicon glyphicon-plus"></span></button>
                                                            <button type="button" name='decreaseTotal' class="btn"><span class="glyphicon glyphicon-minus"></span></button>
                                                        </td>
                                                        <td><form:input path="PCTotal" type="input" class="form-control" id="PCTotal" value="0"/></td>
                                                    </tr>
                                                    <tr>
                                                        <td style="padding-top: 1.5%;">Safe Walks</td>
                                                        <td>
                                                            <button type="button" name='increaseTotal' class="btn" style="background-color: blue;"><span class="glyphicon glyphicon-plus"></span></button>
                                                            <button type="button" name='decreaseTotal' class="btn"><span class="glyphicon glyphicon-minus"></span></button>
                                                        </td>
                                                        <td><form:input path="SWTotal" type="input" class="form-control" id="SWTotal" value="0"/></td>
                                                    </tr>
                                                    <tr>
                                                        <td style="padding-top: 1.5%;">Hazards/Service Request</td>
                                                        <td>
                                                            <button type="button" name='increaseTotal' class="btn" style="background-color: blue;"><span class="glyphicon glyphicon-plus"></span></button>
                                                            <button type="button" name='decreaseTotal' class="btn"><span class="glyphicon glyphicon-minus"></span></button>
                                                        </td>
                                                        <td><form:input path="HSRTotal" type="input" class="form-control" id="HSRTotal" value="0"/></td>
                                                    </tr>
                                                    <tr>
                                                        <td style="padding-top: 1.5%;">Assist Security</td>
                                                        <td>
                                                            <button type="button" name='increaseTotal' class="btn" style="background-color: blue;"><span class="glyphicon glyphicon-plus"></span></button>
                                                            <button type="button" name='decreaseTotal' class="btn"><span class="glyphicon glyphicon-minus"></span></button>
                                                        </td>
                                                        <td><form:input path="ASTotal" type="input" class="form-control" id="ASTotal" value="0"/></td>
                                                    </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-md-12 ">
                                    <label > Notes : </label>
                                    <form:input path="notes" class="form-control" type="input" id="notes" value=""/>
                                </div>

                                <div class="pull-right">
                                    <form:button type="submit" name="timecard" id="timecard" class="btn btn-success" style="margin-top:1em;margin-right:1em;">Submit Time Card</form:button>
                                </div>

                            </div> <%--end of row--%>
                        </form:form> <%--end of form--%>
                    </div> <%--end of panel body--%>
                </div><%--end of panel primary--%>
            </div>
        </div>
    </div>
    <!-- /#page-content-wrapper -->

</div>
<!-- /#wrapper -->



<!-- Bootstrap core JavaScript -->
<script src="resources/jquery/jquery.min.js"></script>
<script src="resources/popper/popper.min.js"></script>
<script src="resources/bootstrap/js/bootstrap.min.js"></script>

<!-- Menu Toggle Script -->
<script>
    $("#menu-toggle").click(function(e) {
        e.preventDefault();
        $("#wrapper").toggleClass("toggled");
    });
</script>

</body>
</html>