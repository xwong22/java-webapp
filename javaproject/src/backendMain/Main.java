	package backendMain;

import java.io.IOException;

import jxl.read.biff.BiffException;

import createStmt.CreateStatement;
import dbConnection.JDBCConnection;
import executeUpdate.SQLUpdate;

import readData.bufferedReader;
import readData.excelReader;

import calculateValue.calculateD11;
import calculateValue.calculateD111;
import calculateValue.calculateD112;
import calculateValue.calculateD12;
import calculateValue.calculateD121;
import calculateValue.calculateD122;

public class Main {

	public static void main(String[] args) throws BiffException, IOException {
		// 连接数据库
		JDBCConnection dc = new JDBCConnection();
		CreateStatement cst = new CreateStatement(dc);
		SQLUpdate eu = new SQLUpdate(cst.stmt);
		
		// 新建Patents table (存储excel中提取的数据）
		eu.executeSQL("CREATE TABLE Patents(patentID int not null auto_increment PRIMARY KEY,"
				+ "publicationID varchar(20),"
				+ "applicationYear int,"
				+ "authorizationYear int,"
				+ "country varchar(10))");
		
		// 新建Journals table （存储txt中提取的数据）
		eu.executeSQL("CREATE TABLE Journals(journalID int not null auto_increment PRIMARY KEY,"
				+ "author varchar(30),"
				+ "publicationYear int,"
				+ "country varchar(30),"
				+ "doi varchar(255))");
		
		// 关闭数据库连接
		cst.close();
		dc.closeConnection();
		
		// 从excel中提取所需数据
		String excelpath = "专利数据.xls";
		excelReader exr = new excelReader();
		exr.readAndInsertDatabase(excelpath);
		
		// 从txt中提取所需数据
		bufferedReader br = new bufferedReader();
		for (int i = 1; i <= 6; i++) {
			String txtpath = "C:/Users/wongx/eclipse-workspace/黄湘榆Java大作业/信息化高被引论文/数据" + i + ".txt";
			br.readAndInsertDatabase(txtpath);
		}
		
		// 计算D-111
		calculateD111 cD111 = new calculateD111();
		cD111.calculateAndCreateTable();
		
		// 计算D-112
		calculateD112 cD112 = new calculateD112();
		cD112.calculateAndCreateTable();
		
		// 计算D-121
		calculateD121 cD121 = new calculateD121();
		cD121.calculateAndCreateTable();
		
		// 计算D-121
		calculateD122 cD122 = new calculateD122();
		cD122.calculateAndCreateTable();
		
		
		// 计算D-11
		calculateD11 cD11 = new calculateD11();
		cD11.calculateAndCreateTable();
		
		// 计算D-12
		calculateD12 cD12 = new calculateD12();
		cD12.calculateAndCreateTable();
	}

}
