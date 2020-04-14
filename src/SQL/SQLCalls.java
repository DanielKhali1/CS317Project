//STEP 1. Import required packages
package SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class SQLCalls {
	// JDBC driver name and database URL
	public String DB_URL = "jdbc:mysql://localhost:3306/players";

	// Database credentials
	String USER = "root";
	String PASS = "toor";

	Connection conn = null;
	Statement stmt = null;

	/*
	 * Time format for gameplay stats (maybe might need to use if we change from
	 * double to time stats, shouldn't be a big deal to change in the code)
	 * 
	 * DateTimeFormatter gameplayFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
	 */
	
	public static void main(String[] args) {
		SQLCalls s = new SQLCalls("mysql.us.cloudlogin.co", "3306", "dkhalil_cs317", "dkhalil_cs317", "6d9d6FHkfI");
		try {
//			System.out.println("Total Deaths: " + s.getTotalDeaths("username1"));
//			System.out.println("Total Kills: " + s.getTotalKills("username1"));
//			System.out.println("Display Name: " + s.getDisplayName("username1"));
//			System.out.println("Password: " + s.getPassword("username1"));
//			System.out.println("Admin: " + s.getAdminStatus("username1"));
//			
//			System.out.println("Average Life Span: " + s.getAvgLifespan("username1"));
//			System.out.println("Total Time Played " + s.getTotalTimePlayed("username1"));
//			System.out.println("Amount of Days Played: " + s.getDaysPlayed("username1"));
//			System.out.println("Average Match Length: " + s.getAvgMatchLength("username1"));
			
			s.removeRecord("Danny");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public SQLCalls(String host, String port, String database, String User, String Pass)
	{
		this.USER = User;
		this.PASS = Pass;
		DB_URL = "jdbc:mysql://"+ host + ":" + port +  "/" + database + "?user=" + this.USER + "&password=" + PASS + "&useUnicode=true&characterEncoding=UTF-8";
	}

	public SQLCalls()
	{
		DB_URL = "jdbc:mysql://localhost:3306/players";
		USER = "root";
		PASS = "toor";
	}

	// add new record
	public void newRecord(String username, String password)
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "INSERT INTO Player(Username, displayName, Deaths, Kills) VALUES ('" + username + "','" + username + "','0','0');"; 
			stmt.executeUpdate(sql);
			sql = "INSERT INTO Account(Username, Password, Admin) VALUES ('" + username + "','" + password + "','0');"; 
			stmt.executeUpdate(sql);
			sql = "INSERT INTO TimeStats(Username, avgLifespan, totalPlayTime, daysPlayed, avgMatchLength) VALUES ('" + username + "', '0','0','0','0');";
			stmt.executeUpdate(sql);
			System.out.println("Succssful update");
			stmt.close();
			conn.close();
		} 
		catch (final SQLException se) {se.printStackTrace();}
		catch (final Exception e) {e.printStackTrace();} 
		finally { try { if (stmt != null) stmt.close();} catch (final SQLException se2) {}
		try { if (conn != null) conn.close();} catch (final SQLException se) {se.printStackTrace();}}			
	}

	// remove record
	public void removeRecord(String username)
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "DELETE FROM Player WHERE Username = '" + username + "'"; 
			stmt.executeUpdate(sql);
			sql = "DELETE FROM Account WHERE Username = '" + username + "'"; 
			stmt.executeUpdate(sql);
			sql = "DELETE FROM TimeStats WHERE Username = '" + username + "'"; 
			stmt.executeUpdate(sql);
			System.out.println("Succssful update");
			stmt.close();
			conn.close();
		} 
		catch (final SQLException se) {se.printStackTrace();}
		catch (final Exception e) {e.printStackTrace();} 
		finally { try { if (stmt != null) stmt.close();} catch (final SQLException se2) {}
		try { if (conn != null) conn.close();} catch (final SQLException se) {se.printStackTrace();}}	
	}
	
	//update certain attributes
	public void setTotalKills(String curPlayer, int kills)
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "UPDATE Player SET Kills = " + kills + " WHERE Username = '" + curPlayer + "';";
			stmt.executeUpdate(sql);
			System.out.println("Succssful update");
			stmt.close();
			conn.close();
		} 
		catch (final SQLException se) {se.printStackTrace();}
		catch (final Exception e) {e.printStackTrace();} 
		finally { try { if (stmt != null) stmt.close();} catch (final SQLException se2) {}
		try { if (conn != null) conn.close();} catch (final SQLException se) {se.printStackTrace();}}
	}
	
	public void setTotalDeaths(String curPlayer, int deaths)
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "UPDATE Player SET Deaths = " + deaths + " WHERE Username = '" + curPlayer + "';";
			stmt.executeUpdate(sql);
			System.out.println("Succssful update");
			stmt.close();
			conn.close();
		} 
		catch (final SQLException se) {se.printStackTrace();}
		catch (final Exception e) {e.printStackTrace();} 
		finally { try { if (stmt != null) stmt.close();} catch (final SQLException se2) {}
		try { if (conn != null) conn.close();} catch (final SQLException se) {se.printStackTrace();}}
	}
	
	public void setDisplayName(String curPlayer, String displayName) 
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "UPDATE Player SET displayName = '" + displayName + "' WHERE Username = '" + curPlayer + "';";
			stmt.executeUpdate(sql);
			System.out.println("Succssful update");
			stmt.close();
			conn.close();
		} 
		catch (final SQLException se) {se.printStackTrace();}
		catch (final Exception e) {e.printStackTrace();} 
		finally { try { if (stmt != null) stmt.close();} catch (final SQLException se2) {}
		try { if (conn != null) conn.close();} catch (final SQLException se) {se.printStackTrace();}}
	}
	
	public void setPassword(String curPlayer, String password) 
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "UPDATE Account SET Password = '" + password + "' WHERE Username = '" + curPlayer + "';";
			stmt.executeUpdate(sql);
			System.out.println("Succssful update");
			stmt.close();
			conn.close();
		} 
		catch (final SQLException se) {se.printStackTrace();}
		catch (final Exception e) {e.printStackTrace();} 
		finally { try { if (stmt != null) stmt.close();} catch (final SQLException se2) {}
		try { if (conn != null) conn.close();} catch (final SQLException se) {se.printStackTrace();}}
	}
	
	public void setAdminStatus(String curPlayer, boolean Admin) 
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "UPDATE Account SET Admin = '" +  ( (Admin)? 1 : 0 ) + "' WHERE Username = '" + curPlayer + "';";
			stmt.executeUpdate(sql);
			System.out.println("Succssful update");
			stmt.close();
			conn.close();
		} 
		catch (final SQLException se) {se.printStackTrace();}
		catch (final Exception e) {e.printStackTrace();} 
		finally { try { if (stmt != null) stmt.close();} catch (final SQLException se2) {}
		try { if (conn != null) conn.close();} catch (final SQLException se) {se.printStackTrace();}}		
	}
	
	public void setTotalTimePlayed(String curPlayer, double totalTimePlayed) 
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "UPDATE TimeStats SET totalPlayTime = '" +  totalTimePlayed + "' WHERE Username = '" + curPlayer + "';";
			stmt.executeUpdate(sql);
			System.out.println("Succssful update");
			stmt.close();
			conn.close();
		} 
		catch (final SQLException se) {se.printStackTrace();}
		catch (final Exception e) {e.printStackTrace();} 
		finally { try { if (stmt != null) stmt.close();} catch (final SQLException se2) {}
		try { if (conn != null) conn.close();} catch (final SQLException se) {se.printStackTrace();}}			
	}
	
	public void setAvgLifeSpan(String curPlayer, double avglifespan) 
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "UPDATE TimeStats SET avgLifeSpan = '" +  avglifespan + "' WHERE Username = '" + curPlayer + "';";
			stmt.executeUpdate(sql);
			System.out.println("Succssful update");
			stmt.close();
			conn.close();
		} 
		catch (final SQLException se) {se.printStackTrace();}
		catch (final Exception e) {e.printStackTrace();} 
		finally { try { if (stmt != null) stmt.close();} catch (final SQLException se2) {}
		try { if (conn != null) conn.close();} catch (final SQLException se) {se.printStackTrace();}}			
	}
	
	public void setDaysPlayed(String currPlayer, double daysPlayed) 
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "UPDATE TimeStats SET daysPlayed = '" +  daysPlayed + "' WHERE Username = '" + currPlayer + "';";
			stmt.executeUpdate(sql);
			System.out.println("Succssful update");
			stmt.close();
			conn.close();
		} 
		catch (final SQLException se) {se.printStackTrace();}
		catch (final Exception e) {e.printStackTrace();} 
		finally { try { if (stmt != null) stmt.close();} catch (final SQLException se2) {}
		try { if (conn != null) conn.close();} catch (final SQLException se) {se.printStackTrace();}}
	}
	
	public void setAvgMatchLength(String currPlayer, double avgmatchlength) 
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "UPDATE TimeStats SET avgMatchLength = '" +  avgmatchlength + "' WHERE Username = '" + currPlayer + "';";
			stmt.executeUpdate(sql);
			System.out.println("Succssful update");
			stmt.close();
			conn.close();
		} 
		catch (final SQLException se) {se.printStackTrace();}
		catch (final Exception e) {e.printStackTrace();} 
		finally { try { if (stmt != null) stmt.close();} catch (final SQLException se2) {}
		try { if (conn != null) conn.close();} catch (final SQLException se) {se.printStackTrace();}}		
	}
	
	//select certain attributes
	
	public int getTotalKills(String curPlayer) throws Exception {
		int total_kills = 0;
		try {

			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Create query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT Kills FROM Player WHERE Username = '" + curPlayer + "';";

			// STEP 5: Save result
			ResultSet rs = stmt.executeQuery(sql);
			//total_kills = rs.getInt("Kills");
			rs.next();
			total_kills = rs.getInt(1);
			
			// STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();

		} catch (final SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (final Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (final SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (final SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		return total_kills;
	}

	public int getTotalDeaths(String curPlayer) throws Exception {
		int total_deaths = 0;
		try {

			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Create query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT Deaths FROM Player WHERE Username = '" + curPlayer + "';";

			// STEP 5: Save result
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			total_deaths = rs.getInt("Deaths");

			// STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();

		} catch (final SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (final Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (final SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (final SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try

		return total_deaths;
	}

	public String getDisplayName(String curPlayer) throws Exception {
		String display_name = null;

		try {

			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Create query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT displayName FROM Player WHERE Username = '" + curPlayer +"'";
			
			// STEP 5: Save result
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			display_name = rs.getString("displayName");

			// STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();

		} catch (final SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (final Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (final SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (final SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try

		return display_name;
	}

	public String getPassword(String username) throws Exception {
		String pass_hash = null;

		try {

			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// STEP 3: Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Create query
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT Password FROM Account WHERE Username = '" + username + "';";
			// STEP 5: Save result
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();

			pass_hash = rs.getString("password");

			// STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();

		} catch (final SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (final Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (final SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (final SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try

		return pass_hash;
	}

	public Boolean getAdminStatus(String username) throws Exception {
		Boolean is_admin = false;
		try {
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Create query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT Admin FROM Account WHERE Username = '" + username + "';";
			// STEP 5: Save result
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			is_admin = rs.getBoolean("Admin");

			// STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();

		} catch (final SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (final Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (final SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (final SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try

		return is_admin;
	}

	public Double getTotalTimePlayed(String curPlayer) throws Exception {
		Double total_time = null;

		try {

			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Create query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT totalPlayTime FROM TimeStats WHERE Username = '" + curPlayer + "';";
			// STEP 5: Save result
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			total_time = rs.getDouble("totalPlayTime");
			// STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();

		} catch (final SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (final Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (final SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (final SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try

		return total_time;
	}

	public Double getAvgLifespan(String curPlayer) throws Exception {
		Double avg_lifespan = null;

		try {
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Create query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT avgLifespan FROM TimeStats WHERE Username =  '" + curPlayer + "';";

			// STEP 5: Save result
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			avg_lifespan = rs.getDouble("avgLifespan");
			// STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();

		} catch (final SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (final Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (final SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (final SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try

		return avg_lifespan;
	}

	public Double getDaysPlayed(String curPlayer) throws Exception {
		Double days = null;

		try {

			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Create query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT daysPlayed FROM TimeStats WHERE Username = '" + curPlayer + "';";
			// STEP 5: Save result
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			days = rs.getDouble("daysPlayed");

			// STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();

		} catch (final SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (final Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (final SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (final SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try

		return days;
	}

	public Double getAvgMatchLength(String curPlayer) throws Exception {
		Double avgMatchLength = null;

		try {

			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Create query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT avgMatchLength FROM TimeStats WHERE Username = '" + curPlayer + "';";
			// STEP 5: Save result
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			avgMatchLength = rs.getDouble("avgMatchLength");

			// STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();

		} catch (final SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (final Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (final SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (final SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try

		return avgMatchLength;
	}

	public ArrayList<String> getAllUsernames()
	{
		
		ArrayList<String> users = new ArrayList<String>();

		try {

			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Create query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT Username FROM Player";
			// STEP 5: Save result
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				users.add(rs.getString("Username"));
			}

			// STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();

		} catch (final SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (final Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (final SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (final SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		return users;
	}
	
}