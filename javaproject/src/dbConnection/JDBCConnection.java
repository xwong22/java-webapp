package dbConnection;

import java.sql.*;

public class JDBCConnection {
	// 数据库连接对象
	public Connection connection;
	
	
	// 方法：建立连接 + 连接原有数据库
	public JDBCConnection() {
		try {
			// Java6之后不需要再explicitly指明driver名称
			
			// 加载驱动程序
			//String className = "com.mysql.cj.jbdc.Driver";
			//Class.forName(className);
			System.out.println("正在建立数据库链接...");
			String URL = "jdbc:mysql://localhost:3307/researchDB";
			String USER = "root";
			String PASSWORD = "1234";
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("数据库连接成功。");
		}
		// 因为没有用Class.forName所以不需要
//		// 找不到驱动程序
//		catch (ClassNotFoundException ex) {
//			System.out.println("找不到数据库驱动程序");
//			ex.printStackTrace();
//		}
		// 不能连接到数据库
		catch (SQLException ex) {
			System.out.println("不能建立和数据库的连接");
		}
		
	}
	
	
	// 方法：关闭连接
	public void closeConnection() {
		try {
			System.out.println("正在关闭数据库连接...");
			connection.close();
			System.out.println("数据库连接关闭成功。");
			
		}
		catch (Exception ex) {
			System.out.println("数据库连接关闭失败。");
		}
	}
}
