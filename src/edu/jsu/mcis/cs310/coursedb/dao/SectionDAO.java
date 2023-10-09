package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import com.github.cliftonlabs.json_simple.*;

public class SectionDAO {
    
    private static final String QUERY_FIND = "SELECT FROM section WHERE studentid = ? AND termid = ? AND crn = ? ORDER BY crn";
    
    private final DAOFactory daoFactory;
    
    SectionDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    public String find(int termid, String subjectid, String num) {
        
        String result = "[]";
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        JsonArray jsonarray = new JsonArray();
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                ps = conn.prepareStatement(QUERY_FIND);
                ps.setInt(1, termid);
                ps.setString(2, subjectid);
                ps.setString(3, num);
                
                try(ResultSet resultset = ps.executeQuery())
                {
                    while (resultset.next())
                    {
                        JsonObject sectionInfo = new JsonObject();
                        sectionInfo.put("crn", resultset.getInt("crn"));
                        sectionInfo.put("studentid", resultset.getString("studentid"));
                        sectionInfo.put("num", resultset.getString("num"));
                        
                        jsonarray.add(sectionInfo);
                    }
                }
                
                
                result = jsonarray.toJson();
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