package readData;

import java.io.File;
import java.io.IOException;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import createStmt.CreateStatement;
import dbConnection.JDBCConnection;
import executeUpdate.SQLUpdate;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class excelReader {

	public void readAndInsertDatabase(String filepath) throws BiffException, IOException {
		Workbook wrb = Workbook.getWorkbook(new File(filepath));
		Sheet rs = wrb.getSheet(0);
		
		// regex
		Pattern pattern = Pattern.compile("(?<year>\\A\\d{4})");
		Pattern pattern2 = Pattern.compile(", (?<country>\\w{2})");
		
		int cols = rs.getColumns();
		int rows = rs.getRows();
		
		// 连接数据库
		JDBCConnection dc = new JDBCConnection();
		CreateStatement cst = new CreateStatement(dc);
		SQLUpdate eu = new SQLUpdate(cst.stmt);
		
		
		// 遍历每一行
		for (int i = 1; i < rows; i++) {
			
			// 声明变量
			String publicationID = "";
			int applicationYear = 0;
			int authorizationYear = 0;
			Boolean countryFound = false;
			Set<String> countryList = new HashSet<>();
			
			// 遍历每一列
			for (int j = 0; j < cols; j++) {
				String cell = rs.getCell(j, i).getContents();
				
				// 如果是公开号那列
				if (j==0) {
					publicationID = cell;
				}
				
				// 如果是申请日那列，抽出年份
				if (j==2) {
					Matcher matcher = pattern.matcher(cell);
					if (matcher.find()) {
						applicationYear = Integer.parseInt(matcher.group());
					}
				}
				
				// 如果是授权日那列，抽出年份
				if (j==3) {
					Matcher matcher = pattern.matcher(cell);
					if (matcher.find()) {
						authorizationYear = Integer.parseInt(matcher.group());
					}
				}
				
				// 如果是发明人地址，抽出国家
				if (j==4) {
					Matcher matcher2 = pattern2.matcher(cell);
					while (matcher2.find()) {
						String current = matcher2.group("country");
						if (current.equals("CN") 
								|| current.equals("US") 
								|| current.equals("GB") 
								|| current.equals("FR") 
								|| current.equals("RU")) {
							countryList.add(current);
							countryFound = true;
						}
					}
					if (!countryFound) {
						countryList.add("");
					}
				}
			}
			
			// 存储每行记录
			for (String country : countryList) {
				eu.executeSQL("INSERT INTO Patents(publicationID, applicationYear, authorizationYear, country) "
						+ "VALUES ('" + publicationID + "',"
						+ applicationYear
						+ ","
						+ authorizationYear
						+ ",'"
						+ country
						+ "')");
			}

		}
		System.out.println("excel数据insert完毕");
		cst.close();
		dc.closeConnection();
	}
}
