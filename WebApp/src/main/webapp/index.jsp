<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<body>
	<jsp:include page="base.jsp" />

	<div class="container">
		<br>
		
		<h1>请选择指标</h1>

		<br>
	</div>
	
	<!-- 一级指标 -->
	<div class="container">
		<h2>一级指标</h2>
		
		<br>
		
		<form
			action="<%=request.getContextPath()%>/FirstLvlIndicatorServlet"
			method="post">

			<div class="form-check">
				<input class="form-check-input" type="radio" name="indicatorName"
					id="d11" size=30 value="d11"> <label
					class="form-check-label" for="d11"> D-11 </label>
			</div>
			<div class="form-check">
				<input class="form-check-input" type="radio" name="indicatorName"
					id="d12" size=30 value="d12"> <label
					class="form-check-label" for="d12"> D-12 </label>
			</div>
			<br> 
			<input type=submit value="查询" class="btn btn-dark">
		</form>
	</div>
	
	<br>

	<!-- 二级指标 -->
	<div class="container">
		<h2>二级指标</h2>
		
		<br>
		
		<form
			action="<%=request.getContextPath()%>/SecondLvlIndicatorServlet"
			method="post">

			<div class="form-check">
				<input class="form-check-input" type="radio" name="indicatorName"
					id="d111" size=30 value="d111"> <label
					class="form-check-label" for="d111"> D-111 </label>
			</div>
			<div class="form-check">
				<input class="form-check-input" type="radio" name="indicatorName"
					id="d112" size=30 value="d112"> <label
					class="form-check-label" for="d112"> D-112 </label>
			</div>
			<div class="form-check">
				<input class="form-check-input" type="radio" name="indicatorName"
					id="d121" size=30 value="d121"> <label
					class="form-check-label" for="d121"> D-121 </label>
			</div>
			<div class="form-check">
				<input class="form-check-input" type="radio" name="indicatorName"
					id="d122" size=30 value="d122"> <label
					class="form-check-label" for="d122"> D-122 </label>
			</div>

			<br> <input type=submit value="查询" class="btn btn-dark">
		</form>
	</div>
</body>
</html>