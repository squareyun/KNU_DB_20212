<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="text/html; charset=UTF-8">
    <title>DataBase App</title>
    <link rel="stylesheet" href="css/bootstrap.css" />
    <link rel="stylesheet" href="css/custom.css"/>
	<link rel="stylesheet" href="css/friends.css"/>
	<link rel="stylesheet" href="css/index.css"/>
    <link rel="stylesheet" href="css/login.css?after" />
    <script src="http://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src="js/bootstrap.js"></script>

    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
	
    <script> 
		 function showErrorMessage(msg){
		  		console.log(msg);
			    Swal.fire({
			        icon: 'error',
			        title: msg,
			        showConfirmButton: true,
			      })
			      
		 	return;      
		}
		function showSuccessMessage(msg){
		  		console.log(msg);
			    Swal.fire({
			        icon: 'success',
			        title: msg,
			        showConfirmButton: false,
			        timer: 1000
			     })  
		 	return;      
		}
  </script>
</head>