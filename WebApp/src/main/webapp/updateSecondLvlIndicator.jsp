<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>修改二级指标</title>
</head>
<body>
	<jsp:include page="base.jsp" />
	
	<jsp:useBean id="resultsForIndicator"
		type="beans.secondLvlIndicatorBean" scope="session"></jsp:useBean>
		
	<br>
	
	<div class="container">
		<h1>修改二级指标</h1>
	</div>
	
	<br>
	
	<div class="container">
		<form action="<%=request.getContextPath()%>/UpdateServletSecondLvl"
			method="post">
			<div>
				<label for="country" class="form-label">Country</label>
				<input
					type="text" name="country" id="country" class="form-control" size=30
					value="<%=resultsForIndicator.get(0).getCountry()%>">
			</div>
			
			<div>
				<label for="year" class="form-label">Year</label>
				<input
					type="text" name="year" id="year" class="form-control" size=30
					value="<%=resultsForIndicator.get(0).getYear()%>">
			</div>
			
			<div>
				<label for="count" class="form-label">Count</label>
				<input
					type="text" name="count" id="count" class="form-control" size=30
					value="<%=resultsForIndicator.get(0).getCount()%>">
			</div>
				
			<input
				type="hidden" name="indicatorName" size=30
				value="<%=session.getAttribute("updateIndicatorName")%>"> 
				
			<input
				type="hidden" value=<%=resultsForIndicator.get(0).getId()%>
				name="updateId"> 
			
			<br>
			
			<input type="submit" value="Update" class="btn btn-dark">
		</form>
	</div>


</body>
</html>