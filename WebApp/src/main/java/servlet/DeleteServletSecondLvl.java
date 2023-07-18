package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.secondLvlIndicatorDBBean;

@WebServlet("/DeleteServletSecondLvl")
public class DeleteServletSecondLvl extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	secondLvlIndicatorDBBean secondLvlIndicatorDBBean = null;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		String indicatorName = request.getParameter("indicatorName");
		int deleteId = Integer.parseInt(request.getParameter("deleteId"));
		
		secondLvlIndicatorDBBean = new secondLvlIndicatorDBBean();
		secondLvlIndicatorDBBean.deleteById(indicatorName, deleteId);
		
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}
}
