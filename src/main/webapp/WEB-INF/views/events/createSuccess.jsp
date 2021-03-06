<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<c:url var="resourceUrl" value="/resources" />
<link href="${resourceUrl}/bootstrap-3.3.1/css/bootstrap.css"
	rel="stylesheet" />
<link href="${resourceUrl}/css/custom.css" rel="stylesheet" />
<title>Registration Success</title>
</head>
<body class="header">
	<div class="container" align="center">
		<jsp:include page="../includes/header.jsp" />
		<table border="0">
			<tr>
				<td colspan="2" align="center"><h2>Registration Succeeded!</h2></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<h3>Thank you for registering! Here's the review of your
						details:</h3>
				</td>
			</tr>
			<tr>
				<td>Owner:</td>
				<td>${event.owner}</td>
			</tr>
			<tr>
				<td>Summary:</td>
				<td>${event.summary}</td>
			</tr>
			<tr>
				<td>Description:</td>
				<td>${event.description}</td>
			</tr>
			<tr>
				<td>Time:</td>
				<td>${event.when}</td>
			</tr>
		</table>
		<input type="button" value="Home"
			onClick="location.href='/calendar-dev/';">
	</div>
</body>
</html>