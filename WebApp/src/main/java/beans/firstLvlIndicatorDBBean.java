package beans;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class firstLvlIndicatorDBBean {
	
	// 存储连接数据库所需要的资料
	private String URL = "jdbc:mysql://localhost:3307/researchDB";
	private String USER = "root";
	private String PASSWORD = "1234";
	
	// 连接数据库所需
	Connection conn = null;
	Statement stmt = null;
	ResultSet rset = null;
	
	// 准备好要返回的结果的变量
	firstLvlIndicatorBean result = new firstLvlIndicatorBean();
	firstLvlIndicatorCountryBean resultByCountry = new firstLvlIndicatorCountryBean();
	
	// 空的构造方法
	public firstLvlIndicatorDBBean() {
		
	}
	
	// 接收指标名称的构造方法：接收一个指标名称，把该指标所有的数据存储在结果中
	public firstLvlIndicatorDBBean(String indicatorName) {
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
				firstLvlIndicator temp = new firstLvlIndicator();
				temp.setId((rset.getInt("id")));
				temp.setCountry((rset.getString("country")));
				temp.setYear((rset.getInt("year")));
				temp.setScore((rset.getBigDecimal("score")));
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
	public firstLvlIndicatorDBBean(String indicatorName, String[] countriesArray) {
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

				firstLvlIndicatorBean tempBean = new firstLvlIndicatorBean();
				
				// 解决d12的国家名称和d11不统一的问题
				if ((indicatorName.equals("d12"))) {
					country = countryMapping.get(country);
				}
				
				rset = stmt.executeQuery("SELECT * FROM researchdb." + indicatorName
						+ " WHERE country = '" + country + "'");
				
				if (rset == null || !rset.next()) {
					continue;
				}
				
				do {
					firstLvlIndicator temp = new firstLvlIndicator();
					temp.setId((rset.getInt("id")));
					temp.setCountry((rset.getString("country")));
					temp.setYear((rset.getInt("year")));
					temp.setScore((rset.getBigDecimal("score")));
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
	public firstLvlIndicatorDBBean(String indicatorName, int updateId) {
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
				firstLvlIndicator temp = new firstLvlIndicator();
				temp.setId((rset.getInt("id")));
				temp.setCountry((rset.getString("country")));
				temp.setYear((rset.getInt("year")));
				temp.setScore((rset.getBigDecimal("score")));
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
	public firstLvlIndicatorBean getResult() {
		return result;
	}
	
	// 返回查询结果（相关国家的）
	public firstLvlIndicatorCountryBean getResultByCountry() {
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
	public void insert(String indicatorName, String country, int year, BigDecimal score) {
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					   ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("新增 "+ country + ", " + year + ", " + score);
		
		// 插入新记录
		try {
			stmt.executeUpdate("INSERT INTO researchdb." + indicatorName + "(country, year, score) "
					+ "VALUES('" + country 
					+ "'," 
					+ year 
					+ ","
					+ score
					+ ")");
			
		} catch (SQLException e) {
			System.out.println("添加指标失败");
			e.printStackTrace();
		}
	}
	
	// 修改：根据id修改指标
	public void updateById(String indicatorName, int updateId, String country, int year, BigDecimal score) {
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					   ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("修改后： "+ country + ", " + year + ", " + score);

		try {
			stmt.executeUpdate("UPDATE researchdb." + indicatorName 
					+ " SET country = '" + country 
					+ "', year = " + year 
					+ ", score = " + score 
					+ " WHERE id = " + updateId);
			
		} catch (SQLException e) {
			System.out.println("修改指标失败");
			e.printStackTrace();
		}
		
	}
}
