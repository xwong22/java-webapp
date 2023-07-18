package executeUpdate;

import java.sql.*;

public class SQLUpdate {
	private Statement stmt;

	public SQLUpdate(Statement stmt) {
		this.stmt = stmt;
	}
	
	public void executeSQL(String sql) {
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("SQL with Error: " + sql);
			e.printStackTrace();
		}
	}
}
