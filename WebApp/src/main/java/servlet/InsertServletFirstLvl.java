package servlet;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.firstLvlIndicatorDBBean;

@WebServlet("/InsertServletFirstLvl")
public class InsertServletFirstLvl extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	firstLvlIndicatorDBBean firstLvlIndicatorDBBean = null;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String country = request.getParameter("country");
		int year = Integer.parseInt(request.getParameter("year"));
		BigDecimal score = new BigDecimal(request.getParameter("score"));
		String indicatorName = request.getParameter("indicatorName");
		
		firstLvlIndicatorDBBean = new firstLvlIndicatorDBBean();
		firstLvlIndicatorDBBean.insert(indicatorName, country, year, score);
		
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

}