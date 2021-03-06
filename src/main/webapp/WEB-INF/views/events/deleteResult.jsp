<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
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
		<sec:authorize access="hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')">
			<c:if test="${msg != null}">
				<div class="alert alert-success" id="message">
					<c:out value="${msg}" />
				</div>
			</c:if>
		</sec:authorize>
		<table border="0">
			<tr>
				<td colspan="2" align="center"><h2>Delete Succeeded!</h2></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<h3>Delete results</h3>
				</td>
			</tr>
			<tr>
				<td align="center">Events</td>
				<c:forEach items="${nameList}" var="event" varStatus="status">
					<tr>
						<td align="center">${event}</td>
					</tr>
				</c:forEach>
			</tr>
		</table>
	</div>
</body>
</html>