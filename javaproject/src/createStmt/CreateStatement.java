package createStmt;

import java.sql.*;
import dbConnection.JDBCConnection;

public class CreateStatement {
	public Statement stmt;
	
	// 构造方法
	public CreateStatement(JDBCConnection dc) {
		try {
			stmt = dc.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			System.out.println("语句对象创建成功");
		} catch (SQLException e) {
			System.out.println("不能建立语句对象");
		}
	}
	
	// 关闭语句对象
	public void close() {
		try {
			System.out.println("正在关闭语句对象...");
			stmt.close();
			System.out.println("语句对象关闭成功");
		} catch (Exception e) {
			System.out.println("语句对象关闭失败");
		}
	}

}
