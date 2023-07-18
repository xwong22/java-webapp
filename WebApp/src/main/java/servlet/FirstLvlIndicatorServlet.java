package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.firstLvlIndicatorBean;
import beans.firstLvlIndicatorCountryBean;
import beans.firstLvlIndicatorDBBean;

@WebServlet("/FirstLvlIndicatorServlet")
public class FirstLvlIndicatorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// 需要用来连接数据库，调取查询结果
	firstLvlIndicatorDBBean firstLvlIndicatorDBBean = null;
	
	// doGet方法
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		// 初始化变量
		String indicatorName = "";
		String[] indicatorArray = {"d11", "d12"};
		
		// 从前端的请求中获取指标名称
		String indicatorLabel =  request.getParameter("indicatorName");
		
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
			firstLvlIndicatorDBBean = new firstLvlIndicatorDBBean(indicatorName);
			firstLvlIndicatorBean resultsForIndicator = firstLvlIndicatorDBBean.getResult();
						
			HttpSession session = request.getSession();
			session.setAttribute("resultsForIndicator", resultsForIndicator);
			
			// to solve bean not found within scope issue
			firstLvlIndicatorCountryBean resultByCountry = new firstLvlIndicatorCountryBean();
			session.setAttribute("resultByCountry", resultByCountry);
			
			request.getRequestDispatcher("/resultFirstLvl.jsp").forward(request, response);
		
			// 如果是来自生成图表的表格
		} else if ((countriesArray != null ) && (countriesArray.length != 0)) {
			
			firstLvlIndicatorDBBean = new firstLvlIndicatorDBBean(indicatorName);
			firstLvlIndicatorBean resultsForIndicator = firstLvlIndicatorDBBean.getResult();
			
			String hiddenIndicatorName = request.getParameter("hiddenIndicatorName");
			firstLvlIndicatorDBBean = new firstLvlIndicatorDBBean(hiddenIndicatorName, countriesArray);
			firstLvlIndicatorCountryBean resultByCountry = firstLvlIndicatorDBBean.getResultByCountry();
			
			HttpSession session = request.getSession();
			session.setAttribute("resultByCountry", resultByCountry);
			
			session.setAttribute("resultsForIndicator", resultsForIndicator);

			request.getRequestDispatcher("/visualizationFirstLvl.jsp").forward(request, response);
			
			// 如果是来自修改指标的表格
		} else if (updateId != 0) {
			firstLvlIndicatorDBBean = new firstLvlIndicatorDBBean(updateIndicatorName, updateId);
			firstLvlIndicatorBean resultsForIndicator = firstLvlIndicatorDBBean.getResult();
			
			HttpSession session = request.getSession();
			session.setAttribute("resultsForIndicator", resultsForIndicator);
			session.setAttribute("updateIndicatorName", updateIndicatorName);
			
			request.getRequestDispatcher("/updateFirstLvlIndicator.jsp").forward(request, response);
		}
	}
	
	// doPost方法
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
