<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>

<script type="text/javascript" src="../ui/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="../ui/js/jquery.i18n.properties.js"></script>

<script>


//$(document).ready(function(){$('.botonEliminar').val("NS/NR");})

jQuery.i18n.properties({
    name: 'mensajes',
    path: '../messages/',  
    mode: 'both',
    language: 'es', 
    callback: function() {
    	alert(jQuery.i18n.prop('evento.label.eliminar'));
        //$('.botonEliminar').val("NS/NR");  

        
    }
});
</script>
</head>
<body>
<input class="botonEliminar" type="text"/> 
</body>
</html>