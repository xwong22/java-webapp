<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>一级指标数据展示</title>
</head>
<body>

<jsp:include page="base.jsp" />

	<jsp:useBean id="resultsForIndicator"
		type="beans.firstLvlIndicatorBean" scope="session"></jsp:useBean>
		
	<br>

	<div class="container">
		<h1>
			指标名称：
			<jsp:getProperty name="resultsForIndicator" property="indicatorName" /></h1>

	</div>

	<br>
	
	<div class="container">
	<a href="<%=request.getContextPath()%>/insertFirstLvlIndicator.jsp"><button type="button" class="btn btn-dark">新增指标</button></a>
	</div>
	
	<br>

	<div class="container">
		<table class="table">
			<thead>
				<tr>
					<th scope="col">#</th>
					<th scope="col">Country</th>
					<th scope="col">Year</th>
					<th scope="col">Score</th>
				</tr>
			</thead>

			<tbody>
				<%
				for (int i = 0; i < resultsForIndicator.getSize(); i++) {
				%>
				<tr>
					<th scope="row"><%=i%></th>
					<td><%=resultsForIndicator.get(i).getCountry()%></td>
					<td><%=resultsForIndicator.get(i).getYear()%></td>
					<td><%=resultsForIndicator.get(i).getScore()%></td>
					<td>
					<form
						action="<%=request.getContextPath()%>/FirstLvlIndicatorServlet"
						method="post">
						<input type="hidden" value=<jsp:getProperty name="resultsForIndicator" property="indicatorName" /> name="updateIndicatorName">
						<input type="hidden" value=<%=resultsForIndicator.get(i).getId()%> name="updateId">
						<input type="submit" class="btn btn-dark" value="修改">
					</form>
					</td>
					<td>
					<form
						action="<%=request.getContextPath()%>/DeleteServletFirstLvl"
						method="post">
						<input type="hidden" value=<jsp:getProperty name="resultsForIndicator" property="indicatorName" /> name="indicatorName">
						<input type="hidden" value=<%=resultsForIndicator.get(i).getId()%> name="deleteId">
						<input type="submit" class="btn btn-dark" value="删除">
					</form>
					</td>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>

	</div>

	<br>
	
	<div class="container">
		<h2>请选择国家以进行数据可视化：</h2>
	</div>
	
	<br>

	<div class="container">
		<form action="<%=request.getContextPath()%>/FirstLvlIndicatorVizServlet"
			method="post">
			<p>国家选项（可多选）：</p>
			<input type="hidden" name="hiddenIndicatorName" value=<jsp:getProperty name="resultsForIndicator" property="indicatorName"/>>
			<div class="form-check">
				<input type=checkbox name="countries" id="china" class="btn-check"
					autocomplete="off" value="China" size=30> <label
					class="btn btn-primary" for="china">China</label>
			</div>
			<div class="form-check">
				<input type=checkbox name="countries" id="russia" class="btn-check"
					autocomplete="off" value="Russia" size=30> <label
					class="btn btn-primary" for="russia">Russia</label>
			</div>
			<div class="form-check">
				<input type=checkbox name="countries" id="usa" class="btn-check"
					autocomplete="off" value="USA" size=30> <label
					class="btn btn-primary" for="usa">USA</label>
			</div>
			<div class="form-check">
				<input type=checkbox name="countries" id="england" class="btn-check"
					autocomplete="off" value="England" size=30> <label
					class="btn btn-primary" for="england">England</label>
			</div>
			<div class="form-check">
				<input type=checkbox name="countries" id="france" class="btn-check"
					autocomplete="off" value="France" size=30> <label
					class="btn btn-primary" for="france">France</label>
			</div>
			
			<br>

			<input type=submit value="生成图表" class="btn btn-dark">
		</form>
	</div>

</body>
</html>