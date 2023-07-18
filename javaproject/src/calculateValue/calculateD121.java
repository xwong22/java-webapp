package calculateValue;

import java.sql.ResultSet;
import java.sql.SQLException;

import createStmt.CreateStatement;
import dbConnection.JDBCConnection;
import executeUpdate.SQLUpdate;

public class calculateD121 {
	
	public void calculateAndCreateTable() {
		String sql = "CREATE TABLE D121 AS "
				+ "SELECT \r\n"
				+ "    country, \r\n"
				+ "    applicationYear AS year, \r\n"
				+ "    count(DISTINCT publicationID) AS count,\r\n"
				+ "    ( ((COUNT(DISTINCT publicationID) - MIN(COUNT(DISTINCT publicationID)) OVER (PARTITION BY applicationYear)) / (MAX(COUNT(DISTINCT publicationID)) OVER (PARTITION BY applicationYear) - MIN(COUNT(DISTINCT publicationID)) OVER (PARTITION BY applicationYear))) * 100 ) AS standardizedCount \r\n"
				+ "FROM researchDB.Patents \r\n"
				+ "GROUP BY country, applicationYear \r\n"
				+ "HAVING applicationYear IN (2019, 2020, 2021)\r\n"
				+ "    AND country IN ('CN', 'GB', 'US', 'FR', 'RU')";
		
		// 连接数据库
		JDBCConnection dc = new JDBCConnection();
		CreateStatement cst = new CreateStatement(dc);
		SQLUpdate eu = new SQLUpdate(cst.stmt);
		
		eu.executeSQL(sql);
		eu.executeSQL("ALTER TABLE researchdb.d121 ADD id INT NOT NULL AUTO_INCREMENT primary key FIRST");
		
		String[] countriesArray = {"CN", "FR", "US", "GB", "RU"};
		int[] yearsArray = {2019, 2020, 2021};
		
		for (String c : countriesArray) {
			for (int y : yearsArray) {
				ResultSet rset = null;
				try {
					String querySQL = "SELECT * FROM researchdb.d121 WHERE country = '" + c + "' AND year = " + y;
					rset = cst.stmt.executeQuery(querySQL);
					System.out.println("query done");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				try {
					if (!rset.next()) {
						String insertZeroSQL = "INSERT INTO researchdb.d121(country, year, count, standardizedCount) "
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
		
		System.out.println("D-121计算完毕");
		cst.close();
		dc.closeConnection();
	}

}
