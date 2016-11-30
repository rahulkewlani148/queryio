package com.queryio.common.util;import java.sql.Connection;import java.sql.DriverManager;import java.sql.PreparedStatement;import java.sql.ResultSet;import java.sql.SQLException;import java.sql.Statement;import com.queryio.common.util.Base64;import com.queryio.common.util.CryptManager;public class ControllerPropertyUpdater {	public static void main(String[] args) throws SQLException,			ClassNotFoundException {		Class.forName("org.hsqldb.jdbcDriver");//		String password = "";		if (args.length < 6) {			System.out.println("Run: java com.appperfect.util.ControllerPropertyUpdater db_url db_user db_password property_name old_value new_value");//		} else if (args.length > 3) {//			password = args[3];//		} else {//			System.out//					.println("Warning, connecting to DB using empty password.");		}		System.out.println("Updating property " + args[3] + " replacing " + args[4] + " with " + args[5]);		updateProperty(args[0], args[1], args[2], args[3], args[4], args[5]);	}	public static void updateProperty(String url,		String username, String password, String propertyName,		String oldValue, String newValue) throws SQLException {				Connection connection = null;		Statement statement = null;		ResultSet rs = null;		ResultSet rs2 = null;		PreparedStatement ps = null;		try {			connection = DriverManager.getConnection(url, username,		 			password);			statement = connection.createStatement();            rs = statement.executeQuery("Select count(*) from DB_VERIFY");            if (rs.next())            {                int rowCount = rs.getInt(1);                        String modifiedNewValue = newValue;                String modifiedOldValue = oldValue;                if(rowCount == 2){                    modifiedOldValue = new String(Base64.decode(oldValue.getBytes()));                    modifiedNewValue = new String(Base64.decode(newValue.getBytes()));                }                rs2 = statement.executeQuery("SELECT * FROM CONTROLLERPROPERTY WHERE PROPERTYNAME='"+propertyName+"'");                ps = connection.prepareStatement("UPDATE CONTROLLERPROPERTY SET PROPERTYVALUE=? WHERE PROPERTYVALUE=? AND PROPERTYNAME='"+propertyName+"' AND CONTROLLERID=?");                while(rs2.next()){                    String controllerId = rs2.getString("CONTROLLERID");                    if(rowCount == 3){                    	CryptManager encryptionhandler = CryptManager.createInstance(controllerId);                        modifiedOldValue = encryptionhandler.encryptData(oldValue);                        modifiedNewValue = encryptionhandler.encryptData(newValue);                    }                    if(rs2.getString("PROPERTYVALUE").equals(modifiedOldValue)){                        ps.setString(1, modifiedNewValue);                        ps.setString(2, modifiedOldValue);                        ps.setString(3, controllerId);                        int count = ps.executeUpdate();                        System.out.println("Monitor: " + controllerId + "'s property " + propertyName + " modified, count: " + count);                    }                }            }            else                System.out.println("Database could not be verified. Abort.");		} catch (Exception ex) {			AppLogger.getLogger().fatal(ex);		}		finally		{		    if (rs != null)		    {		        rs.close();		    }		    if (rs2 != null)            {                rs2.close();            }		    if (ps != null)            {                ps.close();            }		    if (statement != null)            {		        statement.close();            }		    if (connection != null) {		        connection.close();		    }		}	}}