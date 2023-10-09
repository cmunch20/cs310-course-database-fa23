package edu.jsu.mcis.cs310.coursedb.dao;

import com.github.cliftonlabs.json_simple.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class RegistrationDAO {
    
    private static final String QUERY = "INSERT INTO registration (studentid, termid, crn) VALUES (?, ?, ?)";
    private static final String QUERY_FIND = "SELECT FROM registration WHERE studentid = ? AND termid = ? AND crn = ? ORDER BY crn";
    
    private final DAOFactory daoFactory;
    
    RegistrationDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    public boolean create(int studentid, int termid, int crn){
        
        boolean result = false;
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        Connection conn = daoFactory.getConnection();
        
        try {
            if (conn.isValid(0)) {
                
            ps = conn.prepareStatement(QUERY);
          
               ps.setInt(1, studentid);
               ps.setInt(2, termid);
               ps.setInt(3, crn);
               
               JsonArray jsonArray = new JsonArray();
                    
               rs = ps.executeQuery();
               int updates = ps.executeUpdate();

               if (updates != 0)
               {
                   result = true;
               }
            }
        }
        
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }

    public boolean delete(int studentid, int termid, int crn) {
        
        boolean result = false;
        
        ResultSet rs = null;
        PreparedStatement ps = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
               ps = conn.prepareStatement("DELETE FROM registration WHERE (studentid, termid, crn) = (?, ?, ?)");
          
               ps.setInt(1, studentid);
               ps.setInt(2, termid);
               ps.setInt(3, crn);
               
               ps.execute();
               rs = ps.getResultSet();
               
               int updates = ps.executeUpdate();

               if (updates > 0)
               {
                   result = true;
               }
                
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {

            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }
    
    public boolean delete(int studentid, int termid) {
        
        boolean result = false;
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                ps = conn.prepareStatement("DELETE FROM registration WHERE studentid = ? AND termid = ?");
          
               ps.setInt(1, studentid);
               ps.setInt(2, termid);
               
               int updates = ps.executeUpdate();
               rs = ps.executeQuery();

               if (updates > 0)
               {
                   result = true;
               }
                
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {

            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }

    public String list(int studentid, int termid) {
        
        String result = "[]";
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
               ps = conn.prepareStatement("Select * FROM registration WHERE id = ? AND termid = ?");
          
               ps.setInt(1, studentid);
               ps.setInt(2, termid);
               
               
               JsonArray jsonArray = new JsonArray();
               ResultSetMetaData metaData = rs.getMetaData();
               int columnNum = metaData.getColumnCount();
               
               while (rs.next())
               {
                    JsonObject jsonObject = new JsonObject();
                    for (int c = 1; c <= columnNum; c++)
                    {
                        String columnName = metaData.getColumnName(c);
                        Object columnValue = rs.getObject(c);
                        jsonObject.put(columnName, columnValue);
                    }
                    
                    jsonArray.add(jsonObject);
               }
               
               result = jsonArray.toString();
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }
    
}
