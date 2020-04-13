//STEP 1. Import required packages
package SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLCalls {
	// JDBC driver name and database URL
	public String DB_URL = "jdbc:mysql://localhost:3306/players";

	// Database credentials
	String USER = "root";
	String PASS = "toor";

	String curPlayer = null;
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
	}
	
	public SQLCalls(String host, String port, String database, String User, String Pass)
	{
		this.USER = User;
		this.PASS = Pass;
		DB_URL = "jdbc:mysql://" + host + ":" + port + "/" + database;
	}

	public SQLCalls()
	{
		DB_URL = "jdbc:mysql://localhost:3306/players";
		USER = "root";
		PASS = "toor";
	}

	// Player Entity Queries
	public void addNewUsername(String curPlayer, String ip, String port) throws Exception {
		try {
			DB_URL = "jdbc:mysql://" + ip + ":" + port + "/players";
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Create query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "INSERT INTO player " + "VALUES (" + curPlayer + ", 12, 15)";

			// STEP 5: Save result
			stmt.executeUpdate(sql);

			// STEP 6: Clean-up environment
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
	}

	
	public int getTotalKills(String curPlayer, String ip, String port) throws Exception {
		int total_kills = 0;
		try {

			DB_URL = "jdbc:mysql://" + ip + ":" + port + "/?user=root";
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Create query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT Kills FROM Player WHERE Username = " + curPlayer;

			// STEP 5: Save result
			ResultSet rs = stmt.executeQuery(sql);
			total_kills = rs.getInt("Kills");

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

	public int getTotalDeaths(String curPlayer, String ip, String port) throws Exception {
		int total_deaths = 0;
		try {

			DB_URL = "jdbc:mysql://" + ip + ":" + port + "/?user=root";
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Create query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT Deaths FROM Player WHERE Username = " + curPlayer;

			// STEP 5: Save result
			ResultSet rs = stmt.executeQuery(sql);
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

	public String getDisplayName(String curPlayer, String ip, String port) throws Exception {
		String display_name = null;

		try {

			DB_URL = "jdbc:mysql://" + ip + ":" + port + "/?user=root";
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Create query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT displayName FROM Player WHERE Username = " + curPlayer;
			// STEP 5: Save result
			ResultSet rs = stmt.executeQuery(sql);
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

	// Account Entity Queries

	// only use for login, then save locally while logged in for use in other
	// queries

	public String getPassword(String username, String ip, String port) throws Exception {
		String pass_hash = null;

		try {

			DB_URL = "jdbc:mysql://" + ip + ":" + port + "/?user=root";
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Create query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT Password FROM Account WHERE Username = " + username;
			// STEP 5: Save result
			ResultSet rs = stmt.executeQuery(sql);
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

	public Boolean getAdminStatus(String username, String ip, String port) throws Exception {
		Boolean is_admin = false;
		try {

			DB_URL = "jdbc:mysql://" + ip + ":" + port + "/?user=root";
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Create query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT Admin FROM Account WHERE Username = " + username;
			// STEP 5: Save result
			ResultSet rs = stmt.executeQuery(sql);
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

	// TimeStats Entity Queries

	public Double getTotalTimePlayed(String curPlayer, String ip, String port) throws Exception {
		Double total_time = null;

		try {

			DB_URL = "jdbc:mysql://" + ip + ":" + port + "/?user=root";
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Create query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT totalPlayTime FROM TimeStats WHERE Username = " + curPlayer;
			// STEP 5: Save result
			ResultSet rs = stmt.executeQuery(sql);
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

	public Double getAvgLifespan(String curPlayer, String ip, String port) throws Exception {
		Double avg_lifespan = null;

		try {

			DB_URL = "jdbc:mysql://" + ip + ":" + port + "/?user=root";
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Create query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT avgLifespan FROM TimeStats WHERE Username = " + curPlayer;

			// STEP 5: Save result
			ResultSet rs = stmt.executeQuery(sql);
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

	public Double getDaysPlayed(String curPlayer, String ip, String port) throws Exception {
		Double days = null;

		try {

			DB_URL = "jdbc:mysql://" + ip + ":" + port + "/?user=root";
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Create query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT daysPlayed FROM TimeStats WHERE Username = " + curPlayer;
			// STEP 5: Save result
			ResultSet rs = stmt.executeQuery(sql);
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

	public Double getAvgMatchLength(String curPlayer, String ip, String port) throws Exception {
		Double avgMatchLength = null;

		try {

			DB_URL = "jdbc:mysql://" + ip + ":" + port + "/?user=root";
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Create query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT avgMatchLength FROM TimeStats WHERE Username = " + curPlayer;
			// STEP 5: Save result
			ResultSet rs = stmt.executeQuery(sql);
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

}