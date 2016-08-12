package calendar.fc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CalendarDAO {

    public CalendarDAO() {
    }
    
    public String insertGame(String tCasa, String tFora, String comp, String date, String title){
        Connection c = null;
        Statement stmt = null;
        String sql = "";
        try {
          Class.forName("org.sqlite.JDBC");
          c = DriverManager.getConnection("jdbc:sqlite:calendar.db");
          c.setAutoCommit(false);
          System.out.println("Opened database successfully");

          stmt = c.createStatement();
          sql = "INSERT INTO Game (t_home, t_away, competition, date, titulo) " +
                       "VALUES ("+tCasa+", "+tFora+", "+comp+", \""+date+"\", \""+title+"\");"; 
            System.out.println(sql);
          stmt.executeUpdate(sql);

          stmt.close();
          c.commit();
          c.close();
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
        return sql;
    }
}
