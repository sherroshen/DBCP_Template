package test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;
import org.omg.Messaging.SyncScopeHelper;

import util.DBUtil;

public class test {

	/*
	 * 测试DBUtil
	 */
	@Test
	public void test1(){
		System.out.println(1);
		Connection conn = null;
		System.out.println(2);
		try {
			System.out.println(3);
			conn = DBUtil.getConnection();
			System.out.println(4);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(conn);
		}
	}	
	
	/*
	 * 测试动态拼接
	 */
	@Test
	public void test2(){
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			Statement smt = conn.createStatement();
			String sql = "UPDATE emp SET name ='sherro' WHERE num = 88";
			int rows = smt.executeUpdate(sql);
			System.out.println("执行成功了"+rows+"行");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("执行出错",e);
		}finally{
			DBUtil.close(conn);
		}
	}
	
	/*
	 *使用PrepareStatement执行更新语句
	 */
	@Test
	public void test3(){
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
//			String sql1 = "Update emp SET name = 'henry wang' WHERE num = ?";
//			PreparedStatement ps = conn.prepareStatement(sql1);
			
			String sql2 = "Update emp SET num = ? WHERE name = ?";
//			ps.setInt(1, 67);
			
			PreparedStatement ps = conn.prepareStatement(sql2);
			ps.setInt(1, 77);
			ps.setString(2, "rico");
			int row = ps.executeUpdate();
			System.out.println("执行了："+row+"行");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(conn);
		}
	}
	
	/*
	 * 使用PreparedStatement和ResultSet搭配使用
	 */
	@Test
	public void test4(){
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT * FROM emp WHERE name = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, "sherro");
			boolean flag = ps.execute();
			System.out.println("是否执行成功:"+flag);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				System.out.println(rs);
				System.out.println(rs.getString(1));
				System.out.println(rs.getString(2));
				System.out.println(rs.getInt(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(conn);
		}
	}
	
	/*
	 * 使用PreparedStatement模拟登陆
	 */
	@Test
	public void test5(){
		Connection conn = null;
		try {
			String name ="sherro";
			String pwd = "a OR 8 =8";
			conn = DBUtil.getConnection();
			String sql = "SELECT * FROM emp WHERE name = ? AND age = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, pwd);
			
			ResultSet rs = ps.executeQuery();
			
			boolean flag = ps.execute();
			if(flag == true){
				System.out.println("执行成功!");
			}
			if(rs.next()){
				System.out.println("登陆成功!");
				System.out.println(rs.getString(1));
				System.out.println(rs.getString(2));
				System.out.println(rs.getInt(3));
			}else{
				System.out.println("登陆失败!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(conn);
		}
	}
	
	/*
	 * 结果集元数据
	 */
	@Test
	public void test6(){
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			Statement smt = conn.createStatement();
			String sql = "SELECT * FROM emp";
			ResultSet rs = smt.executeQuery(sql);
			
			ResultSetMetaData md = rs.getMetaData();
			System.out.println(md.getColumnCount());        //获取列数
			System.out.println(md.getColumnTypeName(2));    //获取2列的类型
			System.out.println(md.getColumnDisplaySize(2)); //获取2列的长度
			System.out.println(md.getColumnName(2));        //获取2列的列名
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(conn);
		}
	}

}
