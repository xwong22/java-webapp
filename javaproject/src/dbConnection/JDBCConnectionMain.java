/*
 *  测试连接数据库
 */


package dbConnection;

public class JDBCConnectionMain {

	public static void main(String[] args) {		
		// 连接数据库
		JDBCConnection dbConnection = new JDBCConnection();
		dbConnection.closeConnection();

	}

}
