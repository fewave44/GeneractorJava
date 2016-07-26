package com.fewave.util.base;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.fewave.util.mapping.Table;
import com.fewave.util.mapping.Variable;
public class OracleManage {
	
	String[] strTables = {"BAS_KEYS"};
	
	public List<Table> getTable(){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Table> tables = new ArrayList<>();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");//实例化oracle数据库驱动程序(建立中间件)
			String url = "jdbc:oracle:thin:@localhost:1521:orcl";//@localhost为服务器名，sjzwish为数据库实例名
			conn = DriverManager.getConnection(url, "wzcls", "wzcls");//连接数据库，a代表帐户,a代表密码
			stmt = conn.createStatement();//提交sql语句,创建一个Statement对象来将SQL语句发送到数据库
			String sql = "";
			for (int i = 0; i < strTables.length; i++) {
				//查询数据用executeQuery
				sql = "select t.COLUMN_NAME,t.DATA_TYPE,t.DATA_LENGTH,t.DATA_PRECISION,t.NULLABLE,t.COLUMN_ID,c.COMMENTS" +
								" from user_tab_columns t, user_col_comments c" +
								" where t.table_name = c.table_name" +
								" and t.column_name = c.column_name" +
								" and t.table_name = '" + strTables[0] + "'";
				rs = stmt.executeQuery(sql);//执行查询,(ruby)为表名
				Table table = new Table();
				table.setTableName(strTables[0]);
				List<Variable> tableInfo = new ArrayList<>(); 
				while (rs.next()) {//使当前记录指针定位到记录集的第一条记录
					Variable variable = new Variable();
					variable.setPropertyName(rs.getString("COLUMN_NAME"));
					variable.setType(rs.getString("DATA_TYPE"));
					variable.setTypeLength(rs.getString("DATA_LENGTH"));
					variable.setComment(rs.getString("COMMENTS"));
					variable.setColumnId(rs.getString("COLUMN_ID"));
					variable.setNullAble(rs.getString("NULLABLE"));
					tableInfo.add(variable);
				} 
				
				table.setTableInfo(tableInfo);
				tables.add(table);
				//添加数据用executeUpdate
				//stmt.executeUpdate("insert into ss values(7,'张学友')");
	
				//修改数据用executeUpdate
				//stmt.executeUpdate("update ss set name = '张曼玉' where id = 5");
	
				//删除 数据用executeUpdate
				//stmt.executeUpdate("delete from ss where id = 6");														
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				//关闭数据库，结束进程
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return tables;
	}
	
}

