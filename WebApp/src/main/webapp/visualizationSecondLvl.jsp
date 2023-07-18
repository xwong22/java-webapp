<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>二级指标数据可视化</title>
</head>
<body>
	<jsp:include page="base.jsp" />

	<jsp:useBean id="resultByCountry"
		type="beans.secondLvlIndicatorCountryBean" scope="session"></jsp:useBean>
		
	<br>

	<div class="container">
		<h1><%=resultByCountry.get(0).getIndicatorName()%></h1>
		
		<br>

		<canvas id="chart" style="width: 100%; max-width: 700px"></canvas>
	</div>
</body>

<script>

	const BGcolours = [
		"rgba(38, 70, 83, 1.0)",
		"rgba(42, 157, 143, 1.0)",
		"rgba(233, 196, 106, 1.0)",
		"rgba(244, 162, 97, 1.0)",
		"rgba(231, 111, 81, 1.0)"
	]

	
	var count = new Object();
	var year = [2019, 2020, 2021];
	var temp = [];
	var countries = [];
	
	<%for (int i = 0; i < resultByCountry.getSize(); i++) {%>
		temp = [];
		console.log("<%=resultByCountry.get(i).getSearchCondition()%>");
		<%for (int j = 0; j < resultByCountry.get(i).getSize(); j++) {%>
			temp.push(<%=resultByCountry.get(i).get(j).getCount()%>);
		<%}%>
			count["<%=resultByCountry.get(i).getSearchCondition()%>"] = temp;
			countries.push("<%=resultByCountry.get(i).getSearchCondition()%>");
			console.log(count);
	<%}%>

	
	new Chart("chart", {
		type : "line",
		data : {
			labels : year,
			datasets : [
				<%for (int k = 0; k < resultByCountry.getSize(); k++) {%>
					{
						label : countries[<%=k%>],
						backgroundColor : BGcolours[<%=k%>],
						borderColor : BGcolours[<%=k%>],
						data : count[countries[<%=k%>]]
					},
				<%}%>
			]
		},
	});
</script>
</html>