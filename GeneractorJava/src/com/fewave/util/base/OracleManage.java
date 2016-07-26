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
			Class.forName("oracle.jdbc.driver.OracleDriver");//ʵ����oracle���ݿ���������(�����м��)
			String url = "jdbc:oracle:thin:@localhost:1521:orcl";//@localhostΪ����������sjzwishΪ���ݿ�ʵ����
			conn = DriverManager.getConnection(url, "wzcls", "wzcls");//�������ݿ⣬a�����ʻ�,a��������
			stmt = conn.createStatement();//�ύsql���,����һ��Statement��������SQL��䷢�͵����ݿ�
			String sql = "";
			for (int i = 0; i < strTables.length; i++) {
				//��ѯ������executeQuery
				sql = "select t.COLUMN_NAME,t.DATA_TYPE,t.DATA_LENGTH,t.DATA_PRECISION,t.NULLABLE,t.COLUMN_ID,c.COMMENTS" +
								" from user_tab_columns t, user_col_comments c" +
								" where t.table_name = c.table_name" +
								" and t.column_name = c.column_name" +
								" and t.table_name = '" + strTables[0] + "'";
				rs = stmt.executeQuery(sql);//ִ�в�ѯ,(ruby)Ϊ����
				Table table = new Table();
				table.setTableName(strTables[0]);
				List<Variable> tableInfo = new ArrayList<>(); 
				while (rs.next()) {//ʹ��ǰ��¼ָ�붨λ����¼���ĵ�һ����¼
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
				//���������executeUpdate
				//stmt.executeUpdate("insert into ss values(7,'��ѧ��')");
	
				//�޸�������executeUpdate
				//stmt.executeUpdate("update ss set name = '������' where id = 5");
	
				//ɾ�� ������executeUpdate
				//stmt.executeUpdate("delete from ss where id = 6");														
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				//�ر����ݿ⣬��������
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

