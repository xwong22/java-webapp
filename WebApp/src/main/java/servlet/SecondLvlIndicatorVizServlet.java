package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.secondLvlIndicatorCountryBean;
import beans.secondLvlIndicatorDBBean;

// 负责处理可视化请求
@WebServlet("/SecondLvlIndicatorVizServlet")
public class SecondLvlIndicatorVizServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	secondLvlIndicatorDBBean secondLvlIndicatorDBBean = null;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		String hiddenIndicatorName = request.getParameter("hiddenIndicatorName");
		String[] countriesArray =  request.getParameterValues("countries");
	
		if ((countriesArray != null ) && (countriesArray.length != 0)) {

			secondLvlIndicatorDBBean = new secondLvlIndicatorDBBean(hiddenIndicatorName, countriesArray);
			secondLvlIndicatorCountryBean resultByCountry = secondLvlIndicatorDBBean.getResultByCountry();
			
			HttpSession session = request.getSession();
			session.setAttribute("resultByCountry", resultByCountry);
			
			request.getRequestDispatcher("/visualizationSecondLvl.jsp").forward(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
