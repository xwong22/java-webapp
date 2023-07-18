package readData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import createStmt.CreateStatement;
import dbConnection.JDBCConnection;
import executeUpdate.SQLUpdate;

public class bufferedReader {
	
	public void readAndInsertDatabase(String filepath) throws IOException{

		FileReader fr = new FileReader(filepath);
		BufferedReader br = new BufferedReader(fr);
		
		String line;
		line = br.readLine();
		String[] title = line.split("\t");
		
		// 找出"RP"和"PY"的index
		int indexRP = Arrays.asList(title).indexOf("RP");
		int indexPY = Arrays.asList(title).indexOf("PY");
		int indexDI = Arrays.asList(title).indexOf("DI");
		
		// 声明变量
		String author = "";
		String country = "";
		String year = "";
		String doi = "";
		
		// regex
		Pattern pattern = Pattern.compile("(\\w+)\\.");
		Pattern pattern2 = Pattern.compile("(\\w+,\\W\\w+)");
		
		// 连接数据库
		JDBCConnection dc = new JDBCConnection();
		CreateStatement cst = new CreateStatement(dc);
		SQLUpdate eu = new SQLUpdate(cst.stmt);
		
		// 遍历每一行
		while ((line = br.readLine()) != null) {
			// 把该行分成列表
			String[] sentence = line.split("\t");
			
			// 获取年份信息
			if (!sentence[indexPY].isEmpty()) {
				year = sentence[indexPY];
			}
			
			// 获取作者信息
			Matcher matcher2 = pattern2.matcher(sentence[indexRP]);
			if (matcher2.find()) {
				author = matcher2.group(1);
			}
			
			// 获取国家信息
			Matcher matcher = pattern.matcher(sentence[indexRP]);
			if (matcher.find()) {
				country = matcher.group(1);
			}
			
			// 获取doi信息
			if (sentence.length > indexDI) {
				doi = sentence[indexDI];
			}
			
			eu.executeSQL("INSERT INTO Journals(author, publicationYear, country, doi) "
					+ "VALUES ('" + author + "',"
					+ year
					+ ",'"
					+ country
					+ "','"
					+ doi
					+ "')");
		}
		
		br.close();
		fr.close();
		
		System.out.println("txt数据insert完毕");
		cst.close();
		dc.closeConnection();
	}

}
