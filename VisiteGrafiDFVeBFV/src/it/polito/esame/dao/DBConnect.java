package it.polito.esame.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/* DBConnect realizzata come "singleton" */

public class DBConnect {

	static private final String jdbcUrl = "jdbc:mysql://localhost/parole?user=root";
	static private DBConnect instance = null ;
	
	private DBConnect () {
		instance = this ;
		//System.out.println("instance created") ;
	}
	
	public static DBConnect getInstance() {
		if(instance == null)
			return new DBConnect() ;
		else {
			//System.out.println("instance reused") ;
			return instance ;
		}
	}
	
	public Connection getConnection() {
		try {
			Connection conn = DriverManager.getConnection(jdbcUrl) ;
			return conn ;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Cannot get connection "+jdbcUrl, e) ;
		}	
	}

}
