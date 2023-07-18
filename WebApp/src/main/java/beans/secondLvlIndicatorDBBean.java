package beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class secondLvlIndicatorDBBean {
	
	// 存储连接数据库所需要的资料
	private String URL = "jdbc:mysql://localhost:3307/researchDB";
	private String USER = "root";
	private String PASSWORD = "1234";
	
	// 连接数据库所需
	Connection conn = null;
	Statement stmt = null;
	ResultSet rset = null;
	
	// 准备好要返回的结果的变量
	secondLvlIndicatorBean result = new secondLvlIndicatorBean();
	secondLvlIndicatorCountryBean resultByCountry = new secondLvlIndicatorCountryBean();
	
	// 空的构造方法
	public secondLvlIndicatorDBBean() {
		
	}
	
	// 接收指标名称的构造方法：接收一个指标名称，把该指标所有的数据存储在结果中
	public secondLvlIndicatorDBBean(String indicatorName) {
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					   ResultSet.CONCUR_READ_ONLY);
			
			rset = stmt.executeQuery("SELECT * FROM researchdb." + indicatorName);
			
			if (rset == null || !rset.next()) {
				result = null;
				return;
			}
			
			do {
				secondLvlIndicator temp = new secondLvlIndicator();
				temp.setId((rset.getInt("id")));
				temp.setCountry((rset.getString("country")));
				temp.setYear((rset.getInt("year")));
				temp.setCount((rset.getLong("count")));
				temp.setStandardizedCount((rset.getBigDecimal("standardizedCount")));
				result.add(temp);
			} while (rset.next());
			
			result.setIndicatorName(indicatorName);
			
			
		} catch (SQLException e){
			e.printStackTrace();
			
		} finally {
			try {
				if (rset != null) {
					rset.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("关闭连接时出现异常");
				e.printStackTrace();
			}
		}
	}
	
	// 接收指标名称和国家列表的构造方法：用以查询所有相关国家的指标数据（主要用于可视化）
	public secondLvlIndicatorDBBean(String indicatorName, String[] countriesArray) {
		try {
			Map<String, String> countryMapping = new HashMap<>();
			countryMapping.put("China", "CN");
			countryMapping.put("USA", "US");
			countryMapping.put("England", "GB");
			countryMapping.put("France", "FR");
			countryMapping.put("Russia", "RU");
			
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					   ResultSet.CONCUR_READ_ONLY);
			
			// 循环每个国家，获取每个国家的查询结果
			for (String country: countriesArray) {
				
				secondLvlIndicatorBean tempBean = new secondLvlIndicatorBean();
				
				if ((indicatorName.equals("d121")) || (indicatorName.equals("d122"))) {
					country = countryMapping.get(country);
				}
				
				rset = stmt.executeQuery("SELECT * FROM researchdb." + indicatorName
						+ " WHERE country = '" + country + "'");
				
				if (rset == null || !rset.next()) {
					continue;
				}
				
				do {
					secondLvlIndicator temp = new secondLvlIndicator();
					temp.setId((rset.getInt("id")));
					temp.setCountry((rset.getString("country")));
					temp.setYear((rset.getInt("year")));
					temp.setCount((rset.getLong("count")));
					temp.setStandardizedCount((rset.getBigDecimal("standardizedCount")));
					tempBean.add(temp);
				} while (rset.next());
				tempBean.setIndicatorName(indicatorName);
				tempBean.setSearchCondition(country);
				resultByCountry.add(tempBean);
				
			}
			resultByCountry.setQueryCountry(countriesArray);
			
		} catch (SQLException e){
			e.printStackTrace();
			
		} finally {
			try {
				if (rset != null) {
					rset.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("关闭连接时出现异常");
				e.printStackTrace();
			}
		}
	}
	
	// 接收指标名称和要修改的记录的id：调取该id的数据，返回到jsp展示当前数据供用户修改
	public secondLvlIndicatorDBBean(String indicatorName, int updateId) {
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					   ResultSet.CONCUR_READ_ONLY);
			
			rset = stmt.executeQuery("SELECT * FROM researchdb." + indicatorName + " WHERE id =" + updateId);
			
			if (rset == null || !rset.next()) {
				result = null;
				return;
			}
			
			do {
				secondLvlIndicator temp = new secondLvlIndicator();
				temp.setId((rset.getInt("id")));
				temp.setCountry((rset.getString("country")));
				temp.setYear((rset.getInt("year")));
				temp.setCount((rset.getLong("count")));
				temp.setStandardizedCount((rset.getBigDecimal("standardizedCount")));
				result.add(temp);
			} while (rset.next());
			
			result.setIndicatorName(indicatorName);
			
			
		} catch (SQLException e){
			e.printStackTrace();
			
		} finally {
			try {
				if (rset != null) {
					rset.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("关闭连接时出现异常");
				e.printStackTrace();
			}
		}
	}
	
	// 返回查询结果
	public secondLvlIndicatorBean getResult() {
		return result;
	}
	
	// 返回查询结果（相关国家的）
	public secondLvlIndicatorCountryBean getResultByCountry() {
		return resultByCountry;
	}
	
	// 方法：根据id删除指标
	public void deleteById(String indicatorName, int id) {
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					   ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("删除 "+ id + " FROM " + indicatorName);
		try {
			stmt.executeUpdate("DELETE FROM researchdb." + indicatorName + " WHERE id = " + id);
		} catch (SQLException e) {
			System.out.println("删除失败");
			e.printStackTrace();
		}
	}
	
	// 方法：新增指标
	public void insert(String indicatorName, String country, int year, int count) {
		
		Connection conn2 = null;
		Statement stmt2 = null;
		Connection conn3 = null;
		Statement stmt3 = null;
		
		ResultSet rsetMin = null;
		ResultSet rsetMax = null;
		int minCount = 0;
		int maxCount = 999999999;
		
		// 调取数据库中的min count
		try {
			conn2 = DriverManager.getConnection(URL, USER, PASSWORD);
			stmt2 = conn2.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					   ResultSet.CONCUR_READ_ONLY);
			rsetMin = stmt2.executeQuery("SELECT MIN(count) FROM researchdb.d111 WHERE year=" + year);
			rsetMin.first();
			do {
				minCount = rsetMin.getInt("MIN(count)");
				System.out.println("minCount:"+ minCount);
			} while (rsetMin.next());
			
		} catch (SQLException e1) {
			System.out.println("导出min(count)失败");
			e1.printStackTrace();
		} 
		
		// 调取数据库中的max count
		try {
			conn3 = DriverManager.getConnection(URL, USER, PASSWORD);
			stmt3 = conn3.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					   ResultSet.CONCUR_READ_ONLY);
			rsetMax = stmt3.executeQuery("SELECT MAX(count) FROM researchdb.d111 WHERE year=" + year);
			rsetMax.first();
			do {
				maxCount = rsetMax.getInt("MAX(count)");
				System.out.println("maxCount:"+ maxCount);
			} while (rsetMax.next());
			
		} catch (SQLException e1) {
			System.out.println("导出max(count)失败");
			e1.printStackTrace();
		} 
		
		
		// 判断新的数值是不是新的min或新的max
		if (count < minCount) {
			minCount = count;
		}
		if (count > maxCount) {
			maxCount = count;
		}
		
		// 计算minmax归一化后的数值
		double standardizedCount = ((double) (count - minCount) / (double) (maxCount - minCount)) * 100;
//		BigDecimal standardizedCount = new BigDecimal("0.0");
		
		System.out.println("新增 "+ country + ", " + year + ", " + count + ", " + standardizedCount);
		
		// 插入新记录
		try {
			stmt2.executeUpdate("INSERT INTO researchdb." + indicatorName + "(country, year, count, standardizedCount) "
					+ "VALUES('" + country 
					+ "'," 
					+ year 
					+ ","
					+ count
					+ ","
					+ standardizedCount
					+ ")");
			
		} catch (SQLException e) {
			System.out.println("添加指标失败");
			e.printStackTrace();
		}
	}
	
	// 修改：根据id修改指标
	public void updateById(String indicatorName, int updateId, String country, int year, int count) {
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					   ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("修改后： "+ country + ", " + year + ", " + count);

		try {
			stmt.executeUpdate("UPDATE researchdb." + indicatorName 
					+ " SET country = '" + country 
					+ "', year = " + year 
					+ ", count = " + count 
					+ " WHERE id = " + updateId);
			
		} catch (SQLException e) {
			System.out.println("修改指标失败");
			e.printStackTrace();
		}
		
	}

}
