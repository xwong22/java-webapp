package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.secondLvlIndicatorDBBean;

@WebServlet("/InsertServletSecondLvl")
public class InsertServletSecondLvl extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	secondLvlIndicatorDBBean secondLvlIndicatorDBBean = null;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String country = request.getParameter("country");
		int year = Integer.parseInt(request.getParameter("year"));
		int count = Integer.parseInt(request.getParameter("count"));
		String indicatorName = request.getParameter("indicatorName");
		
		secondLvlIndicatorDBBean = new secondLvlIndicatorDBBean();
		secondLvlIndicatorDBBean.insert(indicatorName, country, year, count);
		
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

}
