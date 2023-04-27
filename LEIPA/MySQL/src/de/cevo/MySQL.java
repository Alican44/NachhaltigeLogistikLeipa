package de.cevo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQL {
	
	private static final String host = "localhost";
	private static final String port = "3306";
	private static final String	database = "mysql-test";
	private static final String	username = "root";
	private static final String	password = "";
	
	private static Connection con;
	
	public static boolean isConnected() {
		return (con == null ? false : true);
	}
	
	public static void connect() throws ClassNotFoundException {
		
	
		if(!isConnected()) {
			
			try {
				
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
			System.out.println("MySQL verbunden!");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void disconnect() {
		if (isConnected()) {
			try {
				con.close();
			System.out.println("MySQL-Verbindung geschlossen!");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void update(String qry) {
		try {
		PreparedStatement ps = con.prepareStatement(qry);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public static void getAll() {
		try {
			String query = "SELECT * FROM user";
			PreparedStatement st = con.prepareStatement(query);
			ResultSet rs = st.executeQuery(query);


		        String firstName = rs.getString("Name");
		        Integer age = rs.getInt("Alter");
		        
		     
		       System.out.println("Name : " + firstName + " Alter: " + age);
		      st.close();
		      
	}  catch (Exception e)
	    {
	      System.err.println("Got an exception! ");
	      System.err.println(e.getMessage());
	    }
	
	

}
}
