package executeUpdate;

import createStmt.CreateStatement;
import dbConnection.JDBCConnection;

public class SQLUpdateMain {

	public static void main(String[] args) {
		JDBCConnection dc = new JDBCConnection();
		CreateStatement cst = new CreateStatement(dc);
		SQLUpdate eu = new SQLUpdate(cst.stmt);
		cst.close();
		dc.closeConnection();
	}

}
