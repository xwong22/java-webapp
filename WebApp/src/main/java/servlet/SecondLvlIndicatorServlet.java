package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.secondLvlIndicatorBean;
import beans.secondLvlIndicatorCountryBean;
import beans.secondLvlIndicatorDBBean;

@WebServlet("/SecondLvlIndicatorServlet")
public class SecondLvlIndicatorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// 需要用来连接数据库，调取查询结果
	secondLvlIndicatorDBBean secondLvlIndicatorDBBean = null;
	
	// doGet方法
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		// 初始化变量
		String indicatorName = "";
		String[] indicatorArray = {"d111", "d112", "d121", "d122"};
		String indicatorLabel =  request.getParameter("indicatorName");
		
		// 从前端的请求中获取指标名称
		for (String i : indicatorArray) {
			if (i.equals(indicatorLabel)) {
				indicatorName = i;
			}
		}
		
		// 从前端的请求中获取国家列表、要修改的指标名称与id
		String[] countriesArray =  request.getParameterValues("countries");
		String updateIndicatorName = request.getParameter("updateIndicatorName");
		String updateIdString = request.getParameter("updateId");
		int updateId = 0;
		if (updateIdString != null) {
			updateId = Integer.parseInt(updateIdString);
		}
		
		// 判断收到的是来自哪个表格的请求
		// 如果是来自首页的表格
		if ((indicatorName != null) && (indicatorName != "")) {
			secondLvlIndicatorDBBean = new secondLvlIndicatorDBBean(indicatorName);
			secondLvlIndicatorBean resultsForIndicator = secondLvlIndicatorDBBean.getResult();
			
			HttpSession session = request.getSession();
			session.setAttribute("resultsForIndicator", resultsForIndicator);
			
			// to solve bean not found within scope issue
			secondLvlIndicatorCountryBean resultByCountry = new secondLvlIndicatorCountryBean();
			session.setAttribute("resultByCountry", resultByCountry);
			
			request.getRequestDispatcher("/resultSecondLvl.jsp").forward(request, response);
			
			// 如果是来自生成图表的表格
		} else if ((countriesArray != null ) && (countriesArray.length != 0)) {
			System.out.println("i am here in else block");
			
			secondLvlIndicatorDBBean = new secondLvlIndicatorDBBean(indicatorName);
			secondLvlIndicatorBean resultsForIndicator = secondLvlIndicatorDBBean.getResult();
			
			String hiddenIndicatorName = request.getParameter("hiddenIndicatorName");
			secondLvlIndicatorDBBean = new secondLvlIndicatorDBBean(hiddenIndicatorName, countriesArray);
			secondLvlIndicatorCountryBean resultByCountry = secondLvlIndicatorDBBean.getResultByCountry();
			
			HttpSession session = request.getSession();
			session.setAttribute("resultByCountry", resultByCountry);
			
			session.setAttribute("resultsForIndicator", resultsForIndicator);
			
			request.getRequestDispatcher("/visualizationSecondLvl.jsp").forward(request, response);
			
			// 如果是来自修改指标的表格
		} else if (updateId != 0) {
			secondLvlIndicatorDBBean = new secondLvlIndicatorDBBean(updateIndicatorName, updateId);
			secondLvlIndicatorBean resultsForIndicator = secondLvlIndicatorDBBean.getResult();
			
			HttpSession session = request.getSession();
			session.setAttribute("resultsForIndicator", resultsForIndicator);
			session.setAttribute("updateIndicatorName", updateIndicatorName);
			
			request.getRequestDispatcher("/updateSecondLvlIndicator.jsp").forward(request, response);
		}
	}
	
	// doPost方法
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
