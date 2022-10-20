package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import gui.util.Utils;
import security.security;

public class db {
	
	private static Connection conexao = null;
	private static String DB_URL;
	private static String DB_NAME;
	private static String DB_USER;
	private static String DB_PASS;
	private static String FULL_URL;
	
	
	public static Connection getConnection() {
		if (conexao == null) {
			try {
				Properties dbdata = loadProperties();
				DB_URL = security.securityShow(dbdata.getProperty("DB_URL"));
				DB_NAME = security.securityShow(dbdata.getProperty("DB_NAME"));
				DB_USER = security.securityShow(dbdata.getProperty("DB_USER"));
				DB_PASS = security.securityShow(dbdata.getProperty("DB_PASS"));
				FULL_URL = "jdbc:mysql://"+DB_URL+":3306/"+DB_NAME+"?allowPublicKeyRetrieval=true";
				
				conexao =  DriverManager.getConnection(FULL_URL, DB_USER, DB_PASS);
			}
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		return conexao;
	}
	
	public static void closeConnection () {
		if (conexao != null) {
			try {
				conexao.close();
			}
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
			
		}
	}
	
	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}	

	private static Properties loadProperties() {
		try (FileInputStream fileconfig = new FileInputStream(Utils.loadFileConfig())){
			Properties prop = new Properties();
			prop.load(fileconfig);
			return prop;
		}
		catch (IOException e) {
			throw new DbException(e.getMessage());
		}
	}

}

