/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andreicristea
 * @param <DataType>
 */
public abstract class Database<DataType> {
    protected final String url;
    protected final String name;
    protected final String tableName;
    protected Connection connection; 
    protected List<DataType> data;
    
    
    protected Database(String url, String name, String tableName) {
        this.url = url;
        this.name = name;
        this.tableName = tableName;
        data = new ArrayList<>();
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            System.out.println("Connection: " + url + '/' + name);
            this.connection = DriverManager.getConnection(url + '/' + name);
        } catch (SQLException ex) {
            System.out.println("Connection failed to establish. Please try again later");
            System.out.println("SQLException:" + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("org.apache.derby.jdbc.EmbeddedDriver not found");
            System.out.println(ex.getMessage());
        }
        
        createTable();
        loadData();
    }
    
    
    public List<DataType> getData() {
        return data;
    }
    
    protected void loadData(FunctionResultDatabase<DataType> call, String sql) {
       try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                DataType d = call.createData(rs);
                data.add(d);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Levels.class.getName()).log(Level.INFO, null, ex);
        }
    }
    
    protected boolean createTable(String sql) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            Logger.getLogger(Levels.class.getName()).log(Level.FINE, "Table {0} is created", tableName);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Levels.class.getName()).log(Level.INFO, null, ex);
            return false;
        }
    }
    
    protected int storeData(DataType data, String sql) {
        try (Statement stmt = connection.createStatement()){
            return stmt.executeUpdate(sql);
        } catch (Exception ex){
            Logger.getLogger(Levels.class.getName()).log(Level.INFO, null, ex);
        }
        return 0;
    }
    
    protected abstract void loadData();
    protected abstract boolean createTable();
}
