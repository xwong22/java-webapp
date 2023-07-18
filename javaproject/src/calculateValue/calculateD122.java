package calculateValue;

import java.sql.ResultSet;
import java.sql.SQLException;

import createStmt.CreateStatement;
import dbConnection.JDBCConnection;
import executeUpdate.SQLUpdate;

public class calculateD122 {
	
	public void calculateAndCreateTable() {
		String sql = "CREATE TABLE D122 AS "
				+ "SELECT \r\n"
				+ "    country, \r\n"
				+ "    authorizationYear AS year, \r\n"
				+ "    count(DISTINCT publicationID) AS count,\r\n"
				+ "    ( ((COUNT(DISTINCT publicationID) - MIN(COUNT(DISTINCT publicationID)) OVER (PARTITION BY authorizationYear)) / (MAX(COUNT(DISTINCT publicationID)) OVER (PARTITION BY authorizationYear) - MIN(COUNT(DISTINCT publicationID)) OVER (PARTITION BY authorizationYear))) * 100) AS standardizedCount \r\n"
				+ "FROM researchDB.Patents \r\n"
				+ "GROUP BY country, authorizationYear \r\n"
				+ "HAVING authorizationYear IN (2019, 2020, 2021)\r\n"
				+ "    AND country IN ('CN', 'GB', 'US', 'FR', 'RU')";
		
		// 连接数据库
		JDBCConnection dc = new JDBCConnection();
		CreateStatement cst = new CreateStatement(dc);
		SQLUpdate eu = new SQLUpdate(cst.stmt);
		
		eu.executeSQL(sql);
		eu.executeSQL("ALTER TABLE researchdb.d122 ADD id INT NOT NULL AUTO_INCREMENT primary key FIRST");
		
		String[] countriesArray = {"CN", "FR", "US", "GB", "RU"};
		int[] yearsArray = {2019, 2020, 2021};
		
		for (String c : countriesArray) {
			for (int y : yearsArray) {
				ResultSet rset = null;
				try {
					String querySQL = "SELECT * FROM researchdb.d122 WHERE country = '" + c + "' AND year = " + y;
					rset = cst.stmt.executeQuery(querySQL);
					System.out.println("query done");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				try {
					if (!rset.next()) {
						String insertZeroSQL = "INSERT INTO researchdb.d122(country, year, count, standardizedCount) "
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
		
		System.out.println("D-122计算完毕");
		cst.close();
		dc.closeConnection();
	}

}
