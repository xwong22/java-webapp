<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新增二级指标</title>
</head>
<body>
	<jsp:include page="base.jsp" />
	
	<br>
	
	<div class="container">
		<h1>新增二级指标</h1>
	</div>
	
	<br>

	<div class="container">
		<form action="<%=request.getContextPath()%>/InsertServletSecondLvl"
			method="post">
			
			<div>
				<label for="indicatorName" class="form-label">Indicator Name</label>
				<input type="text" name="indicatorName" id="indicatorName" class="form-control" size=30>
			</div>
			
			<div>
				<label for="name" class="form-label">Country</label>
				<input type="text" name="country" id="country" class="form-control" size=30>
			</div>
			
			<div>
				<label for="year" class="form-label">Year</label>
				<input type="text" name="year" id="year" class="form-control" size=30>
			</div>
			
			<div>
				<label for="count" class="form-label">Count</label>
				<input type="text" name="count" id="count" class="form-control" size=30>
			</div>
			
			<br>
			
			<input type="submit" value="Insert" class="btn btn-dark">
	
	
		</form>
	</div>
	

</body>
</html>