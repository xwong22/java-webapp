package createStmt;
import dbConnection.JDBCConnection;

public class CreateStatementMain {

	public static void main(String[] args) {
		JDBCConnection dc = new JDBCConnection();
		CreateStatement cst = new CreateStatement(dc);
		cst.close();
		dc.closeConnection();
	}

}
