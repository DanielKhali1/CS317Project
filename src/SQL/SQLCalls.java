//STEP 1. Import required packages
package SQL;

import java.sql.*;


public class SQLCalls {
   // JDBC driver name and database URL
	public  String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   public  String DB_URL = "jdbc:mysql://127.0.0.1:3306/?user=root";

   //  Database credentials
   static  String USER = "dkhalil";
   static  String PASS = "0234089";
   
   String curPlayer = null;
   Connection conn = null;
   Statement stmt = null;
   
   
   public static void main(String[] args) 
   {
	   SQLCalls s = new SQLCalls("mysql.us.cloudlogin.co", "3306");
	   
   }
   
   public SQLCalls(String host, String port) 
   {
        try{
        	
        	DB_URL = "jdbc:mysql://" + host + ":" + port  + "/dkhalil_cs317";
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
           // String sql;
            //sql = "SELECT id, first, last, age FROM Employees";
           // final ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
//            while(rs.next()){
//                //Retrieve by column name
//                final int id  = rs.getInt("id");
//                final int age = rs.getInt("age");
//                final String first = rs.getString("first");
//                final String last = rs.getString("last");
//
//                //Display values
//                System.out.print("ID: " + id);
//                System.out.print(", Age: " + age);
//                System.out.print(", First: " + first);
//                System.out.println(", Last: " + last);
//            }
//            //STEP 6: Clean-up environment
//            rs.close();
            stmt.close();
            conn.close();
        }catch(final SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(final Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(final SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(final SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
    System.out.println("Goodbye!");
    }//end main

    //Time format for gameplay stats (maybe might need to use if we change from double to time stats, shouldn't be a big deal to change in the code)
    //DateTimeFormatter gameplayFormat = DateTimeFormatter.ofPattern("HH:mm:ss"); 

    //Player Entity Queries
    int getTotalKills(String curPlayer)  throws Exception{
        int total_kills;
        System.out.println("Creating statement...");
        stmt = conn.createStatement();
        String sql;
        sql = "SELECT Kills FROM Player WHERE Username = " + curPlayer;
        ResultSet rs = stmt.executeQuery(sql);
        total_kills = rs.getInt("Kills");
        return total_kills;
    }

    int getTotalDeaths(String curPlayer)  throws Exception{
        int total_deaths;
        System.out.println("Creating statement...");
        stmt = conn.createStatement();
        String sql;
        sql = "SELECT Deaths FROM Player WHERE Username = " + curPlayer;
        ResultSet rs = stmt.executeQuery(sql);
        total_deaths = rs.getInt("Deaths");
        return total_deaths;
    }

    String getDisplayName(String curPlayer) throws Exception{
        String display_name = null;
        System.out.println("Creating statement...");
        stmt = conn.createStatement();
        String sql;
        sql = "SELECT displayName FROM Player WHERE Username = " + curPlayer;
        ResultSet rs = stmt.executeQuery(sql);
        display_name = rs.getString("displayName");
        return display_name;
    }

    //Account Entity Queries

    //only use for login, then save locally while logged in for use in other queries
    String getPassword(String username, String Password)  throws Exception{
         String pass_hash = null;
         stmt = conn.createStatement();
         String sql;
         sql = "SELECT Password FROM Account WHERE Username = " + username;
         ResultSet rs = stmt.executeQuery(sql);
         pass_hash = rs.getString(Password);
         return pass_hash;
    }

    Boolean getAdminStatus(String username)  throws Exception{
        Boolean is_admin = false;
        stmt = conn.createStatement();
        String sql;
        sql = "SELECT Admin FROM Account WHERE Username = " + username;
        ResultSet rs = stmt.executeQuery(sql);
        is_admin = rs.getBoolean("Admin");
        return is_admin;
    }

    //TimeStats Entity Queries
    Double getAvgLifespan(String curPlayer)  throws Exception{
        Double avg_lifespan = null;
        System.out.println("Creating statement...");
        stmt = conn.createStatement();
        String sql;
        sql = "SELECT avgLifespan FROM TimeStats WHERE Username = " + curPlayer;
        ResultSet rs = stmt.executeQuery(sql);
        avg_lifespan = rs.getDouble("avgLifespan");
        return avg_lifespan;
    }


    Double getTotalTimePlayed(String curPlayer)  throws Exception{
        Double total_time = null;
        System.out.println("Creating statement...");
        stmt = conn.createStatement();
        String sql;
        sql = "SELECT totalPlayTime FROM TimeStats WHERE Username = " + curPlayer;
        ResultSet rs = stmt.executeQuery(sql);
        total_time = rs.getDouble("totalPlayTime");
        return total_time;
    }

    Double getDaysPlayed(String curPlayer)  throws Exception{
        Double days = null;
        System.out.println("Creating statement...");
        stmt = conn.createStatement();
        String sql;
        sql = "SELECT daysPlayed FROM TimeStats WHERE Username = " + curPlayer;
        ResultSet rs = stmt.executeQuery(sql);
        days = rs.getDouble("daysPlayed");
        return days;
    }

    Double getAvgMatchLength(String curPlayer)  throws Exception{
        Double avgMatchLength = null;
        System.out.println("Creating statement...");
        stmt = conn.createStatement();
        String sql;
        sql = "SELECT avgMatchLength FROM TimeStats WHERE Username = " + curPlayer;
        ResultSet rs = stmt.executeQuery(sql);
        avgMatchLength = rs.getDouble("avgMatchLength");
        return avgMatchLength;
    }
    
}//end FirstExample