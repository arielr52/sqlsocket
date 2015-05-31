package sqlsocket.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

    
/**
 * Connection to the database
 * @author Ariel
 *
 */
public class Sql {

    private Connection con;
    private Statement stmt;
    ResultSet rs;
    static{
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public void open() throws SQLException{
        con = DriverManager.
                getConnection("jdbc:h2:./target/example"
                    ,"sa","sa");
        stmt = con.createStatement();
    }
    
    public void close() throws SQLException{
        rs.close();
        con.close();
    }
    
    public boolean execute(String sql) throws SQLException{
        return stmt.execute(sql);
    }
    
    public void executeQuery(String sql) throws SQLException{
        rs = stmt.executeQuery(sql);
    }
    
    
    public Object[] next() throws SQLException{
        boolean hasNext =rs.next();
        
        Object[] result = new Object[rs.getMetaData().getColumnCount()];
        if(!hasNext){
            return new Object[0];
        }
        for(int i=0;i<result.length;i++){
            result[i]=rs.getObject(i+1);
        }
        return result;
    }
    
    public void test() throws SQLException{
        open();
        executeQuery("select id, name from user");
        while(true){
            Object[] result = next();
            if(result.length==0){
                break;
            }
            System.out.println(result[0]+","+result[1]);
        }
        close();
    }

    public static void main(String a[]){
        Sql sql = new Sql();
        try {
            sql.test();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    
    public static void testSetup(String a[]){
        try {
            Class.forName("org.h2.Driver");
            Connection con = DriverManager.
                getConnection("jdbc:h2:./target/example"
                    ,"sa","sa");
            
            Statement stmt = con.createStatement();
            System.out.println("Created DB Connection....");
            String table = "CREATE TABLE USER (ID INT, NAME VARCHAR(50));";
            //System.out.println(stmt.execute(table));
            stmt.execute("INSERT INTO USER VALUES(1, 'Hello'), (2, 'World');");
            ResultSet rs = stmt.executeQuery("select id, name from user");
            while(rs.next()){
                System.out.println(rs.getString("name"));
                System.out.println(rs.getInt("id"));
            }
            rs.close();
            con.close();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    
}
