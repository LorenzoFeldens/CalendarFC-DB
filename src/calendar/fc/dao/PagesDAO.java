package calendar.fc.dao;

import calendar.fc.entidades.Quadro;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class PagesDAO {

    public PagesDAO() {
    }
    
    public ArrayList<Quadro> getAllTimes(){
        Connection c = null;
        Statement stmt = null;
        ArrayList<Quadro> lista = new ArrayList<>();
        try {
          Class.forName("org.sqlite.JDBC");
          c = DriverManager.getConnection("jdbc:sqlite:CalendarFCPages.db");
          c.setAutoCommit(false);

          stmt = c.createStatement();
          ResultSet rs = stmt.executeQuery( "SELECT Time.idcalendar as id, Time.nome as nome, Tipo.nome as tipo, Time.novoNome as novoNome FROM Time JOIN Tipo ON (Time.tipo = Tipo.id) ORDER BY Time.id;" );
          while ( rs.next() ) {
             Quadro q = new Quadro();
             q.setNome(rs.getString("nome"));
             q.setTipo(rs.getString("tipo"));
             q.setIdCalendar(rs.getInt("id"));
             q.setNovoNome(rs.getString("novoNome"));
             
             lista.add(q);
          }
          rs.close();
          stmt.close();
          c.close();
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
        return lista;
    }
    
    public ArrayList<Quadro> getAllCompeticoes(){
        Connection c = null;
        Statement stmt = null;
        ArrayList<Quadro> lista = new ArrayList<>();
        try {
          Class.forName("org.sqlite.JDBC");
          c = DriverManager.getConnection("jdbc:sqlite:CalendarFCPages.db");
          c.setAutoCommit(false);

          stmt = c.createStatement();
          ResultSet rs = stmt.executeQuery( "SELECT Competicao.idcalendar as id, Competicao.nome as nome, Tipo.nome as tipo FROM Competicao JOIN Tipo ON (Competicao.tipo = Tipo.id) ORDER BY Competicao.id;" );
          while ( rs.next() ) {
             Quadro q = new Quadro();
             q.setNome(rs.getString("nome"));
             q.setTipo(rs.getString("tipo"));
             q.setIdCalendar(rs.getInt("id"));
             
             lista.add(q);
          }
          rs.close();
          stmt.close();
          c.close();
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
        return lista;
    }
}
