package util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;


public class DBUtil {
	private static BasicDataSource ds;
	
	static{
		Properties p = new Properties();
		try {
			p.load(DBUtil.class.getClassLoader().getResourceAsStream("db.properties"));
			String url = p.getProperty("url");
			String user = p.getProperty("user");
			String pwd = p.getProperty("pwd");
			String driver = p.getProperty("driver");
			String initsize = p.getProperty("initsize");
			String maxsize = p.getProperty("maxsize");
			
			ds = new BasicDataSource();
			ds.setDriverClassName(driver);
			ds.setUrl(url);
			ds.setUsername(user);
			ds.setPassword(pwd);
			ds.setInitialSize(Integer.parseInt(initsize));
			ds.setMaxActive(Integer.parseInt(maxsize));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("读取配置文件失败",e);
		}
	}
	
	public static Connection getConnection() throws SQLException{
		return ds.getConnection();
	}
	
	public static void close(Connection conn){
			try {
				if(conn != null){
					ds.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
}
