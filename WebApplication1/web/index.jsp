<%-- 
    Document   : index
    Created on : 20/05/2020, 9:53:12 AM
    Author     : Rob
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Stock Restful</title>
    </head>
    <body>
        <h1>Stock Restful Web Service</h1>
        
        <a href="<%= response.encodeURL(request.getContextPath())%>/stockservice/stock">Get all stock</a></br>
        <a href="<%= response.encodeURL(request.getContextPath())%>/stockservice/stock/positive">Get positive stock</a></br>
        <a href="<%= response.encodeURL(request.getContextPath())%>/stockservice/stock/negative">Get negative stock</a></br>
        
    </body>
</html>
