package calculateValue;

import createStmt.CreateStatement;
import dbConnection.JDBCConnection;
import executeUpdate.SQLUpdate;

public class calculateD12 {
	
	public void calculateAndCreateTable() {
		String sql = "CREATE TABLE d12 AS "
				+ "SELECT \r\n"
				+ "    a.country, \r\n"
				+ "    a.year, \r\n"
				+ "    ((a.standardizedCount + b.standardizedCount) / 2) AS score \r\n"
				+ "FROM researchdb.d121 AS a, researchdb.d122 AS b \r\n"
				+ "WHERE a.country = b.country AND a.year = b.year";
		
		// 连接数据库
		JDBCConnection dc = new JDBCConnection();
		CreateStatement cst = new CreateStatement(dc);
		SQLUpdate eu = new SQLUpdate(cst.stmt);
		
		
		eu.executeSQL(sql);
		
		eu.executeSQL("ALTER TABLE researchdb.d12 ADD id INT NOT NULL AUTO_INCREMENT primary key FIRST");
		
		System.out.println("D-12计算完毕");
		cst.close();
		dc.closeConnection();
	}

}
