package calculateValue;

import java.sql.ResultSet;
import java.sql.SQLException;

import createStmt.CreateStatement;
import dbConnection.JDBCConnection;
import executeUpdate.SQLUpdate;

public class calculateD112 {
	public void calculateAndCreateTable() {
		String sql = "CREATE TABLE D112 AS "
				+ "SELECT \r\n"
				+ "    country, \r\n"
				+ "    publicationYear AS year, \r\n"
				+ "    COUNT(*) AS count, \r\n"
				+ "    ( ((COUNT(*) - MIN(COUNT(*)) OVER (PARTITION BY publicationYear)) / (MAX(COUNT(*)) OVER (PARTITION BY publicationYear) - MIN(COUNT(*)) OVER (PARTITION BY publicationYear))) * 100 ) AS standardizedCount \r\n"
				+ "FROM researchdb.journals \r\n"
				+ "GROUP BY country, publicationYear \r\n"
				+ "HAVING publicationYear IN (2019, 2020, 2021) \r\n"
				+ "    AND country IN ('China', 'England', 'USA', 'France', 'Russia') ";
				
		
		// 连接数据库
		JDBCConnection dc = new JDBCConnection();
		CreateStatement cst = new CreateStatement(dc);
		SQLUpdate eu = new SQLUpdate(cst.stmt);
		
		eu.executeSQL(sql);
		eu.executeSQL("ALTER TABLE researchdb.d112 ADD id INT NOT NULL AUTO_INCREMENT primary key FIRST");
		
		String[] countriesArray = {"China", "France", "USA", "England", "Russia"};
		int[] yearsArray = {2019, 2020, 2021};
		
		for (String c : countriesArray) {
			for (int y : yearsArray) {
				ResultSet rset = null;
				try {
					String querySQL = "SELECT * FROM researchdb.d112 WHERE country = '" + c + "' AND year = " + y;
					rset = cst.stmt.executeQuery(querySQL);
					System.out.println("query done");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				try {
					if (!rset.next()) {
						String insertZeroSQL = "INSERT INTO researchdb.d112(country, year, count, standardizedCount) "
								+ "VALUES('" + c + "', " + y + ", 0, 0)";
						eu.executeSQL(insertZeroSQL);
						System.out.println("insert zero done");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		System.out.println("D-112计算完毕");
		cst.close();
		dc.closeConnection();
	}

}
