package com.flx.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcUtil {

	static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/restaurant";
	static final String USER = "root";
	static final String PASS = "LOUlou1107";
	
   private static JdbcUtil jdbcUtil = null;
   
   private JdbcUtil(){
	   
   }
   
   public static JdbcUtil getInstance(){
	   if(jdbcUtil==null){
		   jdbcUtil = new JdbcUtil();
	   }
	   
	   return jdbcUtil;
   }
	
   static{
	    try {
		  Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
   }	
   
   /**
    * 
   * @Title: getConnection  
   * @Description: 获取数据库连接
   * @param:  
   * @return: Connection
   * @throws  
    */
   public Connection getConnection() throws SQLException{
	   return DriverManager.getConnection(DB_URL,USER,PASS);
   }
   
   /**
    * 
   * @Title: close  
   * @Description: 关闭数据库连接释放资源
   * @param:  
   * @return: void
   * @throws  
    */
   public void close(ResultSet resultSet,Statement statement,Connection connection){
	   try {
		   if(null!=resultSet){
		     resultSet.close();
		   }
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(null!=statement){
					statement.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}finally{
				try {
					if(null!=connection){
						connection.close();
					}
				} catch (Exception e3) {
					e3.printStackTrace();
				}
			}
		}
	   
   }
	

}
